<?php
include("conexion.php");
$ticket_cliente=$_POST['ticket_cliente'];
$fecha=$_POST['fecha'];

$query="UPDATE  venta SET id_proceso='4' where ticket_cliente='$ticket_cliente' and fecha='$fecha' and id_proceso='3';";


$consulta=$conexion->query($query);


  ?>