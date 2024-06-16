-- drop database if exists superkinaldb;
create database if not exists superKinalDB;

use superKinalDB;

create table Cargos(
	cargoId int not null auto_increment,
    nombreCargo varchar(30) not null,
    descripcionCargo varchar(100),
    primary key PK_cargoId (cargoId)
);

create table Clientes(
	clienteId int not null auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    telefono varchar(30),
    direccion varchar(150) not null,
    nit varchar(30) not null,
    primary key PK_clienteId (clienteId)
);

create table CategoriaProductos(
	categoriaProductosId int not null auto_increment,
    nombreCategoria varchar(30) not null,
    descripcionCategoria varchar(100) not null,
    primary key PK_categoriaProductosId (categoriaProductosId)
);


create table Distribuidores(
	distribuidorId int not null auto_increment,
    nombreDistribuidor varchar(60),
    direccionDistribuidor varchar(200),
    nitDistribuidor varchar(15),
    telefonoDistribuidor varchar(15),
    web varchar(50),
    primary key PK_distribuidorId (distribuidorId)
);

create table Compras(
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal(10,2),
    primary key PK_compraId (compraId)
);

create table Empleados(
	empleadoId int not null auto_increment,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar(30) not null,
    sueldo decimal(10,2) not null,
    horaEntrada time not null,
    horaSalida time not null,
    cargoId int not null,
    encargadoId int,
    primary key PK_empleadoId (empleadoId),
	constraint FK_Empleados_Cargos foreign key (cargoId)
		references Cargos(cargoId),
	constraint FK_Empleados_encargadoId foreign key (encargadoId)
		references Empleados(empleadoId)
);


create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    clienteId int not null,
    empleadoId int not null,
    total decimal(10,2),
    primary key PK_facturaId (facturaId),
    constraint FK_Facturas_Clientes foreign key (clienteId)
		references Clientes(clienteId),
	constraint FK_Facturas_Empleados foreign key (empleadoId)
		references Empleados (empleadoId)
);

create table TicketSoporte(
	ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(30) not null,
    estatus varchar(30) not null,
    clienteId int not null,
    facturaId int,
    primary key PK_ticketSoporteId (ticketSoporteId),
    constraint FK_ticketSoporte_Clientes foreign key (clienteId)
		references Clientes(clienteId),
	constraint FK_ticketSoporte_Facturas foreign key (facturaId)
		references Facturas(facturaId)
	
);


create table Productos(
	productoId int not null auto_increment,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100),
    cantidadStock int not null,
    precioVentaUnitario decimal(10,2) not null,
    precioVentaMayor decimal(10,2) not null,
    precioCompra decimal(10,2) not null,
    imagenProducto longblob,
    distribuidorId int not null,
    categoriaProductosId int not null,
    primary key PK_productoId (productoId),
    constraint FK_Productos_Distribuidores foreign key (distribuidorId)
		references	Distribuidores(distribuidorId),
	constraint FK_Productos_CategoriaProductos foreign key (categoriaProductosId)
		references CategoriaProductos(categoriaProductosId)
);



create table Promociones(
	promocionId int not null auto_increment,
    precioPromocion decimal(10,2) not null,
    descripcionPromocion varchar(100),
    fechaInicio date not null,
    fechaFinalizacion date not null,
    productoId int not null,
    primary key PK_promocionId (promocionId),
    constraint FK_Promociones_Productos foreign key (productoId)
		references Productos(productoId)
);




create table DetalleCompra(
	detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    primary key PK_detalleCompraId (detalleCompraId),
    constraint FK_DetalleCompra_Productos foreign key(productoId)
		references Productos(productoId),
	constraint FK_DetalleCompra_Compras foreign key(compraId)
		references Compras(compraId)
);

create table DetalleFactura(
	detalleFacturaId int not null auto_increment,
    facturaId int not null,
    productoId int not null,
    primary key PK_detalleFacturaId (detalleFacturaId),
    constraint FK_DetalleFactura_Facturas foreign key (facturaId)
		references Facturas(facturaId),
	constraint FK_DetalleFactura_Productos foreign key (productoId)
		references Productos(productoId)
);

