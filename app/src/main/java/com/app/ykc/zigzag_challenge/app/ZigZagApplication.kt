package com.app.ykc.zigzag_challenge.app

import android.app.Application
import com.app.ykc.zigzag_challenge.BuildConfig
import com.app.ykc.zigzag_challenge.data.ShoppingMallRepository
import com.app.ykc.zigzag_challenge.utils.FileReader
import com.facebook.stetho.Stetho
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber
import java.io.File

/**
 * Launcher icon made by Freepik at flaticon.com
 */
class ZigZagApplication : Application() {

    lateinit var moshi : Moshi
    lateinit var repository : ShoppingMallRepository

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }

        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        repository = ShoppingMallRepository(moshi, FileReader(assets))

    }
}