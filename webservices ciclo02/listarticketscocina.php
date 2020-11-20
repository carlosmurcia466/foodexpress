<?php 
include("conexion.php");

$fecha=$_GET['fecha'];
$id_proceso=$_GET['id_proceso'];

$query="SELECT dv.ticket_cliente as 'ticket',e.hora as 'hora',e.fecha as 'fecha' from detalle_venta dv inner join venta e on e.ticket_cliente = dv.ticket_cliente inner join factura f on f.ticket_cliente=dv.ticket_cliente inner join proceso_orden po on po.id_proceso=e.id_proceso where e.id_proceso='$id_proceso' and e.fecha='$fecha' group by f.ticket_cliente desc;";

$consulta=$conexion->query($query);


while ($row=$consulta->fetch_array()) {
       $array_datos[]=array_map('utf8_encode',$row);

}

echo json_encode($array_datos);
$consulta->close();


 ?>