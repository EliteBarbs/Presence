package com.example.presence

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.presence.fragments.HomeFragment
import com.example.presence.fragments.ProfileFragment
import com.example.presence.fragments.SettingsFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val settingsFragment = SettingsFragment()
    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val markAttendanceFragment = MarkAttendanceFragment()
    private val showAttendanceFragment = ShowAttendanceFragment()
    private val teamsAttendanceFragment = TeamsAttendanceFragment()
    private val addLocationFragment = AddLocationFragment()
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var toolbar : androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        replaceFragment(homeFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.settingsFragment -> replaceFragment(settingsFragment)
                R.id.homeFragment -> replaceFragment(homeFragment)
                R.id.profileFragment -> replaceFragment(profileFragment)
            }
            true
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.navView)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.markAttendance1 -> replaceFragment(markAttendanceFragment)
                R.id.showAttendance -> replaceFragment(showAttendanceFragment)
                R.id.teamsAttendance -> replaceFragment(teamsAttendanceFragment)
                R.id.addLocation -> replaceFragment(addLocationFragment)
                R.id.logout -> logOut()
            }
            true
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        finish()
         intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}