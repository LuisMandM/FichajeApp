package com.example.gestionapp.Model

import java.time.LocalDate
import java.util.Calendar

class Evento(
    var index: Int,
    var tipo: EnumEvent,
    var fecha: Calendar,
    var horaInit: String,
    var horaEnd: String,
    var notas: String,
    var usuario: Usuario
) {


}