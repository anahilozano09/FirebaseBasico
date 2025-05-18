package mx.unam.firebasebasico

import android.app.Application
import android.content.Context

class CursoFirebaseApp : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}