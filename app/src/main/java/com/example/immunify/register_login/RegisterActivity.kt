package com.example.immunify.register_login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.immunify.R
import com.example.immunify.other.Snack
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity: Snack() {
    private lateinit var auth: FirebaseAuth

    private var name: EditText?= null
    private var email: EditText? = null
    private var password: EditText? = null
    private var repeatPassword: EditText? = null
    private var loginTV: TextView? = null
    private var registerBtn: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()


        name= findViewById(R.id.regNameEDTV)
        email = findViewById(R.id.regEmailEDTV)
        password = findViewById(R.id.regPasswordEDTV)
        repeatPassword = findViewById(R.id.regRePasswordEDTV)
        loginTV = findViewById(R.id.alreadyHaveAnAccountTV)
        registerBtn = findViewById(R.id.regBTN)
        auth = Firebase.auth



        loginTV?.setOnClickListener {
            goToLogin()
        }

        registerBtn?.setOnClickListener {
            if (validRegisterInformation()) {

                val login: String = email?.text.toString().trim() { it <= ' ' }
                val pass: String = password?.text.toString().trim() { it <= ' ' }
                val name: String = name?.text.toString().trim() { it <= ' ' }



                auth.createUserWithEmailAndPassword(login,pass)
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful){
                            Log.d(ContentValues.TAG,"createUserWithEmail:success")
                            registrationSuccess()
                            goToLogin()
                        }else{
                            Log.w(ContentValues.TAG,"createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }


                    }
            }



        }



    }

    private fun validRegisterInformation(): Boolean {
        if (name?.text.isNullOrBlank()) {
            name?.error = "You can't set your name to nothing!"
            return false
        } else if ((name?.text?.length ?: 0) < 4) {
            name?.error = "Your nick can't be shorter than 4 letters"
            return false
        } else if ((name?.text?.length ?: 0) >= 10) {
            name?.error = "Your nick can't be longer than 10 letters"
            return false
        }

        if (email?.text.isNullOrBlank()) {
            showSnackbar("Email is required", false)
            return false
        }

        var doesItHaveAdd = false

        for (char in email?.text.toString()) {
            if (char == '@') {
                doesItHaveAdd = true
                break
            }
        }

        if (!doesItHaveAdd) {
            showSnackbar("Provide valid email!", false)
            return false
        }


        if (password?.text.isNullOrBlank()) {
            showSnackbar("Password is required", false)
            return false
        }
        if (password!!.length() < 8) {
            showSnackbar("Your password needs to be at least 8 characters long!", false)
            return false
        }

        var doesItHaveAtLeastOneCapitalLetter = false
        var atLeastOneSpecialSign = false
        val specialCharacters = setOf(
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*',
            '(',
            ')',
            '-',
            '_',
            '=',
            '+',
            '`',
            '~',
            '{',
            '}',
            ':',
            ';',
            '\"',
            '\'',
            '<',
            '>',
            '.',
            '?',
            '/'
        )

        for (char in password?.text.toString()) {
            if (char.isUpperCase()) {
                doesItHaveAtLeastOneCapitalLetter = true
                break
            }
        }

        for (char in password?.text.toString()) {
            if (specialCharacters.contains(char)) {
                atLeastOneSpecialSign = true
                break
            }
        }

        if (!doesItHaveAtLeastOneCapitalLetter) {
            showSnackbar("Your password should have at least one capital letter!", false)
            return false
        } else if (!atLeastOneSpecialSign) {
            showSnackbar("Password should have at least one special character!", false)
            return false
        }

        if (!password?.text?.toString().equals(repeatPassword?.text?.toString())) {
            showSnackbar("The passwords aren't the same!", false)
            return false
        }

        return true

    }

    fun registrationSuccess() {
        Toast.makeText(
            this@RegisterActivity, "You are registered successfully",
            Toast.LENGTH_LONG
        ).show()

    }
    fun goToLogin (){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
