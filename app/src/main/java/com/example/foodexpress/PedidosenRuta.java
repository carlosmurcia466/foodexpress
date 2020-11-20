package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.List;

public class PedidosenRuta extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
    String id_tipo_usuario="0";
    ListView listView;
    List<String> listTitle;
    static String[][] tabla;
    Button btnverticket;
    String  ticket="",horaus="",fechaus="";

    public static void setTabla(String[][] tabla) {
        PedidosenRuta.tabla = tabla;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidosen_ruta);

        btnverticket=findViewById(R.id.idverpedidosenruta);
        listView=findViewById(R.id.listaticketsenruta);

        Bundle datos=getIntent().getExtras();
        assert datos != null;
        id_tipo_usuario=datos.getString("usuario");


        init();
        btnverticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent vper=new Intent(getApplicationContext(),verpedidosenruta.class);
                     vper.putExtra("ticket_cliente",tabla[i][0]);
                     vper.putExtra("usuario",id_tipo_usuario);
                startActivity(vper);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent vpr=new Intent(getApplicationContext(),menu_general.class);
        vpr.putExtra("usuario",id_tipo_usuario);
        startActivity(vpr);
        finish();
    }

    public  void init(){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://foodexpress.webcindario.com/listarticketscocina.php?id_proceso=3&fecha="+date;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length()>0){
                    try{
                        JSONArray jsonArray=new JSONArray(response);
                        extraerlista(jsonArray);

                    }catch (JSONException jsonexception1){
                        Toast.makeText(getApplicationContext(),"no hay nuevos registros ",Toast.LENGTH_LONG).show();
                        Intent vmg=new Intent(getApplicationContext(),menu_general.class);
                        vmg.putExtra("usuario",id_tipo_usuario);
                        startActivity(vmg);
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
        String[][] tabla=  new String[jsonArray.length()][3];
        try {
            for(int i=0; i<jsonArray.length();i++){

                JSONObject jsonObject=jsonArray.getJSONObject(i);
                ticket=jsonObject.getString("ticket");
                horaus=jsonObject.getString("hora");
                fechaus=jsonObject.getString("fecha");

                tabla[i][0]=jsonObject.getString("ticket");
                tabla[i][1]=jsonObject.getString("hora");
                tabla[i][2]=jsonObject.getString("fecha");

                listTitle.add("Numero de ticket: "+ticket+"\nHora: "+horaus+"\nFecha: "+fechaus);

            }
            setTabla(tabla);

        }
        catch (JSONException jsonException2){
            Toast.makeText(getApplicationContext(),"error al extraer datos "
                    +jsonException2.toString(),Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listTitle);
        listView.setAdapter(adapter);
    }
}