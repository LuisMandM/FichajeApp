package com.example.gestionapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionapp.Model.EnumEvent
import com.example.gestionapp.Model.Evento
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GraphFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GraphFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentGraphBinding? = null

    private val binding get() = _binding!!

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
        loadPie()

    }

    private fun loadPie() {
        (activity as MainActivity).viewModel.eventos.observe(activity as MainActivity) {
            val datos: SharedPreferences =
                (activity as MainActivity).getSharedPreferences("user_Data", Context.MODE_PRIVATE)
            val user = datos.getInt("index", 0) ?: 0
            val reporte = mutableListOf(0.0, 0.0, 0.0)

            for (evento in it) {
                when (evento.tipo) {
                    EnumEvent.GENERAL -> reporte[0]++
                    EnumEvent.GUARDIA -> reporte[1]++
                    EnumEvent.REPORTE_HORARIO -> reporte[2]++
                }
            }


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
            binding.pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            binding.pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            binding.pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL

            binding.pieChart.animate()
        }
    }


    private fun loadChar() {
        //(activity as MainActivity).viewModel.mostrarEventos()
        (activity as MainActivity).viewModel.eventos.observe(activity as MainActivity) {
            val datos: SharedPreferences =
                (activity as MainActivity).getSharedPreferences("user_Data", Context.MODE_PRIVATE)
            val user = datos.getInt("index", 0) ?: 0
            var enum0 = 0.0
            var enum1 = 0.0
            var enum2 = 0.0
            for (evento in it) {
                when (evento.tipo) {
                    EnumEvent.GENERAL -> enum0++
                    EnumEvent.GUARDIA -> enum1++
                    EnumEvent.REPORTE_HORARIO -> enum2++
                }
            }

            //val data = mapOf("General" to enum0, "Guardia" to enum1,"Reporte Horario" to enum2)
            val entries: MutableList<BarEntry> = mutableListOf()
            entries.add(BarEntry(0f, enum0.toFloat()))
            entries.add(BarEntry(1f, enum1.toFloat()))
            entries.add(BarEntry(2f, enum2.toFloat()))
            val set: BarDataSet = BarDataSet(entries, "BarDataSet")

            val data: BarData = BarData(set)

            //binding.barChart.data = data
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).viewModel.eventos.removeObservers(activity as MainActivity)

    }

}