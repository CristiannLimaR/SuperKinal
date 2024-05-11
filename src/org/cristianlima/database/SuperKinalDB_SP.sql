
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
create procedure sp_agregarCategoriaProducto(nomCat varchar(30),desCat varchar(100))
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
create procedure sp_buscarCategoriaProducto(catProId int)
	begin
		select * from CategoriaProductos 
        where catProId = categoriaProductosId;
    end$$
DELIMITER ;


DELIMITER $$
create procedure sp_eliminarCategoriaProducto(catProId int)
	begin 
		delete from CategoriaProductos
        where catProId = categoriaProductosId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_editarCategoriaProducto(catProId int,nomCat varchar(30),desCat varchar(100) )
	begin
		update CategoriaProductos set
		nombreCategoria = nomCat,
        descripcionCategoria = desCat
        where catProId = categoriaProductosId;
    end$$
DELIMITER ;

-- -------------------- DetalleFactura ---------------------------------------
DELIMITER $$
create procedure sp_agregarDetalleFactura(in factId int, in prodId int)
begin
	insert into DetalleFactura(facturaId, productoId) values
		(factId, prodId);
end $$
DELIMITER ;

 
DELIMITER $$
create procedure sp_ListarDetalleFacturas()
begin
	select F.facturaId, F.fecha,F.hora,
		concat('Id: ' , P.productoId , ' | ' , P.nombreProducto) as 'Producto', concat('U: ', P.precioVentaUnitario, '| ', 'M: ', P.precioVentaMayor) as 'Precios',
		concat('Id: ', E.empleadoId, ' | ', E.nombreEmpleado, ' ', E.apellidoEmpleado) as 'Empleado',
		concat('Id: ', C.clienteId,'| ',C.nombre, ' ',C.apellido) as 'Cliente',
		F.total from DetalleFactura DE
    join Productos P on DE.productoId = P.productoId
    join Facturas F on DE.facturaId = F.facturaId
    join Empleados E on F.empleadoId = E.empleadoId
    join Clientes C on F.clienteId = C.clienteId;
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
		select F.facturaId, F.fecha,F.hora,
		concat('Id: ' , P.productoId , ' | ' , P.nombreProducto) as 'Producto', concat('U: ', P.precioVentaUnitario, '| ', 'M: ', P.precioVentaMayor) as 'Precios',
		concat('Id: ', E.empleadoId, ' | ', E.nombreEmpleado, ' ', E.apellidoEmpleado) as 'Empleado',
		concat('Id: ', C.clienteId,'| ',C.nombre, ' ',C.apellido) as 'Cliente',
		F.total from DetalleFactura DE
		join Productos P on DE.productoId = P.productoId
		join Facturas F on DE.facturaId = F.facturaId
		join Empleados E on F.empleadoId = E.empleadoId
		join Clientes C on F.clienteId = C.clienteId
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
            concat('Id :' , F.facturaId , 'fecha: ' , F.fecha , 'hora: ' , F.hora) as 'Factura' from TicketSoporte TS
    join Clientes C on TS.clienteId = C.clienteId
    join Facturas F on TS.facturaId = F.facturaId;
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
create procedure sp_agregarEmpleado(in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in hoEn time, in hoSa time, in carId int, in encaId int)
	begin
		insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId,encargadoId) values 
			(nomEmp, apeEmp, sue, hoEn, hoSa, carid,encaId);
    end $$
DELIMITER ;
 
 
DELIMITER $$
create procedure sp_listarEmpleados()
	begin
		select E1.empleadoId, E1.nombreEmpleado, E1.apellidoEmpleado, E1.sueldo, E1.horaEntrada, E1.horaSalida,
        Concat('Id: ' , C.cargoId ,  ' | ' , C.nombreCargo) as 'Cargo',
        Concat('Id: ', E2.empleadoId ,  ' | ' , E2.nombreEmpleado,' ', E2.apellidoEmpleado) as 'Encargado' from Empleados E1
        join Cargos C on E1.cargoId = C.cargoId
        left join Empleados E2 on E1.encargadoId = E2.empleadoId;
    end $$
DELIMITER ;


 
DELIMITER $$
create procedure sp_buscarEmpleado(in empId int)
	begin
		select E1.empleadoId, E1.nombreEmpleado, E1.apellidoEmpleado, E1.sueldo, E1.horaEntrada, E1.horaSalida,
        Concat('Id: ' + C.cargoId +  ' | ' + C.nombreCargo)as 'Cargo',
        Concat('Id: ', E2.empleadoId ,  ' | ' , E2.nombreEmpleado,' ', E2.apellidoEmpleado) as 'Encargado' from Empleados E1
        join Cargos C on E1.cargoId = C.cargoId
        left join Empleados E2 on E1.encargadoId = E2.empleadoId
			where E1.empleadoId = empId;
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
            apellidoEmpleado = apeEmp,
            sueldo = sue,
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
create procedure sp_agregarFactura( in cliId int, in empId int,in proId int) 
	begin
		declare id int;
		insert into Facturas (fecha, hora, clienteId, empleadoId) values 
			(DATE(NOW()),TIME(NOW()), cliId, empId);
		set id = last_insert_id();
        
        call sp_agregarDetalleFactura(id, proId);
		
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


DELIMITER $$
create procedure sp_asignarTotalFactura(in tot decimal(10,2),factId int)
	begin
		update Facturas set
        total = tot *(1 + 0.12)
        where facturaId = factId;
    end$$
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
		select Prom.promocionId,Prom.precioPromocion, Prom.descripcionPromocion as 'Descripcion',Prom.fechaInicio,Prom.fechaFinalizacion,
				concat('Id: ' , P.productoId , ' | ' , P.nombreProducto) as 'Producto' from Promociones Prom
                join Productos P on Prom.productoId = P.productoId;
    end $$
