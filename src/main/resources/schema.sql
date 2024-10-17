-- books table
create table if not exists books (
    book_id serial primary key,
    book_name character varying(255),
    book_writer character varying(255)
);
create table if not exists users (
    user_id serial primary key,
    user_name character varying(255),
    user_pass character varying(255),
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean
);
create table if not exists roles (
    role_id serial primary key,
    role_name character varying(255) unique
);
create table if not exists user_role (
    user_role_id serial primary key,
    user_id integer,
    role_id integer,
    constraint user_fk foreign key (user_id) references users (user_id),
    constraint role_fk foreign key (role_id) references roles (role_id)
);
create table if not exists authorities (
    authority_id serial primary key,
    authority_name character varying(255)
);
create table if not exists role_authority (
    role_authority_id serial primary key,
    role_id integer,
    authority_id integer,
    constraint role_fk foreign key (role_id) references roles (role_id),
    constraint authority_fk foreign key (authority_id) references authorities (authority_id)
);