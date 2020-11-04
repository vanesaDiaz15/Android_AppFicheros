package com.example.loginregistrojson.activities

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
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class Registro : AppCompatActivity() {

    lateinit var et_usr: EditText
    lateinit var et_pwd: EditText
    lateinit var et_nom: EditText
    lateinit var et_ape: EditText

    lateinit var usr: String
    lateinit var pwd: String
    lateinit var name: String
    lateinit var ape: String

    lateinit var btnActualizar: Button

    var registro = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        et_usr = findViewById(R.id.editTextTextUsr)
        et_pwd = findViewById(R.id.editTextTextPwd)
        et_nom = findViewById(R.id.editTextTextNombre)
        et_ape = findViewById(R.id.editTextPwd)

        btnActualizar = findViewById(R.id.buttonAceptar)

        var registro = intent.getBooleanExtra("registro", true)

        if (!registro) {
            btnActualizar.text = "ACTUALIZAR"
            var fich = "user.json"

            var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fich)))
            var textoLeido = bufferedReader.readLine()
            var jsonjObject = JSONObject(textoLeido)

            val cast: JSONArray = jsonjObject.getJSONArray("Users")
            for (i in 0 until cast.length()) {
                val user = cast.getJSONObject(i)
                et_usr.setText(user.getString("usr"))
                et_pwd.setText(user.getString("pwd"))
                et_nom.setText(user.getString("name"))
                et_ape.setText(user.getString("apellido"))
            }

            bufferedReader.close()
        }
    }

    fun aceptar(view: View) {
        var fich = "user.json"

        var act = false

        var usuario = User()

        usr = et_usr.text.toString()
        pwd = et_pwd.text.toString()
        name = et_nom.text.toString()
        ape = et_ape.text.toString()


        var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fich)))
        var textoLeido = bufferedReader.readLine()
        var jsonjObjectPrincipal = JSONObject(textoLeido)

        var fileOutput = openFileOutput(fich, Context.MODE_PRIVATE)

        var outputStreamWriter = OutputStreamWriter(fileOutput)
        val cast: JSONArray = jsonjObjectPrincipal.getJSONArray("Users")
        for (i in 0 until cast.length()) {
            val user : JSONObject = cast.getJSONObject(i)
            if (usr == user.getString("usr")) {
                act = true
                user.put("pwd", pwd)
                user.put("name", name)
                user.put("apellido", ape)
            }
        }

        if (!act){
            var user = JSONObject()
            user.put("usr", usr)
            user.put("pwd", pwd)
            user.put("name", name)
            user.put("apellido", ape)

            cast.put(user)
        }
        jsonjObjectPrincipal.put("Users", cast)
        bufferedReader.close()

        outputStreamWriter.write(jsonjObjectPrincipal.toString())
        outputStreamWriter.close()
        fileOutput.close()

        var resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.putExtra("usuario", usuario.getBundle())

        setResult(Activity.RESULT_OK, resultIntent)


        finish()
    }

    fun cancelar(view: View) {
        finish()
    }
}