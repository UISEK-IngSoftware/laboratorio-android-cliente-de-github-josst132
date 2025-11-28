package ec.edu.uisek.githubclient

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uisek.githubclient.databinding.ActivityRepoFormBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.models.RepoOwner

class RepoForm : AppCompatActivity() {
    private lateinit var binding: ActivityRepoFormBinding
    private var existingRepo: Repo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        existingRepo = intent.getParcelableExtra("repo_key")

        existingRepo?.let {
            binding.repoNameInput.setText(it.name)
            binding.repoDescriptionInput.setText(it.description)
        }

        binding.cancelButton.setOnClickListener { finish() }
        binding.saveButton.setOnClickListener { saveRepo() }
    }

    private fun saveRepo() {
        val repoName = binding.repoNameInput.text.toString().trim()
        val repoDescription = binding.repoDescriptionInput.text.toString().trim()

        if (repoName.isBlank()) {
            Toast.makeText(this, "El nombre del repositorio es requerido", Toast.LENGTH_SHORT).show()
            return
        }

        val resultIntent = Intent()
        val repo = existingRepo?.apply {
            name = repoName
            description = repoDescription
        } ?: Repo(
            id = System.currentTimeMillis(),
            name = repoName,
            description = repoDescription,
            language = "Kotlin",
            owner = RepoOwner(id = 1, login = "MiUsuario", avatarUrl = "")
        )

        resultIntent.putExtra("repo_key", repo)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
