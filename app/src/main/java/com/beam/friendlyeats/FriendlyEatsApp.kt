package com.beam.friendlyeats

import android.app.Application
import com.couchbase.lite.CouchbaseLite
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class FriendlyEatsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initCouchbase()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@FriendlyEatsApp)
        }
    }

    private fun initCouchbase() {
        CouchbaseLite.init(this)
    }
}
