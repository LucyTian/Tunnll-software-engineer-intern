package com.lu.mymap


import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(),OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private val TAG = "MapsActivity"
    private lateinit var mGoogleApiClient:GoogleApiClient

    private var mMap:GoogleMap? = null
    private val KEY_CAMERA_POSITION:String = "camera_position"
    private val KEY_LOCATION:String = "location"
    private val DEFAULT_ZOOM:Float = 15f
    private val mDefaultLocation:LatLng = LatLng(-33.8523341,151.2106085)

    var mLastLocation:Location? = null
    var mCameraPosition:CameraPosition?=null

    var mLocationPermission: Boolean = false
    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retrieve location and camera position from saved instance state
        if (savedInstanceState != null) {
            mLastLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        // retrieve the content view that renders the map
        setContentView(R.layout.activity_main)

        // Initializing googleApiClient
        mGoogleApiClient = GoogleApiClient.Builder(this).
                enableAutoManage(this /* FragmentActivity*/,
                        this/* OnConnectionFailedListener*/)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build()

        mGoogleApiClient.connect()

        /*
        val btnExit: ImageButton = findViewById(R.id.btn1)

        btnExit.setOnClickListener {
            finish()
            System.exit(0)
        }
        */

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
                android.Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED){
            mLocationPermission = true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }

        /**
         * Get the best and most recent location of the device, which might be null when a location
         * is not available
         */
        if(mLocationPermission) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        }

        // Set the map's camera position to the current location of the device
        if (mCameraPosition != null) {
            mMap?.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition))
        } else if (mLastLocation != null) {
                //print("mLocation exits")
            val lastLocation :Location? = mLastLocation
            val mLatLng:LatLng = LatLng(lastLocation!!.latitude, lastLocation.longitude)
            mMap?.addMarker(MarkerOptions().position(mLatLng).title("You are here"))
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,DEFAULT_ZOOM))
        } else {
            print("No place record")
            Log.d(TAG,"Current location is null. Using default.")
            mMap?.addMarker(MarkerOptions().position(mDefaultLocation).title("This is sydney"))
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation,DEFAULT_ZOOM))
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
}