package com.example.loginregistrojson

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregistrojson.activities.Registro
import com.example.loginregistrojson.dialog.LoginDialog
import com.example.loginregistrojson.model.User
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    val REQUEST_CODE_REGISTRO = 1000

    var usuario = User()

    lateinit var btnLogin : Button
    lateinit var btnInfo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.buttonLogin)
        btnInfo = findViewById(R.id.buttonInfo)

        btnInfo.isEnabled = false

    }

    fun login(view: View) {
        var loginDialog = LoginDialog()
        var fich = "user.json"
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fich)))
        var textoLeido = bufferedReader.readLine()
        val jsonjObject = JSONObject(textoLeido)

        loginDialog.usr = jsonjObject.getString("usr")
        loginDialog.password = jsonjObject.getString("pwd")
        bufferedReader.close()

        loginDialog.mainActivity = this
        loginDialog.show(supportFragmentManager, "loginDialog_tag")
    }


    fun registro(view: View) {
        var intentRegistro = Intent(this, Registro::class.java)
        intentRegistro.putExtra("registro", true)
        startActivityForResult(intentRegistro, REQUEST_CODE_REGISTRO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_REGISTRO){
                var bundleData = data!!.getBundleExtra("usuario")

                usuario.setBundle(bundleData!!)

                btnLogin.isEnabled = true
            }
        }
    }

    fun activarBotonInfo(){
        btnInfo.isEnabled = true
    }

    fun info(view: View) {
        var intentInfo = Intent(this, Registro::class.java)
        intentInfo.putExtra("registro", false)

        startActivity(intentInfo)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Atención: ")
        builder.setMessage("¿Desea salir de la aplicación?")

        builder.setPositiveButton("SI"){ dialog, _ -> finish()}
        builder.setNegativeButton("NO"){ dialog, which ->  }
        builder.show()
    }
}