create table NivelesAcceso(
	nivelAccesoId int not null auto_increment,
    nivelAcceso varchar(40) not null,
    primary key PK_nivelAccesoId(nivelAccesoId)
);

-- Usuarios , contraseñas y nivel de acceso
create table Usuarios( 
	usuarioId int not null auto_increment,
    usuario varchar(30) not null,
    pass varchar(100) not null,
    nivelAccesoId int not null,
    empleadoId int not null,
    primary key PK_usuarioId (usuarioId),
    constraint FK_Usuarios_NivelesAcceso foreign key Usuarios(nivelAccesoId)
		references NivelesAcceso(nivelAccesoId),
	constraint FK_Usuarios_Empleados foreign key Usuarios(empleadoId)
		references Empleados(empleadoId)
);

CREATE TABLE HistorialPrecios (
    historialId INT NOT NULL AUTO_INCREMENT,
    productoId INT NOT NULL,
    fechaCambio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    precioAnterior DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (historialId),
    FOREIGN KEY (productoId) REFERENCES Productos(productoId)
);





insert into NivelesAcceso(nivelAcceso) values
('Admin'), ('Usuario');

INSERT INTO Cargos (nombreCargo, descripcionCargo) VALUES
('Gerente', 'Encargado de supervisar todas las operaciones'),
('Vendedor', 'Encargado de ventas'),
('Cajero', 'Encargado de la caja'),
('Repartidor', 'Encargado de las entregas'),
('Administrador', 'Encargado de la administración'),
('Asistente', 'Ayuda en tareas administrativas'),
('Soporte Técnico', 'Encargado de soporte técnico'),
('Marketing', 'Encargado de marketing'),
('Contador', 'Encargado de contabilidad'),
('Recursos Humanos', 'Encargado de recursos humanos'),
('Logística', 'Encargado de logística'),
('IT Manager', 'Encargado de TI'),
('Analista', 'Analista de datos'),
('Supervisor', 'Supervisor de área'),
('Diseñador', 'Encargado de diseño'),
('Consultor', 'Consultor especializado'),
('Product Manager', 'Encargado de gestión de productos'),
('Customer Service', 'Encargado de servicio al cliente'),
('Operaciones', 'Encargado de operaciones diarias'),
('Asesor', 'Asesor de ventas');

truncate clientes;
-- Insertar 20 registros adicionales en la tabla Clientes
INSERT INTO Clientes (nombre, apellido, telefono, direccion, nit) VALUES
('Francisco', 'Castillo', '555-1239', 'Calle Venus 1717', '12345987-6'),
('Alicia', 'Silva', '555-5671', 'Av. Marte 1818', '23456098-5'),
('David', 'Jiménez', '555-8762', 'Calle Mercurio 1919', '34567109-4'),
('Gloria', 'Navarro', '555-4320', 'Calle Neptuno 2020', '45678210-3'),
('Javier', 'Ponce', '555-9871', 'Av. Júpiter 2121', '56789321-2'),
('Isabel', 'Rojas', '555-6549', 'Calle Saturno 2222', '67890432-1'),
('Emilio', 'Rivera', '555-3211', 'Av. Urano 2323', '78901543-0'),
('Patricia', 'Cabrera', '555-7891', 'Calle Plutón 2424', '89012654-9'),
('Roberto', 'Guerrero', '555-6541', 'Av. Luna 2525', '90123765-8'),
('Carmen', 'Mejía', '555-3212', 'Calle Sol 2626', '01234876-7'),
('Alberto', 'Aguilar', '555-9872', 'Calle Estrella 2727', '12345987-4'),
('Gabriela', 'Sandoval', '555-8763', 'Av. Cometa 2828', '23456098-3'),
('Felipe', 'Mora', '555-7653', 'Calle Venus 2929', '34567109-2'),
('Verónica', 'Peña', '555-6542', 'Av. Tierra 3030', '45678210-1'),
('Rafael', 'Padilla', '555-5431', 'Calle Marte 3131', '56789321-0'),
('Sara', 'Lara', '555-4328', 'Av. Mercurio 3232', '67890432-9'),
('Hugo', 'Salazar', '555-3213', 'Calle Júpiter 3333', '78901543-8'),
('Natalia', 'Palacios', '555-2109', 'Av. Saturno 3434', '89012654-7'),
('Ernesto', 'Herrera', '555-1098', 'Calle Urano 3535', '90123765-6'),
('Mónica', 'Vega', '555-9087', 'Av. Plutón 3636', '01234876-5');




