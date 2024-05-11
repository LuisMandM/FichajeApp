package com.example.gestionapp.RecycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.databinding.CardEventRecyclerviewBinding


class Adapter(val lista: MutableList<Evento>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    //El ViewHolder es la clase de cada uno de los contenedores
    inner class ViewHolder(val binding: CardEventRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    //captura la vista que hemos creado (recyclerview_item) y crea una instancia del viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardEventRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //cargamos los datos en cada una de las instancias del ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtVTipo.text = lista[position].tipo.toString()
        holder.binding.txtVwHoraInit.text = lista[position].horaInit
        holder.binding.txtVwHoraEnd.text = lista[position].horaEnd
    }

    //retorna el número de elementos que vamos a querer que tenga el contenedor padre
    override fun getItemCount(): Int {
        return lista.count()
    }
}
