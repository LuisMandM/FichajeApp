package com.example.gestionapp.BBDD

import androidx.lifecycle.MutableLiveData
import com.example.gestionapp.Model.Actividades
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.Model.Jornada
import com.example.gestionapp.Model.Role
import com.example.gestionapp.Model.Usuario
import com.example.gestionapp.Model.Utilitties
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
                        Utilitties().enumValue(i.getInt("tipo")),
                        fecha,
                        i.getString("HoraInit") ?: "",
                        i.getString("HoraEnd") ?: "",
                        i.getString("notas") ?: "",
                        i.getInt("usuario") ?: 0
                    )
                }
                currentEventos.postValue(eventos)
            }
        }
        return currentEventos
    }

    fun mostrarJornadas(): MutableLiveData<List<Jornada>> {
        val currentJornada: MutableLiveData<List<Jornada>> = MutableLiveData()
        val query = ParseQuery.getQuery<ParseObject>("Jornada")
        query.findInBackground() { objects, e ->
            if (e == null) {
                val eventos = objects.map { i ->
                    val fecha = Calendar.getInstance()
                    fecha.timeInMillis = i.getLong("fecha")
                    Jornada(
                        i.getInt("index"),
                        fecha,
                        i.getInt("usuario") ?: 0
                    )
                }
                currentJornada.postValue(eventos)
            }
        }
        return currentJornada
    }

    fun mostrarActividades(jornadaID : Int): MutableLiveData<List<Actividades>> {
        val currentActivities: MutableLiveData<List<Actividades>> = MutableLiveData()
        val query = ParseQuery.getQuery<ParseObject>("RelTrabajosJornada")
        query.findInBackground() { objects, e ->
            if (e == null) {
                val currentJornada: MutableLiveData<Jornada> = JornadaById(jornadaID)

                val actividad = objects.map { i ->
                    Actividades(
                        i.getInt("index"),
                        Utilitties().enumValue(i.getInt("Trabajo")),
                        currentJornada
                    )
                }
                currentActivities.postValue(actividad)
            }
        }
        return currentActivities
    }

    fun insertarEvento(current: Evento) {
        val registro = ParseObject("Eventos")
        registro.put("index", current.index)
        registro.put("tipo", Utilitties().indexEnum(current.tipo))
        registro.put("fecha", current.fecha.timeInMillis)
        registro.put("HoraInit", current.horaInit)
        registro.put("HoraEnd", current.horaEnd)
        registro.put("notas", current.notas)
        registro.put("usuario",current.usuario)
        registro.saveInBackground {
            if (it != null) {
                it.localizedMessage?.let { message ->
                    throw Exception(message)
                }
            }
        }
    }
    fun insertarJornada(current: Jornada) {
        val registro = ParseObject("Jornada")
        registro.put("index", current.index)
        registro.put("fecha", current.fecha.timeInMillis)
        registro.put("usuario",current.usuario)
        registro.saveInBackground {
            if (it != null) {
                it.localizedMessage?.let { message ->
                    throw Exception(message)
                }
            }
        }
    }

    fun insertarActividad(current: Actividades) {
        val registro = ParseObject("RelTrabajosJornada")
        val jornada : MutableLiveData<Jornada> = MutableLiveData()
        registro.put("index", current.index)
        registro.put("Trabajo", Utilitties().indexEnum(current.trabajo))
        registro.put("Jornada", current.jornada.value?.index ?: -1)
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
                parseObject.put("tipo", Utilitties().indexEnum(current.tipo))
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

    fun eventById(id:Int):MutableLiveData<Evento>{
        val current = MutableLiveData<Evento>()
        val query = ParseQuery.getQuery<ParseObject>("Eventos")
        query.whereEqualTo("index", id)
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                val fecha = Calendar.getInstance()
                fecha.timeInMillis = i.getLong("fecha")
                val evento = Evento(
                    i.getInt("index"),
                    Utilitties().enumValue(i.getInt("tipo")),
                    fecha,
                    i.getString("HoraInit") ?: "",
                    i.getString("HoraEnd") ?: "",
                    i.getString("notas") ?: "",
                    i.getInt("Usuario") ?: 0
                )
                current.postValue(evento)
            } else {
                throw Exception(parseException)
            }
        }
        return current
    }

    fun JornadaById(id:Int):MutableLiveData<Jornada>{
        val current = MutableLiveData<Jornada>()
        val query = ParseQuery.getQuery<ParseObject>("Eventos")
        query.whereEqualTo("index", id)
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                val fecha = Calendar.getInstance()
                fecha.timeInMillis = i.getLong("fecha")
                val jornada = Jornada(
                    i.getInt("index"),
                    fecha,
                    i.getInt("usuario") ?: 0
                )
                current.postValue(jornada)
            } else {
                throw Exception(parseException)
            }
        }
        return current
    }

    fun mostrarUsuarios(): MutableLiveData<List<Usuario>> {
        val currentEventos: MutableLiveData<List<Usuario>> = MutableLiveData()
        val query = ParseQuery.getQuery<ParseObject>("Usuarios")
        query.findInBackground() { objects, e ->
            if (e == null) {
                val usuarios = objects.map { i ->
                    Usuario(
                        i.getString("User")?:"",
                        i.getString("Password")?:"",
                        Role.GENERAL, i.getInt("index")?:-1
                    )
                }
                currentEventos.postValue(usuarios)
            }
        }
        return currentEventos
    }

    fun getKey(user:String, password:String):MutableLiveData<String>{
        val current = MutableLiveData<String>()
        val query = ParseQuery.getQuery<ParseObject>("Usuarios")
        query.whereEqualTo("User", user)
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                if (i.getString("Password").equals(password)){
                    current.postValue(i.getString("objectId"))
                }else current.postValue("")
            } else {
                print(parseException)
                throw Exception(parseException)
            }
        }
        return current
    }



    fun buscarIdMaximo(): MutableLiveData<Int> {
        val query =
            ParseQuery.getQuery<ParseObject>("Eventos")
        var max = MutableLiveData<Int>()
        query.addDescendingOrder("index")
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                val id = i.getInt("index")
                max.postValue(id)
            } else {
                max.postValue(0)
            }
        }
        return max
    }

    fun buscarIdMaximoJornada(): MutableLiveData<Int> {
        val query =
            ParseQuery.getQuery<ParseObject>("Jornada")
        var max = MutableLiveData<Int>()
        query.addDescendingOrder("index")
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                val id = i.getInt("index")
                max.postValue(id)
            } else {
                max.postValue(0)
            }
        }
        return max
    }

    fun buscarIdMaximoActvidad(): MutableLiveData<Int> {
        val query =
            ParseQuery.getQuery<ParseObject>("RelTrabajosJornada")
        var max = MutableLiveData<Int>()
        query.addDescendingOrder("index")
        query.getFirstInBackground { i, parseException ->
            if (parseException == null) {
                val id = i.getInt("index")
                max.postValue(id)
            } else {
                max.postValue(0)
            }
        }
        return max
    }
}