<?php
include ('cn.php'); 

$iduser=$_GET['id_usuario'];
$fecha=$_GET['fecha'];
$id_proceso=$_GET['id_proceso'];

if ($resultset = getSQLResultSet("SELECT fecha,id_proceso,ticket_cliente FROM `venta` WHERE id_usuario='$iduser' AND fecha='$fecha' AND id_proceso='$id_proceso'")) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	
    	}
 }
   
?>