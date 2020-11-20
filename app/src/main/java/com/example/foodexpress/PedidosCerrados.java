package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class PedidosCerrados extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
    String id_usuario="0";
    ListView listView;
    List<String> listTitle;
    static String[][] tabla;
    Button btnverticket;
    String  ticket="",horaus="",fechaus="";

    public static void setTabla(String[][] tabla) {
        PedidosCerrados.tabla = tabla;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_cerrados);

        btnverticket=findViewById(R.id.idverpedidoscerrados);
        listView=findViewById(R.id.listaticketcerrados);
        init();
        btnverticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });


    }


    public  void init(){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://foodexpress.webcindario.com/listarticketscocina.php?id_proceso=4&fecha="+date;
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