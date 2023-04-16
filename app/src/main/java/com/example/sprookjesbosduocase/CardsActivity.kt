package com.example.sprookjesbosduocase

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

class CardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_compass -> {
                    val intent = Intent(this, CompassActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_fairy_goggles -> {
                    val intent = Intent(this, FairyGogglesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_cards -> {
                    val intent = Intent(this, CardsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        //Code for card pop up
        //referance for the code https://youtu.be/ukL6oURCAq4
        val btn = findViewById<ImageView>(R.id.thewhale)
        btn.setOnClickListener{

            val dialogBinding = layoutInflater.inflate(R.layout.card_popup, null)

            val myDialog = Dialog(this)
            myDialog.setContentView(dialogBinding)

            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            val closebtn = dialogBinding.findViewById<ImageView>(R.id.close)
            closebtn.setOnClickListener{
                myDialog.dismiss()
            }

        }

    }

}