drop database if exists jSimpleBlog; 
create database if not exists jSimpleBlog; 

use jSimpleBlog;


create table user(
  id int(8) not null primary key auto_increment,
   email varchar(50) not null,
   nickName nvarchar(50) not null,
   pwd varchar(50) not null,
   createdDate datetime not null ,
    lastLoginDate datetime not null ,
    avatar nvarchar(50) null,
);

create table category(
id int(8) not null primary key auto_increment,
name nvarchar(50) not null,
content text not null,
linkName nvachar(50) not null,
createdDate datetime not null ,
lastModifiedDate datetime not null ,
uid int(8) not null,
CONSTRAINT `uid_user_category` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
);

create table tag(
id int(8) not null primary key auto_increment,
name nvarchar(50) not null,
linkName nvachar(50) not null,
createdDate datetime not null ,
lastModifiedDate datetime not null ,
uid int(8) not null,
CONSTRAINT `uid_user_tag` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
);

create table blog(
  id int(8) not null primary key auto_increment,
  title nvarchar(50) not null,
  content text not null,
  linkName nvachar(50) not null,
  createdDate datetime not null,
  lastModifiedDate datetime not null,
  uid int(8) not null ,
  viewCount int(8) not null default 0,
  CONSTRAINT `uid_user_blog` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
);

create table blog_tag(
  tagId int(8) not null,
  blogId int(8) not null,
  createdDate datetime not null,
  CONSTRAINT `tagId_tag` FOREIGN KEY (`tagId`) REFERENCES `tag` (`id`),
  CONSTRAINT `blogId_blog` FOREIGN KEY (`blogId`) REFERENCES `blog` (`id`),
  primary key(tagId,blogId)
);

 
