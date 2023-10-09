drop databases if exists base_test;
create database base_test;

use base_test;

drop table if exists tb_student;
create table tb_student(
    id int primary key auto_increment,
    name varchar(30),
    major varchar(30),
    sno varchar(16)
);