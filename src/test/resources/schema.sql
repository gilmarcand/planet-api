--table
create table planet
(id bigint generated by default as identity,
climate varchar(255) not null,
films bigint, name varchar(255) not null,
terrain varchar(255) not null,
 primary key (id));