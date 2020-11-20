package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class agregarusuario extends AppCompatActivity {


    Spinner spinner;
    int id_tipo_usuario = 4;
    EditText nombre, telefono, correo, direccion_completa, detalle_direccion, password;
    Button btnguardar, btnsalir;
    String text = "";
    TelephonyManager tMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarusuario);
        spinner = (Spinner) findViewById(R.id.spinner);
        nombre = findViewById(R.id.txtnuevonombrecompleto);
        telefono = findViewById(R.id.txtnuevotelefono);
        correo = findViewById(R.id.txtnuevocorreo);
        direccion_completa = findViewById(R.id.txtnuevodireccioncompleta);
        detalle_direccion = findViewById(R.id.txtnuevodetalledireccion);
        password = findViewById(R.id.txtnuevopassword);
        btnguardar = findViewById(R.id.btnagregarusuarionuevo);
        btnsalir = findViewById(R.id.btnsalirnuevo);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departamentos, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text=adapterView.getItemAtPosition(i).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int  departamento= spinner.getSelectedItemPosition();
                String nombreu=nombre.getText().toString().trim();
                String telefonou=telefono.getText().toString().trim();
                String correou=correo.getText().toString().trim();
                String direccion_completau=direccion_completa.getText().toString().trim();
                String detalle_direccionu=detalle_direccion.getText().toString().trim();
                String passwordu=password.getText().toString().trim();
                String id=telefono.getText().toString().trim();

                if(departamento>0) {


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
                    }
                    else {

                        try {

                            UsuariosSQLite conn=new UsuariosSQLite(getApplicationContext(),"deliverydb",null,1);
                            SQLiteDatabase db=conn.getWritableDatabase();
                            ContentValues values=new ContentValues();

                                values.put(utilidades.nombre_completo, nombre.getText().toString());
                                values.put(utilidades.telefono, telefono.getText().toString());
                                values.put(utilidades.correo, correo.getText().toString());
                                values.put(utilidades.departamento, text);
                                values.put(utilidades.direccion_completa, direccion_completa.getText().toString());
                                values.put(utilidades.detalle_direccion, detalle_direccion.getText().toString());
                                values.put(utilidades.password, password.getText().toString());
                                values.put(utilidades.id_tipo_usuario, id_tipo_usuario);


                                long usuariogg = db.insert(utilidades.TABLA_USUARIO, utilidades.id_usuario, values);
                            insertarUsuario("https://foodexpress.webcindario.com/insertarcliente.php");


                            Toast.makeText(getApplicationContext(), "usuario guardado" + usuariogg, Toast.LENGTH_SHORT).show();
                                
                        }
                        catch (Exception e){


                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }

                            }
                }
                else {
                    Toast.makeText(getApplicationContext(),"seleccione un departamento",Toast.LENGTH_LONG).show();
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

                int  departamento= spinner.getSelectedItemPosition();
                String nombreu=nombre.getText().toString().trim();
                String telefonou=telefono.getText().toString().trim();
                String correou=correo.getText().toString().trim();
                String direccion_completau=direccion_completa.getText().toString().trim();
                String detalle_direccionu=detalle_direccion.getText().toString().trim();
                String passwordu=password.getText().toString().trim();
                int id_tipo_usuario=4;


                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("nombre_completo",nombreu);
                parametros.put("telefono",telefonou);
                parametros.put("correo",correou);
                parametros.put("departamento",text);
                parametros.put("direccion_completa",direccion_completau);
                parametros.put("detalle_direccion",detalle_direccionu);
                parametros.put("id_tipo_usuario",String.valueOf(id_tipo_usuario));
                parametros.put("contrasena",passwordu);

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }










}