INSERT INTO CategoriaProductos (nombreCategoria, descripcionCategoria) VALUES
('Electrónica', 'Productos electrónicos y gadgets'),
('Ropa', 'Vestimenta y accesorios'),
('Alimentos', 'Productos alimenticios'),
('Bebidas', 'Bebidas y licores'),
('Muebles', 'Muebles y decoración'),
('Juguetes', 'Juguetes para niños y adultos'),
('Libros', 'Libros y material de lectura'),
('Herramientas', 'Herramientas y equipos'),
('Jardinería', 'Productos de jardinería'),
('Deportes', 'Equipos deportivos'),
('Mascotas', 'Productos para mascotas'),
('Belleza', 'Productos de belleza y cuidado personal'),
('Salud', 'Productos de salud y bienestar'),
('Oficina', 'Productos de oficina'),
('Computadoras', 'Computadoras y accesorios'),
('Videojuegos', 'Consolas y videojuegos'),
('Automotriz', 'Accesorios y repuestos de autos'),
('Hogar', 'Productos para el hogar'),
('Joyería', 'Joyas y accesorios'),
('Instrumentos', 'Instrumentos musicales');


INSERT INTO Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId) VALUES
('Juan', 'Perez', 1500.00, '09:00:00', '17:00:00', 1, NULL),
('Maria', 'Lopez', 1400.00, '09:00:00', '17:00:00', 2, 1),
('Carlos', 'Garcia', 1300.00, '09:00:00', '17:00:00', 3, 1),
('Ana', 'Martinez', 1200.00, '09:00:00', '17:00:00', 4, 2),
('Jose', 'Rodriguez', 1100.00, '09:00:00', '17:00:00', 5, 2),
('Laura', 'Gonzalez', 1000.00, '09:00:00', '17:00:00', 6, 3),
('Luis', 'Hernandez', 900.00, '09:00:00', '17:00:00', 7, 3),
('Elena', 'Jimenez', 800.00, '09:00:00', '17:00:00', 8, 4),
('Pedro', 'Fernandez', 700.00, '09:00:00', '17:00:00', 9, 4),
('Sofia', 'Ruiz', 600.00, '09:00:00', '17:00:00', 10, 5),
('David', 'Ramirez', 500.00, '09:00:00', '17:00:00', 11, 5),
('Marta', 'Torres', 400.00, '09:00:00', '17:00:00', 12, 6),
('Sergio', 'Flores', 300.00, '09:00:00', '17:00:00', 13, 6),
('Lucia', 'Rivera', 200.00, '09:00:00', '17:00:00', 14, 7),
('Francisco', 'Gomez', 100.00, '09:00:00', '17:00:00', 15, 7),
('Julia', 'Diaz', 1500.00, '09:00:00', '17:00:00', 16, 8),
('Pablo', 'Cruz', 1400.00, '09:00:00', '17:00:00', 17, 8),
('Sandra', 'Morales', 1300.00, '09:00:00', '17:00:00', 18, 9),
('Andres', 'Ortega', 1200.00, '09:00:00', '17:00:00', 19, 9),
('Natalia', 'Rojas', 1100.00, '09:00:00', '17:00:00', 20, 10);

