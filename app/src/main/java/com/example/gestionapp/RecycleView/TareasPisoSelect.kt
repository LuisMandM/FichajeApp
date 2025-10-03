package com.example.gestionapp.RecycleView

import com.example.gestionapp.Model.EnumTareas

data class TareasPisoSelect(
    val tarea: EnumTareas,
    var selected: Boolean = false
    ) {

}