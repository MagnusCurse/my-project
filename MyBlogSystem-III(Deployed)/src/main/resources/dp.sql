-- Create database
drop database if exists mycnblog;
create database mycnblog DEFAULT CHARACTER SET utf8mb4;

-- Use the database
use mycnblog

-- Create user table
drop table if exists user_info;
create table user_info(
      id int primary key auto_increment,
      username varchar(100) unique,
      nickname varchar(100) unique,
      mail varchar(100) unique, --
      introduction varchar(500), -- user brief introduction
      photo varchar(500) default '', --
      avatar_url varchar(200) default '' -- the url of the avatar photo source
      password varchar(64) not null,
      create_time datetime default now(),
      update_time datetime default now(),
) default charset 'utf8mb4';


-- Create blog table
drop table if exists blog_info;
create table blog_info(
      id int primary key auto_increment, -- auto_increment_id
      user_id int not null,
      title varchar(100) not null,
      content text not null,
      like_count int default 0,
      view_count int default 0
      create_time datetime default now(),
      update_time datetime default now(),
)default charset 'utf8mb4';


-- Create comment table
drop table if exists comment_info;
create table comment_info(
     id int primary key auto_increment,
     parent_id int,
     user_id int,
     blog_id int,
     username varchar(100),
     comment varchar(360),
     create_time datetime default now(),
     like_count int,
     replied_username varchar(100)
)default charset 'utf8mb4';


-- Create blog thump-up record table
drop table if exists blog_like_info;
create table blog_like_info(
   id int primary key auto_increment, -- auto_increment id
   blog_id int not null, -- ID of the blog being liked
   user_id int not null, -- ID of the user liking the blog
   create_time datetime default now(),
   update_time datetime default now()
)default charset 'utf8mb4';

-- Create followed record table
drop table if exists follow_info;
create table follow_info (
     id int primary key auto_increment,
     user_id int not null, --
     followed_user_id int not null, --
     create_time datetime default now(),
     update_time datetime default now()
)default charset 'utf8mb4';
