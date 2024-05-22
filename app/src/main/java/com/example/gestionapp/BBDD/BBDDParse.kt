package com.example.gestionapp.BBDD

import androidx.lifecycle.MutableLiveData
import com.example.gestionapp.Model.EnumEvent
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.Model.VM
import com.parse.ParseObject
import com.parse.ParseQuery
import java.util.Calendar

class BBDDParse {
    fun mostrarEventos(): MutableLiveData<List<Evento>> {
        val currentEventos: MutableLiveData<List<Evento>> = MutableLiveData()
        val query = ParseQuery.getQuery<ParseObject>("Eventos")
        query.findInBackground() { objects, e ->
            if (e == null) {
                val eventos = objects.map { i ->
                    val fecha = Calendar.getInstance()
                    fecha.timeInMillis = i.getLong("fecha")
                    Evento(
                        i.getInt("index"),
                        EnumEvent.GENERAL,
                        fecha,
                        i.getString("HoraInit") ?: "",
                        i.getString("HoraEnd") ?: "",
                        i.getString("notas") ?: "",
                        i.getString("Usuario") ?: ""
                    )
                }
            }
        }
        return currentEventos
    }

    fun insertarEvento(current: Evento) {
        val registro = ParseObject("Eventos")
        registro.put("index", current.index)
        registro.put("tipo", VM().indexEnum(current.tipo))
        registro.put("fecha", current.fecha.timeInMillis)
        registro.put("HoraInit", current.horaInit)
        registro.put("HoraEnd", current.horaEnd)
        registro.put("notas", current.notas)
        registro.put("Usuario", current.usuario)
        registro.saveInBackground {
            if (it != null) {
                it.localizedMessage?.let { message ->
                    throw Exception(message)
                }
            }
        }
    }

    fun borrarEvento(current: Evento) {
        val query =
            ParseQuery.getQuery<ParseObject>("Eventos")
        query.whereEqualTo("index", current.index)
        query.getFirstInBackground { parseObject, parseException ->
            if (parseException == null) {
                parseObject.deleteInBackground {
                    if (it != null) {
                        throw Exception(it.localizedMessage)
                    }
                }
            } else {
                throw Exception(parseException.localizedMessage)
            }
        }
    }

    fun modificarEvento(current: Evento){
        val query =
            ParseQuery.getQuery<ParseObject>("Eventos")
        query.whereEqualTo("index", current.index)
        query.getFirstInBackground { parseObject, parseException ->
            if (parseException == null) {
                parseObject.put("tipo", VM().indexEnum(current.tipo))
                parseObject.put("HoraInit", current.horaInit)
                parseObject.put("HoraEnd", current.horaEnd)
                parseObject.put("notas", current.notas)
                parseObject.saveInBackground {
                    if (it != null) {
                        throw Exception(it.localizedMessage)
                    }
                }
            } else {
                throw Exception(parseException.localizedMessage)
            }
        }
    }
}