DELIMITER ;
 
 

DELIMITER $$
create procedure sp_buscarPromocion(in promoId int)
	begin
		select Prom.promocionId,Prom.precioPromocion, Prom.descripcionPromocion as 'Descripcion',Prom.fechaInicio,Prom.fechaFinalizacion,
				concat('Id: ' , P.productoId , ' | ' , P.nombreProducto) as 'Producto' from Promociones Prom
                join Productos P on Prom.productoId = P.productoId
				where Prom.promocionId = promoId;
    end $$
DELIMITER ;
 

DELIMITER $$
create procedure sp_eliminarPromocion(in promoId int)
	begin
		delete 
			from Promociones
				where promocionId = promoId;
    end $$
DELIMITER ;
 

DELIMITER $$
create procedure sp_editarPromocion(in promoId int, in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
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
create procedure sp_listarCompras()
	begin
		select * from Compras;
    end $$
DELIMITER ;


DELIMITER $$
create procedure sp_agregarCompra(in cant int,in proId int )
	begin 
		declare compraId int;
		insert into Compras (fechaCompra) values
			(Date(now()));
            
		set compraId = last_insert_id();
        
        call sp_agregarDetalleCompra(cant, proId, compraId);
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_buscarCompra(in comId int)
	begin	
		select * from Compras 
			where compraId = comId;
    end $$
DELIMITER ;
DELIMITER $$
create procedure sp_editarCompra(in comId int,in fecCom date)
	begin 
		update Compras
			set 
				fechaCompra = fecCom
                where compraId = comId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_eliminarCompra(in comId int)
	begin 
		delete from Compras
        where compraId = comId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_asignarTotalCompra(in comId int,in totCom decimal (10,2))
	begin 
		update Compras
			set
                totalCompra = totCom
                where compraId = comId;
    end $$
DELIMITER ;

-- ------------------------------------------------------Productos-------------------------------------------------------------------
DELIMITER $$
create procedure sp_listarProductos()
	begin 
		select P.productoId,P.nombreProducto,P.descripcionProducto,P.cantidadStock,P.precioVentaUnitario,P.precioVentaMayor,P.precioCompra,P.imagenProducto,
        concat('Id: ' , D.distribuidorId , ' | ' ,  D.nombreDistribuidor) as 'Distribuidor',
        concat('Id: ' , C.categoriaProductosId , ' | ' ,  C.nombreCategoria) as 'Categoria'from Productos P
        join Distribuidores D on P.distribuidorId = D.distribuidorId
        join CategoriaProductos C on P.categoriaProductosId = C.categoriaProductosId;
    end $$
DELIMITER ;


DELIMITER $$
create procedure sp_agregarProducto(in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima mediumblob, in disId int, in catId int)
	begin
		insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId ) values
			(nom, des, can, preU, preM, preC, ima, disId, catId);
	end $$
DELIMITER ;


DELIMITER $$
create procedure sp_buscarProducto(in proId int)
	begin 
		select P.productoId,P.nombreProducto,P.descripcionProducto,P.cantidadStock,P.precioVentaUnitario,P.precioVentaMayor,P.precioCompra,P.imagenProducto,
        concat('Id: ' , D.distribuidorId , ' | ' ,  D.nombreDistribuidor) as 'Distribuidor',
        concat('Id: ' , C.categoriaProductosId , ' | ' ,  C.nombreCategoria) as 'Categoria'from Productos P
        join Distribuidores D on P.distribuidorId = D.distribuidorId
        join CategoriaProductos C on P.categoriaProductosId = C.categoriaProductosId
        where productoId = proId;
    end $$
DELIMITER ;


DELIMITER $$
create procedure sp_editarProducto(in proId int, in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima mediumblob, in disId int, in catId int )
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



DELIMITER $$
create procedure sp_eliminarProducto(in proId int)
	begin
		delete from Productos
			where productoId = proId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_restarStock(in proId int)
	begin 
		update Productos set
        cantidadStock = cantidadStock - 1
        where productoId = proId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_agregarStock(in proId int,in cant int)
	begin 
		update Productos set
        cantidadStock = cantidadStock + cant
        where productoId = proId;
    end $$
DELIMITER ;
-- ----------------------------------------------------DetalleCompra------------------------------------------------------------------
DELIMITER $$
create procedure sp_ListarDetalleCompras()
	begin 
		select C.compraId, C.fechaCompra,C.totalCompra,
				concat('Id: ' , P.productoId , ' | ' , P.nombreProducto) as 'Producto', P.precioCompra,
                DC.cantidadCompra from DetalleCompra DC
                join Compras C on DC.compraId = C.compraId
                join Productos P on DC.productoId = P.productoId;
    end $$
DELIMITER ;

DELIMITER $$
create procedure sp_agregarDetalleCompra(in canC int, in proId int,in comId int)
	begin 
		insert into DetalleCompra(cantidadCompra, productoId, compraId)values
			(canC, proId, comId);
    end $$
DELIMITER ;


DELIMITER $$
create procedure sp_buscarDetalleCompra(in comId int)
	begin 
		select C.compraId, C.fechaCompra,C.totalCompra,
				concat('Id: ' , P.productoId , ' | ' , P.nombreProducto) as 'Producto', P.precioCompra,
                DC.cantidadCompra from DetalleCompra DC
                join Compras C on DC.compraId = C.compraId
                join Productos P on DC.productoId = P.productoId
			where DC.compraId = comId;
    end $$
DELIMITER ;


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
 
DELIMITER $$
create procedure sp_eliminarDetalleCompra(in detCId int)
	begin 
    delete from DetalleCompra 
			where detalleCompraId = detCId;
    end $$
DELIMITER ;
