package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerPedidosCocina extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
    String id_usuario="0";
    ListView listView;
    List<String> listTitle;
    Button btnverticket,btnsalir;
    TextView tvticket;
    String  categoria="",nombre="",cantidad="";
    String ticket_cliente="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedidos_cocina);

        btnverticket=findViewById(R.id.idpedidoterminado);
        btnsalir=findViewById(R.id.idpedidoterminadosalir);
        tvticket=findViewById(R.id.tvticketpedidocliente);
        listView=findViewById(R.id.listadetallepedidococina);

        Bundle datos=getIntent().getExtras();
        ticket_cliente=datos.getString("ticket_cliente");
        tvticket.setText(ticket_cliente);

        init();

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnverticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mostrar un mensaje de confirmación antes de realizar el test
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(VerPedidosCocina.this);
                alertDialog.setMessage("¿desea comenzar a trabajar esta orden?");
                alertDialog.setTitle("Preparar orden: "+ ticket_cliente);
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                solicitud("https://foodexpress.webcindario.com/enviarpedidoaprocesar.php");
                Toast.makeText(getApplicationContext(),"procesando comida",Toast.LENGTH_SHORT).show();
                      finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                });
                alertDialog.show();



        }
        });



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
                parametros.put("ticket_cliente",ticket_cliente);
                parametros.put("fecha",date);

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public  void init(){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://foodexpress.webcindario.com/listarpedidostickets.php?id_proceso=1&fecha="+date+"&ticket_cliente="+ticket_cliente;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length()>0){
                    try{
                        JSONArray jsonArray=new JSONArray(response);
                        extraerlista(jsonArray);

                    }catch (JSONException jsonexception1){
                        Toast.makeText(getApplicationContext(),"no hay nuevos registros ",Toast.LENGTH_LONG).show();
                        finish();

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error "+error.toString(),Toast.LENGTH_LONG ).show();

            }
        });

        queue.add(stringRequest);
    }

    public void extraerlista(JSONArray jsonArray){
        listTitle=new ArrayList<>();
        try {
            for(int i=0; i<jsonArray.length();i++){

                JSONObject jsonObject=jsonArray.getJSONObject(i);
                categoria=jsonObject.getString("categoria");
                nombre=jsonObject.getString("nombre");
                cantidad=jsonObject.getString("cantidad");


                listTitle.add(cantidad+" "+categoria+" de "+nombre);

            }


        }
        catch (JSONException jsonException2){
            Toast.makeText(getApplicationContext(),"error al extraer datos "
                    +jsonException2.toString(),Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listTitle);
        listView.setAdapter(adapter);
    }
}