package com.example.foodexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class menugeneralcliente extends AppCompatActivity {
    String id_tipo_usuario;
    String id_usuario;
    String nombre;
    String nivel;
    String departamento,direccion="",detalle_direccion="",contra="",telefono="",correo="";
    LinearLayout lyordenar,lycanasta,lytickets,lyeditar,lycerrar,lyinformacion;
    JSONArray ja;
    TextView txtdashboard,txtcorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menugeneralcliente);
        lyeditar=findViewById(R.id.idlineareditardatos);
        lyordenar=findViewById(R.id.idlinearordenar);
        lycanasta=findViewById(R.id.idlinearcanasta);
        lytickets=findViewById(R.id.idlineartickets);
        lycerrar=findViewById(R.id.idlinearcerrar);
        lyinformacion=findViewById(R.id.idlinearinformacion);
        txtdashboard=findViewById(R.id.dashboard);
        txtcorreo=findViewById(R.id.txtdashboard);


        Bundle datos=getIntent().getExtras();
        assert datos != null;
         id_usuario=datos.getString("id_usuario");
        id_usuario=datos.getString("id_usuario");
        nombre=datos.getString("nombreus");
        departamento=datos.getString("departamento");
        direccion=datos.getString("direccion");
        detalle_direccion=datos.getString("detalle_direccion");
        contra=datos.getString("contra");
        telefono=datos.getString("telefono");
        correo=datos.getString("correo");

        Consultacliente("https://foodexpress.webcindario.com/usuariocliente.php?user="+id_usuario);

        lyeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editarusuario=new Intent(getApplicationContext(),actualizardatoscliente.class);
                editarusuario.putExtra("id_usuario",id_usuario);
                finish();
                startActivity(editarusuario);
            }
        });

        lyordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vermenu=new Intent(getApplicationContext(),vermenucliente.class);
                vermenu.putExtra("id_usuario",id_usuario);
                vermenu.putExtra("nombreus",nombre);
                vermenu.putExtra("departamento",departamento);
                vermenu.putExtra("direccion",direccion);
                vermenu.putExtra("detalle_direccion",detalle_direccion);
                vermenu.putExtra("entrar","1");
                startActivity(vermenu);
                finish();

            }
        });

        lycanasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verpedidos=new Intent(getApplicationContext(),detallecliente.class);
                verpedidos.putExtra("id_usuario",id_usuario);
                verpedidos.putExtra("nombreus",nombre);
                verpedidos.putExtra("departamento",departamento);
                verpedidos.putExtra("direccion",direccion);
                verpedidos.putExtra("detalle_direccion",detalle_direccion);
                startActivity(verpedidos);
                finish();

            }
        });

        lytickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verticket=new Intent(getApplicationContext(),ListaTicket.class);
                verticket.putExtra("id_usuario",id_usuario);
                verticket.putExtra("nombreus",nombre);
                verticket.putExtra("departamento",departamento);
                verticket.putExtra("direccion",direccion);
                verticket.putExtra("detalle_direccion",detalle_direccion);
                startActivity(verticket);
                finish();

            }
        });
        lycerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ma=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ma);
                finish();

            }
        });

        lyinformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent menuprincipal=new Intent(getApplicationContext(),menugeneralcliente.class);
        menuprincipal.putExtra("id_usuario",id_usuario);
        menuprincipal.putExtra("nombreus",nombre);
        menuprincipal.putExtra("departamento",departamento);
        menuprincipal.putExtra("direccion",direccion);
        menuprincipal.putExtra("detalle_direccion",detalle_direccion);
        startActivity(menuprincipal);
        finish();
    }

    //consultarcliente

    private void Consultacliente(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response ) {

                try {
                    ja = new JSONArray(response);
                   contra = ja.getString(0);
                    nivel= ja.getString(1);
                    id_usuario=ja.getString(2);
                    nombre=ja.getString(3);
                    departamento=ja.getString(4);
                    direccion=ja.getString(5);
                    detalle_direccion=ja.getString(6);
                    telefono=ja.getString(7);
                    correo=ja.getString(8);

                    txtdashboard.setText(nombre);
                    txtcorreo.setText(correo);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "El usuario no existe en la base de datos", Toast.LENGTH_LONG).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);



    }







}