package com.example.foodexpress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class adaptador_categoriaproductos extends RecyclerView.Adapter<adaptador_categoriaproductos.MyItemViewHolder> {

    private List<categoria_producto> items;
    private Context context;

    public adaptador_categoriaproductos(List<categoria_producto> items,Context context) {
        this.items = items;
        this.context = context;
    }






    public static class MyItemViewHolder extends RecyclerView.ViewHolder
    {
        public  TextView txtnombre;
         public ImageView imageView;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            txtnombre = itemView.findViewById(R.id.nombrecategoria);
            imageView=itemView.findViewById(R.id.imagencategoria);


        }
    }


    @Override
    public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar layout que contiene los campos
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.categoria_card,null);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyItemViewHolder holder, final int position) {


        try{
            categoria_producto cp=items.get(position);
            final String id=cp.getId_tipo_producto();
            final  String id_usuario=cp.getId_usuario();
            final  String nombre=cp.getNombre();
            final  String departamento=cp.getDepartamento();
            final  String direccion=cp.getDireccion();
            final  String detalle_direccion=cp.getDetalle_direccion();
            holder.txtnombre.setText(cp.getCategoria_producto());
            Glide.with(context).load(cp.getImagen()).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{

                    Intent pro=new Intent(context,vermenuproductos.class);
                    pro.putExtra("id",id);
                    pro.putExtra("id_usuario",id_usuario);
                    pro.putExtra("nombreus",nombre);
                    pro.putExtra("departamento",departamento);
                    pro.putExtra("direccion",direccion);
                    pro.putExtra("detalle_direccion",detalle_direccion);
                    pro.putExtra("EXIT", true);
                    context.startActivity(pro);

                }
                    catch (Exception e){
                    Toast.makeText(context,e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }





                }
            });



        }catch (Exception ex)
        {
            Toast.makeText(context,ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }





}
