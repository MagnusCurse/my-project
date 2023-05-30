-- 创建数据库
drop database if exists mycnblog;
create database mycnblog DEFAULT CHARACTER SET utf8mb4;

-- 使用数据数据
use mycnblog;

-- 创建用户表
drop table if exists userinfo;
create table userinfo(
    id int primary key auto_increment,
    username varchar(100) unique,
    password varchar(64) not null,
    photo varchar(500) default '',
    createtime datetime default now(),
    updatetime datetime default now(),
    `state` int default 1,
    email varchar(100) unique,
    nickname varchar(100) unique,
    introduction varchar(500),
    avatar_url varchar(200) default 'img/avatar/default-image.png'
) default charset 'utf8mb4';

-- 创建文章表
drop table if exists  articleinfo;
create table articleinfo(
    id int primary key auto_increment,
    title varchar(100) not null,
    content text not null,
    createtime datetime default now(),
    updatetime datetime default now(),
    uid int not null,
    rcount int not null default 1,
    `state` int default 1,
    likes int default 0,
    views int default 0
)default charset 'utf8mb4';

-- 创建评论表
drop table if exists commentinfo;
create table commentinfo(
 commentID int primary key auto_increment,
 parentCommentID int,
 userID int,
 username varchar(360),
 content varchar(360),
 create_time datetime default now(),
 likes int,
 articleID int
) default charset 'utf8mb4';

-- foreign key (parentCommentID) references commentinfo(commentID)

-- 创建点赞表
drop table if exists likeinfo;
create table likeinfo(
  id int primary key auto_increment,
  userID int,
  commentID int,
  articleID int
) default charset 'utf8mb4';

-- 创建草稿表
drop table if exists draftinfo;

create table draftinfo(
   id int primary key auto_increment,
   title varchar(100) not null,
   content text not null,
   createtime datetime default now(),
   updatetime datetime default now(),
   uid int not null
)default charset 'utf8mb4';










