
use superkinaldb;

-- ----------------------------------------- CLIENTES ---------------------------------------------------------------
DELIMITER $$
create procedure sp_agregarCliente(nom varchar(30),ape varchar(30), tel varchar(30),dir varchar(150), nit varchar(30) )
	begin 
		insert into Clientes(nombre, apellido, telefono, direccion, nit)values
		(nom, ape, tel, dir,nit);
	end$$
DELIMITER ;

DELIMITER $$
create procedure sp_listarClientes()
	begin 
		select * from Clientes;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_BuscarCliente(cliId int)
	begin 
		select * from Clientes
        where cliId = clienteId;
    end $$ 
DELIMITER ;

DELIMITER $$
create procedure sp_EliminarCLiente(cliId int)
	begin 
		delete from Clientes 
        where cliId = clienteId;
    end $$
DELIMITER ;


DELIMITER $$
create procedure sp_EditarCliente (cliId int, nom varchar(30),ape varchar(30), tel varchar(30),dir varchar(150), nit varchar(30))
	begin 
		update Clientes  set
        nombre = nom,
        apellido = ape,
        telefono = tel,
        direccion = dir,
        nit = nit
        where cliId = clienteId;
    end $$
DELIMITER ;

-- ------------------------------------------------------------ CARGO -----------------------------------------------
DELIMITER $$
create procedure sp_agregarCargo(nomCar varchar(30), desCar varchar(100)) 
	begin
		insert into Cargos(nombreCargo, descripcionCargo) values
        (nomCar,desCar);
	end$$
DELIMITER ;

DELIMITER $$
create procedure sp_listarCargos()
	begin 
		select * from Cargos;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_buscarCargo(carId int)
	begin
		select * from Cargos 
        where carId = cargoId;
    end$$
DELIMITER ;

DELIMITER $$
create procedure sp_eliminarCargo(carId int)
	begin
		delete from Cargos 
        where carId = cargoId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_editarCargo(carId int, nomCar varchar(30), desCar varchar(100))
	begin 
		update Cargos  set
			nombreCargo = nomCar,
			descripcionCargo = desCar
			where carId = cargoId;
    end $$
DELIMITER ;

-- ------------------------------------------------------ DISTRIBUIDORES -------------------------------------------------------

DELIMITER $$
create procedure sp_agregarDistribuidor(nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),web varchar(50))
	begin
		insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web) values
			(nomDis,dirDis,nitDis,telDis,web);
    end$$
DELIMITER ;

DELIMITER $$
create procedure sp_listarDistribuidores()
	begin
		select * from Distribuidores;
    end$$
DELIMITER ;

DELIMITER $$
create procedure sp_buscarDistribuidor(disId int)
	begin 
		select * from Distribuidores D 
        where disId = D.distribuidorId;
    end$$
DELIMITER ;

DELIMITER $$
create procedure sp_eliminarDistribuidor(dirId int)
	begin
		delete from Distribuidores 
        where dirId = distribuidorId;
    end$$
DELIMITER ;

DELIMITER $$
create procedure sp_editarDistribuidor(dirId int, nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),web varchar(50))
	begin
		update Distribuidores set
        nombreDistribuidor = nomDis,
        direccionDistribuidor = dirDis,
        nitDistribuidor = nitDis,
        telefonoDistribuidor = telDis,
        web = web
        where dirId = distribuidorId;
    end$$
DELIMITER ;
-- ---------------------------------------------------CATEGORIA PRODUCTOS --------------------------------------
DELIMITER $$
create procedure sp_agregarCategoriaProductos(nomCat varchar(30),desCat varchar(100))
	begin 
		insert into CategoriaProductos(nombreCategoria,descripcionCategoria) values
			(nomCat,desCat);
    end$$
DELIMITER ;

DELIMITER $$
create procedure sp_listarCategoriaProductos()
	begin
		select * from CategoriaProductos;
    end$$
DELIMITER ;

DELIMITER $$
create procedure sp_buscarCategoriaProductos(catProId int)
	begin
		select * from CategoriaProductos 
        where catProId = categoriaProductosId;
    end$$
DELIMITER ;


DELIMITER $$
create procedure sp_eliminarCategoriaProductos(catProId int)
	begin 
		delete from CategoriaProductos
        where catProId = categoriaProductosId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_editarCategoriaProductos(catProId int,nomCat varchar(30),desCat varchar(100) )
	begin
		update CategoriaProductos set
		nombreCategoria = nomCat,
        descripcionCategoria = desCat
        where catProId = categoriaProductosId;
    end$$
DELIMITER ;

-- -------------------- DetalleFactura ---------------------------------------
DELIMITER $$
create procedure sp_AgregarDetalleFactura(in factId int, in prodId int)
begin
	insert into DetalleFactura(facturaId, productoId) values
		(factId, prodId);
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_ListarDetalleFactura()
begin
	select 
		DetalleFactura.detalleFacturaId,
        DetalleFactura.facturaId,
        DetalleFactura.productoId
			from DetalleFactura;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_EliminarDetalleFactura(in detId int)
