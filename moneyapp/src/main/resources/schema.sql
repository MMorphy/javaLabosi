drop table if exists users, authorities, novcanik, trosak;

create table if not exists users (
	id int not null auto_increment primary key,
	username varchar(20) not null,
	password varchar(100) not null,
	enabled bit not null
);

create table if not exists authorities (
	username varchar(20) not null,
 	authority varchar(20) not null
);

create table if not exists novcanik (
	id int not null auto_increment primary key,
	createdate DateTime,
	ime varchar(20) not null,
	tipnovcanika varchar(20) not null,
	username_id int not null,
	foreign key (username_id) references users(id)
);

create table if not exists trosak (
	id int not null auto_increment primary key,
	createdate DateTime,
	naziv varchar(20) not null,
	iznos double not null,
	vrstatroska varchar(20) not null,
	novcanik_id int not null,
	foreign key (novcanik_id) references novcanik (id)
);