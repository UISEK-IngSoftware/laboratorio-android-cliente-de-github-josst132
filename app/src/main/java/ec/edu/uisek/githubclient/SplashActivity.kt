package ec.edu.uisek.githubclient

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uisek.githubclient.services.RetrofitClient
import ec.edu.uisek.githubclient.sevices.SessionManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sessionManager = SessionManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            val credentials = sessionManager.getCredentials()
            if (credentials != null) {
                // Si hay credenciales, inicializar el servicio y ir a MainActivity
                RetrofitClient.createAuthenticatedApiService(credentials.username, credentials.password)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Si no hay credenciales, ir a LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            // Cerrar la SplashActivity para que el usuario no pueda volver a ella
            finish()
        }, 2000) // 2000 milisegundos = 2 segundos
    }
}
