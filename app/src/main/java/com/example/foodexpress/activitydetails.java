package com.example.foodexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activitydetails extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());

    TextView tvnombre_producto,tvprecio,tvdetalle,tvcantidad,tvguardarpedido;
    Button btnmin,btnplus;
    int cantidad=0;
    String id_usuario;
    ImageView imagenproductodetalle;
    JSONArray ja;
    int proceso=0;
    int ticketus=0;
    float suma=0;
    private static String total_URL;
    String id_producto="0";
    String nombre="";
    String total2="0";
    String departamento="",direccion="",detalle_direccion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitydetails);
        tvnombre_producto=findViewById(R.id.tvnombreproductodetalle);
        tvprecio=findViewById(R.id.tvprecioproductodetalle);
        tvdetalle=findViewById(R.id.tvdetalleproducto);
        tvcantidad=findViewById(R.id.tvcantidaddetalle);
        tvguardarpedido=findViewById(R.id.tvguardarpedido);
        btnmin=findViewById(R.id.buttonmin);
        btnplus=findViewById(R.id.buttonplus);
        imagenproductodetalle=findViewById(R.id.imagenproductodetalle);

        Bundle parametros=this.getIntent().getExtras();
        assert parametros != null;
        id_producto=parametros.getString("id_producto");
        nombre=parametros.getString("nombreus");
        departamento=parametros.getString("departamento");
        direccion=parametros.getString("direccion");
        detalle_direccion=parametros.getString("detalle_direccion");
        final String precio=parametros.getString("precio");
        final String detalle=parametros.getString("detalle");
        final String nombre_producto=parametros.getString("nombre_producto");
        final String imagenurl=parametros.getString("imagen");
        id_usuario=parametros.getString("id_usuario");
        total_URL="https://foodexpress.webcindario.com/consultartotalcliente.php?id_usuario="+id_usuario+"&fecha="+date+"&id_proceso="+proceso;
        Picasso.get().load(imagenurl).into(imagenproductodetalle);
        tvnombre_producto.setText(nombre_producto);
        tvprecio.setText(precio);
        tvdetalle.setText(detalle);
        float totalinicial=(Float.parseFloat((tvprecio.getText().toString().trim())))* (Float.parseFloat((tvcantidad.getText().toString().trim())));
        tvguardarpedido.setText("suma: "+obtieneDosDecimales(totalinicial)+" $");

        tvguardarpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Mostrar un mensaje de confirmación antes de realizar el test
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activitydetails.this);
                alertDialog.setMessage("¿Desea agregar este producto a su orden ?");
                alertDialog.setTitle("Agregar producto");
                alertDialog.setIcon(android.R.drawable.dialog_holo_dark_frame);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        consultaticket("https://foodexpress.webcindario.com/consultarticket.php?id_usuario="+id_usuario+"&fecha="+date+"&id_proceso="+proceso);
                        alertDialog.setCancelable(true);


                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        alertDialog.setCancelable(true);
                    }
                });
                alertDialog.show();


            }
        });


        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad=cantidad+1;
                if(cantidad>0){
                    tvcantidad.setText(String.valueOf(cantidad));
                    float total=(cantidad)*Float.parseFloat(precio);
                    suma=total;
                    tvguardarpedido.setText("suma: $" +obtieneDosDecimales(total)+" GUARDAR?");
                }
                else {
                  cantidad=1;
                }


            }
        });
        btnmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidad=cantidad-1;
                if(cantidad>0){
                    tvcantidad.setText(String.valueOf(cantidad));
                    float total=(cantidad)*Float.parseFloat(precio);
                    tvguardarpedido.setText("suma: $" +obtieneDosDecimales(total)+" GUARDAR?");
                }
                else {
                   cantidad=1;
                }

            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent adi=new Intent(getApplicationContext(),vermenucliente.class);
        adi.putExtra("id_usuario",id_usuario);
        adi.putExtra("nombreus",nombre);
        adi.putExtra("departamento",departamento);
        adi.putExtra("direccion",direccion);
        adi.putExtra("detalle_direccion",detalle_direccion);
        adi.putExtra("entrar","1");
        startActivity(adi);
        finish();
    }

    private String obtieneDosDecimales(float valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.opc_ordenar:
                Intent verpedidos=new Intent(getApplicationContext(),detallecliente.class);
                verpedidos.putExtra("id_usuario",id_usuario);
                verpedidos.putExtra("nombreus",nombre);
                verpedidos.putExtra("departamento",departamento);
                verpedidos.putExtra("direccion",direccion);
                verpedidos.putExtra("detalle_direccion",detalle_direccion);
                startActivity(verpedidos);
                finish();
                break;
                case R.id.opc_vermenu:
                Intent menuprincipal=new Intent(getApplicationContext(),menugeneralcliente.class);
                menuprincipal.putExtra("id_usuario",id_usuario);
                menuprincipal.putExtra("nombreus",nombre);
                menuprincipal.putExtra("departamento",departamento);
                menuprincipal.putExtra("direccion",direccion);
                menuprincipal.putExtra("detalle_direccion",detalle_direccion);
                startActivity(menuprincipal);
                finish();
                break;


        }


        return  true;
    }

    private void consultaticket(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response ) {

                try {
                    ja = new JSONArray(response);
                    String fecha = ja.getString(0);
                    int  id_proceso= ja.getInt(1);
                         ticketus= ja.getInt(2);

                    if(ja.length()>0)
                    {

                          insertardetalle("https://foodexpress.webcindario.com/insertardetalle.php");


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    insertarticket("https://foodexpress.webcindario.com/insertarticket.php");
                    insertardetalle("https://foodexpress.webcindario.com/insertardetalle.php");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);



    }

    private void  insertarticket(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros=new HashMap<String, String>();
                Map<String,Integer> parametros1=new HashMap<String, Integer>();


                parametros.put("id_usuario",id_usuario);
                parametros.put("fecha",date);
                parametros.put("hora",hora);
                parametros1.put("id_proceso",proceso);
                consultaticket("https://foodexpress.webcindario.com/consultarticket.php?id_usuario="+id_usuario+"&fecha="+date+"&id_proceso="+proceso);

                return parametros;



            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void  insertardetalle(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros=new HashMap<String, String>();
                Map<String,Integer> parametros1=new HashMap<String, Integer>();

                String cantidadproductos=tvcantidad.getText().toString().trim();


                parametros.put("ticket_cliente",String.valueOf(ticketus));
                parametros.put("id_producto",id_producto);
                parametros.put("cantidad",cantidadproductos);

                return parametros;



            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }






}