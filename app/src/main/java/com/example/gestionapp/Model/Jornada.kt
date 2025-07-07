package com.example.gestionapp.Model

import android.icu.text.AlphabeticIndex
import java.util.Calendar

data class Jornada(
  var index: Int,
  var fecha: Calendar,
  var usuario: Int
){}