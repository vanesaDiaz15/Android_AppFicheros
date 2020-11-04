package com.example.loginregistrojson.dialog

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.loginregistrojson.MainActivity
import com.example.loginregistrojson.R
import com.example.loginregistrojson.model.User

class LoginDialog: DialogFragment() {
    lateinit var mainActivity: MainActivity
    var usr = ArrayList<String>()
    var password = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var viewDialog = inflater.inflate(R.layout.login_dialog, container, false)

        var botonLogin2 = viewDialog.findViewById<Button>(R.id.buttonAlta)
        botonLogin2.setOnClickListener { view ->login(view) }
        return viewDialog
    }

    override fun onStart() {
        super.onStart()

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    fun login(view: View){
        var et_user = dialog!!.findViewById<EditText>(R.id.editTextTextNombre)
        var et_pwd = dialog!!.findViewById<EditText>(R.id.editTextPwd)

        var usrOk = false
        var pwdOk = false

        var user = et_user.text.toString()
        var pwd = et_pwd.text.toString()
        for (usuario in usr) {
            if (user.equals(usuario)) {
                usrOk = true
                break
            }
        }
        for (contr in password){
            if (pwd.equals(contr)){
                pwdOk = true
                break
            }
        }
        if (usrOk && pwdOk){
            var usuario = User()
            usuario.usr = user
            usuario.pwd = pwd
            mainActivity.activarBotonInfo()
            dismiss()
        }else{
            Toast.makeText(this.activity, "Usuario/contraseña incorrectos", Toast.LENGTH_LONG).show()
        }
    }
}