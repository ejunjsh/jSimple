drop database if exists jSimpleBlog; 
create database if not exists jSimpleBlog; 

use jSimpleBlog;


create table user(
  id int(8) not null primary key auto_increment,
   email varchar(50) not null unique,
   nickName nvarchar(50) not null unique,
   pwd varchar(50) not null,
   createdDate datetime not null ,
    lastLoginDate datetime not null,
    avatar varchar(50) not null
);

create table category(
id int(8) not null primary key auto_increment,
name nvarchar(50) not null unique,
content text not null default '',
linkName varchar(50) not null unique,
createdDate datetime not null ,
lastModifiedDate datetime not null ,
uid int(8) not null,
CONSTRAINT `uid_user_category` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
);

create table tag(
id int(8) not null primary key auto_increment,
name nvarchar(50) not null unique,
linkName varchar(50) not null unique,
createdDate datetime not null ,
lastModifiedDate datetime not null ,
uid int(8) not null,
CONSTRAINT `uid_user_tag` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
);

create table blog(
  id int(8) not null primary key auto_increment,
  title nvarchar(50) not null,
  content text not null,
linkName varchar(50) not null unique,
  createdDate datetime not null,
  lastModifiedDate datetime not null,
  uid int(8) not null ,
viewCount int(8) not null default 0,
categoryId int(8) not null,
tags nvarchar(50) not null,
  CONSTRAINT `uid_user_blog` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `categoryId_blog_category` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`)
);

insert `user` ( email,nickName ,pwd , createdDate ,lastLoginDate , avatar) values('sjj050121014@163.com','idiotSky','123',now(),now(),'/app/IMG_2218.jpg');

 