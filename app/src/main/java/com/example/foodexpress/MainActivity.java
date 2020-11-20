package com.example.foodexpress;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView nuevousuario;
    Button btningresar;
    TextView inputmail,inputpassword;
    JSONArray ja;

    ArrayList<usuarios> listausuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nuevousuario=findViewById(R.id.txtnuevacuenta);
        btningresar=findViewById(R.id.btnLogin);
        inputmail=findViewById(R.id.inputEmail);
        inputpassword=findViewById(R.id.inputPassword);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }


        listausuarios = new ArrayList<>();

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sucorreo=inputmail.getText().toString().trim();
                String supass=inputpassword.getText().toString().trim();

                if(TextUtils.isEmpty(sucorreo)){
                    inputmail.setError("campo requerido");
                    inputmail.requestFocus();
                }
                if(TextUtils.isEmpty(supass)){
                    inputpassword.setError("campo requerido");
                    inputpassword.requestFocus();
                }
                else {
                    ConsultaPass("https://foodexpress.webcindario.com/usuario.php?user="+inputmail.getText().toString().trim());

                }


            }
        });


        nuevousuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent agg=new Intent(getApplicationContext(),agregarusuario.class);
                startActivity(agg);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    private void ConsultaPass(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response ) {

                try {
                    ja = new JSONArray(response);
                    String contra = ja.getString(0);
                    String nivel= ja.getString(1);
                    String idusuario=ja.getString(2);
                    String nombreus=ja.getString(3);
                    String departamento=ja.getString(4);
                    String direccion=ja.getString(5);
                    String detalle_direccion=ja.getString(6);
                    String telefono=ja.getString(7);

                    listausuarios.add(new usuarios(contra,nivel,idusuario,nombreus));

                    if(contra.equals(inputpassword.getText().toString())){
                        switch (nivel){
                            case "1":
                                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, menu_general.class);
                                intent.putExtra("usuario",nivel);
                                startActivity(intent);
                                break;
                            case "2":
                                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                                Intent intentd = new Intent(MainActivity.this, menu_general.class);
                                intentd.putExtra("usuario",nivel);
                                startActivity(intentd);
                                break;
                            case "3":
                                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                                Intent intentv = new Intent(MainActivity.this, menu_general.class);
                                intentv.putExtra("usuario",nivel);
                                startActivity(intentv);
                                break;
                            case "4":
                                Intent intent4 = new Intent(MainActivity.this, menugeneralcliente.class);
                                intent4.putExtra("usuario",nivel);
                                intent4.putExtra("id_usuario",idusuario);
                                intent4.putExtra("nombreus",nombreus);
                                intent4.putExtra("departamento",departamento);
                                intent4.putExtra("direccion",direccion);
                                intent4.putExtra("detalle_direccion",detalle_direccion);
                                intent4.putExtra("contra",contra);
                                intent4.putExtra("telefono",telefono);
                                intent4.putExtra("correo",inputmail.getText().toString().trim());
                                startActivity(intent4);
                                finish();
                                break;
                        }
                        inputmail.setText("");
                        inputpassword.setText("");

                    }else{
                        Toast.makeText(getApplicationContext(),"verifique su contraseña",Toast.LENGTH_SHORT).show();
                        inputmail.setText("");
                        inputpassword.requestFocus();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "El usuario no existe en la base de datos", Toast.LENGTH_LONG).show();
                    inputmail.setText("");
                    inputpassword.setText("");
                    inputmail.requestFocus();
                    inputpassword.setError("Introduzca las credenciales correctas!");
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