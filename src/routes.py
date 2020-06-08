import json

import flask_mail
import sqlalchemy
from flask import flash, render_template, redirect, url_for, request, session
from flask_login import login_user, current_user, logout_user, login_required
from sqlalchemy.sql.expression import cast

from src import app, bcrypt, db, mail
from src.extra import admin_required
from src.forms import RegisterForm, LoginForm, RequestResetForm, ResetPasswordForm
from src.models import Flight, Ticket, User, Client, Role, Message, Administrator, FlightBrigade, Dispatch


@app.route('/')
@app.route('/home')
def home():
    return render_template('home.html', label='Home')


@app.route('/about')
def about():
    return render_template('about.html', label='About')


@app.route('/register', methods=['GET', 'POST'])
def register():
    if current_user.is_authenticated:
        return redirect(url_for('home'))
    form = RegisterForm()
    if form.validate_on_submit():
        hashed_password = bcrypt.generate_password_hash(form.password.data).decode('utf-8')
        user = User()
        user.login = form.email.data
        user.password = hashed_password
        user = User()
        user.login = form.email.data
        user.password = hashed_password
        user.role.append(Role.query.filter_by(name='ROLE_USER').first())
        client = Client()
        client.first_name = form.first_name.data
        client.last_name = form.last_name.data
        client.email = form.email.data
        client.age = form.age.data
        client.password = hashed_password
        db.session.add(user)
        db.session.add(client)
        db.session.commit()
        flash(f'Account created for {form.email.data}!', 'success')
        return redirect(url_for('login'))
    return render_template('register.html', label='Register', form=form)


@app.route('/login', methods=['GET', 'POST'])
def login():
    if current_user.is_authenticated:
        return redirect(url_for('home'))
    form = LoginForm()
    if form.validate_on_submit():
        user = User.query.filter_by(login=form.login.data).first()
        if user and bcrypt.check_password_hash(user.password, form.password.data):
            login_user(user, remember=form.remember.data)
            next_page = request.args.get('next')
            return redirect(next_page) if next_page else redirect(url_for('home'))
        else:
            flash('Login unsuccessful. Please check your email and/or password', 'danger')
    return render_template('login.html', label='Login', form=form)


def send_reset_email(user):
    token = user.get_reset_token()
    msg = flask_mail.Message('Password Reset Request', sender='noreply@aviacompany.com', recipients=[user.login])
    msg.body = f'''To reset your password, visit the following link:
    {url_for('reset_token', token=token, _external=True)}
    
    If you did not make this request then simply ignore this email and no changes will be made.
    '''
    mail.send(msg)


@app.route('/reset_password', methods=['GET', 'POST'])
def reset_request():
    if current_user.is_authenticated:
        return redirect(url_for('home'))
    form = RequestResetForm()
    if form.validate_on_submit():
        user = User.query.filter_by(login=form.email.data).first()
        send_reset_email(user)
        flash('An email has been sent with instructions to reset your password.', 'info')
        return redirect(url_for('login'))
    return render_template('request_reset.html', label='Reset Request', form=form)


@app.route('/reset_password/<string:token>', methods=['GET', 'POST'])
def reset_token(token):
    if current_user.is_authenticated:
        return redirect(url_for('home'))
    user = User.verify_reset_token(token)
    if user is None:
        flash('That is an invalid or expired token.', 'warning')
        return redirect(url_for('reset_request'))
    form = ResetPasswordForm()
    if form.validate_on_submit():
        hashed_password = bcrypt.generate_password_hash(form.password.data).decode('utf-8')
        user.password = hashed_password
        db.session.commit()
        flash(f'Your password has been changed!', 'success')
        return redirect(url_for('login'))
    return render_template('reset_token.html', label='Reset Password', form=form)


@app.route('/logout')
def logout():
    logout_user()
    return redirect(url_for('home'))


@app.route('/account')
@login_required
def account():
    client = None
    if current_user.role[0].name == 'ROLE_USER':
        client = Client.query.filter_by(id=current_user.id).first()
    return render_template('account.html', label='Account', client=client)


@app.route('/flights')
@login_required
def flights():
    q = request.args.get('q')
    page = request.args.get('page', 1, type=int)
    if q:
        fl = Flight.query.filter(Flight.departure.contains(q) | Flight.destination.contains(q)
                                 | Flight.flight_time.contains(q)).paginate(page=page, per_page=2)
    else:
        fl = Flight.query.order_by(Flight.id).paginate(page=page, per_page=2)
    return render_template('flight.html', label='Flights', flights=fl)


@app.route('/news')
def news():
    return render_template('news.html', label='News')


@app.route('/track_flight', methods=['GET', 'POST'])
@login_required
def track_flight():
    flight = None
    if request.method == 'POST':
        if request.form.get('id'):
            flight = Flight.query.filter_by(id=request.form['id']).first()
        else:
            flight = None
    return render_template('track_flight.html', label='Track Flight', flight=flight)


