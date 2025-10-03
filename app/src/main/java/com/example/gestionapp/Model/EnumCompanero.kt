package com.example.gestionapp.Model

enum class EnumCompanero(val texto:String) {
    Maria("Maria Peña"),
    Cesar("Cesar Martinez"),
    Beatriz("Beatriz Cherta"),
    Sandra("Sandra Carracedo"),
    Nathaly("Nathaly Bustos"),
    Monica("Monica "),
    Desc("Desconocido"),
    MariaA("Maria Angeles Hernández"),
    LeireG("Leire González");

    companion object {
        fun fromText(texto: String):EnumCompanero?{
            return  EnumCompanero.entries.find { it.texto == texto}
        }
    }
}