INSERT INTO Distribuidores (nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web) VALUES
('Distribuidora Alimentos y Bebidas', 'Avenida Reforma 9-30, Zona 10, Guatemala City, Guatemala', '123456-7', '+502-1234-5678', 'www.distribuidora-alimentos.com'),
('Comercializadora de Materiales de Construcción', '7a Avenida 12-34, Zona 1, Quetzaltenango, Guatemala', '987654-3', '+502-2345-6789', 'www.comercializadora-construccion.gt'),
('Distribuciones Automotrices de Guatemala', 'Boulevard Los Próceres 20-30, Zona 15, Guatemala City, Guatemala', '654321-9', '+502-3456-7890', 'www.distribuciones-automotrices.gt'),
('Distribuidora de Productos Farmacéuticos', 'Avenida Las Américas 5-67, Zona 13, Guatemala City, Guatemala', '321654-5', '+502-4567-8901', 'www.distribuidora-farmaceutica.com'),
('Distribuidora de Artículos Deportivos', 'Avenida Petapa 30-45, Zona 12, Guatemala City, Guatemala', '456789-2', '+502-5678-9012', 'www.distribuidora-deportiva.gt'),
('Comercializadora de Electrodomésticos GT', '4a Calle 15-16, Zona 6, Quetzaltenango, Guatemala', '789123-1', '+502-6789-0123', 'www.comercializadora-electrodomesticos.gt'),
('Distribuidora Nacional de Juguetes', 'Avenida Bolívar 25-26, Zona 9, Guatemala City, Guatemala', '147258-3', '+502-7890-1234', 'www.distribuidora-juguetes.gt'),
('Distribuidora de Libros y Material Educativo', '6a Avenida 8-90, Zona 4, Mixco, Guatemala', '258369-8', '+502-8901-2345', 'www.distribuidora-libros.gt'),
('Distribuidora de Ropa y Moda', 'Avenida Hincapié 15-20, Zona 13, Guatemala City, Guatemala', '963852-4', '+502-2468-1357', 'www.distribuidora-ropa.gt'),
('Comercializadora de Productos Cosméticos', '12 Calle 5-30, Zona 9, Quetzaltenango, Guatemala', '741258-9', '+502-7894-5612', 'www.comercializadora-cosmeticos.gt'),
('Distribuidora de Productos Agropecuarios', 'Carretera a Antigua km 10, Ciudad de Guatemala, Guatemala', '852963-1', '+502-3698-1478', 'www.distribuidora-agropecuarios.gt'),
('Distribuidora de Artículos para el Hogar', 'Avenida Las Américas 8-45, Zona 15, Guatemala City, Guatemala', '159357-8', '+502-4567-8523', 'www.distribuidora-hogar.gt'),
('Distribuidora de Equipos Industriales', 'Boulevard Vista Hermosa 21-30, Zona 12, Guatemala City, Guatemala', '753951-2', '+502-7894-6235', 'www.distribuidora-industrial.gt'),
('Comercializadora de Productos Veterinarios', 'Carretera al Pacífico 15-25, Zona 4, Mixco, Guatemala', '369852-7', '+502-1478-3698', 'www.comercializadora-veterinarios.gt'),
('Distribuidora de Equipos Médicos', 'Avenida Las Américas 12-34, Zona 10, Guatemala City, Guatemala', '258147-6', '+502-8523-9632', 'www.distribuidora-medicos.gt'),
('Distribuidora de Productos Ecológicos', 'Avenida Reforma 25-26, Zona 9, Quetzaltenango, Guatemala', '951753-3', '+502-3698-7412', 'www.distribuidora-ecologicos.gt'),
('Distribuidora de Electrónica y Tecnología', '6a Calle 5-10, Zona 14, Guatemala City, Guatemala', '654321-0', '+502-2222-3333', 'www.distribuidora-electronica.gt'),
('Comercializadora de Artículos Escolares', 'Avenida Las Américas 15-30, Zona 11, Guatemala City, Guatemala', '987654-1', '+502-7777-8888', 'www.comercializadora-escolares.gt'),
('Distribuidora de Productos Químicos', '3a Avenida 8-15, Zona 5, Quetzaltenango, Guatemala', '123456-2', '+502-3333-4444', 'www.distribuidora-quimicos.gt'),
('Distribuidora de Neumáticos y Accesorios', 'Avenida Hincapié 10-20, Zona 9, Guatemala City, Guatemala', '789012-3', '+502-5555-6666', 'www.distribuidora-neumaticos.gt');



