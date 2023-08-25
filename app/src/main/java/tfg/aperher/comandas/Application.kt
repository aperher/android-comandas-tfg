package tfg.aperher.comandas

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    /*override fun onCreate() {
        super.onCreate()

        if (false) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }*/
}
