package com.laurasando.juego_aprendix_mobile.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.laurasando.juego_aprendix_mobile.R
import com.laurasando.juego_aprendix_mobile.databinding.ActivityMenuNavegacionBinding
import com.laurasando.juego_aprendix_mobile.ui.fragments.CursosFragment
import com.laurasando.juego_aprendix_mobile.ui.fragments.PerfilFragment
import com.laurasando.juego_aprendix_mobile.ui.fragments.ProgresoFragment

class MenuNavegacion : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMenuNavegacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuNavegacionBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        //inflo el layout
        val headerView = navigationView.getHeaderView(0)
        val textViewNombreUsuario = headerView.findViewById<TextView>(R.id.txt_menuDes_nombre)
        val textViewCorreoUsuario = headerView.findViewById<TextView>(R.id.txt_menuDes_correo)

        //recupero credenciales en el usuario
        val nombreUsuario = intent.getStringExtra("nombreUsuario")
        val correoUsuario = intent.getStringExtra("correoUsuario")

        Toast.makeText(this, "exito"+ nombreUsuario, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "exito"+ correoUsuario, Toast.LENGTH_SHORT).show()

        //asigno las credenciales a lostextview
       textViewNombreUsuario.text = nombreUsuario
        textViewCorreoUsuario.text = correoUsuario

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

}

