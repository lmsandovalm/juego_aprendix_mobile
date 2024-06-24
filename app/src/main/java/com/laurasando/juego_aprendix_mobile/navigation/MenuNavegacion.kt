package com.laurasando.juego_aprendix_mobile.navigation

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.background.LifeRegenerationService
import com.laurasando.juego_aprendix_mobile.data.local.SharePreferencesManager
import com.laurasando.juego_aprendix_mobile.databinding.ActivityMenuNavegacionBinding

class MenuNavegacion : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuNavegacionBinding
    private lateinit var shrManager: SharePreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuNavegacionBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        shrManager = SharePreferencesManager(this)


        val intent = Intent(this, LifeRegenerationService::class.java)
        val userId = shrManager.getPref("userId", "Empty").toString()
        intent.putExtra("userId", userId)
        startService(intent)


        setSupportActionBar(binding.appBarHomeUsuario.toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        //inflo el layout
        val headerView = navigationView.getHeaderView(0)
        val textViewNombreUsuario = headerView.findViewById<TextView>(R.id.txt_menuDes_nombre)
        val textViewCorreoUsuario = headerView.findViewById<TextView>(R.id.txt_menuDes_correo)


        //recupero los datos
        val nombreUsuario = intent.getStringExtra("nombreUsuario")
        val correoUsuario = intent.getStringExtra("correoUsuario")

        //asigno los datos a los textview
        textViewNombreUsuario.text = nombreUsuario
        textViewCorreoUsuario.text = correoUsuario

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home_usuario)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_cursos, R.id.nav_perfil, R.id.nav_progreso
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    private fun NavigationView.setNavigationItemSelectedListener(menuNavegacion: MenuNavegacion) {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_menu, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_usuario)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_salir -> Toast.makeText(this, "Sesion Cerrada", Toast.LENGTH_SHORT).show()
        }
        //return super.onOptionsItemSelected(item)
        return false
    }

    /*###########################################################################################*/
    /*private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMenuNavegacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuNavegacionBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)






        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toogle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        if (savedInstanceState == null){
            replaceFragment(PerfilFragment())
            navigationView.setCheckedItem(R.id.nav_perfil)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_perfil -> replaceFragment(PerfilFragment())
            R.id.nav_cursos -> replaceFragment(CursosFragment())
            R.id.nav_progreso -> replaceFragment(ProgresoFragment())
            R.id.nav_salir -> Toast.makeText(this,"Sesion Cerrada", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }
    #########################################################################################*/

}



