DROP TABLE ingredients CASCADE ;
DROP TABLE pizza CASCADE ;
DROP TABLE compo CASCADE ;

CREATE TABLE ingredients(id int, name text,primary key (id));
INSERT INTO ingredients VALUES (1,'creme fraiche');
INSERT INTO ingredients VALUES (2,'sauce tomate');
INSERT INTO ingredients VALUES (3,'chorizo');
INSERT INTO ingredients VALUES (4,'lardons');
INSERT INTO ingredients VALUES (5,'aubergines');
INSERT INTO ingredients VALUES (6,'champignons');
INSERT INTO ingredients VALUES (7,'pomme de terre');
INSERT INTO ingredients VALUES (8,'poivrons');
INSERT INTO ingredients VALUES (9,'jambon');
INSERT INTO ingredients VALUES (10,'chevre');
INSERT INTO ingredients VALUES (11,'miel');
INSERT INTO ingredients VALUES (12,'emmental');
INSERT INTO ingredients VALUES (13,'camembert');
INSERT INTO ingredients VALUES (14,'mozzarella');
INSERT INTO ingredients VALUES (15,'saumon');
INSERT INTO ingredients VALUES (16,'noix');
INSERT INTO ingredients VALUES (17,'ananas');
INSERT INTO ingredients VALUES (18,'poulet');


CREATE TABLE pizza(id int,name text,price numeric,pate text,primary key (id));
INSERT INTO pizza VALUES (1,'pizza Saumon',9.99,'fine');
INSERT INTO pizza VALUES (2,'pizza 4 fromages',9.99,'fine');
INSERT INTO pizza VALUES (3,'pizza Reine',15.0,'Mozza-crust');
INSERT INTO pizza VALUES (4,'pizza Chevre Miel',12.0,'fine');
INSERT INTO pizza VALUES (5,'pizza Jambon',9.99,'fine');
INSERT INTO pizza VALUES (6,'pizza Hawaiian',13.0,'Mozza-crust');


CREATE TABLE compo(idP int,idI int,foreign key (idP) references pizza(id) on delete cascade,foreign key (idI) references ingredients(id) on delete cascade,primary key (idI,idP));
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

select * from pizza INNER JOIN compo ON compo.idP = pizza.id INNER JOIN ingredients ON compo.idI = ingredients.id;