package com.yilmazgokhan.superhero

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

/**
 * Core application class
 */
@HiltAndroidApp
class SuperHeroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initAdMob()
        disableDarkMode()
    }

    /**
     * Initialize Google AdMob
     */
    private fun initAdMob() {
        MobileAds.initialize(this) {}
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}