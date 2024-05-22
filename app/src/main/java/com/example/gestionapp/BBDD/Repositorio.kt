package com.example.gestionapp.BBDD

import androidx.lifecycle.MutableLiveData
import com.example.gestionapp.Model.Evento

class Repositorio(val parser: BBDDParse) {
    fun mostrarEventos(): MutableLiveData<List<Evento>> {
        return parser.mostrarEventos()
    }

    fun insertarEvento(current: Evento) {
        parser.insertarEvento(current)
    }

    fun borrarEvento(current: Evento) {
        parser.borrarEvento(current)
    }

    fun modificarEvento(current: Evento) {
        parser.modificarEvento(current)
    }

    fun idMaximo(): MutableLiveData<Int> {
        return parser.buscarIdMaximo()
    }

    fun eventById(index: Int): MutableLiveData<Evento> {
        return parser.eventById(index)
    }

}