package com.lu.mymap


import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.ImageButton


import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.system.exitProcess

/**
 * Create an activity that displays a map showing the place of the device and set a marker on it.
 * Also add an icon to exit the app
 */
class MainActivity : AppCompatActivity(),OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private val TAG = "MapsActivity"
    private var mMap:GoogleMap? = null

    // The entry point to Google Play Services, used Places API and Fused Location Provider
    // for more accurate location
    private lateinit var mGoogleApiClient:GoogleApiClient

    // Set a default location (Sydney, Australia) and default zoom of 15 when location permission
    // is not granted
    private val mDefaultLocation:LatLng = LatLng(40.439722,-79.796389)
    private val DEFAULT_ZOOM:Float = 15f
    var mLocationPermission: Boolean = false
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:Int = 1


    private val KEY_CAMERA_POSITION:String = "camera_position"
    private val KEY_LOCATION:String = "location"


    // The last known location retrieved by the Fused Location Provider
    var mLastLocation:Location? = null

    // The location of where the map shows
    var mCameraPosition:CameraPosition?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve location and camera position from saved instance state
        if (savedInstanceState != null) {
            mLastLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        // retrieve the content view that renders the map
        setContentView(R.layout.activity_main)

        // Build the Play services client for used by the Fused Location Provider and Places API.
        // Use the addApi() method to request the Google Places API and the Fused Location Provider
        mGoogleApiClient = GoogleApiClient.Builder(this).
                enableAutoManage(this /* FragmentActivity*/,
                        this/* OnConnectionFailedListener*/)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build()
        mGoogleApiClient.connect()

        /**
         * Add a image button over the map which exit the activity on click
         */
        val btnExit: ImageButton = findViewById(R.id.btn1)

        btnExit.setOnClickListener {
            finish()
            System.exit(0)
        }
    }

    /**
     * Saves the state of the map when activity is paused
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        val lastMap:GoogleMap? = mMap
        if(lastMap!=null){
            outState?.putParcelable(KEY_CAMERA_POSITION,lastMap.cameraPosition)
            outState?.putParcelable(KEY_LOCATION,mLastLocation)
            super.onSaveInstanceState(outState)
        }
    }

    /**
     *  Builds the map when Google Play services client is successfully connected
     */
    override fun onConnected(p0: Bundle?) {
        // Obtain the SupportMapFragment and get notified when map is ready to be used
        val mapFragment:SupportMapFragment = supportFragmentManager.
                findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Handle situation of failure to connect to Google Play services client
     */
    override fun onConnectionFailed(@NonNull result: ConnectionResult) {
        Log.d(TAG,"Google play services connection failed: ConnectionResult.getErrorCode() = "
                + result.errorCode)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d(TAG,"Google Play services connection suspended")
    }

    /**
     * Get the current location of the device, and position the map's camera to there
     */
    fun getDeviceLocation() {
        /**
         * Request location permission to get the location of the device.
         */

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mLocationPermission = true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }

        /**
         * Get the best and most recent location of the device, which might be null when a location
         * is not available. And set the map's camera position to the current location of device
         */
        if (mLocationPermission) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        }

        // Set the map's camera position to the current location of the device
        if (mCameraPosition != null) {
            mMap?.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition))
        } else if (mLastLocation != null) {
            val lastLocation: Location? = mLastLocation
            val mLatLng: LatLng = LatLng(lastLocation!!.latitude, lastLocation.longitude)
            mMap?.addMarker(MarkerOptions().position(mLatLng).title("You are here"))
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, DEFAULT_ZOOM))
        } else {
            //println("gps location not found!!!!!")
            Log.d(TAG, "Current location is null. Using default.")
            mMap?.addMarker(MarkerOptions().position(mDefaultLocation).title("This is Pittsburgh"))
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM))
        }
    }
    /**
     * Manipulate the map when it's available. This callback is triggered when the map is ready
     * to be used
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getDeviceLocation()
    }

    /**
     * Handles the result of request for location permission, repeat requesting if denied
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions:
                                    Array<out String>, @NonNull grantResults: IntArray) {
        mLocationPermission = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermission = true
                }
            }
        }
        getDeviceLocation()
    }
}