@app.route('/tickets')
@login_required
def tickets():
    q = request.args.get('q')
    page = request.args.get('page', 1, type=int)
    if q:
        ti = Ticket.query.filter(Ticket.destination.contains(q) | cast(Ticket.price, sqlalchemy.String).contains(q)) \
            .paginate(page=page, per_page=2)
    else:
        ti = Ticket.query.order_by(Ticket.id).paginate(page=page, per_page=2)
    return render_template('tickets.html', label='Tickets', tickets=ti)


@app.route('/tickets/<int:id>', methods=['GET', 'POST'])
@login_required
def ticket_page(id):
    if request.method == 'POST':
        if session.get('tickets'):
            session['tickets'] = json.loads(session['tickets'])
            session['total'] = json.loads(session['total'])
        else:
            session['tickets'] = []
            session['total'] = []

        new_item = True
        for item in session['tickets']:
            if item == int(request.form['id']):
                new_item = False

        if new_item:
            session['tickets'].append(request.form['id'])
            session['total'].append(request.form['total'])

        session['tickets'] = json.dumps(session['tickets'])
        session['total'] = json.dumps(session['total'])

        return redirect(url_for('order'))

    return render_template('ticket_page.html', label=f'Ticket {id}', ticket=Ticket.query.filter_by(id=id).first())


@app.route('/order', methods=['GET', 'POST'])
@login_required
def order():
    if session.get('tickets'):
        tickets_id = json.loads(session['tickets'])
        total = json.loads(session['total'])
    else:
        tickets_id = []
        total = []

    over_total = 0.0

    for tot in total:
        over_total += float(tot)

    for index, item in enumerate(tickets_id):
        tickets_id[index] = Ticket.query.filter_by(id=item).first()

    if request.method == 'POST':
        if request.form['submit_button'] == 'Remove':
            for index, item in enumerate(tickets_id):
                if item.id == int(request.form['id']):
                    over_total -= float(total[index])
                    total.pop(index)
                    tickets_id.pop(index)

            for index, item in enumerate(tickets_id):
                tickets_id[index] = item.id

            session['tickets'] = json.dumps(tickets_id)
            session['total'] = json.dumps(total)
        elif request.form['submit_button'] == 'Clear All':
            tickets_id.clear()
            total.clear()
            session['tickets'] = json.dumps(tickets_id)
            session['total'] = json.dumps(total)
        elif request.form['submit_button'] == 'Buy':
            client = Client.query.filter_by(id=current_user.id).first()
            client.tickets = tickets_id
            for item in tickets_id:
                item.client_id = current_user.id
            db.session.commit()
            tickets_id.clear()
            total.clear()
            session['tickets'] = json.dumps(tickets_id)
            session['total'] = json.dumps(total)
            return redirect(url_for('thanks'))
        return redirect(url_for('order'))

    return render_template('order.html', label='Order', order=zip(tickets_id, total), over_total=round(over_total, 2))


@app.route('/messenger', methods=['GET', 'POST'])
@login_required
@admin_required
def messenger():
    q = request.args.get('q')
    page = request.args.get('page', 1, type=int)
    if q:
        message = Message.query.filter(Message.msg_to.contains(q) | Message.msg_from.contains(q)
                                       | Message.msg_text.contains(q)
                                       | cast(Message.date_sent, sqlalchemy.String).contains(q)
                                       | cast(Message.id, sqlalchemy.String).contains(q)) \
            .paginate(page=page, per_page=5)
    else:
        message = Message.query.paginate(page=page, per_page=5)
    if request.method == 'POST':
        if request.form['submit_button'] == 'Send':
            admin = Administrator.query.filter_by(username=request.form['msgTo']).first()
            flight_brigade = FlightBrigade.query.filter_by(username=request.form['msgTo']).first()
            dispatch = Dispatch.query.filter_by(username=request.form['msgTo']).first()

            if current_user.login == request.form['msgTo']:
                flash('You cannot send message to yourself', 'danger')
            elif admin is None and flight_brigade is None and dispatch is None:
                flash('This user does not exist', 'danger')
            else:
                message = Message()
                message.msg_from = current_user.login
                message.msg_to = request.form['msgTo']
                message.msg_text = request.form['msgText']
                db.session.add(message)
                db.session.commit()
            message = Message.query.paginate(page=page, per_page=5)
        elif request.form['submit_button'] == 'Show Messages For Me':
            message = Message.query.filter_by(msg_to=current_user.login).paginate(page=page, per_page=5)

    return render_template('messenger.html', label='Messenger', messages=message)


@app.route('/thanks')
@login_required
def thanks():
    return render_template('thanks.html', label='Thanks')