begin
	delete
		from DetalleFactura
			where detalleFacturaId = detId;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_BuscarDetalleFactura(in detId int)
begin
	select 
		DetalleFactura.detalleFacturaId,
        DetalleFactura.facturaId,
        DetalleFactura.productoId
			from DetalleFactura
			where detalleFacturaId = detId;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_EditarDetalleFactura(in detId int, in factId int, in prodId int)
begin
	update DetalleFactura
		set 
			facturaId = factId,
            productoId = prodId
            where detalleFacturaId = detId;
end $$
DELIMITER ;

-- --------------------------------------TICKET SOPORTE -------------------------------------------------------
DELIMITER $$
create procedure sp_agregarTicketSoporte(in des varchar(250), in cliId int, in facId int)
begin
	insert into TicketSoporte(descripcionTicket,estatus,clienteId,facturaId) values
		(des,'Recien Creado',cliId,facId);
end $$
DELIMITER ;


 
DELIMITER $$
create procedure sp_ListarTicketSoporte()
begin
	select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus, 
			concat('Id: ', C.clienteId,'| ',C.nombre, ' ',C.apellido)  AS 'Cliente',
            TS.facturaId from TicketSoporte TS
    join Clientes C on TS.clienteId = C.clienteId;
end $$
DELIMITER ;


 
DELIMITER $$
create procedure sp_EliminarTicketSoporte(in ticId int)
begin
	delete from TicketSoporte
			where ticketSoporteId = ticId;
end$$
DELIMITER ;
 
DELIMITER $$
create procedure sp_BuscarTicketSoporte(in ticId int)
begin 
	select
		TicketSoporte.ticketSoporteId,
        TicketSoporte.descripcionTicket,
        TicketSoporte.estatus,
        TicketSoporte.clienteId,
        TicketSoporte.facturaId
			from TicketSoporte
			where ticketSoporteId = ticId;
end $$
DELIMITER ;
 
DELIMITER $$
create procedure sp_EditarTicketSoporte(in ticId int,in des varchar(250), in est varchar(30), in cliId int, in facId int )
begin
	update TicketSoporte
		set 
			descripcionTicket = des,
            estatus = est,
            clienteId = cliId,
            facturaId = facId
				where ticketSoporteId = ticId;
end $$
DELIMITER ;


-- --------------------------------- Empleados --------------------------------------
 
DELIMITER $$
create procedure sp_agregarEmpleado(in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in hoEn time, in hoSa time, in carId int)
	begin
		insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId) values 
			(nomEmp, apeEmp, sue, hoEn, hoSa, carid);
    end $$
DELIMITER ;
 
 
DELIMITER $$
create procedure sp_listarEmpleados()
	begin
		select * from Empleados;
    end $$
DELIMITER ;
 
 
DELIMITER $$
create procedure sp_buscarEmpleado(in empId int)
	begin
		select * from Empleados
			where empleadoId = empId;
    end $$
DELIMITER ;
 
 
DELIMITER $$
create procedure sp_eliminarEmpleado(in empId int)
	begin
		delete 
			from Empleados
				where empleadoId = empId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_editarEmpleado(in empId int, in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in hoEn time, in hoSa time, in carId int, in encarId int)
	begin
		update Empleados
			set 
            nombreEmpleado = nomEmp,
            apeEmp = apellidoEmpleado,
            sueldo = suel,
            horaEntrada = hoEn,
            horaSalida = hoSa,
            cargoId = carId,
            encargadoId = encarId
            where empleadoId = empId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_editarEncargado(in empId int, encarId int)
	begin
		update Empleados
			set
            encargadoId = encarId;
    end$$
DELIMITER ;
-- ----------------- FACTURAS -------------------------------------
 
DELIMITER $$
create procedure sp_agregarFactura( in cliId int, in empId int)
	begin
		insert into Facturas (fecha, hora, clienteId, empleadoId) values 
			(DATE(NOW()),TIME(NOW()), cliId, empId);
    end $$
DELIMITER ;


 
 
DELIMITER $$
create procedure sp_listarFacturas()
	begin
		select * from Facturas;
    end $$
DELIMITER ;
 
 

DELIMITER $$
create procedure sp_buscarFacturas(in facId int)
	begin
		select * from Facturas
			where facturaId = facId;
    end $$
DELIMITER ;
 
 

DELIMITER $$
create procedure sp_eliminarFacturas(in facId int)
	begin
		delete 
			from Facturas
				where facturaId = facId;
    end $$
DELIMITER ;
 

