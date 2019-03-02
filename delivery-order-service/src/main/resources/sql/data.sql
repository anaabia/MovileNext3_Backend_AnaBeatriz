
USE ifood;
INSERT ifood.restaurant values (1,'https://media-cdn.tripadvisor.com/media/photo-s/0e/e5/b5/5e/logo-restaurante.jpg',"La fusteria",1);
INSERT ifood.restaurant values (2,'https://blog.agenciadosite.com.br/wp-content/uploads/2017/01/logo-para-restaurante.jpg',"Fabrica de sabores",1);
INSERT ifood.restaurant values (3,'https://media-cdn.tripadvisor.com/media/photo-s/05/44/36/fd/varanda-bar-e-restaurante.jpg',"Varanda",1);
INSERT ifood.restaurant values (4,'http://ondecomeremsalvador.com.br/wp-content/uploads/2013/02/logo-amaranto-12.jpg',"Amaranto",1);
INSERT ifood.restaurant values (5,'https://media-cdn.tripadvisor.com/media/photo-s/0b/c1/2d/c4/logo-cabana.jpg',"Cabana",1);

INSERT ifood.meal VALUES (1,1,null,15.60,"Marmitex grande", 1);
INSERT ifood.meal VALUES (2,1,null,19.90,"Risoto com frango", 2);
INSERT ifood.meal VALUES (3,1,null,11.80,"Lanche de peito de peru e queijo", 2);
INSERT ifood.meal VALUES (4,0,null,25.50,"Marmitex medio", 1);
INSERT ifood.meal VALUES (5,1,null,14.30,"Açai com granola e frutas", 3);
INSERT ifood.meal VALUES (6,1,"2 niguiri salmao, 3 joe de salmao, 2 temaki de salmao",39.90,"Combo de salmao", 4);
INSERT ifood.meal VALUES (7,1,"Acompanha arroz, feijao e salada",57.80,"Parmegiana de frango grande", 5);
INSERT ifood.meal VALUES (8,1,"Acompanha vinagrete e farofa",9.60,"Espetinho", 3);
INSERT ifood.meal VALUES (9,1,null,10.60,"Marmitex pequeno", 1);

INSERT ifood.garnish values (1,1,"Arroz branco", 1.90, 180, 1);
INSERT ifood.garnish values (2,1,"Arroz integral", 1.90, 180,1);
INSERT ifood.garnish values (3,1,"Feijao", 2.90, 180,1);
INSERT ifood.garnish values (4,1,"Carne moide", 8.90, 200,1);
INSERT ifood.garnish values (5,1,"Peito de frango", 5.90, 200,1);
INSERT ifood.garnish values (6,1,"Brocolis", 2.60, 200,1);
INSERT ifood.garnish values (7,1,"Cenoura", 1.50, 200,1);

INSERT ifood.garnish values (8,1,"Arroz branco", 1.90, 120, 4);
INSERT ifood.garnish values (9,1,"Arroz integral", 1.90, 120,4);
INSERT ifood.garnish values (10,1,"Feijao", 2.90, 120,4);
INSERT ifood.garnish values (11,1,"Carne moide", 8.90, 160,4);
INSERT ifood.garnish values (12,1,"Peito de frango", 5.90, 160,4);
INSERT ifood.garnish values (13,1,"Brocolis", 2.60, 160,4);
INSERT ifood.garnish values (14,1,"Cenoura", 1.50, 160,4);

INSERT ifood.garnish values (15,1,"Arroz branco", 1.90, 80, 9);
INSERT ifood.garnish values (16,1,"Arroz integral", 1.90, 80,9);
INSERT ifood.garnish values (17,1,"Feijao", 2.90, 80,9);
INSERT ifood.garnish values (18,1,"Carne moide", 8.90, 100,9);
INSERT ifood.garnish values (19,1,"Peito de frango", 5.90, 100,9);
INSERT ifood.garnish values (20,1,"Brocolis", 2.60, 100,9);
INSERT ifood.garnish values (21,0,"Cenoura", 1.50, 100,9);

INSERT ifood.garnish values (22,0,"Morango", 1.90, 80, 5);
INSERT ifood.garnish values (23,1,"Kiwi", 1.90, 80,5);
INSERT ifood.garnish values (24,1,"Banana", 2.90, 80,5);
INSERT ifood.garnish values (25,1,"Manga", 8.90, 100,5);

INSERT ifood.garnish values (26,1,"Patinho", 2.60, 150,9);
INSERT ifood.garnish values (27,1,"Picanha", 1.50, 150,9);
INSERT ifood.garnish values (28,1,"Pao de alho", 1.90, 150, 9);