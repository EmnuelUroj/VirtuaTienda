package jemmanuel.romeror.virtuatienda.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import jemmanuel.romeror.virtuatienda.Constantes
import jemmanuel.romeror.virtuatienda.R
import jemmanuel.romeror.virtuatienda.databinding.ActivityRegistroVendedorBinding

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creando Cuenta")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrarV.setOnClickListener {
            validarInformacion()
        }

    }

    private var nombres = ""
    private var email = ""
    private var password = ""
    private var cPassword = ""
    private fun validarInformacion() {
        nombres = binding.etNombresV.text.toString().trim()
        email = binding.etEmailV.text.toString().trim()
        password = binding.etPasswordV.text.toString().trim()
        cPassword = binding.etCPasswordV.text.toString().trim()

        if (nombres.isEmpty()) {
            binding.etNombresV.error = "Ingrese sus nombres"
            binding.etNombresV.requestFocus()
        } else if (email.isEmpty()) {
            binding.etEmailV.error = "Ingrese su email"
            binding.etEmailV.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailV.error = "Email no válido"
            binding.etEmailV.requestFocus()
        } else if (password.isEmpty()) {
            binding.etPasswordV.error = "Ingrese una contraseña"
            binding.etPasswordV.requestFocus()
        } else if (password.length < 6){
            binding.etPasswordV.error = "Seis caracteres mínimo"
            binding.etPasswordV.requestFocus()
        } else if (cPassword.isEmpty()) {
            binding.etCPasswordV.error = "Confirme su contraseña"
            binding.etCPasswordV.requestFocus()
        } else if (password != cPassword) {
            binding.etCPasswordV.error = "Las contraseñas no coinciden"
            binding.etCPasswordV.requestFocus()
        } else{
            registrarVendedor()
        }
    }

    private fun registrarVendedor() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                insertarInfoBD()
            }
            .addOnFailureListener { e->
                Toast.makeText(this,"Fallo el registro debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun insertarInfoBD(){
        progressDialog.setMessage("Guardando información...")
        val uidBD = firebaseAuth.uid
        val nombreBD = nombres
        val emailBD = email
        val tiempoBD = Constantes().obtenerTiempoD()

        val datosVendedor = HashMap<String, Any>()


        datosVendedor["uid"] = "$uidBD"
        datosVendedor["nombres"] = "$nombreBD"
        datosVendedor["email"] = "$emailBD"
        datosVendedor["tipoUsuario"] = "Vendedor"
        datosVendedor["tiempo_registro"] = "$tiempoBD"

        val references = FirebaseDatabase.getInstance().getReference("Usuarios")
        references.child(uidBD!!)
            .setValue(datosVendedor)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivityVendedor::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"Fallo el registro debido en BD debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}