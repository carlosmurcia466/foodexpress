<?php
include ('cn.php'); 
$user=$_GET['user'];
if ($resultset = getSQLResultSet("SELECT contrasena,id_tipo_usuario,id_usuario,nombre_completo,departamento,direccion_completa,detalle_direccion,telefono FROM `usuarios` WHERE correo='$user'")) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
    	
    	}
 }
   
?>