delete from novcanik;
delete from trosak;
delete from users;
delete from authorities;

insert into users (username, password, enabled)
values ('admin', '$2a$10$M0z7Z.ne.vBxOjIoqBoTQOoCUss4F.7zi9g6m93Zx8sl7XEYk36Vy', 1);
insert into users (username, password, enabled)
values ('oper',  '$2a$10$s/tNXPGwC58sztYCN7S8KuvRRYfOEVcS2f6aFBSS6IRBXLdXVP3Km', 1);
insert into users (username, password, enabled)
values ('test',  '$2a$10$s/tNXPGwC58sztYCN7S8KuvRRYfOEVcS2f6aFBSS6IRBXLdXVP3Km', 1);
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('oper', 'ROLE_USER');

insert into novcanik (ime, createdate, tipnovcanika, username_id)
	values ('admin', CURRENT_TIMESTAMP, 'Kartica', 1);
insert into novcanik (ime, createdate, tipnovcanika, username_id)
	values ('oper', CURRENT_TIMESTAMP, 'Gotovina', 2);
insert into novcanik (ime, createdate, tipnovcanika, username_id)
	values ('test', CURRENT_TIMESTAMP, 'Kartica', 3);

insert into trosak (createdate, naziv, iznos, vrstatroska, novcanik_id)
	values (CURRENT_TIMESTAMP, 'adminTrosak1', 100, 'Hrana', 1);

insert into trosak (createdate, naziv, iznos, vrstatroska, novcanik_id)
	values (CURRENT_TIMESTAMP, 'adminTrosak2', 100, 'Rezije', 1);
	
insert into trosak (createdate, naziv, iznos, vrstatroska, novcanik_id)
	values (CURRENT_TIMESTAMP, 'operTrosak1', 200, 'Hrana', 2);

insert into trosak (createdate, naziv, iznos, vrstatroska, novcanik_id)
	values (CURRENT_TIMESTAMP, 'operTrosak2', 200, 'Hrana', 2);

insert into trosak (createdate, naziv, iznos, vrstatroska, novcanik_id)
	values (CURRENT_TIMESTAMP, 'operTrosak2', 300, 'Hrana', 3);

insert into trosak (createdate, naziv, iznos, vrstatroska, novcanik_id)
	values (CURRENT_TIMESTAMP, 'operTrosak1', 300, 'Hrana', 3);