-- insertando valores -------------------------------------------------------------------------------------------------------------------------------
-- CATEGORIES
insert into categories values (1, "Hoteles con habitaciones privadas", "https://images.pexels.com/photos/6394711/pexels-photo-6394711.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "Hotel");
insert into categories values (2, "Hostales con habitaciones compartidas", "https://images.pexels.com/photos/4825701/pexels-photo-4825701.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "Hostal");
insert into categories values (3, "Habitaciones en casas de familia", "https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "Bed and Breakfast");
insert into categories values (4, "Habitaciones en paraderos apartados", "https://images.pexels.com/photos/9130978/pexels-photo-9130978.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500", "Lodge");

-- CITIES
insert into cities values (1, "Argentina", "Cordoba");
insert into cities values (2, "Argentina", "San Miguel de Tucuman");
insert into cities values (3, "Argentina", "San Carlos de Bariloche");
insert into cities values (4, "Argentina", "Buenos Aires");
insert into cities values (5, "Argentina", "Puerto Iguazu");
insert into cities values (6, "Argentina", "Mendoza");
insert into cities values (7, "Argentina", "Carilo");		

-- PRODUCTS
insert into products values (1,"Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.", "Tabby burmese munchkin, bobcat. Munchkin thai for sphynx. Scottish fold havana brown. Siberian tom for savannah and munchkin. Bengal tom so bombay.",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo","Check-out: 10:00. No se permiten fiestas. No fumar.", 24, -26.82787, -65.19368, "Garden Park Hotel", "Av. Soldati", "330", 1, 2);
insert into products values (2,"Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.", "Donskoy cheetah but puma, tomcat kitty. Maine coon siberian kitten munchkin and singapura ocicat. American bobtail tiger for thai, thai. Bobcat turkish",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo","Check-out: 10:00. No se permiten fiestas. No fumar.", 24, -41.13309, -71.30676, "Hotel Tierra Gaucha", "Villegas", "148", 1, 3);
insert into products values (3,"Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.", "Grimalkin jaguar donskoy for tabby. Persian devonshire rex but british shorthair mouser. Sphynx malkin. British shorthair american",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo","Check-out: 10:00. No se permiten fiestas. No fumar.",24,  -34.61314, -58.38577, "Mariel B&B", "Av. Belgrano", "1386", 3, 4);
insert into products values (4,"Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.", "Donskoy cheetah but puma, tomcat kitty. Maine coon siberian kitten munchkin and singapura ocicat. American bobtail tiger for thai, thai. Bobcat turkish",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo","Check-out: 10:00. No se permiten fiestas. No fumar.",24, -34.57400, -58.47043, "B&B Polo", "La Pampa", "4035", 3, 4);
insert into products values (5, "Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.","Birman maine coon or thai. Tomcat. Manx burmese abyssinian burmese american bobtail so siberian. Ragdoll norwegian forest egyptian mau. ",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo","Check-out: 10:00.  No se permiten fiestas. No fumar.",24, -25.59862, -54.56808, "Hostel Park Iguazu", "Julio P. Amarante", "111", 2, 5);
insert into products values (6,"Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.", "Sphynx malkin yet balinese . Sphynx bengal. British shorthair american shorthair. Munchkin cougar donskoy himalayan ocelot. Thai thai and turkish angora turkish angora ocicat, donskoy bengal.",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo", "Check-out: 10:00. No se permiten fiestas. No fumar.",24,-32.89182,-68.85620, "Chill Inn Hostel", "Av. Arístides Villanueva", "385", 2, 6);
insert into products values (7,"Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.", "Cat ipsum dolor sit amet, savannah thai. Kitten bobcat tom kitten british shorthair yet balinese but persian. Devonshire rex ocelot, yet tiger singapura, grimalkin cheetah.",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo","Check-out: 10:00. No se permiten fiestas. No fumar.",24, -25.60613, -54.56296, "Palo Rosa Lodge", "Ruta Nacional 12", "Km 1640", 4, 5);
insert into products values (8,"Agrega las fechas de tu viaje para obtener los detalles de cancelación de esta estadía.", "Lion ragdoll. Savannah birman singapura lion egyptian mau savannah or devonshire rex. Ocicat american shorthair for kitty siamese, but bengal for sphynx. American bobtail tiger.",0 ,"Se aplican las pautas de distanciamiento social y otras normas relacionadas con el coronavirus. Detector de humo","Check-out: 10:00. No se permiten fiestas. No fumar.",24, -37.16543, -56.89577, "Cabañas Lemu Hue", "Avutarda", "1110", 4, 7);

-- CHARACTERISTICS
insert into characteristics values (1,"fas fa-swimmer","Piscina");
insert into characteristics values (2,"fas fa-wifi","Wifi");
insert into characteristics values (3,"fas fa-car","Estacionamiento gratuito");
insert into characteristics values (4,"fas fa-spa","Spa");
insert into characteristics values (5,"fas fa-utensils","Restaurante");
insert into characteristics values (6,"fas fa-tv","Televisor");
insert into characteristics values (7,"far fa-snowflake","Aire acondicionado");
insert into characteristics values (8,"fas fa-paw","Apto mascotas");

-- IMAGES
-- producto 1
insert into images values (1,"img1-prod1","https://images.pexels.com/photos/2736388/pexels-photo-2736388.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",1);
insert into images values (2,"img2-prod1","https://images.pexels.com/photos/2873951/pexels-photo-2873951.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",1);
insert into images values (3,"img3-prod1", "https://images.pexels.com/photos/3770883/pexels-photo-3770883.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",1);
insert into images values (4,"img4-prod1","https://images.pexels.com/photos/6466304/pexels-photo-6466304.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260",1);
insert into images values (5,"img5-prod1","https://images.pexels.com/photos/5371575/pexels-photo-5371575.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",1);
-- producto 2
insert into images values (6,"img1-prod2","https://images.pexels.com/photos/1743373/pexels-photo-1743373.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",2);
insert into images values (7,"img2-prod2","https://images.pexels.com/photos/4577673/pexels-photo-4577673.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",2);
insert into images values (8,"img3-prod2","https://images.pexels.com/photos/7820379/pexels-photo-7820379.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",2);
insert into images values (9,"img4-prod2","https://images.pexels.com/photos/262048/pexels-photo-262048.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",2);
insert into images values (10,"img5-prod2","https://images.pexels.com/photos/2878741/pexels-photo-2878741.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",2);
-- producto 3
insert into images values (11,"img5-prod3","https://images.pexels.com/photos/5847383/pexels-photo-5847383.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",3);
insert into images values (12,"img1-prod3","https://images.pexels.com/photos/5137985/pexels-photo-5137985.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",3);
insert into images values (13,"img2-prod3","https://images.pexels.com/photos/5138172/pexels-photo-5138172.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",3);
insert into images values (14,"img3-prod3","https://images.pexels.com/photos/5146917/pexels-photo-5146917.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",3);
insert into images values (15,"img4-prod3","https://images.pexels.com/photos/5137773/pexels-photo-5137773.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",3);
-- producto 4
insert into images values (16,"img1-prod4","https://images.pexels.com/photos/9139179/pexels-photo-9139179.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",4);
insert into images values (17,"img2-prod4","https://images.pexels.com/photos/5146929/pexels-photo-5146929.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",4);
insert into images values (18,"img3-prod4","https://images.pexels.com/photos/9420758/pexels-photo-9420758.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",4);
insert into images values (19,"img4-prod4","https://images.pexels.com/photos/1833337/pexels-photo-1833337.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",4);
insert into images values (20,"img5-prod4","https://images.pexels.com/photos/1458742/pexels-photo-1458742.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",4);
-- producto 5
insert into images values (21,"img1-prod5","https://images.pexels.com/photos/7968283/pexels-photo-7968283.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",5);
insert into images values (22,"img2-prod5","https://images.pexels.com/photos/4907433/pexels-photo-4907433.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",5);
insert into images values (23,"img3-prod5","https://images.pexels.com/photos/5157282/pexels-photo-5157282.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",5);
insert into images values (24,"img4-prod5","https://images.pexels.com/photos/5138193/pexels-photo-5138193.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",5);
insert into images values (25,"img5-prod5","https://images.pexels.com/photos/4907455/pexels-photo-4907455.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",5);
-- producto 6
insert into images values (26,"img1-prod6","https://images.pexels.com/photos/5158948/pexels-photo-5158948.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",6);
insert into images values (27,"img2-prod6","https://images.pexels.com/photos/4778421/pexels-photo-4778421.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",6);
insert into images values (28,"img3-prod6","https://images.pexels.com/photos/5146876/pexels-photo-5146876.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",6);
insert into images values (29,"img4-prod6","https://images.pexels.com/photos/5137788/pexels-photo-5137788.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",6);
insert into images values (30,"img5-prod6","https://images.pexels.com/photos/5137963/pexels-photo-5137963.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",6);
-- producto 7
insert into images values (31,"img1-prod7","https://images.pexels.com/photos/9297000/pexels-photo-9297000.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",7);
insert into images values (32,"img2-prod7","https://images.pexels.com/photos/4940760/pexels-photo-4940760.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",7);
insert into images values (33,"img3-prod7","https://images.pexels.com/photos/4917176/pexels-photo-4917176.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",7);
insert into images values (34,"img4-prod7","https://images.pexels.com/photos/1843655/pexels-photo-1843655.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",7);
insert into images values (35,"img5-prod7","https://images.pexels.com/photos/5591663/pexels-photo-5591663.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",7);
-- producto 8
insert into images values (36,"img1-prod8","https://images.pexels.com/photos/2662183/pexels-photo-2662183.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",8);
insert into images values (37,"img2-prod8","https://images.pexels.com/photos/9433045/pexels-photo-9433045.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",8);
insert into images values (38,"img3-prod8","https://images.pexels.com/photos/3951743/pexels-photo-3951743.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",8);
insert into images values (39,"img4-prod8","https://images.pexels.com/photos/3932765/pexels-photo-3932765.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",8);
insert into images values (40,"img5-prod8","https://images.pexels.com/photos/4915571/pexels-photo-4915571.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",8);

-- PRODUCTS_CHARACTERISTICS
insert into products_characteristics values (1,1);
insert into products_characteristics values (1,2);
insert into products_characteristics values (1,3);
insert into products_characteristics values (1,4);
insert into products_characteristics values (1,5);

insert into products_characteristics values (2,1);
insert into products_characteristics values (2,2);
insert into products_characteristics values (2,3);
insert into products_characteristics values (2,4);
insert into products_characteristics values (2,6);

insert into products_characteristics values (3,1);
insert into products_characteristics values (3,2);
insert into products_characteristics values (3,3);
insert into products_characteristics values (3,7);

insert into products_characteristics values (4,1);
insert into products_characteristics values (4,2);
insert into products_characteristics values (4,3);
insert into products_characteristics values (4,8);

insert into products_characteristics values (5,1);
insert into products_characteristics values (5,2);
insert into products_characteristics values (5,3);
insert into products_characteristics values (5,6);

insert into products_characteristics values (6,1);
insert into products_characteristics values (6,2);
insert into products_characteristics values (6,3);
insert into products_characteristics values (6,5);

insert into products_characteristics values (7,1);
insert into products_characteristics values (7,2);
insert into products_characteristics values (7,3);
insert into products_characteristics values (7,7);

insert into products_characteristics values (8,1);
insert into products_characteristics values (8,2);
insert into products_characteristics values (8,3);
insert into products_characteristics values (8,5);
insert into products_characteristics values (8,6);


insert into puntuation_counters values(1, 7.5833, 6, 12, 1, 2, 3, 0,  1);
insert into puntuation_counters values(2, 8.3703, 15, 7, 0, 2, 2, 1,  2);
insert into puntuation_counters values(3, 4.1666, 0, 1, 1, 1, 4, 2,  3);
insert into puntuation_counters values(4, 3.176470, 0, 2, 6, 12, 2, 3,  4);
insert into puntuation_counters values(5, 5.675675, 12, 13, 11, 21, 12, 5,  5);
insert into puntuation_counters values(6, 3.4,0, 1, 0, 3, 2, 4,  6);
insert into puntuation_counters values(7, 7.333, 50, 40, 10, 30, 20, 0,  7);
insert into puntuation_counters values(8, -1, 0, 0, 0, 0, 0, 0,  8);


-- varios select ------------------------------------------------------------------------------------------------------------------------------------------
SELECT * FROM categories;
SELECT * FROM cities;
SELECT * FROM products;
SELECT products.*,categories.name FROM products INNER JOIN categories ON categories.id = products.category_id; 
SELECT * FROM characteristics;
SELECT * FROM images;
SELECT * FROM products_characteristics;
SELECT * FROM users;
SELECT * FROM user_types;

-- PRODUCTS_BOOKINGS

insert into bookings values (1, "2022-01-01","2022-01-05",0, 3,1);
insert into bookings values (2, "2022-01-22","2022-01-25",0, 4,1);
insert into bookings values (3, "2022-01-05","2022-01-12",0, 5,1);
insert into bookings values (4, "2022-01-12","2022-01-18",0, 6,1);
insert into bookings values (5, "2022-01-18","2022-01-22",0, 7,1);
insert into bookings values (6, "2022-01-05","2022-01-22",0, 8,1);
insert into bookings values (7, "2023-01-01","2023-01-05",0, 8,1);
insert into bookings values (8, "2020-01-01","2020-01-05",0, 7,1);

-- FAVORITES_USER_1

insert into favorites values (1, 1, 1);
insert into favorites values (2, 3, 1);
insert into favorites values (3, 5, 1);
insert into favorites values (4, 7, 1);
