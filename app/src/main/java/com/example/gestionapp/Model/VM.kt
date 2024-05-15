package com.example.gestionapp.Model

import androidx.lifecycle.ViewModel
import java.util.Calendar

class VM : ViewModel() {
    var eventos: MutableList<Evento> = mutableListOf()

    init {
        draftDemo()
    }

    private fun draftDemo() {
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

    private fun indexAsigment(): Int {
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

    fun UpdateRegister(event: Evento): Boolean {
        try {
            val index = eventIndex(event.index)
            return if (index != -1){
                eventos[index] = event
                true
            }else false

        } catch (e: Exception) {
            return false
        }
    }

    fun searchID(id: Int): Evento? {
        var found: Evento? = null
        for (evento in eventos) {
            if (evento.index == id) {
                found = evento
                break
            }
        }
        return found;
    }

    fun indexEnum(tipo: EnumEvent): Int {
        return when (tipo) {
            EnumEvent.GENERAL -> 0
            EnumEvent.GUARDIA -> 1
            EnumEvent.REPORTE_HORARIO -> 2
        }
    }

    private fun eventIndex(id: Int): Int {
        var index = -1
        for (i in 0..eventos.size - 1) {
            if (eventos[i].index == id) {
                index = i
                break
            }
        }
        return index
    }
}