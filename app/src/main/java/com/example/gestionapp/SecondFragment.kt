package com.example.gestionapp

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.gestionapp.Model.EnumEvent
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var fecha: Calendar = Calendar.getInstance()
    private var id_Evento: Int = -1
    private var creating: Boolean = true

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
        fillSpinner()

        id_Evento = arguments?.getInt("id_evento") ?: -1
        if (id_Evento != -1) {
            creating = false
            binding.btnDelete.isEnabled = true
            val current = (activity as MainActivity).viewModel.searchIDEvent(id_Evento)
            current?.let {

                binding.spinnerMotivo.setSelection(
                    (activity as MainActivity).viewModel.indexEnum(current.tipo)
                )
                binding.edTxHoraInit.setText(current.horaInit)
                binding.edTxHoraEnd.setText(current.horaEnd)
                binding.edTxObservations.setText(current.notas)
            }
        } else {
            binding.btnDelete.isEnabled = false
        }


        binding.btnCR.setOnClickListener {
            if (creating) SaveEvent()
            else updateEvent()
        }

        binding.btnDelete.setOnClickListener {
            deleteEvent()
        }


    }

    private fun deleteEvent() {
        if ((activity as MainActivity).viewModel.deleteRegister(id_Evento)) {
            Toast.makeText(
                (activity as MainActivity), "Registro Eliminado Correctamente",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)
        } else Toast.makeText(
            (activity as MainActivity), "Algo ha ido mal intenta nuevamente",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateEvent() {
        val current = Evento(
            id_Evento,
            selectedEnum(binding.spinnerMotivo.selectedItem.toString()), fecha,
            binding.edTxHoraInit.text.toString(),
            binding.edTxHoraEnd.text.toString(),
            binding.edTxObservations.text.toString(),(activity as MainActivity).viewModel.currentUser
        )

        if ((activity as MainActivity).viewModel.updateRegister(current)) {
            Toast.makeText(
                (activity as MainActivity), "Registro Actualizado Correctamente",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)
        } else Toast.makeText(
            (activity as MainActivity), "Algo ha ido mal intenta nuevamente",
            Toast.LENGTH_SHORT
        ).show()

    }

    private fun SaveEvent() {
        val tipo = binding.spinnerMotivo.selectedItem.toString()
        val horaI = binding.edTxHoraInit.text.toString()
        val horaE = binding.edTxHoraEnd.text.toString()
        val notas = binding.edTxObservations.text.toString()
        val eventFecha = fecha
        val current = (activity as MainActivity).viewModel.addRegister(
            selectedEnum(tipo),
            eventFecha,
            horaI,
            horaE,
            notas,

        )

        if (current) {
            Toast.makeText(
                (activity as MainActivity), "Registro Guardado Correctamente",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)
        } else {
            Toast.makeText(
                (activity as MainActivity), "Algo ha ido mal intenta nuevamente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun fillSpinner() {
        val tipos = listOf(EnumEvent.GENERAL, EnumEvent.GUARDIA, EnumEvent.REPORTE_HORARIO)
        binding.spinnerMotivo.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, tipos)
    }

    private fun fechaEvent() {
        val date = arguments?.getLong("fecha") ?: Calendar.getInstance().timeInMillis
        val convert = Calendar.getInstance();
        convert.timeInMillis = date
        fecha = convert
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDateStr = dateFormat.format(fecha.time)
        Toast.makeText(
            (activity as MainActivity),
            "Fecha seleccionada: $selectedDateStr.",
            Toast.LENGTH_SHORT
        ).show()
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