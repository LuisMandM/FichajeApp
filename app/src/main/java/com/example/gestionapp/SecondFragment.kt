package com.example.gestionapp

import android.R
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.gestionapp.Model.EnumEvent
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.Model.Utilitties
import com.example.gestionapp.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
    private var correctExit: Boolean = false
    private var obsNum: Boolean = false
    private var obsCurrentE: Boolean = false

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
        binding.switchRango.isChecked = true

        id_Evento = arguments?.getInt("id_evento") ?: -1
        if (id_Evento != -1) {
            creating = false
            binding.btnDelete.isEnabled = true

            (activity as MainActivity).viewModel.searchEvent(id_Evento)
            (activity as MainActivity).viewModel.currentEvent.observe(activity as MainActivity) {
                binding.spinnerMotivo.setSelection(
                    Utilitties().indexEnum(it.tipo)
                )
                binding.edTxHoraInit.setText(it.horaInit)
                binding.edTxHoraEnd.setText(it.horaEnd)
                binding.edTxObservations.setText(it.notas)
            }
        } else {
            binding.btnDelete.text = "Cancelar"
        }

        binding.switchRango.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                binding.edTxHoraEnd.isEnabled = true
                binding.edTxHoraEnd.visibility = View.VISIBLE
                binding.txtVwHoraEnd.visibility = View.VISIBLE
            } else {
                binding.edTxHoraEnd.isEnabled = false
                binding.edTxHoraEnd.visibility = View.GONE
                binding.txtVwHoraEnd.visibility = View.GONE

            }
        }

        binding.btnCR.setOnClickListener {
            if (validacionCampos()) {
                if (creating) SaveEvent()
                else updateEvent()
            }
        }

        binding.btnDelete.setOnClickListener {
            if (!creating) deleteEvent()
            else findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)

        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(com.example.gestionapp.R.menu.menu_secondfragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    com.example.gestionapp.R.id.itemSave -> {
                        if (creating) SaveEvent()
                        else updateEvent()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

    private fun validacionCampos(): Boolean {
        var valid = true
        val regexHoras = Regex("^(?:[01]\\d|2[0-3]):[0-5]\\d$")
        if (binding.edTxHoraInit.text.isNotEmpty()) {
            if (binding.switchRango.isChecked) {
                if (binding.edTxHoraEnd.text.isNotEmpty()) {
                    if (!regexHoras.matches(binding.edTxHoraEnd.text.toString())) {
                        Toast.makeText(
                            (activity as MainActivity), "La hora no coindice con el formato HH:MM",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (!validarHoras()) {
                            Toast.makeText(
                                (activity as MainActivity),
                                "La hora de fin es previa a la hora de inicio",
                                Toast.LENGTH_SHORT
                            ).show()
                            valid = false
                        }
                    }
                } else {
                    Toast.makeText(
                        (activity as MainActivity), "La hora de fin esta vacia",
                        Toast.LENGTH_SHORT
                    ).show()
                    valid = false
                }
            } else {
                if (!regexHoras.matches(binding.edTxHoraInit.text.toString())) {
                    Toast.makeText(
                        (activity as MainActivity), "La hora no coindice con el formato HH:MM",
                        Toast.LENGTH_SHORT
                    ).show()
                    valid = false
                }
            }

        } else {
            Toast.makeText(
                (activity as MainActivity), "La hora de Inicio esta vacia",
                Toast.LENGTH_SHORT
            ).show()
            valid = false
        }

        return valid
    }


    private fun validarHoras(): Boolean {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val hora1 = LocalTime.parse(binding.edTxHoraInit.text.toString(), formatter)
        val hora2 = LocalTime.parse(binding.edTxHoraEnd.text.toString(), formatter)
        return hora1.isBefore(hora2)
    }

    private fun deleteEvent() {

        try {
            (activity as MainActivity).viewModel.currentEvent.observe(activity as MainActivity) {
                (activity as MainActivity).viewModel.deleteEvent(it)
                Toast.makeText(
                    (activity as MainActivity), "Registro Eliminado Correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)
            }


        } catch (error: Exception) {
            Toast.makeText(
                (activity as MainActivity), "Algo ha ido mal intenta nuevamente",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun updateEvent() {
        val current = Evento(
            id_Evento,
            selectedEnum(binding.spinnerMotivo.selectedItem.toString()), fecha,
            binding.edTxHoraInit.text.toString(),
            if (binding.switchRango.isChecked) binding.edTxHoraEnd.text.toString() else binding.edTxHoraInit.text.toString(),
            binding.edTxObservations.text.toString(), (activity as MainActivity).userInSession()
        )

        try {
            (activity as MainActivity).viewModel.updateEvent(current)
            correctExit = true
            Toast.makeText(
                (activity as MainActivity), "Registro Actualizado Correctamente",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)
        } catch (e: Exception) {
            Toast.makeText(
                (activity as MainActivity), "Algo ha ido mal intenta nuevamente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun SaveEvent() {

        (activity as MainActivity).viewModel.idMax()
        (activity as MainActivity).viewModel.numMax.observe(activity as MainActivity) {
            val tipo = binding.spinnerMotivo.selectedItem.toString()
            val horaI = binding.edTxHoraInit.text.toString()
            val horaE =
                if (binding.switchRango.isChecked) binding.edTxHoraEnd.text.toString() else horaI
            val notas = binding.edTxObservations.text.toString()
            val eventFecha = fecha
            val current = Evento(
                it + 1, selectedEnum(tipo),
                eventFecha,
                horaI,
                horaE,
                notas, (activity as MainActivity).userInSession()
            )

            try {
                (activity as MainActivity).viewModel.insertEvent(current)
                correctExit = true
                Toast.makeText(
                    (activity as MainActivity), "Registro Guardado Correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)
            } catch (e: Exception) {
                Toast.makeText(
                    (activity as MainActivity), "Algo ha ido mal intenta nuevamente",
                    Toast.LENGTH_SHORT
                ).show()
                print(e)
            }
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
        if (creating && correctExit) (activity as MainActivity).viewModel.numMax.removeObservers(
            activity as MainActivity
        )
        else {
            if (correctExit) {
                (activity as MainActivity).viewModel.currentEvent.removeObservers(activity as MainActivity)
            }
        }

    }
}