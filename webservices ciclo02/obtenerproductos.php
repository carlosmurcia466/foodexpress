<?php 

define('DB_HOST','mysql.webcindario.com');
define('DB_USER', 'foodexpress');
define('DB_PASS','elsalvador466');
define('DB_NAME', 'foodexpress');

$conn =new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

$id_tipo_producto=$_GET['id_tipo_producto'];

if(mysqli_connect_errno()){
  echo "fallo la conexion: ".mysqli_connect_errno();
  die();
}

$stmt=$conn->prepare("SELECT id_producto,nombre_producto,id_tipo_producto,precio,detalle_producto,imagen FROM producto WHERE id_tipo_producto='$id_tipo_producto';");

$stmt->execute();
$stmt->bind_result($id_producto,$nombre_producto,$id_tipo_producto,$precio,$detalle_producto,$imagen);
$categoria =array();

while ($stmt->fetch()) {
	$temp=array();
	$temp['id_producto']=$id_producto;
	$temp['nombre_producto']=$nombre_producto;
	$temp['id_tipo_producto']=$id_tipo_producto;
	$temp['precio']=$precio;
	$temp['detalle_producto']=$detalle_producto;
	$temp['imagen']=$imagen;

	array_push($categoria, $temp);
}
echo json_encode($categoria);





 ?>