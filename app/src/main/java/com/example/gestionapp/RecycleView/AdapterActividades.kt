package com.example.gestionapp.RecycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionapp.Model.EnumTareas
import com.example.gestionapp.R

class AdapterActividades(
    private val opciones: List<EnumTareas>,
    private val seleccionados: MutableList<EnumTareas> = mutableListOf()
) : RecyclerView.Adapter<AdapterActividades.ViewHolder>() {

    inner class ViewHolder(val checkBox: CheckBox) : RecyclerView.ViewHolder(checkBox)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val checkBox = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checkbox, parent, false) as CheckBox
        return ViewHolder(checkBox)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val opcion = opciones[position]
        holder.checkBox.text = opcion.texto
        holder.checkBox.isChecked = seleccionados.contains(opcion)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seleccionados.add(opcion)
            } else {
                seleccionados.remove(opcion)
            }
        }
    }

    override fun getItemCount(): Int = opciones.size

    fun getSeleccionados(): MutableList<EnumTareas> = seleccionados
}