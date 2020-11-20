package com.example.foodexpress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class vermenuproductos extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GridView mGridView;
    private ProgressBar mProgressBar;

    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    String id_tipo_producto="0";
    String id_usuario;
    private String FEED_URL;
    String nombre;
    String departamento,direccion,detalle_direccion;
    private Object vermenucliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermenuproductos);

        Bundle parametros=this.getIntent().getExtras();


        if(parametros !=null){
            id_tipo_producto=parametros.getString("id");
            id_usuario=parametros.getString("id_usuario");
            nombre=parametros.getString("nombreus");
            departamento=parametros.getString("departamento");
            direccion=parametros.getString("direccion");
            detalle_direccion=parametros.getString("detalle_direccion");
            FEED_URL = "https://foodexpress.webcindario.com/obtenerproductos.php?id_tipo_producto="+id_tipo_producto;

        }




        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_item, mGridData);
        mGridView.setAdapter(mGridAdapter);

        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                GridItem item = (GridItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(vermenuproductos.this, activitydetails.class);

                //Pass the image title and url to DetailsActivity
                intent. putExtra("id_producto", item.getId_producto()).
                        putExtra("precio",item.getPrecio()).
                        putExtra("detalle",item.getDetalle_producto()).
                        putExtra("nombre_producto", item.getNombre_producto()).
                        putExtra("imagen", item.getImagen()).
                        putExtra("id_usuario",id_usuario).
                        putExtra("nombreus",nombre).
                        putExtra("departamento",departamento).
                        putExtra("direccion",direccion).
                        putExtra("detalle_direccion",detalle_direccion);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Start details activity
                startActivity(intent);
                finish();
            }
        });

        //Start download
        new AsyncHttpTask().execute(FEED_URL);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ma=new Intent(getApplicationContext(),vermenucliente.class);
        ma.putExtra("id_usuario",id_usuario);
        ma.putExtra("nombreus",nombre);
        ma.putExtra("departamento",departamento);
        ma.putExtra("direccion",direccion);
        ma.putExtra("detalle_direccion",detalle_direccion);
        ma.putExtra("entrar","1");
        startActivity(ma);
        finish();
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient  httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            } else {
                Toast.makeText(vermenuproductos.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            //Hide progressbar
            mProgressBar.setVisibility(View.GONE);
        }
    }


    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {

        try {
            JSONArray mJsonArray = new JSONArray(result);
            JSONObject mJsonObject = new JSONObject();
            GridItem item;

            for (int i = 0; i < mJsonArray.length(); i++) {
                item = new GridItem();

                mJsonObject = mJsonArray.getJSONObject(i);
                //id_producto,nombre_producto,id_tipo_producto,precio,detalle_producto,imagen
                String id_producto = mJsonObject.getString("id_producto");
                String name = mJsonObject.getString("nombre_producto");
                String id_tipo_producto = mJsonObject.getString("id_tipo_producto");
                String precio = mJsonObject.getString("precio");
                String detalle_producto = mJsonObject.getString("detalle_producto");
                String image = mJsonObject.getString("imagen");

                item.setId_producto(id_producto);
                item.setNombre_producto(name);
                item.setId_tipo_producto(id_tipo_producto);
                item.setPrecio(precio);
                item.setDetalle_producto(detalle_producto);
                if(image !=null)
                    item.setImagen(image);
                mGridData.add(item);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}