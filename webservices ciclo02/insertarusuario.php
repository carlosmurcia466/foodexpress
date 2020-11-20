<?php
include 'conexion.php';
$nombre_completo=$_POST['nombre_completo'];
$telefono=$_POST['telefono'];
$correo=$_POST['correo'];
$departamento=$_POST['departamento'];
$direccion_completa=$_POST['direccion_completa'];
$detalle_direccion=$_POST['detalle_direccion'];
$id_tipo_usuario=$_POST['id_tipo_usuario'];
$contrasena=$_POST['contrasena'];



$query="INSERT INTO usuarios(nombre_completo,telefono,correo,departamento,direccion_completa,detalle_direccion,id_tipo_usuario,contrasena)values('$nombre_completo','$telefono','$correo','$departamento','$direccion_completa','$detalle_direccion','$id_tipo_usuario','$contrasena');";
if(mysqli_query($conexion,$query)){
	echo "Registro agregado exitosamente";
}
else{
	echo "Error agregando el registro";
}
mysqli_close($conexion);

 ?>