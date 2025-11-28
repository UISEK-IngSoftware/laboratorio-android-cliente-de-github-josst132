package ec.edu.uisek.githubclient

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uisek.githubclient.databinding.ActivityLoginBinding
import ec.edu.uisek.githubclient.services.RetrofitClient
import ec.edu.uisek.githubclient.sevices.SessionManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (sessionManager.getCredentials() != null) {
            navigateToMain()
            return
        }

        binding.logginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val username = binding.userInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            sessionManager.saveCredentials(username, password)

            RetrofitClient.createAuthenticatedApiService(username, password)

            navigateToMain()
        } else {
            Toast.makeText(this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}