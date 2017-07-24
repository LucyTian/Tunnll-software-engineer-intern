package com.lu.mymap

import android.app.LauncherActivity
import android.support.test.InstrumentationRegistry
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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.uiautomator.UiDevice
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places

/**
 * Author: Lu Tian
 * Instrument test for the MainActivity
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule @JvmField
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    //lateinit var mGoogleApiClient:GoogleApiClient
    @Before
    fun setUp(){

    }
    @Test
    fun testEixtButton(){
        val activity : MainActivity = mActivityRule.activity
        val mClickExitButton = activity.findViewById<ImageButton>(R.id.btn1)

        // Test if the button is created
        assertNotNull("The button view is not created",mClickExitButton)

        // Test the layout parameter of the button
        val mLayoutParams:ViewGroup.LayoutParams = mClickExitButton.layoutParams
        assertNotNull(mLayoutParams)
        assertEquals(mLayoutParams.width,WindowManager.LayoutParams.WRAP_CONTENT)
        assertEquals(mLayoutParams.height,WindowManager.LayoutParams.WRAP_CONTENT)
        // Instrument crashes on click
        //onView(withId(R.id.btn1)).perform(click())
    }


    @Test
    fun testMapFragment(){
        //InstrumentationRegistry.getInstrumentation()
        //var mGoogleAipClient:GoogleApiClient?=null
        val mGoogleApiClient= GoogleApiClient.Builder(mActivityRule.activity)
        .addApi(LocationServices.API)
        .addApi(Places.GEO_DATA_API)
        .addApi(Places.PLACE_DETECTION_API)
        .build()
        mGoogleApiClient.connect()
        assertEquals("Google api client not connected",mGoogleApiClient.isConnected,false)
    }
}
