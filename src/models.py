import datetime

from flask_login import UserMixin
from flask_security import RoleMixin

from src import db, login_manager

assoc_table_admin_flight_crew = db.Table('assoc_admin_flight_crew', db.Model.metadata,
                                         db.Column('admin_id', db.BigInteger, db.ForeignKey('admins.id')),
                                         db.Column('flight_brigade_id', db.BigInteger,
                                                   db.ForeignKey('flight_brigades.id')))

assoc_table_admin_dispatch = db.Table('assoc_admin_dispatch', db.Model.metadata,
                                      db.Column('admin_id', db.BigInteger, db.ForeignKey('admins.id')),
                                      db.Column('dispatch_id', db.BigInteger, db.ForeignKey('dispatches.id')))

assoc_table_dispatch_flight_crew = db.Table('assoc_dispatch_flight_crew', db.Model.metadata,
                                            db.Column('dispatch_id', db.BigInteger, db.ForeignKey('dispatches.id')),
                                            db.Column('flight_brigade_id', db.BigInteger,
                                                      db.ForeignKey('flight_brigades.id')))

assoc_table_role_user = db.Table('assoc_role_user', db.Model.metadata,
                                 db.Column('role_id', db.BigInteger, db.ForeignKey('roles.id')),
                                 db.Column('user_id', db.BigInteger, db.ForeignKey('users.id')))


class AddService(db.Model):
    __tablename__ = 'add_services'
    id = db.Column(db.BigInteger, primary_key=True)
    description = db.Column(db.String(255), nullable=False)
    price = db.Column(db.Float(2), nullable=False)
    ticket_id = db.Column(db.BigInteger, db.ForeignKey('tickets.id'))

    def __init__(self, *args, **kwargs):
        super(AddService, self).__init__(self, *args, **kwargs)

    def __repr__(self):
        return f'<Additional Service {self.id}>'


class Administrator(db.Model):
    __tablename__ = 'admins'
    id = db.Column(db.BigInteger, primary_key=True)
    username = db.Column(db.String(32), unique=True, nullable=False)
    password = db.Column(db.String(255), unique=True, nullable=False)
    client = db.relationship('Client')
    flight_crew = db.relationship('FlightBrigade', secondary=assoc_table_admin_flight_crew)
    dispatch = db.relationship('Dispatch', secondary=assoc_table_admin_dispatch)
    flight_id = db.Column(db.BigInteger, db.ForeignKey('flights.id'))

    def __repr__(self):
        return f'<Admin {self.id}>'


class Client(db.Model):
    __tablename__ = 'clients'
    id = db.Column(db.BigInteger, primary_key=True)
    first_name = db.Column(db.String(32), nullable=False)
    last_name = db.Column(db.String(32), nullable=False)
    email = db.Column(db.String(255), unique=True, nullable=False)
    age = db.Column(db.Integer, nullable=False)
    password = db.Column(db.String(255), unique=True, nullable=False)
    ticket = db.relationship('Ticket', uselist=False, back_populates='client')
    admin_id = db.Column(db.BigInteger, db.ForeignKey('admins.id'))

    def __repr__(self):
        return f'<Client {self.id}>'


class Dispatch(db.Model):
    __tablename__ = 'dispatches'
    id = db.Column(db.BigInteger, primary_key=True)
    username = db.Column(db.String(32), unique=True, nullable=False)
    password = db.Column(db.String(255), unique=True, nullable=False)
    flight_crew = db.relationship('FlightBrigade', secondary=assoc_table_dispatch_flight_crew)
    flight_id = db.Column(db.BigInteger, db.ForeignKey('flights.id'))

    def __repr__(self):
        return f'<Dispatch {self.id}>'


class Flight(db.Model):
    __tablename__ = 'flights'
    id = db.Column(db.BigInteger, primary_key=True)
    departure = db.Column(db.String(255), nullable=False)
    destination = db.Column(db.String(255), nullable=False)
    flight_time = db.Column(db.String(6), nullable=False)
    admins = db.relationship('Administrator')
    dispatches = db.relationship('Dispatch')
    flight_crews = db.relationship('FlightBrigade')

    def __repr__(self):
        return f'<Flight {self.id}>'


class FlightBrigade(db.Model):
    __tablename__ = 'flight_brigades'
    id = db.Column(db.BigInteger, primary_key=True)
    username = db.Column(db.String(32), unique=True, nullable=False)
    password = db.Column(db.String(255), unique=True, nullable=False)
    flight_id = db.Column(db.BigInteger, db.ForeignKey('flights.id'))

    def __repr__(self):
        return f'<Flight Brigade {self.id}>'


class Ticket(db.Model):
    __tablename__ = 'tickets'
    id = db.Column(db.BigInteger, primary_key=True)
    destination = db.Column(db.String(32), nullable=False)
    price = db.Column(db.Float(2), nullable=False)
    add_services = db.relationship('AddService')
    client_id = db.Column(db.BigInteger, db.ForeignKey('clients.id'))
    client = db.relationship('Client', back_populates='ticket')

    def __repr__(self):
        return f'<Ticket {self.id}>'


class Message(db.Model):
    __tablename__ = 'messages'
    id = db.Column(db.BigInteger, primary_key=True)
    msg_from = db.Column(db.String(32), nullable=False)
    msg_to = db.Column(db.String(32), nullable=False)
    msg_text = db.Column(db.String(255))
    date_sent = db.Column(db.DateTime, nullable=False, default=datetime.datetime.now().replace(microsecond=0))

    def __repr__(self):
        return f'<Message {self.id}>'


@login_manager.user_loader
def load_user(user_id):
    return User.query.get(int(user_id))


class User(db.Model, UserMixin):
    __tablename__ = 'users'
    id = db.Column(db.BigInteger, primary_key=True)
    login = db.Column(db.String(32), unique=True, nullable=False)
    password = db.Column(db.String(255), unique=True, nullable=False)

    role = db.relationship('Role', secondary=assoc_table_role_user)

    def __repr__(self):
        return f'<User {self.id}>'


class Role(db.Model, RoleMixin):
    __tablename__ = 'roles'
    id = db.Column(db.BigInteger, primary_key=True)
    name = db.Column(db.String(255), unique=True, nullable=False)

    def __repr__(self):
        return f'<Role {self.id}>'
