create table tb_author (
    id uuid not null primary key,
    name varchar(100) not null,
    birth_date date not null,
    nationality varchar(50) not null,
    created_at timestamp default now(),
    updated_at timestamp,
    id_user uuid
);

create table tb_book (
    id uuid not null primary key,
    isbn varchar(20) not null,
    title varchar(150) not null,
    published_date date not null,
    genre varchar(30) not null,
    price numeric(18, 2),
    created_at timestamp default now(),
    updated_at timestamp,
    id_user uuid,

    id_author uuid not null references tb_author(id),
    constraint chk_genre check (genre in ('FICTION', 'FANTASY', 'MYSTERY', 'ROMANCE', 'BIOGRAPHY', 'SCIENCE'))
);