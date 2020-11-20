package com.example.foodexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class vermenucliente extends AppCompatActivity {


    ArrayList<categoria_producto> listacategoria;

    RecyclerView rvcategoria;
    String id_usuario="0";
    String entrar="0";

    private static final String CATEGORIA_URL = "https://foodexpress.webcindario.com/obtenercategoria_productos.php";

    String nombre_cliente="";
    String departamento="",direccion="",detalle_direccion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermenucliente);


        Bundle  parametros=this.getIntent().getExtras();
        if(parametros !=null){
           id_usuario=parametros.getString("id_usuario");
            nombre_cliente=parametros.getString("nombreus");
            departamento=parametros.getString("departamento");
            direccion=parametros.getString("direccion");
            detalle_direccion=parametros.getString("detalle_direccion");
            entrar=parametros.getString("entrar");

            if(!entrar.equals("1")){
                finish();
            }

        }


        


        rvcategoria= findViewById(R.id.recylcerViewcategoria);
        rvcategoria.setHasFixedSize(true);
        rvcategoria.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listacategoria = new ArrayList<>();

        obtenercategoria();
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

                                String imagen = jsonObject1.getString("imagen");
                                String nombre = jsonObject1.getString("categoria_producto");
                                String id=jsonObject1.getString("id_tipo_producto");

                                   listacategoria.add(new categoria_producto(nombre,imagen,id,id_usuario,nombre_cliente,departamento,direccion,detalle_direccion));

                                 }

                            adaptador_categoriaproductos adaptador = new adaptador_categoriaproductos(listacategoria,getApplicationContext());

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent menug=new Intent(getApplicationContext(),menugeneralcliente.class);
        menug.putExtra("id_usuario",id_usuario);
        menug.putExtra("nombreus",nombre_cliente);
        menug.putExtra("departamento",departamento);
        menug.putExtra("direccion",direccion);
        menug.putExtra("detalle_direccion",detalle_direccion);
        menug.putExtra("EXIT", true);
        startActivity(menug);
        menug.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
    }




}
