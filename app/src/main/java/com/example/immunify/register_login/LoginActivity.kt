package com.example.immunify.register_login

//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
//import com.example.immunify.firestore.FireStoreData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.immunify.MainActivity
import com.example.immunify.R
import com.example.immunify.other.Snack
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : Snack() {

    var mAuth = FirebaseAuth.getInstance()

    private var email: EditText? = null
    private var password: EditText? = null
    private var loginBTN: Button? = null
    private var registerTV: TextView? = null
//    private var gameChangerBTN: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Thread.sleep(2500)
//        installSplashScreen()

        Log.d("LoginActivity", "onCreate called")

        setContentView(R.layout.activity_login)

        email = findViewById(R.id.emailEDTV)
        password = findViewById(R.id.passwordEDTV)
        loginBTN = findViewById(R.id.loginBTN)
        registerTV = findViewById(R.id.dontHaveAccountTV)
//        gameChangerBTN = findViewById(R.id.gameChangerBTN)

        supportActionBar?.hide()


        val user = mAuth.currentUser

        /*
        https://stackoverflow.com/questions/22262463/firebase-how-to-keep-an-android-user-logged-in
         */
        if (user != null) {
            val i = Intent(this@LoginActivity, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        } else {

            loginBTN?.setOnClickListener {

                if (validateLogin()) { // Check if login credentials are valid
                    loginInRegistered()
                    goToMain()

                }
            }

//            forgottenPasswordTV?.setOnClickListener {
//                val intent = Intent(this, ForgottenPasswordActivity::class.java)
//                startActivity(intent)
//            }

            registerTV?.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

//            gameChangerBTN?.setOnClickListener {
//
//
//                FirebaseAuth.getInstance()
//                    .signInWithEmailAndPassword("firstuser@test.com", "!Qwerty123")
//                    .addOnCompleteListener { task ->
//
//                        if (task.isSuccessful) {
//                            Toast.makeText(
//                                this@LoginActivity,
//                                "You logged in successfully!",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        } else {
//                            showSnackbar(task.exception!!.message.toString(), false)
//                        }
//
//                    }
//            }
        }
    }

    private fun validateLogin(): Boolean {
        if (email?.text.isNullOrBlank()) {
            showSnackbar("Please provide your email!", false)
            return false
        }

        if (password?.text.isNullOrBlank()) {
            showSnackbar("Please provide your password!", false)
            return false
        }

        return true
    }

    private fun loginInRegistered() {
        if (validateLogin()) {
            val em = email?.text.toString().trim() { it <= ' ' }
            val pass = password?.text.toString().trim() { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(em, pass)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "You logged in successfully!",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        showSnackbar(task.exception!!.message.toString(), false)
                    }

                }
        }

    }

    open fun goToMain() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.email.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("uID", uid)
        startActivity(intent)
        finish()

    }

}