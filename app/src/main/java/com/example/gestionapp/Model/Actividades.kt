package com.example.gestionapp.Model

import androidx.lifecycle.MutableLiveData

data class Actividades(
    var index: Int,
    var trabajo: EnumEvent,
    var jornada: MutableLiveData<Jornada>
) {
}