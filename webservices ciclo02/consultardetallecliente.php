<?php 

define('DB_HOST','mysql.webcindario.com');
define('DB_USER', 'foodexpress');
define('DB_PASS','elsalvador466');
define('DB_NAME', 'foodexpress');

$iduser=$_GET['id_usuario'];
$fecha=$_GET['fecha'];
$id_proceso=$_GET['id_proceso'];

$conn =new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

if(mysqli_connect_errno()){
  echo "fallo la conexion: ".mysqli_connect_errno();
  die();
}


$stmt=$conn->prepare("SELECT   pr.nombre_producto as 'nombre_producto',pr.precio as 'precio',sum(dv.cantidad) as 'cantidad',pr.imagen as 'imagen',dv.id_producto as 'id_producto',dv.ticket_cliente as 'ticket_cliente',e.id_proceso as 'id_proceso' from detalle_venta dv inner join  producto pr on pr.id_producto=dv.id_producto inner join venta e on e.ticket_cliente = dv.ticket_cliente where e.id_usuario='$iduser' and e.fecha='$fecha' and e.id_proceso='$id_proceso' Group by pr.nombre_producto;");

$stmt->execute();
$stmt->bind_result($nombre_producto,$precio,$cantidad,$imagen,$id_producto,$ticket_cliente,$id_proceso);
$categoria =array();

while ($stmt->fetch()) {
	$temp=array();
	$temp['nombre_producto']=$nombre_producto;
	$temp['precio']=$precio;
	$temp['cantidad']=$cantidad;
	$temp['imagen']=$imagen;
	$temp['id_producto']=$id_producto;
	$temp['ticket_cliente']=$ticket_cliente;
	$temp['id_proceso']=$id_proceso;
	
	array_push($categoria, $temp);
}
echo json_encode($categoria);





 ?>