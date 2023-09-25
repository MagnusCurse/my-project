drop database if exists musicplayer;
create database musicplayer;

use musicplayer;

drop table if exists user;
create table user
(
id int primary key auto_increment,
username varchar(50) not null,
password varchar(255) not null
);d

drop table if exists music;
create table music
(
id int primary key auto_increment,
title varchar(50) not null,
singer varchar(30) not null,
uploadtime varchar(100) not null,
url varchar(1000) not null,
user_id int not null
);

drop table if exists love;
create table love
(
id int primary key auto_increment,
user_id int not null,
music_id int not null
);
