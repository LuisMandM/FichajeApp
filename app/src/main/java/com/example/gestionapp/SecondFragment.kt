package com.example.gestionapp

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gestionapp.Model.EnumEvent
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.databinding.FragmentSecondBinding
import java.util.Calendar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    var fecha: Calendar = Calendar.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fechaEvent()
        Toast.makeText(
            (activity as MainActivity),
            "Fecha seleccionada: $fecha",
            Toast.LENGTH_SHORT
        ).show()
        fillSpinner()

        binding.btnCR.setOnClickListener {

            val tipo = binding.spinnerMotivo.selectedItem.toString()
            val horaI = binding.edTxHoraInit.text.toString()
            val horaE = binding.edTxHoraEnd.text.toString()
            val notas = binding.edTxObservations.text.toString()
            val current = Evento(selectedEnum(tipo), fecha, horaI, horaE, notas)
        }


    }

    private fun fillSpinner() {
        val tipos = listOf(EnumEvent.GENERAL, EnumEvent.GUARDIA, EnumEvent.REPORTE_HORARIO)
        binding.spinnerMotivo.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, tipos)
    }

    private fun fechaEvent() {
        var date = arguments?.getLong("fecha") ?: Calendar.getInstance().timeInMillis
        var convert = Calendar.getInstance();
        convert.timeInMillis = date
        fecha = convert;
    }

    private fun selectedEnum(enumString: String): EnumEvent {
        when (enumString) {
            EnumEvent.GENERAL.toString() -> return EnumEvent.GENERAL
            EnumEvent.GUARDIA.toString() -> return EnumEvent.GUARDIA
            EnumEvent.REPORTE_HORARIO.toString() -> return EnumEvent.REPORTE_HORARIO
            else -> return EnumEvent.GENERAL

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}