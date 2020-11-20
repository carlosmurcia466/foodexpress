package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class actualizardatoscliente extends AppCompatActivity {

    String id_tipo_usuario="";
    String id_usuario="";
    String nombre="";
    String departamento="",direccion="",detalle_direccion="",contra="",telefono="",correo="";
    Spinner spinner;
    TextView txtnombre,txttelefono,txtcorreo,txtdireccioncompleta,txtdetalledireccion,txtcontra;
    Button btnactualizarcliente;
    String text="";
    JSONArray ja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizardatoscliente);
        txtnombre=findViewById(R.id.txtnuevonombrecompletoactualizar);
        txttelefono=findViewById(R.id.txtnuevotelefonoactualizar);
        txtcorreo=findViewById(R.id.txtnuevocorreoactualizar);
        txtdireccioncompleta=findViewById(R.id.txtnuevodireccioncompletaactualizar);
        txtdetalledireccion=findViewById(R.id.txtnuevodetalledireccionactualizar);
        txtcontra=findViewById(R.id.txtnuevopasswordactualizar);
        spinner = (Spinner) findViewById(R.id.spinneractualizar);
        btnactualizarcliente=findViewById(R.id.btnactualizarusuario);

        Bundle datos=getIntent().getExtras();
        assert datos != null;
        id_tipo_usuario=datos.getString("usuario");
        id_usuario=datos.getString("id_usuario");

        Consultacliente("https://foodexpress.webcindario.com/usuariocliente.php?user="+id_usuario);



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

        btnactualizarcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int  departamento= spinner.getSelectedItemPosition();
                String nombreu=txtnombre.getText().toString().trim();
                String telefonou=txttelefono.getText().toString().trim();
                String correou=txtcorreo.getText().toString().trim();
                String direccion_completau=txtdireccioncompleta.getText().toString().trim();
                String detalle_direccionu=txtdetalledireccion.getText().toString().trim();
                String passwordu=txtcontra.getText().toString().trim();
                String id=txttelefono.getText().toString().trim();

                if(departamento>0) {


                    if (TextUtils.isEmpty(nombreu)) {
                        txtnombre.setError("valor requerido");
                        txtnombre.requestFocus();
                    }
                    if (TextUtils.isEmpty(telefonou)) {
                        txttelefono.setError("valor requerido");
                        txttelefono.requestFocus();
                    }
                    if (TextUtils.isEmpty(correou)) {
                        txtcorreo.setError("valor requerido");
                        txtcorreo.requestFocus();
                    }
                    if (TextUtils.isEmpty(direccion_completau)) {
                        txtdireccioncompleta.setError("valor requerido");
                        txtdireccioncompleta.requestFocus();
                    }
                    if (TextUtils.isEmpty(detalle_direccionu)) {
                        txtdetalledireccion.setError("valor requerido");
                        txtdetalledireccion.requestFocus();
                    }
                    if (TextUtils.isEmpty(passwordu)) {
                        txtcontra.setError("valor requerido");
                        txtcontra.requestFocus();
                    }
                    else {

                        try {


                            Toast.makeText(getApplicationContext(), "usuario actualizado" , Toast.LENGTH_SHORT).show();

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



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent menuprincipal=new Intent(getApplicationContext(),menugeneralcliente.class);
        menuprincipal.putExtra("id_usuario",id_usuario);
        menuprincipal.putExtra("nombreus",nombre);
        menuprincipal.putExtra("departamento",departamento);
        menuprincipal.putExtra("direccion",direccion);
        menuprincipal.putExtra("detalle_direccion",detalle_direccion);
        startActivity(menuprincipal);
        finish();
    }

    private void Consultacliente(String URL) {

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
                    String correo=ja.getString(8);

                    txtnombre.setText(nombreus);
                    txtcorreo.setText(correo);
                    txtdetalledireccion.setText(detalle_direccion);
                    txtdireccioncompleta.setText(direccion);
                    txttelefono.setText(telefono);
                    txtcontra.setText(contra);


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