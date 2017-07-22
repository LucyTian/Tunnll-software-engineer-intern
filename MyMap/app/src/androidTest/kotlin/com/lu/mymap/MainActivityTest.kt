package com.lu.mymap

import android.app.LauncherActivity
import android.support.test.runner.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import android.support.test.espresso.Espresso.*
import android.widget.ImageButton
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.gms.maps.GoogleMap

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

/**
 * Author: Lu Tian
 * Instrument test for the MainActivity
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule @JvmField
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun ClickButtonActivityTest(){
        val activity : MainActivity = mActivityRule.activity
        val mClickExitButton = activity.findViewById<ImageButton>(R.id.btn1)

        // Test if the button is created
        assertNotNull("The button view is not created",mClickExitButton)

        // Test the layout parameter of the button
        val mLayoutParams:ViewGroup.LayoutParams = mClickExitButton.layoutParams
        assertNotNull(mLayoutParams)
        assertEquals(mLayoutParams.width,WindowManager.LayoutParams.WRAP_CONTENT)
        assertEquals(mLayoutParams.height,WindowManager.LayoutParams.WRAP_CONTENT)
    }
/*
    @Test
    fun getMLocationPermission() {
        val test:String="aa"
        assertEquals(test,"aa")
    }

    @Test
    fun setMLocationPermission() {
    }

    @Test
    fun getPERMISSIONS_REQUEST_ACCESS_FINE_LOCATION() {
    }

    @Test
    fun getMLastLocation() {
    }

    @Test
    fun setMLastLocation() {
    }

    @Test
    fun getMCameraPosition() {
    }

    @Test
    fun setMCameraPosition() {
    }

    @Test
    fun onCreate() {
    }

    @Test
    fun onSaveInstanceState() {
    }

    @Test
    fun onConnected() {
    }

    @Test
    fun onConnectionFailed() {
    }

    @Test
    fun onConnectionSuspended() {
    }

    @Test
    fun getDeviceLocation() {
    }

    @Test
    fun onMapReady() {
    }

    @Test
    fun onRequestPermissionsResult() {
    }
*/
}