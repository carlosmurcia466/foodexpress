<?php
include("conexion.php");
$id_usuario=$_POST['id_usuario'];
$fecha=$_POST['fecha'];

$query="UPDATE  venta SET id_proceso='1' where id_usuario='$id_usuario' and fecha='$fecha' and id_proceso='0';";


$consulta=$conexion->query($query);


  ?>