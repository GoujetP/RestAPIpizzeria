DROP TABLE ingredients CASCADE ;
DROP TABLE pizza CASCADE ;
DROP TABLE compo CASCADE ;

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

select pizza.price,sum(ingredients.price)from pizza INNER JOIN compo ON compo.idP = pizza.id INNER JOIN ingredients ON compo.idI = ingredients.id where idP=1
group by pizza.price;
select idI from compo where idP=1;
Select * from ingredients INNER JOIN compo ON compo.idI = ingredients.id where idP = 1;
Select Sum(price) from ingredients INNER JOIN compo ON compo.idI = ingredients.id where idP = 1