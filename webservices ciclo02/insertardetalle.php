<?php
include 'conexion.php';
$ticket_cliente=$_POST['ticket_cliente'];
$id_producto=$_POST['id_producto'];
$cantidad=$_POST['cantidad'];




$query="INSERT INTO detalle_venta(ticket_cliente,id_producto,cantidad)values('$ticket_cliente','$id_producto','$cantidad')";

if(mysqli_query($conexion,$query)){
	echo "Registro agregado exitosamente";
}
else{
	echo "Error agregando el registro";
}
mysqli_close($conexion);

 ?>