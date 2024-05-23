package com.example.gestionapp.Model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gestionapp.BBDD.Repositorio
import kotlinx.coroutines.launch

class VM(private val repositorio: Repositorio) : ViewModel(){
    lateinit var eventos: MutableLiveData<List<Evento>>
    //var usuarios: MutableList<Usuario> = mutableListOf()
    //lateinit var currentUser: MutableLiveData<String>
    lateinit var numMax: MutableLiveData<Int>
    lateinit var currentEvent: MutableLiveData<Evento>
    lateinit var usuarios: MutableLiveData<List<Usuario>>


    fun mostrarEventos()= viewModelScope.launch {
        eventos = repositorio.mostrarEventos()
    }

    fun insertEvent(current:Evento){
        repositorio.insertarEvento(current)
    }
    fun deleteEvent(current:Evento){
        repositorio.borrarEvento(current)
    }
    fun updateEvent(current:Evento){
        repositorio.modificarEvento(current)
    }

    fun idMax()= viewModelScope.launch {
        numMax = repositorio.idMaximo()
    }

    fun searchEvent(index :Int)= viewModelScope.launch {
        currentEvent = repositorio.eventById(index)
    }

    /*fun validateUser(user:String, password:String) = viewModelScope.launch {
        currentUser = repositorio.getKey(user,password)
    }*/

    fun showUsers() = viewModelScope.launch {
        usuarios = repositorio.mostrarUsuarios()
    }


   /* fun searchIDEvent(id: Int): Evento? {
        var found: Evento? = null
        for (evento in eventos) {
            if (evento.index == id) {
                found = evento
                break
            }
        }
        return found;
    }*/


    /*init {
        draftDemo()
    }

    private fun draftDemo() {
        usuarios.add(
            Usuario("luis", "12345", Role.GENERAL, "1")
        )
        usuarios.add(
            Usuario("admin", "12345", Role.ADMIN, "2")
        )
        usuarios.add(
            Usuario("general", "12345", Role.GENERAL, "3")
        )

        eventos.add(
            Evento(
                indexAsigment(),
                EnumEvent.GUARDIA,
                Calendar.getInstance(),
                "15:30",
                "16:00",
                "Prueba",
                usuarios[0].index
            )
        )

        var calendar = Calendar.getInstance()
        calendar.set(2024, 5, 10)
        eventos.add(
            Evento(
                indexAsigment(),
                EnumEvent.GENERAL,
                Calendar.getInstance(),
                "12:30",
                "16:00",
                "Prueba 2",
                usuarios[2].index
            )
        )

        calendar.set(2024, 5, 16)
        eventos.add(
            Evento(
                indexAsigment(),
                EnumEvent.REPORTE_HORARIO,
                Calendar.getInstance(),
                "14:30",
                "16:00",
                "Prueba 3", usuarios[1].index
            )
        )




    }

    private fun indexAsigment(): Int {
        return eventos.size + 1
    }

    fun addRegister(tipo: EnumEvent, fecha: Calendar, horaI: String, horaE: String, notas: String):
            Boolean {
        try {
            val current = Evento(indexAsigment(), tipo, fecha, horaI, horaE, notas, currentUser.index)
            eventos.add(current)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun updateRegister(event: Evento): Boolean {
        try {
            val index = eventIndex(event.index)
            return if (index != -1) {
                eventos[index] = event
                true
            } else false

        } catch (e: Exception) {
            return false
        }
    }

    fun deleteRegister(id: Int): Boolean {
        return try {
            eventos.removeAt(id - 1)
            true
        } catch (e: Exception) {
            false
        }

    }
*/
    /*fun validateUser(username: String, password: String): Boolean {
        var valid = false
        usuarios.forEach {
            if (it.username == username && it.password == password) {
                valid = true
                currentUser = searchIDUser(it.index)!!
            }
        }
        return valid
    }

    fun searchIDEvent(id: Int): Evento? {
        var found: Evento? = null
        for (evento in eventos) {
            if (evento.index == id) {
                found = evento
                break
            }
        }
        return found;
    }
*/
    /*fun searchIDUser(id: String): Usuario? {
        var found: Usuario? = null
        for (usuario in usuarios) {
            if (usuario.index == id) {
                found = usuario
                break
            }
        }
        return found;
    }*/


    /*private fun eventIndex(id: Int): Int {
        var index = -1
        for (i in 0..eventos.size - 1) {
            if (eventos[i].index == id) {
                index = i
                break
            }
        }
        return index
    }*/
}

class EventoViewModelFactory(private val miRepositorio: Repositorio) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VM(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}