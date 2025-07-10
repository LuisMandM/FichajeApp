package com.example.gestionapp

import android.R
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
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionapp.Model.Actividades
import com.example.gestionapp.Model.EnumCompanero
import com.example.gestionapp.Model.EnumTareas
import com.example.gestionapp.Model.Jornada
import com.example.gestionapp.Model.Utilitties
import com.example.gestionapp.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.gestionapp.RecycleView.AdapterActividades


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
//    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterActividades

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
        //binding.switchRango.isChecked = true

        adapter = AdapterActividades(EnumTareas.entries.toList())
        binding.recyclerCheckBox.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCheckBox.adapter = adapter

        id_Evento = arguments?.getInt("id_evento") ?: -1
        if (id_Evento != -1) {
            creating = false
            binding.btnDelete.isEnabled = true

/*            (activity as MainActivity).viewModel.searchEvent(id_Evento)
            (activity as MainActivity).viewModel.currentEvent.observe(activity as MainActivity) {
                binding.spinnerMotivo.setSelection(
                    Utilitties().indexEnum(it.tipo)
                )
//                binding.edTxHoraInit.setText(it.horaInit)
//                binding.edTxHoraEnd.setText(it.horaEnd)
//                binding.edTxObservations.setText(it.notas)
            }*/
        } else {
            binding.btnDelete.text = "Cancelar"
        }

        binding.btnCR.setOnClickListener {
//            if (validacionCampos()) {
//                if (creating) SaveEvent()
//                else updateEvent()
//            }
            saveRegister()
        }

        binding.btnDelete.setOnClickListener {
//            if (!creating) deleteEvent()
//            else findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)

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
//                        if (creating) SaveEvent()
//                        else updateEvent()
                        saveRegister()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

/*    private fun validacionCampos(): Boolean {
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
    }*/


/*    private fun validarHoras(): Boolean {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val hora1 = LocalTime.parse(binding.edTxHoraInit.text.toString(), formatter)
        val hora2 = LocalTime.parse(binding.edTxHoraEnd.text.toString(), formatter)
        return hora1.isBefore(hora2)
    }*/

/*    private fun deleteEvent() {

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

    }*/

/*
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
*/
private fun saveRegister() {

    (activity as MainActivity).viewModel.idMax()
    (activity as MainActivity).viewModel.jornadaMax.observe(activity as MainActivity) {
        val enfermera = binding.spinnerMotivo.selectedItem.toString()
        val eventFecha = fecha
        val current = Jornada(
            it + 1,
            eventFecha, selectedEnum(enfermera), (activity as MainActivity).userInSession()
        )

        try {
            (activity as MainActivity).viewModel.insertJornada(current)
            correctExit = true


            (activity as MainActivity).viewModel.idActvidadMax()
            (activity as MainActivity).viewModel.actvidadMax.observe(activity as MainActivity) {
                val tareas: MutableList<EnumTareas> = adapter.getSeleccionados()
                var increase = 1
                for(tarea in tareas){
                    val currentTask = Actividades(
                        it + increase, tarea, current
                    )
                    (activity as MainActivity).viewModel.insertActividad(currentTask)
                    increase ++
                }

                Toast.makeText(
                    (activity as MainActivity), "Registro Guardado Correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(com.example.gestionapp.R.id.action_SecondFragment_to_FirstFragment)
            }

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
        //val tipos = listOf(EnumEvent.GENERAL, EnumEvent.GUARDIA, EnumEvent.REPORTE_HORARIO)
        val enfermeras : Array<EnumCompanero> = enumValues<EnumCompanero>()
        binding.spinnerMotivo.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, enfermeras)
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

    private fun selectedEnum(enumString: String): EnumCompanero {
        return enumValues<EnumCompanero>().find { it.name == enumString } ?: EnumCompanero.Beatriz
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (creating && correctExit) {
            (activity as MainActivity).viewModel.jornadaMax.removeObservers(activity as MainActivity)
            (activity as MainActivity).viewModel.actvidadMax.removeObservers(activity as MainActivity)
        }
        else {
            if (correctExit) {
                (activity as MainActivity).viewModel.currentJornada.removeObservers(activity as MainActivity)
            }
        }

    }
}