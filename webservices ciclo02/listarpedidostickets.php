<?php 
include("conexion.php");

$fecha=$_GET['fecha'];
$id_proceso=$_GET['id_proceso'];
$ticket_cliente=$_GET['ticket_cliente'];

$query="SELECT cp.categoria_producto as 'categoria',p.nombre_producto as 'nombre',sum(dv.cantidad) as 'cantidad' from detalle_venta dv inner join producto p on p.id_producto=dv.id_producto inner join categoria_producto cp on cp.id_tipo_producto=p.id_tipo_producto inner join venta e on e.ticket_cliente = dv.ticket_cliente inner join factura f on f.ticket_cliente=dv.ticket_cliente inner join proceso_orden po on po.id_proceso=e.id_proceso where e.id_proceso='$id_proceso' and e.fecha='$fecha' and dv.ticket_cliente='$ticket_cliente' Group by p.nombre_producto;";

$consulta=$conexion->query($query);


while ($row=$consulta->fetch_array()) {
       $array_datos[]=array_map('utf8_encode',$row);

}

echo json_encode($array_datos);
$consulta->close();


 ?>