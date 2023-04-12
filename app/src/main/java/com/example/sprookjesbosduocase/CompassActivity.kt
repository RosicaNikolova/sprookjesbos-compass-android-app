package com.example.sprookjesbosduocase

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompassActivity : AppCompatActivity(), SensorEventListener {

    // define the display assembly compass picture
    private lateinit var image: ImageView

    // record the compass picture angle turned
    private var currentDegree = 0f

    // device sensor manager
    private lateinit var mSensorManager: SensorManager

    private lateinit var tvHeading: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        //super.onResume()

        // Start the LocationService
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }

        // our compass image
        image = findViewById(R.id.compassImg)

        // initialize your android device sensor capabilities
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        //navigation
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
        val myDialog = Dialog(this)

        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // no need for code
            }

            override fun onFinish() {
                val dialogBinding = layoutInflater.inflate(R.layout.compass_dialog, null)
                myDialog.setContentView(dialogBinding)
                myDialog.setCancelable(true)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                val closebtn = dialogBinding.findViewById<ImageView>(R.id.close)
                closebtn.setOnClickListener{
                    myDialog.dismiss()
                }

                val yesbtn = dialogBinding.findViewById<Button>(R.id.yesbtn)
                yesbtn.setOnClickListener{
                    val intent = Intent(this@CompassActivity, FairyGogglesActivity::class.java)
                    startActivity(intent)
                }

                val btnLater = dialogBinding.findViewById<Button>(R.id.btnLater)
                btnLater.setOnClickListener{
                    val intent = Intent(this@CompassActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        //start the timer
        timer.start();
    }


    override fun onResume() {
        super.onResume()

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(
            this,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // get the angle around the z-axis rotated
            val degree = Math.round(event.values[0])

            //tvHeading.text = "Heading: $degree degrees"

            // create a rotation animation (reverse turn degree degrees)
            val ra = RotateAnimation(
                currentDegree,
                -degree.toFloat(),
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )

            // how long the animation will take place
            ra.duration = 210

            // set the animation after the end of the reservation status
            ra.fillAfter = true

            // Start the animation
            image.startAnimation(ra)
            currentDegree = -degree.toFloat()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // not in use
    }

//    override fun onResume() {
//        super.onResume()
//        // Start the LocationService
//        Intent(applicationContext, LocationService::class.java).apply {
//            action = LocationService.ACTION_START
//            startService(this)
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        // Stop the LocationService
//        Intent(applicationContext, LocationService::class.java).apply {
//            action = LocationService.ACTION_STOP
//            startService(this)
//        }
//    }

}