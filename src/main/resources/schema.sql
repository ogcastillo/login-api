create table users(
    user_id bigint identity not null,
    firstname varchar (128) not null,
    lastname varchar (128) not null,
    username varchar (128) unique not null,
    email varchar (128) unique not null,
    password varchar (64) not null,

    constraint pk_user_id primary key (user_id)

);