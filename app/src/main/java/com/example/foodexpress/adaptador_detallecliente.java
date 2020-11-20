package com.example.foodexpress;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adaptador_detallecliente  extends  RecyclerView.Adapter<adaptador_detallecliente.MyItemcliente> {

    private List<clienteproductosdetalle> items;
    private Context context;
    String ticket;
    String id_producto;
    String URL = "";


    public adaptador_detallecliente(List<clienteproductosdetalle> items,Context context) {
        this.items = items;
        this.context = context;
    }


    public static class MyItemcliente extends RecyclerView.ViewHolder
    {
        private final TextView tvtotal;
        public TextView txtnombre,txtprecio,txtcantidad,txttotalpagar;
        public ImageView imageView;
        public Button btnDelete;


        public MyItemcliente(View itemView) {
            super(itemView);
            txtnombre = itemView.findViewById(R.id.nombreproductodetallecliente);
            txtprecio = itemView.findViewById(R.id.precioproductodetallecliente);
            txtcantidad = itemView.findViewById(R.id.cantidaddetallecliente);
            imageView=itemView.findViewById(R.id.imagenproductodetallecliente);
            txttotalpagar=itemView.findViewById(R.id.tvtotalapagar);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvtotal=itemView.findViewById(R.id.tvtotalapagar);


        }

    }


    @NonNull
    @Override
    public adaptador_detallecliente.MyItemcliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflar layout que contiene los campos
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.clientecard,null);
        return new adaptador_detallecliente.MyItemcliente(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final adaptador_detallecliente.MyItemcliente holder, final int position) {


        try{
            clienteproductosdetalle cp=items.get(position);

                  final String total=cp.getTotal();
                holder.txtnombre.setText(cp.getNombre_producto());
                holder.txtprecio.setText("precio: $ " + cp.getPrecio());
                holder.txtcantidad.setText("cantidad: " + cp.getCantidad());
                Glide.with(context).load(cp.getImagen()).into(holder.imageView);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try{



                            clienteproductosdetalle cp=items.get(position);
                            ticket = cp.getTicket_cliente();
                            id_producto = cp.getId_producto();
                            URL = "https://foodexpress.webcindario.com/eliminarDetalle_Venta.php?id_producto="+id_producto+"&ticket="+ticket;
                            eliminar(URL);
                          items.remove(holder.getAdapterPosition());
                          notifyDataSetChanged();
                         Intent dv=new Intent(context,detallecliente.class);
                            dv.putExtra("id_usuario",cp.getId_usuario());
                            dv.putExtra("nombreus",cp.getNombre_cliente());
                            dv.putExtra("departamento",cp.getDepartamento());
                            dv.putExtra("direccion",cp.getDireccion());
                            dv.putExtra("detalle_direccion",cp.getDetalle_direccion());
                         context.startActivity(dv);


                        }
                        catch (Exception e){
                            Toast.makeText(context, e.getMessage()+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }



                    }
                });

        }catch (Exception ex)
        {


        }



    }

    @Override
    public int getItemCount() {


            return items.size();


    }


    public void  eliminar(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("id_producto",id_producto);
                parametros.put("ticket",ticket);


                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


}
