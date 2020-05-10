create table add_service
(
    id          bigserial    not null
        constraint add_service_pk
            primary key,
    description varchar(255) not null,
    price       float8       not null,
    ticket_id   bigint
);

create table admin
(
    id       bigserial    not null
        constraint admin_pk
            primary key,
    username varchar(255) not null,
    password varchar(255) not null
);

create table client
(
    id         bigserial    not null
        constraint client_pk
            primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,
    age        int          not null,
    password   varchar(255) not null,
    admin_id   bigint,
    ticket_id  bigint,
    user_id    bigint
);

create table dispatch
(
    id       bigserial    not null
        constraint dispatch_pk
            primary key,
    username varchar(255) not null,
    password varchar(255) not null
);

create table flight
(
    id             bigserial    not null
        constraint flight_pk
            primary key,
    departure      varchar(255) not null,
    destination    varchar(255) not null,
    flight_time    varchar(255) not null,
    admin_id       bigint,
    dispatch_id    bigint,
    flight_crew_id bigint
);

create table flight_crew
(
    id       bigserial    not null
        constraint flight_crew_pk
            primary key,
    username varchar(255) not null,
    password varchar(255) not null
);

create table ticket
(
    id          bigserial    not null
        constraint ticket_pk
            primary key,
    destination varchar(255) not null,
    price       float8       not null
);

alter table add_service
    add constraint add_service_ticket_id_fk
        foreign key (ticket_id) references ticket;

alter table client
    add constraint client_admin_id_fk
        foreign key (admin_id) references admin;
alter table client
    add constraint client_ticket_id_fk
        foreign key (ticket_id) references ticket;

alter table flight
    add constraint flight_admin_id_fk
        foreign key (admin_id) references admin;
alter table flight
    add constraint flight_dispatch_id_fk
        foreign key (dispatch_id) references dispatch;
alter table flight
    add constraint flight_flight_crew_id_fk
        foreign key (flight_crew_id) references flight_crew;

create table admin_dispatch
(
    admin_id    bigserial not null
        constraint admin_dispatch_admin_id_fk
            references admin,
    dispatch_id bigserial not null
        constraint admin_dispatch_pk
            primary key
        constraint admin_dispatch_dispatch_id_fk
            references dispatch
);

create table admin_flight_crew
(
    admin_id       bigserial not null
        constraint admin_flight_crew_admin_id_fk
            references admin,
    flight_crew_id bigserial not null
        constraint admin_flight_crew_pk
            primary key
        constraint admin_flight_crew_flight_crew_id_fk
            references flight_crew
);

create table dispatch_flight_crew
(
    dispatch_id    bigserial not null
        constraint dispatch_flight_crew_dispatch_id_fk
            references dispatch,
    flight_crew_id bigserial not null
        constraint dispatch_flight_crew_pk
            primary key
        constraint dispatch_flight_crew_flight_crew_id_fk
            references flight_crew
);

create table t_user
(
    id       bigserial    not null
        constraint user_pk
            primary key,
    login    varchar(255) not null,
    password varchar(255) not null
);

create table t_role
(
    id   bigserial    not null
        constraint role_pk
            primary key,
    name varchar(255) not null
);

create table t_user_roles
(
    user_id  bigserial not null
        constraint role_user_user_id_fk
            references t_user,
    roles_id bigserial
        constraint role_user_role_id_fk
            references t_role
);