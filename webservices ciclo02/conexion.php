<?php 

$hostname='mysql.webcindario.com';
$database='foodexpress';
$username='foodexpress';
$password='elsalvador466';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "El sitio web está experimentado problemas... ";
}
else{
    
}
	




 ?>