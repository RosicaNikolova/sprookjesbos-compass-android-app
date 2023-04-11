package com.example.sprookjesbosduocase

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompassActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private var currentLocation: Location? = null
    private val destinationLocation = Location("")

    private lateinit var compass: ImageView
    //private lateinit var picture: ImageView
    private var currentDegree = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)

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



        compass = findViewById(R.id.compassImg)
        //picture = findViewById(R.id.picture)

        // Set the destination location
        destinationLocation.latitude = 37.421998
        destinationLocation.longitude = -122.084

        // Get the location manager and request location updates
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                this
            )
        }
    }

    override fun onResume() {
        super.onResume()

        // Get the rotation animation for the compass

            val rotateAnimation = RotateAnimation(
                currentDegree,
                -currentLocation?.bearing!!,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            ).apply {
                duration = 250
                fillAfter = true
            }

            compass.startAnimation(rotateAnimation)
            currentDegree = -currentLocation?.bearing!!

            // Check if the user has reached the destination location
            if (currentLocation?.distanceTo(destinationLocation) ?: 0f < 10f) {
                // Load and display the picture
                val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.compass)
                compass.setImageBitmap(rotateBitmap(bitmap, -currentDegree))
                compass.setOnClickListener {
                    MediaStore.Images.Media.insertImage(
                        contentResolver,
                        bitmap,
                        "Picture",
                        "Description"
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.type = "image/*"
                    startActivity(intent)
                }
            }
        }

    override fun onLocationChanged(location: Location) {
        currentLocation = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d("Location", "Status changed to $status")
    }

    override fun onProviderEnabled(provider: String) {
        Log.d("Location", "Provider $provider enabled")
    }

    override fun onProviderDisabled(provider: String) {
        Log.d("Location", "Provider $provider disabled")
    }

    private fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
