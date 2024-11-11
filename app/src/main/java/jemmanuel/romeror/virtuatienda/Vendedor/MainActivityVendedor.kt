package jemmanuel.romeror.virtuatienda.Vendedor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import jemmanuel.romeror.virtuatienda.R
import jemmanuel.romeror.virtuatienda.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import jemmanuel.romeror.virtuatienda.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import jemmanuel.romeror.virtuatienda.Vendedor.Nav_Fragments_Vendedor.FragmentInicioV
import jemmanuel.romeror.virtuatienda.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaV
import jemmanuel.romeror.virtuatienda.Vendedor.Nav_Fragments_Vendedor.FragmentReseniasV
import jemmanuel.romeror.virtuatienda.databinding.ActivityMainVendedorBinding

class MainActivityVendedor : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainVendedorBinding
    private var firebaseAuth : FirebaseAuth?= null


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth= FirebaseAuth.getInstance()
        comprobarSesion()


        binding.navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this
            , binding.drawerLayout
            , toolbar
            , R.string.open_drawer
            , R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        replaceFragment(FragmentInicioV())
        binding.navigationView.setCheckedItem(R.id.op_inicio_v)
    }

    private fun comprobarSesion() {
        if (firebaseAuth!!.currentUser==null) {
            startActivity(Intent(applicationContext, RegistroVendedorActivity::class.java))
            Toast.makeText(applicationContext,"Vendedor no registrado", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(applicationContext,"Vendedor en linea", Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_inicio_v -> {
                replaceFragment(FragmentInicioV())
            }
            R.id.op_mi_tienda_v -> {
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_resenia_v -> {
                replaceFragment(FragmentReseniasV())
            }
            R.id.op_cerrar_sesion_v ->{
                Toast.makeText(applicationContext,"Proceso Finalizado", Toast.LENGTH_SHORT).show()
            }
            R.id.op_mis_productos_v -> {
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v -> {
                replaceFragment(FragmentOrdenesV())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}