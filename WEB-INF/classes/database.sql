DROP TABLE if exists ingredients CASCADE ;
DROP TABLE if exists pizza CASCADE ;
DROP TABLE if exists compo CASCADE ;
DROP TABLE if exists users CASCADE ;
DROP TABLE if exists orders CASCADE ;
DROP TABLE if exists identification CASCADE ;

CREATE TABLE ingredients(id int, name text,price numeric,primary key (id));
INSERT INTO ingredients VALUES (1,'creme fraiche',1.2);
INSERT INTO ingredients VALUES (2,'sauce tomate',0.8);
INSERT INTO ingredients VALUES (3,'chorizo',1.5);
INSERT INTO ingredients VALUES (4,'lardons',1.5);
INSERT INTO ingredients VALUES (5,'aubergines',0.5);
INSERT INTO ingredients VALUES (6,'champignons',0.9);
INSERT INTO ingredients VALUES (7,'pomme de terre',0.30);
INSERT INTO ingredients VALUES (8,'poivrons',0.6);
INSERT INTO ingredients VALUES (9,'jambon',3.3);
INSERT INTO ingredients VALUES (10,'chevre',3.0);
INSERT INTO ingredients VALUES (11,'miel',2.0);
INSERT INTO ingredients VALUES (12,'emmental',3.0);
INSERT INTO ingredients VALUES (13,'camembert',4.0);
INSERT INTO ingredients VALUES (14,'mozzarella',3.5);
INSERT INTO ingredients VALUES (15,'saumon',6.0);
INSERT INTO ingredients VALUES (16,'noix',3.2);
INSERT INTO ingredients VALUES (17,'ananas',3.7);
INSERT INTO ingredients VALUES (18,'poulet',2.5);


CREATE TABLE pizza(id int,name text,price numeric,pate text,primary key (id));


INSERT INTO pizza VALUES (1,'pizza Saumon',6.99,'fine');
INSERT INTO pizza VALUES (2,'pizza 4 fromages',5.99,'fine');
INSERT INTO pizza VALUES (3,'pizza Reine',5.0,'Mozza-crust');
INSERT INTO pizza VALUES (4,'pizza Chevre Miel',4.0,'fine');
INSERT INTO pizza VALUES (5,'pizza Jambon',5.99,'fine');
INSERT INTO pizza VALUES (6,'pizza Hawaiian',8.0,'Mozza-crust');


CREATE TABLE compo(idP int,idI int,
foreign key (idP) references pizza(id) on delete cascade,
foreign key (idI) references ingredients(id) on delete cascade,primary key (idP,idI));


INSERT INTO compo VALUES (1,1);
INSERT INTO compo VALUES (1,15);
INSERT INTO compo VALUES (1,14);

INSERT INTO compo VALUES (2,2);
INSERT INTO compo VALUES (2,10);
INSERT INTO compo VALUES (2,12);
INSERT INTO compo VALUES (2,13);
INSERT INTO compo VALUES (2,14);
INSERT INTO compo VALUES (2,7);

INSERT INTO compo VALUES (3,2);
INSERT INTO compo VALUES (3,6);
INSERT INTO compo VALUES (3,9);
INSERT INTO compo VALUES (3,3);
INSERT INTO compo VALUES (3,12);


INSERT INTO compo VALUES (4,1);
INSERT INTO compo VALUES (4,10);
INSERT INTO compo VALUES (4,11);
INSERT INTO compo VALUES (4,16);

INSERT INTO compo VALUES (5,2);
INSERT INTO compo VALUES (5,9);
INSERT INTO compo VALUES (5,8);
INSERT INTO compo VALUES (5,6);

INSERT INTO compo VALUES (6,2);
INSERT INTO compo VALUES (6,17);
INSERT INTO compo VALUES (6,14);
INSERT INTO compo VALUES (6,11);
INSERT INTO compo VALUES (6,9);

CREATE TABLE users
(id int,username text,password text,name text,rue text,city text,number varchar(10),email text,primary key (id));
insert into users VALUES (1,'bestpierro59','cambraizoo','pierre',' 10 rue des pierres','Lille','0707050351','bestpierro62@gmail.com');
insert into users VALUES (2,'bgdu62','moi','mounir','9 rue des mouns','Henin-Beaumont','0781953385','mounirlemagnifique@gmail.com');
insert into users VALUES (3,'colin','LOL','colin','32 avenue jolivet','Cassel','0321546853','colinjolivet@gmail.com');




CREATE TABLE orders(orderId int,idU int,idP int,qty int,date date,hours time,finish bool,foreign key (idU) references users(id),foreign key (idP)references pizza(id),primary key (orderId,idP));
insert into orders VALUES (2,1,2,2,'09/02/2022','12:02:45',true);
insert into orders VALUES (2,1,3,2,'09/02/2022','12:02:45',true);
insert into orders VALUES (3,3,5,1,'02/05/2022','11:30:59',false);
insert into orders VALUES (4,3,3,2,'02/05/2022','11:30:59',false);
insert into orders VALUES (5,3,1,5,'02/05/2022','11:30:59',false);
insert into orders VALUES (9,1,4,2,'25/12/2022','20:56:12',true);
insert into orders VALUES (7,1,3,1,'04/08/2022','18:15:36',true);
insert into orders VALUES (6,2,1,1,'25/06/2022','12:30:20',true);
insert into orders VALUES (1,2,2,3,'01/01/2022','00:30:25',true);
insert into orders VALUES (8,2,4,1,'30/09/2022','12:11:21',true);




select pizza.price,sum(ingredients.price)from pizza INNER JOIN compo ON compo.idP = pizza.id INNER JOIN ingredients ON compo.idI = ingredients.id where idP=1
group by pizza.price;

select idI from compo where idP=1;

Select * from ingredients INNER JOIN compo ON compo.idI = ingredients.id where idP = 1;

Select p.name,Sum(ingredients.price+p.price) from ingredients INNER JOIN compo ON compo.idI = ingredients.id INNER JOIN pizza p on p.id = compo.idP group by p.name ;

select idU,uC.name,p.name,orders.qty,sum(i.price+p.price),date,finish from orders INNER JOIN pizza p on p.id = orders.idP INNER JOIN users uC on uC.id = orders.idU INNER JOIN compo c on p.id = c.idP INNER JOIN ingredients i on i.id = c.idI
group by idU,orders.qty,date, p.name, uC.name,finish;

select orderId,sum(i.price+p.price)*qty as total from orders INNER JOIN pizza p on p.id = orders.idP  INNER JOIN compo c on p.id = c.idP INNER JOIN ingredients i on i.id = c.idI where orderId=1
group by idU,orderId,qty;

Select id,token from users ;