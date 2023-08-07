package com.hsworms_project.dd_assist

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var wuerfel: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navView.background = null
        replaceFragment(HomeFragment())

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miHome -> replaceFragment(HomeFragment())
                R.id.miSearch -> {
                    replaceFragment(SearchFragment())
                }
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
            if(wuerfel < 50) {
            wuerfel += 1
            }
        }
        else if(view == bt4) {
            if(wuerfel <=45) {
                wuerfel += 5
            }
        }
        val ausgabe: String = wuerfel.toString()
        text.text = ausgabe
    }

    private var art = 0
    fun roll (view: View) {
        val text = findViewById<TextView>(R.id.textView5)
        val spinner: Spinner = findViewById(R.id.spinner2)
        val dice = spinner.selectedItem.toString()
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
                stringBuilder.append("$ergebnis\n")
            }
            else {
                stringBuilder.append("$ergebnis, ")
            }
        }
        stringBuilder.append("\nSumme der Würfel: $sum")
        text.text = stringBuilder.toString()
    }

    /*Funktionalität der WebSearch*/
    fun openWeb(view: View) {
        val rasse = findViewById<Button>(R.id.btn_search_rasse)
        val klasse = findViewById<Button>(R.id.btn_search_classes)
        val backg = findViewById<Button>(R.id.btn_search_backgrpund)
        val feat = findViewById<Button>(R.id.btn_search_feats)
        val item = findViewById<Button>(R.id.btn_search_items)
        val spell = findViewById<Button>(R.id.btn_search_speels)
        val monster = findViewById<Button>(R.id.btn_search_monster)
        val five = findViewById<Button>(R.id.btn_search_fivee)
        val wView = findViewById<WebView>(R.id.wview_search)

        wView.settings.javaScriptEnabled = true
        wView.webViewClient = WebViewClient()

        if (view == rasse) {
            wView.loadUrl("https://5e.tools/races.html#aarakocra_dmg")
        }
        else if(view == klasse){
            wView.loadUrl("https://5e.tools/classes.html#artificer_tce")
        }
        else if(view == backg) {
            wView.loadUrl("https://5e.tools/backgrounds.html#acolyte_phb")
        }
        else if(view == feat) {
            wView.loadUrl("https://5e.tools/feats.html#aberrant%20dragonmark_erlw")
        }
        else if(view == item) {
            wView.loadUrl("https://5e.tools/items.html#abacus_phb")
        }
        else if(view == spell) {
            wView.loadUrl("https://5e.tools/spells.html#absorb%20elements_xge")
        }
        else if(view == monster) {
            wView.loadUrl("https://5e.tools/bestiary.html#aarakocra_mm")
        }
        else if(view == five) {
            wView.loadUrl("https://5e.tools/")
        }
    }
}
