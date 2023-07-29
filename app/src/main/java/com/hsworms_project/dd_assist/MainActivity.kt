package com.hsworms_project.dd_assist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    var wuerfel: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navView.background = null
        replaceFragment(HomeFragment())

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miHome -> replaceFragment(HomeFragment())
                R.id.miChar -> replaceFragment(CharFragment())
                R.id.miNote -> replaceFragment(NoteFragment())
                R.id.miDice -> {
                    replaceFragment(DiceFragment())
                    wuerfel = 0
                }
                R.id.miSettings -> replaceFragment(SettingsFragment())
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

    /*Funktionalität des Dices Fragment*/
    fun dices(view: View) {

        val text = findViewById<TextView>(R.id.textView3)
        val bt1 = findViewById<Button>(R.id.button5)
        val bt2 = findViewById<Button>(R.id.button6)
        val bt3 = findViewById<Button>(R.id.button7)
        val bt4 = findViewById<Button>(R.id.button8)

        if(view == bt1) {
            if(wuerfel > 0) {
                wuerfel -= 1
            }
        }
        else if(view == bt2) {
            if(wuerfel >= 5) {
                wuerfel -= 5
            }
        }
        else if(view == bt3) {
            wuerfel += 1
        }
        else if(view == bt4) {
            wuerfel += 5
        }
        var massege: String = wuerfel.toString()
        text.text = massege
    }

    var art = 0
    fun roll (view: View) {
        val text = findViewById<TextView>(R.id.textView5)
        val spinner: Spinner = findViewById<Spinner>(R.id.spinner2)
        var dice = spinner.selectedItem.toString()
        val stringBuilder = StringBuilder()
         when(dice){
             "d4" -> art = 4
             "d6" -> art = 6
             "d8" -> art = 8
             "d10" -> art = 10
             "d12" -> art = 12
             "d20" -> art = 20
             "d100" -> art = 100
         }

        var sum = 0

        for (i in 1..wuerfel){
            val zufallsZahl = Random.nextInt(0, 1000)
            val ergebnis = (zufallsZahl % art) + 1
            sum += ergebnis
            if(i == wuerfel) {
                stringBuilder.append("$ergebnis")
            }
            else {
                stringBuilder.append("$ergebnis, ")
            }
        }
        stringBuilder.append("\nSumme der Würfel: $sum")
        text.text = stringBuilder.toString()
    }

}