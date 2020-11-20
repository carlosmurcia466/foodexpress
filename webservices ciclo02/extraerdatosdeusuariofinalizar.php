<?php
include ('cn.php'); 
$user=$_GET['user'];
if ($resultset = getSQLResultSet("SELECT u.nombre_completo as 'nombre',u.telefono as 'telefono',u.departamento as 'departamento',u.direccion_completa as 'direccion',u.detalle_direccion as 'detalle',f.total as 'total' from usuarios u inner join venta v on v.id_usuario=u.id_usuario inner join detalle_venta dv on dv.ticket_cliente =v.ticket_cliente inner join factura f on f.ticket_cliente=dv.ticket_cliente where v.ticket_cliente='$user' group by nombre_completo")) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	
    	}
 }
   
?>