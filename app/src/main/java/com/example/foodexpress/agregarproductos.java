package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class agregarproductos extends AppCompatActivity {


    JSONArray ja;
    JSONObject jo;
    String[] datos;
    RequestQueue requestQueue;

    private Button btnBuscar;
    private Button btnSubir;
    private ImageView imageView;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL ="https://foodexpress.webcindario.com/uploadproducto.php";
    private String KEY_NOMBRE = "nombre_producto";
    private String KEY_iDCATEGORIA = "id_tipo_producto";
    private String KEY_PRECIO = "precio";
    private String KEY_DETALLE = "detalle_producto";
    private String KEY_IMAGEN = "imagen";

    Spinner spinercategoria;
    EditText edtnombre,edtprecio;
    MultiAutoCompleteTextView edtdetalleproducto;
    String capturar_id="";
    String categoria="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarproductos);
        btnBuscar = (Button) findViewById(R.id.btnBuscarimagenproducto);
        btnSubir = (Button) findViewById(R.id.btnsubirproducto);
        edtnombre= (EditText) findViewById(R.id.edtnombreproducto);
        edtprecio= (EditText) findViewById(R.id.edtprecioproducto);
        edtdetalleproducto=(MultiAutoCompleteTextView)findViewById(R.id.edtdetalleproducto);
        spinercategoria=findViewById(R.id.spinnercategoria);
        imageView  = (ImageView) findViewById(R.id.imageViewproducto);

        listaproductos("https://foodexpress.webcindario.com/listatipoproductos.php");


        spinercategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoria= spinercategoria.getItemAtPosition(spinercategoria.getSelectedItemPosition()).toString();

               buscarid("https://foodexpress.webcindario.com/buscaridtipoproducto.php?categoria_producto="+categoria+"");


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });


        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre=edtnombre.getText().toString().trim();
                String precio=edtprecio.getText().toString().trim();
                String detalle=edtdetalleproducto.getText().toString().trim();
                int   categoria= spinercategoria.getSelectedItemPosition();

                if(categoria>=0){

                    if (TextUtils.isEmpty(nombre)) {
                        edtnombre.setError("valor requerido");
                        edtnombre.requestFocus();
                    }
                    if (TextUtils.isEmpty(precio)) {
                        edtprecio.setError("valor requerido");
                        edtprecio.requestFocus();
                    }
                    if (TextUtils.isEmpty(detalle)) {
                        edtdetalleproducto.setError("valor requerido");
                        edtdetalleproducto.requestFocus();
                    }
                    else {

                        try{

                            uploadImage();


                        }
                        catch (Exception e){

                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"seleccionar categoria",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void llenarLista(String[] array){
        if(datos!=null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, array);
            spinercategoria.setAdapter(adapter);
        }
    }


    private void  listaproductos(String URL){
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

                        capturar_id=jsonObject.getString("id_tipo_producto");


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

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getApplicationContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtener el nombre de la imagen
                String nombre=edtnombre.getText().toString().trim();
                String precio=edtprecio.getText().toString().trim();
                String detalle=edtdetalleproducto.getText().toString().trim();

                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_NOMBRE,nombre);
                params.put(KEY_iDCATEGORIA, capturar_id);
                params.put(KEY_PRECIO, precio);
                params.put(KEY_DETALLE, detalle);
                params.put(KEY_IMAGEN, imagen);

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }







    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}