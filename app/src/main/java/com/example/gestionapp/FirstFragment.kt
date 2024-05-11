package com.example.gestionapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.RecycleView.Adapter
import com.example.gestionapp.databinding.FragmentFirstBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var fecha: Calendar
    private var eventos: MutableList<Evento> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fecha = Calendar.getInstance()
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDateStr = dateFormat.format(calendar.time)
            fecha = calendar
            Toast.makeText(
                (activity as MainActivity),
                "Fecha seleccionada: $selectedDateStr",
                Toast.LENGTH_SHORT
            ).show()
            //loadEventsperDay()
            var currentEvents: MutableList<Evento> = mutableListOf()
            for (evento in (activity as MainActivity).eventos) {
                if (formatCalendar(evento.fecha) == formatCalendar(fecha)) {
                    currentEvents.add(evento)
                }
            }
            binding.eventRecycler.layoutManager = LinearLayoutManager(activity as MainActivity)
            binding.eventRecycler.adapter = Adapter(currentEvents)

        }

        binding.btonNewEntry.setOnClickListener {
            var date = fecha.timeInMillis
            val bundle = bundleOf("fecha" to date)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }


    }

    private fun loadEventsperDay() {
        val currentDate: Calendar = fecha
        for (evento in (activity as MainActivity).eventos) {
            if (evento.fecha == currentDate) {
                eventos.add(evento)
            }
        }
    }

    private fun formatCalendar(dateRaw: Calendar): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val format = dateFormat.format(dateRaw.time)
        return format
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}