create table if not exists roles
(
    id   bigserial
        primary key,
    name varchar(30) not null
);

alter table roles
    owner to postgres;

create table if not exists users
(
    id                bigserial
        primary key,
    first_name        varchar(50)                               not null,
    last_name         varchar(100)                              not null,
    email             varchar(100)                              not null
        unique,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6),
    is_deleted        boolean      default false                not null,
    password          varchar(100)                              not null,
    role_id           bigint
        constraint foreign_key_name
            references roles
);

alter table users
    owner to postgres;

create table if not exists organizations
(
    id                bigserial
        primary key,
    type              varchar(15)                               not null,
    name              varchar(100)                              not null,
    employees_amount  integer,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6),
    user_id           bigint                                    not null
        constraint organizations_users_id_fk
            references users
            on update cascade on delete cascade,
    is_deleted        boolean      default false                not null,
    solvency          double precision,
    liquidity         double precision
);

alter table organizations
    owner to postgres;

create unique index if not exists organizations_name_user_id_uindex
    on organizations (name, user_id);

create table if not exists organization_data
(
    id                    bigserial
        primary key,
    organization_id       bigint not null
        unique
        constraint organization_data_organizations_id_fk
            references organizations
            on update cascade on delete cascade,
    bankroll              double precision,
    short_investments     double precision,
    short_receivables     double precision,
    short_liabilities     double precision,
    intangible_assets     double precision,
    main_assets           double precision,
    production_reverses   double precision,
    unfinished_production double precision,
    finished_products     double precision,
    borrowed_funds        double precision
);

alter table organization_data
    owner to postgres;

create table if not exists formules
(
    id    bigserial
        primary key,
    value text not null
);

alter table formules
    owner to postgres;
