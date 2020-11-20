<?php
include 'conexion.php';
$ticket_cliente=$_POST['ticket_cliente'];
$total=$_POST['total'];


$query="INSERT INTO factura(ticket_cliente,total)values('$ticket_cliente','$total');";

if(mysqli_query($conexion,$query)){
	echo "Registro agregado exitosamente";
}
else{
	echo "Error agregando el registro";
}
mysqli_close($conexion);

 ?>