package com.example.loginregistrojson.activities

import android.R.id
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregistrojson.MainActivity
import com.example.loginregistrojson.R
import com.example.loginregistrojson.model.User
import org.json.JSONObject
import java.io.*


class Registro : AppCompatActivity() {

    lateinit var et_usr: EditText
    lateinit var et_pwd: EditText
    lateinit var et_nom: EditText
    lateinit var et_ape: EditText

    lateinit var btnCancelar: Button

    var registro = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        et_usr = findViewById(R.id.editTextTextUsr)
        et_pwd = findViewById(R.id.editTextTextPwd)
        et_nom = findViewById(R.id.editTextTextNombre)
        et_ape = findViewById(R.id.editTextPwd)

        btnCancelar = findViewById(R.id.buttonCancel)

        var registro = intent.getBooleanExtra("registro", true)


        if (!registro) {
            var fich = File("user.json")
            var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fich.toString())))
            var textoLeido = bufferedReader.readLine()
            val jsonjObject = JSONObject(textoLeido)
            et_usr.setText(jsonjObject.getString("usr"))
            et_pwd.setText(jsonjObject.getString("pwd"))
            et_nom.setText(jsonjObject.getString("name"))
            et_ape.setText(jsonjObject.getString("apellido"))
            bufferedReader.close()

        }
    }

    fun aceptar(view: View) {
        if (registro) {
            var usuario = User()

            var usr = et_usr.text.toString()
            var pwd = et_pwd.text.toString()
            var name = et_nom.text.toString()
            var ape = et_ape.text.toString()

            usuario.usr = usr
            usuario.pwd = pwd
            usuario.name = name
            usuario.apellido = ape

            var fich = "user.json"
            var fileOutput = openFileOutput(fich, Context.MODE_PRIVATE)
            var outputStreamWriter = OutputStreamWriter(fileOutput)

            var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fich)))
            var textoLeido = bufferedReader.readLine()
            val jsonjObject = JSONObject(textoLeido)

            if(usr.equals(jsonjObject.getString("usr"))){
                jsonjObject.put("pwd", pwd)
                jsonjObject.put("name", name)
                jsonjObject.put("apellido", ape)
                outputStreamWriter.write(jsonjObject.toString())
            }else{
                val obj = JSONObject()
                obj.put("usr", usr)
                obj.put("pwd", pwd)
                obj.put("name", name)
                obj.put("apellido", ape)
                outputStreamWriter.write(obj.toString())
            }

            bufferedReader.close()

            outputStreamWriter.close()
            fileOutput.close()

            var resultIntent = Intent(this, MainActivity::class.java)
            resultIntent.putExtra("usuario", usuario.getBundle())

            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    fun cancelar(view: View) {
        finish()
    }
}