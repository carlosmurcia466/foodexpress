package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class verpedidosenruta extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
    ListView listView;
    List<String> listTitle;
    Button btnverticket,btnsalir;
    TextView tvticket,tvtelefono,tvdepartamento,tvdireccion,tvdetalledireccion,tvcobrar,tvnombrecompleto;
    String  categoria="",nombre="",cantidad="";
    String ticket_cliente="";
    JSONArray ja;
    String id_tipo_usuario="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verpedidosenruta);

        btnverticket=findViewById(R.id.btnfinalizarpedidocliente);
        btnsalir=findViewById(R.id.btnsalirfinalizarcliente);
        tvticket=findViewById(R.id.tvticketfinalizarpedido);
        listView=findViewById(R.id.listadedetallefinalizarcliente);
        tvtelefono=findViewById(R.id.tvnumerodetelefonofinalizar);
        tvdepartamento=findViewById(R.id.tvdepartamentofinalizar);
        tvdireccion=findViewById(R.id.tvdireccionfinalizar);
        tvdetalledireccion=findViewById(R.id.tvdetalledireccionfinalizar);
        tvnombrecompleto=findViewById(R.id.tvtnombrecompletofinalizar);
        tvcobrar=findViewById(R.id.tvcobrarfinalizar);


        Bundle datos=getIntent().getExtras();
        ticket_cliente=datos.getString("ticket_cliente");
        id_tipo_usuario=datos.getString("usuario");
        tvticket.setText(ticket_cliente);

        Consultacliente("https://foodexpress.webcindario.com/extraerdatosdeusuariofinalizar.php?user="+ticket_cliente);

        init();
        btnverticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Mostrar un mensaje de confirmación antes de realizar el test
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(verpedidosenruta.this);
                alertDialog.setMessage("¿desea cobrar orden?");
                alertDialog.setTitle("cobrar orden: "+ ticket_cliente);
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        solicitud("https://foodexpress.webcindario.com/finalizarpedido.php");
                        Toast.makeText(getApplicationContext(),"procesando comida",Toast.LENGTH_SHORT).show();
                        Intent vpr=new Intent(getApplicationContext(),PedidosenRuta.class);
                        vpr.putExtra("usuario",id_tipo_usuario);
                        startActivity(vpr);
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

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent vpr=new Intent(getApplicationContext(),PedidosenRuta.class);
        vpr.putExtra("usuario",id_tipo_usuario);
        startActivity(vpr);
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
        String url="https://foodexpress.webcindario.com/listarpedidostickets.php?id_proceso=3&fecha="+date+"&ticket_cliente="+ticket_cliente;
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

    private String obtieneDosDecimales(float valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }


    private void Consultacliente(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response ) {

                try {
                    ja = new JSONArray(response);
                    String nombre = ja.getString(0);
                    String telefono= ja.getString(1);
                    String departamento=ja.getString(2);
                    String direccion=ja.getString(3);
                    String detalle=ja.getString(4);
                    String total=ja.getString(5);
                    float totalcliente=Float.parseFloat(total);


                    tvnombrecompleto.setText(nombre);
                    tvtelefono.setText(telefono);
                    tvdepartamento.setText(departamento);
                    tvdireccion.setText(direccion);
                    tvdetalledireccion.setText(detalle);
                    tvcobrar.setText("$"+obtieneDosDecimales(totalcliente));

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