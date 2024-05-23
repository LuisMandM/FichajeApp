package com.example.gestionapp.BBDD

import androidx.lifecycle.MutableLiveData
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.Model.Usuario

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

    fun getKey(user:String, password:String):MutableLiveData<String>{
        return parser.getKey(user,password)
    }

    fun mostrarUsuarios():MutableLiveData<List<Usuario>>{
        return parser.mostrarUsuarios()
    }

}