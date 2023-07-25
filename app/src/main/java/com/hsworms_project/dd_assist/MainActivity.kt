package com.hsworms_project.dd_assist

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.NavigationBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navView.background = null
        navView.menu.getItem(2).isEnabled = false
        replaceFragment(HomeFragment())

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miHome -> replaceFragment(HomeFragment())
                R.id.miChar -> replaceFragment(CharFragment())
                R.id.miNote -> replaceFragment(NoteFragment())
                R.id.miDice -> replaceFragment(DiceFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentFrameLayout, fragment)
        fragmentTransaction.commit()
    }
}