DELIMITER $$
create procedure sp_editarFacturas(in facId int, in tot decimal(10, 2), in cliId int, in empId int)
	begin
		update Facturas
			set 
            fecha = fe,
            hora = ho,
            total = tot,
            clienteId = cliId,
            empleadoId = empId
            where facturaId = facId;
    end $$
DELIMITER ;
 
-- --------------------- Promociones ------------------------------------------
 

DELIMITER $$
create procedure sp_agregarPromocion(in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		insert into Promociones (precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId) values 
			(prePro, descPro, feIni, feFina, proId);
    end $$
DELIMITER ;
 

DELIMITER $$
create procedure sp_listarPromociones()
	begin
		select * from Promociones;
    end $$
DELIMITER ;
 
 

DELIMITER $$
create procedure sp_buscarPromociones(in promoId int)
	begin
		select * from Promociones
			where promocionId = promoId;
    end $$
DELIMITER ;
 

DELIMITER $$
create procedure sp_eliminarPromociones(in promoId int)
	begin
		delete 
			from Promociones
				where promocionId = promoId;
    end $$
DELIMITER ;
 

DELIMITER $$
create procedure sp_editarPromociones(in promoId int, in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		update Promociones
			set 
            precioPromocion = prePro,
            descripcionPromocion = descPro,
            fechaInicio = feIni,
            fechaFinalizacion = feFina,
            productoId = proId
            where promocionId = promoId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_listarCompra()
	begin
		select * from Compras;
    end $$
DELIMITER ;
-- agregar
DELIMITER $$
create procedure sp_agregarCompra(in fecCom date, in totCom decimal (10,2))
	begin 
		insert into Compras (fechaCompra, totalCompra) values
			(fecCom, totCom);
    end $$
DELIMITER ;
-- buscar
DELIMITER $$
create procedure sp_buscarCompra(in comId int)
	begin	
		select * from Compras 
			where compraId = comId;
    end $$
DELIMITER ;
-- editar
DELIMITER $$
create procedure sp_editarCompra(in comId int,in fecCom date,in totCom decimal (10,2))
	begin 
		update Compras
			set 
				fechaCompra = fecCom,
                totalCompra = totCom
                where compraId = comId;
    end $$
DELIMITER ;
-- eliminar 
DELIMITER $$
create procedure sp_eliminarCompra(in comId int)
	begin 
		delete from Compras
        where compraId = comId;
    end $$
DELIMITER ;

-- ------------------------------------------------------Productos-------------------------------------------------------------------
-- listar
DELIMITER $$
create procedure sp_listarProductos()
	begin 
		select * from Productos;
    end $$
DELIMITER ;
-- agregar
DELIMITER $$
create procedure sp_agregarProducto(in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima blob, in disId int, in catId int)
	begin
		insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId ) values
			(nom, des, can, preU, preM, preC, ima, disId, catId);
	end $$
DELIMITER ;


-- buscar
DELIMITER $$
create procedure sp_buscarProducto(in proId int)
	begin 
		select * from Productos
        where productoId = proId;
    end $$
DELIMITER ;
-- editar
DELIMITER $$
create procedure sp_editarProducto(in proId int, in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima blob, in disId int, in catId int )
	begin
		update Productos	
			set 
            nombreProducto = nom,
            descripcionProducto = des,
            cantidadStock = can,
            precioVentaUnitario = preU,
            precioVentaMayor = preM,
            precioCompra = preC,
            imagenProducto = ima,
            distribuidorId = disId,
            categoriaProductosId = catId
            where productoId = proId;
    end $$
DELIMITER ;
-- eliminar 
create procedure sp_eliminarProducto(in proId int)
	begin
		delete from Productos
			where productoId = proId
    end $$
-- ----------------------------------------------------DetalleCompra------------------------------------------------------------------
-- listar
DELIMITER $$
create procedure sp_ListarDetalleCompra()
	begin 
		select * from DetalleCompra;
    end $$
DELIMITER ;
-- agregar
DELIMITER $$
create procedure sp_agregarDetalleCompra(in canC int, in proId int,in comId int)
	begin 
		insert into DetalleCompra(cantidadCompra, productoId, compraId)values
			(canC, proId, comId);
    end $$
DELIMITER ;
-- buscar
DELIMITER $$
create procedure sp_buscarDetalleCompra(in detCId int)
	begin 
		select * from DetalleCompra
			where detalleCompraId = detCId;
    end $$
DELIMITER ;
-- editar
DELIMITER $$
create procedure sp_editarDetalleCompra(in detCId int, in canC int, in proId int,in comId int)
	begin 
		update DetalleCompra
			set 
				cantidadCompra = canC,
                productoId = proId,
                compraId = comId
                where detalleCompraId = detCId;
    end $$
DELIMITER ;
-- eliminar 
DELIMITER $$
create procedure sp_eliminarDetalleCompra(in detCId int)
	begin 
    delete from DetalleCompra 
			where detalleCompraId = detCId;
    end $$
DELIMITER ;

