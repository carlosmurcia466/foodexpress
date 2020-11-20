package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class detallecliente extends AppCompatActivity {

    ArrayList<clienteproductosdetalle> listadetallecliente;
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
    JSONArray ja;
    int ticketus=0;
    RecyclerView rvcategoria;
    String id_usuario="0";
    int proceso=0;
    Double total=0.0;
    String total2 = "0";
    private static String CATEGORIA_URL;
    private static String total_URL;
    TextView tvtotal;
    Button enviarpedido;
    String ticket_cliente;
    String nombre_cliente="";
    String departamento="",direccion="",detalle_direccion="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallecliente);
        tvtotal=findViewById(R.id.tvtotalapagar);
        enviarpedido=findViewById(R.id.buttonenviarpedido);

        Bundle  parametros=this.getIntent().getExtras();
        if(parametros !=null){

            id_usuario=parametros.getString("id_usuario");
            nombre_cliente=parametros.getString("nombreus");
            departamento=parametros.getString("departamento");
            direccion=parametros.getString("direccion");
            detalle_direccion=parametros.getString("detalle_direccion");
            total_URL="https://foodexpress.webcindario.com/consultartotalcliente.php?id_usuario="+id_usuario+"&fecha="+date+"&id_proceso="+proceso;
            CATEGORIA_URL = "https://foodexpress.webcindario.com/consultardetallecliente.php?id_usuario="+id_usuario+"&fecha="+date+"&id_proceso="+proceso;


        }

        rvcategoria= findViewById(R.id.recylcerViewdetallecliente);
        rvcategoria.setHasFixedSize(true);
        rvcategoria.setItemAnimator(new DefaultItemAnimator());
        rvcategoria.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listadetallecliente = new ArrayList<>();

        obtenercategoria();
        obtenertotal();


        enviarpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    consultaticket("https://foodexpress.webcindario.com/consultarticketcliente.php?id_usuario=" + id_usuario + "&fecha=" + date + "&id_proceso=" + proceso+"&ticket_cliente="+ticket_cliente);


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mag=new Intent(getApplicationContext(),menugeneralcliente.class);
        mag.putExtra("id_usuario",id_usuario);
        mag.putExtra("nombreus",nombre_cliente);
        mag.putExtra("departamento",departamento);
        mag.putExtra("direccion",direccion);
        mag.putExtra("detalle_direccion",detalle_direccion);
        startActivity(mag);
        finish();

    }

    private void  solicitud(String URL){
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
                parametros.put("id_usuario",id_usuario);
                parametros.put("fecha",date);

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void obtenercategoria() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, CATEGORIA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String nombre_producto = jsonObject1.getString("nombre_producto");
                                String precio= jsonObject1.getString("precio");
                                String cantidad = jsonObject1.getString("cantidad");
                                String imagen = jsonObject1.getString("imagen");
                                String id_producto = jsonObject1.getString("id_producto");
                                ticket_cliente= jsonObject1.getString("ticket_cliente");
                                String id_proceso = jsonObject1.getString("id_proceso");

                                listadetallecliente.add(new clienteproductosdetalle( nombre_producto,  precio,  cantidad,  imagen,  id_producto,  ticket_cliente,  id_proceso,total2,id_usuario,nombre_cliente,departamento,direccion,detalle_direccion));
                                obtenertotal();
                            }

                            adaptador_detallecliente adaptador = new adaptador_detallecliente(listadetallecliente,getApplicationContext());

                            rvcategoria.setAdapter(adaptador);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }
    public void obtenertotal( ) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, total_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                 total= jsonObject1.getDouble("total");
                                 total2=jsonObject1.getString("total");

                                 if(total != null){
                                     tvtotal.setText("total:$ "+total);
                                 }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
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
                        //Mostrar un mensaje de confirmación antes de realizar el test
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(detallecliente.this);
                        alertDialog.setMessage("¿Desea enviar su orden a "+departamento+","+direccion+","+detalle_direccion+" ?");
                        alertDialog.setTitle(nombre_cliente);
                        alertDialog.setIcon(android.R.drawable.dialog_holo_dark_frame);
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                    solicitud("https://foodexpress.webcindario.com/enviarpedido.php");
                                    insertarfactura("https://foodexpress.webcindario.com/insertarfactura.php");
                                    Toast.makeText(getApplicationContext(),"pedidos enviados",Toast.LENGTH_LONG).show();

                                Intent menuprincipal=new Intent(getApplicationContext(),menugeneralcliente.class);
                                menuprincipal.putExtra("id_usuario",id_usuario);
                                menuprincipal.putExtra("nombreus",nombre_cliente);
                                menuprincipal.putExtra("departamento",departamento);
                                menuprincipal.putExtra("direccion",direccion);
                                menuprincipal.putExtra("detalle_direccion",detalle_direccion);
                                startActivity(menuprincipal);
                                finish();


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


                } catch (JSONException e) {
                    e.printStackTrace();
                    tvtotal.setText("no hay pedidos en proceso");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    private void  insertarfactura(String URL){
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



                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("ticket_cliente", ticket_cliente);
                    parametros.put("total", total2);
                    return parametros;

            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




}