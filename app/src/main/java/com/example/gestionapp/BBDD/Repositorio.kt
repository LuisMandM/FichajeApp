package com.example.gestionapp.BBDD

import androidx.lifecycle.MutableLiveData
import com.example.gestionapp.Model.Actividades
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.Model.Jornada
import com.example.gestionapp.Model.Usuario

class Repositorio(val parser: BBDDParse) {
    fun mostrarEventos(): MutableLiveData<List<Evento>> {
        return parser.mostrarEventos()
    }

    fun mostrarJornadas(): MutableLiveData<List<Jornada>> {
        return parser.mostrarJornadas()
    }

    fun mostrarActividades(jornadaID:Int): MutableLiveData<List<Actividades>> {
        return parser.mostrarActividades(jornadaID)
    }

    fun insertarEvento(current: Evento) {
        parser.insertarEvento(current)
    }

    fun insertarJornada(current: Jornada) {
        parser.insertarJornada(current)
    }

    fun insertarActividad(current: Actividades) {
        parser.insertarActividad(current)
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

    fun idMaximoActvidad(): MutableLiveData<Int> {
        return parser.buscarIdMaximoActvidad()
    }

    fun idMaximoJornada(): MutableLiveData<Int> {
        return parser.buscarIdMaximoJornada()
    }

    fun eventById(index: Int): MutableLiveData<Evento> {
        return parser.eventById(index)
    }
    fun JornadaById(index: Int): MutableLiveData<Jornada> {
        return parser.JornadaById(index)
    }

    fun getKey(user:String, password:String):MutableLiveData<String>{
        return parser.getKey(user,password)
    }

    fun mostrarUsuarios():MutableLiveData<List<Usuario>>{
        return parser.mostrarUsuarios()
    }

}