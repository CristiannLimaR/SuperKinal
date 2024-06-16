use superkinaldb;

delimiter $$
create function fn_calcularTotal (factId int) returns decimal(10,2) deterministic
begin

	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare cantidad int;
    declare i int default 1;
    declare curFacturaId, curProductoId int;
    
    
    declare cursorDetalleFactura cursor for 
    select DF.facturaId , DF.productoId from DetalleFactura DF;

    open cursorDetalleFactura;
    
    totalLoop :loop
    fetch cursorDetalleFactura into curFacturaId, curProductoId;
    
    select COUNT(*) into cantidad from DetalleFactura DF
    where DF.facturaId = factId and DF.productoId = curproductoId;
    
    
	
	if factId = curFacturaId then
            if cantidad >= 3 then 
				set precio = (select P.precioVentaMayor from Productos P where P.productoId = curProductoId);
			else
				set precio = (select P.precioVentaUnitario from Productos P where P.productoId = curProductoId);
			end if;
        set total = total + precio;
    end if;
    

    if i = (select count(*) from detalleFactura) then
		leave totalLoop;
	end if;

    set i = i + 1;
    end loop totalLoop;

    call sp_asignarTotalFactura(total, factId);
    
    return total;
end $$
delimiter ;



select * from facturas;
call sp_agregarDetalleFactura(1,2);
delimiter $$
create function fn_calcularTotalCompra (comId int) returns decimal(10,2) deterministic
begin

	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curCompraId, curProductoId int;
    declare curCantidad decimal(10,2);
    
    
    declare cursorDetalleCompra cursor for 
    select DC.cantidadCompra, DC.productoId,DC.compraId from DetalleCompra DC;

    open cursorDetalleCompra;
    
    totalLoop :loop

    fetch cursorDetalleCompra into curCantidad, curProductoId,curCompraId;


	if comId = curCompraId then
		set precio = curCantidad * (select P.precioCompra from Productos P where P.productoId = curProductoId);
        set total = total + precio;
    end if;

    if i = (select count(*) from detalleCompra) then
		leave totalLoop;
	end if;

    set i = i + 1;
    end loop totalLoop;

    call sp_asignarTotalCompra(comId,total);
    
    return total;
end $$
delimiter ;

delimiter $$
create trigger tg_agregarTotalFactura
after insert on DetalleFactura
for each row
Begin
	declare total decimal(10,2);
    set total = (select fn_calcularTotal(new.facturaId));
end$$
delimiter ;

delimiter $$
create trigger tg_restarStock
after insert on detallefactura
for each row
begin
	call sp_restarStock(New.productoId);
end$$
delimiter ;

delimiter $$
create trigger tg_agregarTotalCompra
after insert on DetalleCompra
for each row 
begin
	declare total decimal(10,2);
    set total = (select fn_calcularTotalCompra(new.compraId));
end$$
delimiter ;


delimiter $$
create trigger tg_agregarStock
after insert on detalleCompra
for each row
begin 
	call sp_agregarStock(New.productoId,new.cantidadCompra);
end$$
delimiter ;

DELIMITER $$

CREATE TRIGGER trg_actualizar_precio_unitario
AFTER INSERT ON Promociones
FOR EACH ROW
BEGIN
    DECLARE old_precio DECIMAL(10,2);
    
    SELECT precioVentaUnitario INTO old_precio
    FROM Productos
    WHERE productoId = NEW.productoId;
    
    UPDATE Productos
    SET precioVentaUnitario = NEW.precioPromocion
    WHERE productoId = NEW.productoId;
 
    INSERT INTO HistorialPrecios (productoId, precioAnterior)
    VALUES (NEW.productoId, old_precio);
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER trg_restaurar_precio_unitario
BEFORE DELETE ON Promociones
FOR EACH ROW
BEGIN
    DECLARE old_precio DECIMAL(10,2);
    
  
    SELECT precioAnterior INTO old_precio
    FROM HistorialPrecios
    WHERE productoId = OLD.productoId
    ORDER BY fechaCambio DESC
    LIMIT 1;
    
    
    UPDATE Productos
    SET precioVentaUnitario = old_precio
    WHERE productoId = OLD.productoId;
    
END$$

DELIMITER ;



