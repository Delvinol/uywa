package com.delvin.uywalkyc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.delvin.uywalkyc.PaseadoresSchema.PaseadorResponseItem

interface OnDetalleClickListener {
    fun onDetalleClick(
        position: Int,
        ubicacion: String, nombres: String,
        apellidos:String,
        email:String,
        descripcion:String,
        tarifa: Double,
        categoriaNombre:String,
        dni:String, celular:String,latitud:String,longitud:String)
}
var urlbase="http://192.168.18.8:8080/api/v1/paseador/"
class MyAdapter(var con: Context,var detalleClickListener: OnDetalleClickListener , var list: List<PaseadorResponseItem>):RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    inner class ViewHolder(v : View):RecyclerView.ViewHolder(v)
    {
        var tvName = v.findViewById<TextView>(R.id.RV_tv)
        var tvUbicacion = v.findViewById<TextView>(R.id.ubicacion)
        var tvCategoria = v.findViewById<TextView>(R.id.categoria)
        var tvTarifa = v.findViewById<TextView>(R.id.tarifa)
        var btnDetalle=v.findViewById<Button>(R.id.btnDetalle)
        var iconImageView = v.findViewById<ImageView>(R.id.iconImageView)
    }
    val coloresIcono = arrayOf(
        R.color.black,
        R.color.bluepalid,
        R.color.orangepalid,
        R.color.green,
        R.color.greenpalid,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(con).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paseador = list[position]

        val colorResource = coloresIcono[position % coloresIcono.size]

        // Aplicar el color como tint
        val color = ContextCompat.getColor(con, colorResource)
        holder.iconImageView.setColorFilter(color)
        // Formatear el texto según tus requisitos
        val categoriaText = "Categoría: ${paseador.categoriaNombre}"
        val ubicacionText = "Ubicación: ${paseador.ubicacion}"
        val tarifaText = "Tarifa: S/.${paseador.tarifa}"

        // Establecer el texto en los TextView correspondientes

        holder.tvName.text = paseador.nombres.capitalize()
        holder.tvUbicacion.text = ubicacionText
        holder.tvCategoria.text = categoriaText
        holder.tvTarifa.text = tarifaText
        holder.btnDetalle.setOnClickListener {
            val paseador = list[position]
            detalleClickListener.onDetalleClick(position, paseador.ubicacion, paseador.nombres,paseador.apellidos,paseador.email,paseador.descripcion,paseador.tarifa,paseador.categoriaNombre,paseador.dni,paseador.celular,paseador.latitud,paseador.longitud)
        }
    }


}