<?php
include("conexion.php");
$ticket_cliente=$_POST['ticket_cliente'];
$fecha=$_POST['fecha'];

$query="UPDATE  venta SET id_proceso='2' where ticket_cliente='$ticket_cliente' and fecha='$fecha' and id_proceso='1';";


$consulta=$conexion->query($query);


  ?>