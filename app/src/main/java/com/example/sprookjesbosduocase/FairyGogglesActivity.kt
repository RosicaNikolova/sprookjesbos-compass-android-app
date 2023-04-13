package com.example.sprookjesbosduocase

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FairyGogglesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fairy_goggles)

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

        val whalevideo = findViewById<VideoView>(R.id.whalevideo)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(whalevideo)

        val offlineUri = Uri.parse("andorid.resource://$packageName/${R.raw.whale}")

        whalevideo.setMediaController(mediaController)
        whalevideo.setVideoURI(offlineUri)
        whalevideo.requestFocus()
        whalevideo.start()


        //Code for card pop up
        //referance for the code https://youtu.be/ukL6oURCAq4
        val myDialog = Dialog(this)

        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // no need for code
            }

            override fun onFinish() {
                val dialogBinding = layoutInflater.inflate(R.layout.goggles_popup, null)
                myDialog.setContentView(dialogBinding)
                myDialog.setCancelable(true)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                val collectbtn = dialogBinding.findViewById<Button>(R.id.collectbtn)
                collectbtn.setOnClickListener{
                    myDialog.dismiss()
                }
            }
        }

        //start the timer
        timer.start();


    }
}