package com.example.gestionapp.Model

import androidx.lifecycle.ViewModel
import java.util.Calendar

class VM : ViewModel() {
    var eventos: MutableList<Evento> = mutableListOf()
    var usuarios: MutableList<Usuario> = mutableListOf()
    lateinit var currentUser: Usuario

    init {
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

    fun validateUser(username: String, password: String): Boolean {
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

    fun searchIDUser(id: String): Usuario? {
        var found: Usuario? = null
        for (usuario in usuarios) {
            if (usuario.index == id) {
                found = usuario
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