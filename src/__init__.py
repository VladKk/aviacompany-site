from flask import Flask
from flask_admin import Admin
from flask_bcrypt import Bcrypt
from flask_login import LoginManager
from flask_migrate import Migrate, MigrateCommand
from flask_script import Manager
from flask_sqlalchemy import SQLAlchemy

from src.config import Config

app = Flask(__name__, static_folder='../static', template_folder='../templates')

app.config.from_object(Config)

db = SQLAlchemy(app)
bcrypt = Bcrypt(app)
login_manager = LoginManager(app)

login_manager.login_view = 'login'
login_manager.login_message_category = 'info'

import src.models
from src.extra import AdminIndex, ModelV

admin = Admin(app, index_view=AdminIndex(), template_mode='bootstrap3')

admin.add_view(ModelV(src.models.Flight, db.session))
admin.add_view(ModelV(src.models.Message, db.session))
admin.add_view(ModelV(src.models.AddService, db.session))
admin.add_view(ModelV(src.models.Administrator, db.session))
admin.add_view(ModelV(src.models.Ticket, db.session))
admin.add_view(ModelV(src.models.FlightBrigade, db.session))
admin.add_view(ModelV(src.models.Dispatch, db.session))
admin.add_view(ModelV(src.models.Client, db.session))
admin.add_view(ModelV(src.models.User, db.session))
admin.add_view(ModelV(src.models.Role, db.session))

migrate = Migrate(app, db)
manager = Manager(app)

manager.add_command('db', MigrateCommand)

if __name__ == '__main__':
    manager.run()
    app.run()
