package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agregarusuarioadministrador extends AppCompatActivity {


    JSONArray ja;
    JSONObject jo;
    String[] datos;
    RequestQueue requestQueue;

    Spinner spinerdepartamento, spinner;
    EditText nombre, telefono,correo,direccion_completa,detalle_direccion,password;
    Button btnguardar, btnsalir;
    String textdepartamento="";
    String capturar_id="";
    String fruta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarusuarioadministrador);

        spinerdepartamento = (Spinner) findViewById(R.id.departamentospinner);
        nombre=findViewById(R.id.txtadnombrecompleto);
        telefono=findViewById(R.id.txtadtelefono);
        correo=findViewById(R.id.txtadcorreo);
        direccion_completa=findViewById(R.id.txtaddireccioncompleta);
        detalle_direccion=findViewById(R.id.txtaddetalledireccion);
        password=findViewById(R.id.txtadpassword);
        btnguardar=findViewById(R.id.btnagregaradusuarionuevo);
        btnsalir=findViewById(R.id.btnsalirad);
        spinner=findViewById(R.id.tipousuarioadspinner);

        listausuarios("https://foodexpress.webcindario.com/listatipousuarios.php");







        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fruta=spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();

                buscarid("https://foodexpress.webcindario.com/buscaridtipousuario.php?tipo_usuario="+fruta+"");





            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departamentos, android.R.layout.simple_spinner_item);
        spinerdepartamento.setAdapter(adapter);

        spinerdepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textdepartamento=adapterView.getItemAtPosition(i).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int  departamento= spinerdepartamento.getSelectedItemPosition();
                int usuario= spinner.getSelectedItemPosition();
                String nombreu=nombre.getText().toString().trim();
                String telefonou=telefono.getText().toString().trim();
                String correou=correo.getText().toString().trim();
                String direccion_completau=direccion_completa.getText().toString().trim();
                String detalle_direccionu=detalle_direccion.getText().toString().trim();
                String passwordu=password.getText().toString().trim();

                if(usuario>=0) {

                    if (departamento > 0) {


                        if (TextUtils.isEmpty(nombreu)) {
                            nombre.setError("valor requerido");
                            nombre.requestFocus();
                        }
                        if (TextUtils.isEmpty(telefonou)) {
                            telefono.setError("valor requerido");
                            telefono.requestFocus();
                        }
                        if (TextUtils.isEmpty(telefonou)) {
                            telefono.setError("valor requerido");
                            telefono.requestFocus();
                        }
                        if (TextUtils.isEmpty(correou)) {
                            correo.setError("valor requerido");
                            correo.requestFocus();
                        }
                        if (TextUtils.isEmpty(direccion_completau)) {
                            direccion_completa.setError("valor requerido");
                            direccion_completa.requestFocus();
                        }
                        if (TextUtils.isEmpty(detalle_direccionu)) {
                            detalle_direccion.setError("valor requerido");
                            detalle_direccion.requestFocus();
                        }
                        if (TextUtils.isEmpty(passwordu)) {
                            password.setError("valor requerido");
                            password.requestFocus();
                        } else {

                            try {

                                insertarUsuario("https://foodexpress.webcindario.com/insertarusuario.php");

                            } catch (Exception e) {


                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "seleccione un departamento", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "seleccione un usuario", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });





    }

    public void llenarLista(String[] array){
        if(datos!=null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, array);
            spinner.setAdapter(adapter);
        }
    }
    private void  listausuarios(String URL){
        final String[] parqueo = new String[1];

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ja = new JSONArray(response);
                    datos = new String[ja.length()];

                    for(int i=0;i<ja.length();i++){

                        String[] splitt = ja.getString(i).split("\"", -2);
                        datos[i]=splitt[1];
                    }
                    parqueo[0] =datos[0];
                    llenarLista(datos);

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),String.valueOf(e),Toast.LENGTH_LONG);
                }
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
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void buscarid(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject=response.getJSONObject(i);

                      capturar_id=jsonObject.getString("id_tipo_usuario");


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();


                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error de conexion",Toast.LENGTH_LONG).show();

            }
        }


        );
        requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

    }












    private void  insertarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();

                nombre.setText("");
                telefono.setText("");
                correo.setText("");
                direccion_completa.setText("");
                detalle_direccion.setText("");
                password.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                int  departamento= spinerdepartamento.getSelectedItemPosition();
                String nombreu=nombre.getText().toString().trim();
                String telefonou=telefono.getText().toString().trim();
                String correou=correo.getText().toString().trim();
                String direccion_completau=direccion_completa.getText().toString().trim();
                String detalle_direccionu=detalle_direccion.getText().toString().trim();
                String passwordu=password.getText().toString().trim();


                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("nombre_completo",nombreu);
                parametros.put("telefono",telefonou);
                parametros.put("correo",correou);
                parametros.put("departamento",textdepartamento);
                parametros.put("direccion_completa",direccion_completau);
                parametros.put("detalle_direccion",detalle_direccionu);
                parametros.put("id_tipo_usuario",capturar_id);
                parametros.put("contrasena",passwordu);

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}