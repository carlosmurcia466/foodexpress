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

import java.util.ArrayList;
import java.util.List;

public class listarmotoristas extends AppCompatActivity {

    ListView listView;
    List<String> listTitle;
    static String[][] tabla;
    Button btnregresar;
    String  nombre="",telefono="",correo="";

    public static void setTabla(String[][] tabla) {
        listarmotoristas.tabla = tabla;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarmotoristas);
        btnregresar=findViewById(R.id.btnregresarmotoristase);
        listView=findViewById(R.id.listamotoristas);

        init();

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }

    public  void init(){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="https://foodexpress.webcindario.com/listarMotoristas.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length()>0){
                    try{
                        JSONArray jsonArray=new JSONArray(response);
                        extraerlista(jsonArray);

                    }catch (JSONException jsonexception1){
                        Toast.makeText(getApplicationContext(),"no hay nuevos registros "+jsonexception1.toString(),Toast.LENGTH_LONG).show();

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
                nombre=jsonObject.getString("nombre_completo");
                telefono=jsonObject.getString("telefono");
                correo=jsonObject.getString("correo");


                tabla[i][0]=jsonObject.getString("nombre_completo");
                tabla[i][1]=jsonObject.getString("telefono");
                tabla[i][2]=jsonObject.getString("correo");


                listTitle.add("Codigo de solicitud: "+nombre+"\n Correo: "+telefono+"\nnombre :"+correo);
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