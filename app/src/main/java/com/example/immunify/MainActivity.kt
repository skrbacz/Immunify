package com.example.immunify

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.immunify.databinding.ActivityMainBinding
import com.example.immunify.register_login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }

    private var clicked = false

    private var add_btn: Button? = null
    private var add_rem: Button?= null
    private var add_vacc: Button?= null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        add_rem= findViewById(R.id.add_reminder)
        add_btn= findViewById(R.id.add)
        add_vacc= findViewById(R.id.add_vacc)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.add.setOnClickListener { view ->
           onAddButtonClicked()
        }

        binding.appBarMain.addVacc.setOnClickListener{
            //działa jak normalne setOnClickListener x
        }

        binding.appBarMain.addReminder.setOnClickListener {

        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_schedule, R.id.nav_vacc_library
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked= !clicked
    }


    private fun setVisibility(clicked:Boolean) {
        if(!clicked){
            binding.appBarMain.addVacc.visibility= View.VISIBLE
            binding.appBarMain.addReminder.visibility= View.VISIBLE
        }else{
            binding.appBarMain.addVacc.visibility= View.INVISIBLE
            binding.appBarMain.addReminder.visibility= View.INVISIBLE

        }
    }

    private fun setAnimation(clicked:Boolean) {
        if(!clicked){
            binding.appBarMain.addVacc.startAnimation(fromBottom)
            binding.appBarMain.addReminder.startAnimation(fromBottom)
            binding.appBarMain.add.startAnimation(rotateOpen)
        }else{
            binding.appBarMain.addVacc.startAnimation(toBottom)
            binding.appBarMain.addReminder.startAnimation(toBottom)
            binding.appBarMain.add.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if(!clicked){
            binding.appBarMain.addVacc.isClickable= true
            binding.appBarMain.addReminder.isClickable= true
        }else{
            binding.appBarMain.addVacc.isClickable=false
            binding.appBarMain.addReminder.isClickable=false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                performLogout()
                return true
            }
            // Handle other menu items if needed
            else -> return super.onOptionsItemSelected(item)
        }
    }



    private fun performLogout() {
        mAuth?.signOut()
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "You've logged out successfully", Toast.LENGTH_SHORT).show()
    }
}