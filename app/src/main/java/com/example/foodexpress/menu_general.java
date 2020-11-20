package com.example.foodexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class menu_general extends AppCompatActivity {

    String id_tipo_usuario="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_general);
        Bundle datos=getIntent().getExtras();
        assert datos != null;
        id_tipo_usuario=datos.getString("usuario");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ma=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(ma);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        switch (id_tipo_usuario) {
            case "1":
                getMenuInflater().inflate(R.menu.menu_administrador, menu);

                break;
            case "2":
                getMenuInflater().inflate(R.menu.menu_cocinero, menu);
                break;
            case "3":
                getMenuInflater().inflate(R.menu.menu_motorista, menu);
                break;
        }

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.opc_agregar_usuario:
                Intent i=new Intent(getApplicationContext(),agregarusuarioadministrador.class);
                startActivity(i);
                break;

            case R.id.opc_mostraradministradores:
                Intent ad=new Intent(getApplicationContext(),listarAdministradores.class);
                startActivity(ad);
                break;
            case R.id.opc_mostrarclientes:
                Intent cli=new Intent(getApplicationContext(),listarClientes.class);
                startActivity(cli);
                break;
            case R.id.opc_mostrarcocineros:
                Intent co=new Intent(getApplicationContext(),listarcocineros.class);
                co.putExtra("id",id_tipo_usuario);
                startActivity(co);
                break;
            case R.id.opc_mostrarmotoristas:
                Intent mo=new Intent(getApplicationContext(),listarmotoristas.class);
                startActivity(mo);
                break;
            case R.id.opc_agregar_categoria_producto:
                Intent  capo=new Intent(getApplicationContext(),agregarcategoriaproducto.class);
                startActivity(capo);
                break;
            case R.id.opc_agregar_producto:
                Intent  ap=new Intent(getApplicationContext(),agregarproductos.class);
                startActivity(ap);
                break;

                //cocina
            case R.id.opc_ver_pedidococina:
                Intent  ovp=new Intent(getApplicationContext(),PedidosCocina.class);
                startActivity(ovp);
                break;
            case R.id.opc_ver_pedido_procesococina:
                Intent  ovpp=new Intent(getApplicationContext(),PedidosenProceso.class);
                startActivity(ovpp);
                break;
            case R.id.opc_ver_pedido_cerradoscocina:
                Intent  ovpc=new Intent(getApplicationContext(),PedidosCerrados.class);
                startActivity(ovpc);
                break;

                //motoristas
            case R.id.opc_ver_pedido_ruta:
                Intent  ovprm=new Intent(getApplicationContext(),PedidosenRuta.class);
                ovprm.putExtra("usuario",id_tipo_usuario);
                startActivity(ovprm);
                finish();
                break;




        }
        return  true;
    }
}