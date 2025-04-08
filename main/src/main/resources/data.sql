insert into tipo_colegio (id, nombre, dcto, max_cuotas) values
(1, 'Publico', 20, 10),
(2, 'Privado', 0, 4),
(3, 'Subvencionado', 10, 7);


INSERT INTO usuario (id, nombres, apellidos, rut, fecha_nacimiento, grad_year, nombre_colegio, id_tipo_colegio) VALUES
(100, 'Matías Alejandro', 'González Fuentes', '19.456.789-K', '2000-05-15', 2018, 'Liceo Nacional de Maipú', 1),
(200, 'Valentina Andrea', 'Soto Herrera', '20.123.456-7', '2001-08-22', 2019, 'Colegio Santa María', 3),
(300, 'Felipe Ignacio', 'Muñoz Contreras', '20.789.456-1', '2001-11-03', 2019, 'Instituto Nacional', 1),
(400, 'Catalina Belén', 'Rojas Méndez', '20.333.444-5', '2002-02-17', 2020, 'The English School', 2),
(500, 'Diego Antonio', 'Vargas Leiva', '20.555.666-7', '2001-04-30', 2019, 'Liceo Bicentenario de Puente Alto', 1),
(600, 'Javiera Constanza', 'Araya Silva', '20.999.888-K', '2002-07-12', 2020, 'Colegio San Ignacio', 2),
(700, 'Nicolás Andrés', 'Parra Torres', '21.111.222-3', '2003-01-25', 2021, 'Colegio Particular Las Condes', 2),
(800, 'Francisca Antonia', 'Castro Riquelme', '20.444.555-6', '2002-09-08', 2020, 'Liceo Carmela Carvajal', 1),
(900, 'Sebastián Ignacio', 'Morales Díaz', '20.777.888-9', '2002-03-14', 2020, 'Colegio Pedro de Valdivia', 3),
(1000, 'Camila Paz', 'Vega Domínguez', '21.222.333-4', '2003-06-19', 2021, 'Liceo Manuel Barros Borgoño', 1);