package com.example.loginregistrojson.activities

import android.R.id
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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

    lateinit var usr: String
    lateinit var pwd: String
    lateinit var name: String
    lateinit var ape: String

    var registro = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        et_usr = findViewById(R.id.editTextTextUsr)
        et_pwd = findViewById(R.id.editTextTextPwd)
        et_nom = findViewById(R.id.editTextTextNombre)
        et_ape = findViewById(R.id.editTextPwd)

        var registro = intent.getBooleanExtra("registro", true)

        if (!registro) {
            var fich = "user.json"

            var bufferedReader = BufferedReader(InputStreamReader(openFileInput(fich)))
            var textoLeido = bufferedReader.readLine()
            var jsonjObject = JSONObject(textoLeido)

            et_usr.setText(jsonjObject.getString("usr"))
            et_pwd.setText(jsonjObject.getString("pwd"))
            et_nom.setText(jsonjObject.getString("name"))
            et_ape.setText(jsonjObject.getString("apellido"))

            bufferedReader.close()


            var fileOutput = openFileOutput(fich, Context.MODE_PRIVATE)

            var outputStreamWriter = OutputStreamWriter(fileOutput)

            usr = et_usr.text.toString()
            pwd = et_pwd.text.toString()
            name = et_nom.text.toString()
            ape = et_ape.text.toString()

            if (usr == jsonjObject.getString("usr")) {
                jsonjObject.put("pwd", pwd)
                jsonjObject.put("name", name)
                jsonjObject.put("apellido", ape)

            }
            outputStreamWriter.write(jsonjObject.toString())
            outputStreamWriter.close()
            fileOutput.close()


        }
    }

    fun aceptar(view: View) {
        if (registro) {
            var fich = "user.json"

            var usuario = User()

            usr = et_usr.text.toString()
            pwd = et_pwd.text.toString()
            name = et_nom.text.toString()
            ape = et_ape.text.toString()

            usuario.usr = usr
            usuario.pwd = pwd
            usuario.name = name
            usuario.apellido = ape

            var fileOutput = openFileOutput(fich, Context.MODE_PRIVATE)
            var outputStreamWriter = OutputStreamWriter(fileOutput)


            val obj = JSONObject()
            obj.put("usr", usr)
            obj.put("pwd", pwd)
            obj.put("name", name)
            obj.put("apellido", ape)
            outputStreamWriter.write(obj.toString())

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