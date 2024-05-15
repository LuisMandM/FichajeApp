package com.example.gestionapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.databinding.FragmentFirstBinding
import com.example.gestionapp.databinding.FragmentLoginBinding
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            validacionLogin()
        }

    }

    private fun validacionLogin() {
        if ((activity as MainActivity).viewModel.validateUser(
                binding.edTxUser.text.toString(),
                binding.edTxPassword.text.toString()
            )
        ) findNavController().navigate(R.id.action_loginFragment_to_FirstFragment)
        else Toast.makeText(
            (activity as MainActivity),
            "Error al iniciar sesion compruebe los datos e intente nuevamente",
            Toast.LENGTH_SHORT
        ).show()
    }

}