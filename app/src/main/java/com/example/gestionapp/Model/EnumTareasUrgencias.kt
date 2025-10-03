package com.example.gestionapp.Model

enum class EnumTareasUrgencias (val texto:String){
    pgrf("Pgrf plasma traumatología"),
    curas_p("Curas planas"),
    curas_v("Curas vasculares"),
    retiro_s("Retiro de sutura"),
    att_paciente("Atención paciente urgencias"),
    vendajes("Vendajes"),
    Extracciones("Extracciones"),
    Admin_Medicamentos("Admon Medicamentos IV IM"),
    iontoforesis("Iontoforesis"),
    flebotomias("Flebotomias"),
    instala_Vesi("Instalación vesical"),
    //Curas("Curas"),
    Holter_I("Holter y mapa instalacione"),
    Holet_R("Holter y mapa retiro"),
    acc_VScanner("Acceso vías scaner "),
    Resonancia("Resonancia magnética"),
    Caducidad_CParada("Caducidad carro parada"),
    pedidos(" Pedidos de  materiales y medicamentos"),
    reposicion(" reposición de material y medicamentos"),
    Otros("Otros");

    companion object {
        fun fromText(texto: String):EnumTareasUrgencias?{
            return  EnumTareasUrgencias.entries.find { it.texto == texto}
        }
    }
}