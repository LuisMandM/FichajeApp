package com.example.gestionapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.red
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionapp.Model.EnumEvent
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.Model.Utilitties
import com.example.gestionapp.RecycleView.Adapter
import com.example.gestionapp.databinding.FragmentGraphBinding
import com.example.gestionapp.databinding.FragmentSecondBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import okhttp3.internal.Util
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GraphFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentGraphBinding? = null

    private val binding get() = _binding!!
    private var fecha = Calendar.getInstance()
    private var generalReport = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //loadChar()
        fechaEvent()
        loadPie()


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_chart, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.itemGeneralR -> {
                        generalReport = true
                        loadPie()
                        true
                    }

                    R.id.itemDailyR -> {
                        generalReport = false
                        loadPie()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

    private fun filterEvents(eventos: List<Evento>): List<Double> {
        val reporte = mutableListOf(0.0, 0.0, 0.0)
        if (generalReport) {
            for (evento in eventos) {
                when (evento.tipo) {
                    EnumEvent.GENERAL -> reporte[0]++
                    EnumEvent.GUARDIA -> reporte[1]++
                    EnumEvent.REPORTE_HORARIO -> reporte[2]++
                }
            }
        } else {
            val datos: SharedPreferences =
                (activity as MainActivity).getSharedPreferences("user_Data", Context.MODE_PRIVATE)

            val user = datos.getInt("index", 0) ?: 0
            for (evento in eventos) {
                val eventDate = Utilitties().formatCalendar(evento.fecha)
                val date = Utilitties().formatCalendar(fecha)
                if (eventDate == date && evento.usuario == user) {
                    when (evento.tipo) {
                        EnumEvent.GENERAL -> reporte[0]++
                        EnumEvent.GUARDIA -> reporte[1]++
                        EnumEvent.REPORTE_HORARIO -> reporte[2]++
                    }
                }
            }
        }
        return reporte
    }

    private fun loadPie() {
        (activity as MainActivity).viewModel.eventos.observe(activity as MainActivity) {

            val reporte = filterEvents(it)

            /*for (evento in it) {
                when (evento.tipo) {
                    EnumEvent.GENERAL -> reporte[0]++
                    EnumEvent.GUARDIA -> reporte[1]++
                    EnumEvent.REPORTE_HORARIO -> reporte[2]++
                }
            }*/


            val entries: MutableList<PieEntry> = mutableListOf()
            entries.add(PieEntry(reporte[0].toFloat(), "General"))
            entries.add(PieEntry(reporte[1].toFloat(), "Guardia"))
            entries.add(PieEntry(reporte[2].toFloat(), "Reporte de Horario"))
            val set: PieDataSet = PieDataSet(entries, "Reporte Actividades")
            set.colors = listOf(Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA)
            val data: PieData = PieData(set)

            binding.pieChart.data = data
            binding.pieChart.description = Description().apply {
                isEnabled = false
            }

            binding.pieChart.holeRadius = 50f
            binding.pieChart.setExtraOffsets(20f, 0f, 20f, 0f)

            binding.pieChart.legend.isEnabled = true
            binding.pieChart.legend.horizontalAlignment =
                Legend.LegendHorizontalAlignment.CENTER
            binding.pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            binding.pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL

            binding.pieChart.animate()
            binding.pieChart.invalidate()
        }
    }

    private fun fechaEvent() {
        val date = arguments?.getLong("fecha") ?: Calendar.getInstance().timeInMillis
        val convert = Calendar.getInstance();
        convert.timeInMillis = date
        fecha = convert
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).viewModel.eventos.removeObservers(activity as MainActivity)

    }

}