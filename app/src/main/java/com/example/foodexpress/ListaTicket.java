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

public class ListaTicket extends AppCompatActivity {

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
    String id_usuario="0";
    ListView listView;
    List<String> listTitle;
    static String[][] tabla;
    Button btnverticket;
    String  ticket="",proceso="",total="";
    String nombre_cliente="";
    String departamento="",direccion="",detalle_direccion="";

    public static void setTabla(String[][] tabla) {
        ListaTicket.tabla = tabla;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ticket);
       btnverticket=findViewById(R.id.idverticket);
        listView=findViewById(R.id.listatickets);

        Bundle  parametros=this.getIntent().getExtras();
        if(parametros !=null) {

            id_usuario=parametros.getString("id_usuario");
            nombre_cliente=parametros.getString("nombreus");
            departamento=parametros.getString("departamento");
            direccion=parametros.getString("direccion");
            detalle_direccion=parametros.getString("detalle_direccion");
        }
        init();
        btnverticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent menuprincipal=new Intent(getApplicationContext(),menugeneralcliente.class);
        menuprincipal.putExtra("id_usuario",id_usuario);
        menuprincipal.putExtra("nombreus",nombre_cliente);
        menuprincipal.putExtra("departamento",departamento);
        menuprincipal.putExtra("direccion",direccion);
        menuprincipal.putExtra("detalle_direccion",detalle_direccion);
        startActivity(menuprincipal);
        finish();

    }

    private String obtieneDosDecimales(float valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }

    public  void init(){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://foodexpress.webcindario.com/listaticketusuario.php?id_usuario="+id_usuario+"&fecha="+date;
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length()>0){
                    try{
                        JSONArray jsonArray=new JSONArray(response);
                        extraerlista(jsonArray);

                    }catch (JSONException jsonexception1){
                        Toast.makeText(getApplicationContext(),"no hay nuevos registros ",Toast.LENGTH_LONG).show();
                        Intent menuprincipal=new Intent(getApplicationContext(),menugeneralcliente.class);
                        menuprincipal.putExtra("id_usuario",id_usuario);
                        menuprincipal.putExtra("nombreus",nombre_cliente);
                        menuprincipal.putExtra("departamento",departamento);
                        menuprincipal.putExtra("direccion",direccion);
                        menuprincipal.putExtra("detalle_direccion",detalle_direccion);
                        startActivity(menuprincipal);
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
                proceso=jsonObject.getString("proceso");
                total=jsonObject.getString("total");
                float totalcliente=Float.parseFloat(total);


                tabla[i][0]=jsonObject.getString("ticket");
                tabla[i][1]=jsonObject.getString("proceso");
                tabla[i][2]=jsonObject.getString("total");


                listTitle.add("Numero de ticket: "+ticket+"\n Proceso de orden: "+proceso+"\n Total a pagar $:"+obtieneDosDecimales(totalcliente));

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