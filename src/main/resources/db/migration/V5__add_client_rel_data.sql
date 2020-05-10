insert into ticket (id, destination, price)
values (1, 'Berlin', 99.01);
insert into ticket (id, destination, price)
values (2, 'London', 99.02);
insert into ticket (id, destination, price)
values (3, 'Kyiv', 99.03);

insert into t_role(id, name)
values (1, 'ROLE_USER');
insert into t_role(id, name)
values (2, 'ROLE_ADMIN');

insert into t_user(id, login, password)
values (1, 'example1@mail.org', 'pass1');
insert into t_user(id, login, password)
values (2, 'example2@mail.org', 'pass2');

insert into t_user_roles(user_id, roles_id)
values (1, 1);
insert into t_user_roles(user_id, roles_id)
values (2, 2);

insert into client (id, first_name, last_name, email, age, password, admin_id, ticket_id, user_id)
values (1, 'Vasya', 'Pupkin', 'example1@mail.org', 23, 'pass1', 1, 1, 1);
insert into client (id, first_name, last_name, email, age, password, admin_id, ticket_id, user_id)
values (2, 'Petya', 'Vasichkin', 'example2@mail.org', 24, 'pass2', 2, 2, 2);
insert into client (id, first_name, last_name, email, age, password, admin_id, ticket_id)
values (3, 'Vasya', 'Petechkin', 'example3@mail.org', 25, 'pass3', 3, 3);

insert into add_service (id, description, price, ticket_id)
values (1, 'First-class', 12.01, 1);
insert into add_service (id, description, price, ticket_id)
values (2, 'Extra food', 12.02, 2);
insert into add_service (id, description, price, ticket_id)
values (3, 'Free luggage', 12.03, 3);