INSERT INTO Productos (nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId) VALUES
('Laptop HP', 'Laptop HP con 16GB RAM y 512GB SSD', 50, 800.00, 750.00, 700.00, NULL, 17, 15),
('iPhone 13', 'iPhone 13 con 128GB de almacenamiento', 40, 999.00, 950.00, 900.00, NULL, 17, 1),
('Smart TV Samsung', 'Smart TV Samsung 4K de 55 pulgadas', 30, 600.00, 570.00, 550.00, NULL, 17, 1),
('Tablet Samsung', 'Tablet Samsung Galaxy Tab S7', 35, 650.00, 620.00, 600.00, NULL, 17, 1),
('Impresora HP', 'Impresora multifuncional HP LaserJet', 45, 300.00, 280.00, 260.00, NULL, 17, 1),
('Consola PS5', 'Consola PlayStation 5', 25, 500.00, 480.00, 450.00, NULL, 17, 16),
('Teléfono inteligente XYZ', 'Teléfono de última generación con funciones avanzadas.', 50, 1500.00, 1400.00, 1000.00,NULL, 17, 1),
('Televisor LED HD', 'Televisor LED de alta definición con pantalla de 50 pulgadas.', 30, 2500.00, 2300.00, 1800.00,NULL, 17, 1),
('Camisa de vestir', 'Camisa elegante para ocasiones formales.', 100, 500.00, 480.00, 350.00,NULL, 9, 2), 
('Pantalón casual', 'Pantalón cómodo y moderno para uso diario.', 80, 400.00, 380.00, 280.00, NULL,9, 2), 
('Arroz premium', 'Arroz de alta calidad ideal para platos gourmet.', 200, 50.00, 45.00, 30.00,NULL, 1, 3),
('Vino tinto reserva', 'Vino tinto reserva con cuerpo y aroma excepcionales.', 50, 300.00, 280.00, 200.00,NULL, 1, 4), 
('Sofá de cuero', 'Sofá de cuero genuino con diseño contemporáneo.', 10, 3500.00, 3200.00, 2500.00,NULL, 12, 5), 
('Muñeca Barbie', 'Muñeca clásica Barbie con accesorios.', 120, 100.00, 90.00, 70.00, NULL,7, 6), 
('Libro de ciencia ficción', 'Novela de ciencia ficción bestseller del año.', 60, 80.00, 75.00, 50.00,NULL, 8, 7),
('Juego de destornilladores', 'Set de destornilladores profesionales de alta calidad.', 40, 120.00, 110.00, 80.00,NULL, 13, 8), 
('Podadora eléctrica', 'Podadora eléctrica para mantenimiento de jardines.', 25, 600.00, 580.00, 450.00,NULL, 17, 9), 
('Balón de fútbol', 'Balón oficial de fútbol FIFA para entrenamientos.', 70, 50.00, 45.00, 35.00, NULL,5, 10), 
('Comida para perros', 'Alimento balanceado para perros adultos.', 150, 70.00, 65.00, 50.00,NULL, 14, 11), 
('Crema facial', 'Crema facial para reducir líneas de expresión.', 90, 120.00, 110.00, 80.00,NULL, 10, 12), 
('Termómetro digital', 'Termómetro digital para medición precisa de temperatura.', 30, 30.00, 28.00, 20.00,NULL, 4, 13), 
('Impresora multifuncional', 'Impresora multifuncional para uso en oficinas.', 20, 800.00, 750.00, 600.00,NULL, 17, 14), 
('Laptop ultradelgada', 'Laptop ultradelgada con pantalla táctil y procesador rápido.', 15, 2500.00, 2400.00, 1800.00,NULL, 17, 15), 
('Llantas deportivas', 'Llantas deportivas de alto rendimiento para automóviles.', 40, 300.00, 280.00, 200.00,NULL, 20, 17), 
('Juego de sartenes', 'Set de sartenes antiadherentes para cocina.', 60, 150.00, 140.00, 100.00, NULL,12, 18),
('Refrigerador Samsung', 'Refrigerador Samsung French Door', 20, 1500.00, 1450.00, 1400.00, NULL, 6, 18),
('Lavadora Whirlpool', 'Lavadora automática Whirlpool', 30, 700.00, 680.00, 650.00, NULL, 6, 18),
('Cafetera Nespresso', 'Cafetera Nespresso Inissia', 40, 100.00, 95.00, 90.00, NULL, 6, 18);


