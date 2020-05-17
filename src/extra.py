from functools import wraps

from flask import redirect, url_for, flash
from flask_admin import AdminIndexView
from flask_admin.contrib.sqla import ModelView
from flask_login import current_user


def admin_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if current_user.role[0].name == 'ROLE_ADMIN':
            return f(*args, **kwargs)
        else:
            flash('You need to be an admin to view this page.', 'info')
            return redirect(url_for('home'))

    return wrap


class AdminIndex(AdminIndexView):
    def is_accessible(self):
        if current_user.is_authenticated:
            return current_user.role[0].name == 'ROLE_ADMIN'
        else:
            return False


class ModelV(ModelView):
    def is_accessible(self):
        if current_user.is_authenticated:
            return current_user.role[0].name == 'ROLE_ADMIN'
        else:
            return False
