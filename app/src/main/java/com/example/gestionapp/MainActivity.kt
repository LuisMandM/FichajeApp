package com.example.gestionapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.gestionapp.BBDD.BBDDParse
import com.example.gestionapp.BBDD.Repositorio
import com.example.gestionapp.Model.EnumEvent
import com.example.gestionapp.Model.Evento
import com.example.gestionapp.Model.EventoViewModelFactory
import com.example.gestionapp.Model.VM
import com.example.gestionapp.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var eventos: MutableList<Evento> = mutableListOf()
    private val miRepositorio by lazy { Repositorio(BBDDParse()) }
    val viewModel: VM by viewModels{EventoViewModelFactory(miRepositorio)}
    //var lateinit eventos : List<Evento>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            /*R.id.item_Logout -> {
                logout()
                true
            }*/

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun logout() {
        val datos: SharedPreferences = this.getSharedPreferences("user_Data", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = datos.edit()
        editor.putString("username", "")
        editor.putString("password", "")
        editor.apply()
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Borramos la pila de fragmentos
        navController.popBackStack(R.id.loginFragment, false)
        navController.navigate(R.id.loginFragment)


    }

    fun userInSession():Int{
        val datos: SharedPreferences = this.getSharedPreferences("user_Data", Context.MODE_PRIVATE)
        return datos.getInt("index", 0) ?: 0
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}