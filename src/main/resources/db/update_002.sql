create table employee
(
    id       serial primary key          not null,
    name     varchar(2000)               not null,
    lastname varchar(2000)               not null,
    inn      varchar(100)                not null,
    created  timestamp without time zone not null default now()
);

alter table person
    add column
        employee_id int not null default 0;

insert into employee (name, lastname, inn)
values ('name1', 'lastname1', 100);
insert into employee (name, lastname, inn)
values ('name2', 'lastname2', 200);

update person
set employee_id = 1
where id = 1;

update person
set employee_id = 2
where id = 2;

update person
set employee_id = 2
where id = 3;