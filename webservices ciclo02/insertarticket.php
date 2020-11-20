<?php
include 'conexion.php';
$id_usuario=$_POST['id_usuario'];
$fecha=$_POST['fecha'];
$hora=$_POST['hora'];
$id_proceso=$_POST['id_proceso'];



$query="INSERT INTO venta(id_usuario,fecha,hora,id_proceso)values('$id_usuario','$fecha','$hora','$id_proceso')";

if(mysqli_query($conexion,$query)){
	echo "Registro agregado exitosamente";
}
else{
	echo "Error agregando el registro";
}
mysqli_close($conexion);

 ?>