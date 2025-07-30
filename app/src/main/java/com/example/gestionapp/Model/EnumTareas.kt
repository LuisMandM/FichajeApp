package com.example.gestionapp.Model

enum class EnumTareas (val texto:String){
    Ingreso_Paciente("Ingreso Pacientes"),
    Alta_Paciente("Alta paciente"),
    Valoracion_Pte("Valoración Paciente"),
    Venopuncion("Venopunción"),
    Toma_Constantes_V("Toma de constantes"),
    Glucometrias("Glucometrias"),
    Extracciones("Extracciones"),
    Admin_Medicamentos_O("Admon Medicamentos Oral"),
    Admin_Medicamentos_I("Admon Medicamentos Inyectable"),
    Transfucion("Transfusión Sanguinea"),
    Cateterismo_Vesi("Cateterismo Vesical"),
    //Curas("Curas"),
    Retiro_Sonda("Retiro Sonda"),
    Retiro_Dre("Retiro de Drenaje"),
    Identificacion("Identificacion del Paciente"),
    Higiene_manos("Higiene de Manos"),
    Extraccion_Art("Extracción Muestra -Arterial"),
    Hemocultivo("Recogida y procesamiento muestras (Hemocultivo)"),
    Cura("Cura Heridas"),
    Valoracion_UPP("Valoración Ulceras por Presión"),
    Otros("Otros");

    companion object {
        fun fromText(texto: String):EnumTareas?{
            return  EnumTareas.entries.find { it.texto == texto}
        }
    }
}