use superkinaldb;

delimiter $$

create procedure sp_agregarDetalleFactura(in facId int,in proId int)

	begin

		insert into DetalleFactura(facturaId, productoId) values 

		(facId, proId);

	end $$

delimiter ;

 
delimiter $$

create procedure sp_asignarTotal(in tot decimal(10,2), in facId int)

begin 

	update Facturas
		set total = tot 
		where facturaId = facId;

end$$

delimiter ;
 
 
    

delimiter $$
create function fn_calcularTotal (factId int) returns decimal(10,2) deterministic
begin

	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacturaId, curProductoId int;
    declare curPromPrecio decimal(10,2);
    
    
    declare cursorDetalleFactura cursor for 
    select DF.facturaId , DF.productoId from DetalleFactura DF;

    open cursorDetalleFactura;
    
    totalLoop :loop

    fetch cursorDetalleFactura into curFacturaId, curProductoId;
    
    select PR.precioPromocion into curPromPrecio
        from Promociones PR
        where PR.productoId = curProductoId
        and NOW() between PR.fechaInicio and PR.fechaFinalizacion
        order by PR.fechaInicio desc
        Limit 1;

	if factId = curFacturaId then
			if curPromPrecio is not null then
				set precio = curPromPrecio;
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

    call sp_asignarTotal(total, factId);
    
    return total;
end $$
delimiter ;



delimiter $$
create trigger tg_agregarTotal
after insert on DetalleFactura
for each row
Begin

	declare total decimal(10.2);

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
create trigger tg_agregarStock
after insert on detalleCompra
for each row
begin 
	call sp_agregarStock(New.productoId,new.cantidadCompra);
end$$
delimiter ;