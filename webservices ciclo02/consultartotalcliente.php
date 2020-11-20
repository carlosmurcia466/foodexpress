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


$stmt=$conn->prepare("SELECT sum(dv.cantidad*pr.precio) as 'total' FROM `detalle_venta` dv inner join  `producto` pr on pr.id_producto=dv.id_producto    inner join `venta` e on e.ticket_cliente = dv.ticket_cliente where e.id_usuario='$iduser' and e.fecha='$fecha' and e.id_proceso='$id_proceso';");

$stmt->execute();
$stmt->bind_result($total);
$categoria =array();

while ($stmt->fetch()) {
	$temp=array();
	$temp['total']=$total;

	
	array_push($categoria, $temp);
}
echo json_encode($categoria);





 ?>