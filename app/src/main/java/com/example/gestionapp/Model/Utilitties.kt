package com.example.gestionapp.Model

class Utilitties {

    public fun enumValue(index:Int):EnumEvent{
        return when(index){
            0 -> EnumEvent.GENERAL
            1 -> EnumEvent.GUARDIA
            2 -> EnumEvent.REPORTE_HORARIO
            else -> EnumEvent.GENERAL
        }
    }

    fun indexEnum(tipo: EnumEvent): Int {
        return when (tipo) {
            EnumEvent.GENERAL -> 0
            EnumEvent.GUARDIA -> 1
            EnumEvent.REPORTE_HORARIO -> 2
        }
    }
}