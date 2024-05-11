package com.example.gestionapp.Model

import androidx.lifecycle.ViewModel
import java.util.Calendar

class VM : ViewModel() {
    var eventos: MutableList<Evento> = mutableListOf()

    init {
        draftDemo()
    }

    fun draftDemo() {
        eventos.add(
            Evento(
                1,
                EnumEvent.GUARDIA,
                Calendar.getInstance(),
                "15:30",
                "16:00",
                "Prueba"
            )
        )
    }

    fun indexAsigment(): Int {
        return eventos.lastIndex + 1
    }

    fun addRegister(tipo: EnumEvent, fecha: Calendar, horaI: String, horaE: String, notas: String):
            Boolean {
        try {
            val current = Evento(indexAsigment(), tipo, fecha, horaI, horaE, notas)
            eventos.add(current)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}