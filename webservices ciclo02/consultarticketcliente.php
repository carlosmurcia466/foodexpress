<?php
include ('cn.php'); 

$iduser=$_GET['id_usuario'];
$fecha=$_GET['fecha'];
$id_proceso=$_GET['id_proceso'];
$ticket_cliente=$_GET['ticket_cliente'];

if ($resultset = getSQLResultSet("SELECT v.fecha,v.id_proceso,v.ticket_cliente,dv.ticket_cliente FROM venta v inner join detalle_venta dv on v.ticket_cliente=dv.ticket_cliente WHERE id_usuario='$iduser' AND fecha='$fecha' AND id_proceso='$id_proceso' and dv.ticket_cliente='$ticket_cliente'")) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	
    	}
 }
   
?>

