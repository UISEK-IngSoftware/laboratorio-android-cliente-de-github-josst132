package ec.edu.uisek.githubclient

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uisek.githubclient.databinding.ActivityMainBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.models.RepoOwner

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var reposAdapter: ReposAdapter
    private val repos = mutableListOf<Repo>()

    private val formResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val repo = it.data?.getParcelableExtra<Repo>("repo_key")
            if (repo != null) {
                val existingRepo = repos.find { r -> r.id == repo.id }
                if (existingRepo != null) {
                    existingRepo.name = repo.name
                    existingRepo.description = repo.description
                } else {
                    repos.add(0, repo)
                }
                reposAdapter.updateRepositories(repos)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.newRepoFab.setOnClickListener { displayNewRepoForm() }

        fetchRepositories()
    }

    private fun setupRecyclerView() {
        reposAdapter = ReposAdapter(
            onEdit = { repo -> displayEditRepoForm(repo) },
            onDelete = { repo -> deleteRepo(repo) }
        )
        binding.reposRecyclerView.adapter = reposAdapter
    }

    private fun fetchRepositories() {
        repos.addAll(listOf(
            Repo(
                id = 1,
                name = "Mi Primer Repo",
                description = "Esta es una descripción de prueba.",
                language = "Kotlin",
                owner = RepoOwner(id = 1, login = "MiUsuario", avatarUrl = "")
            ),
            Repo(
                id = 2,
                name = "Otro Proyecto",
                description = "Un proyecto increíble para Android.",
                language = "Java",
                owner = RepoOwner(id = 1, login = "MiUsuario", avatarUrl = "")
            ),
            Repo(
                id = 3,
                name = "Cliente de GitHub",
                description = "Una app para visualizar repositorios.",
                language = "Kotlin",
                owner = RepoOwner(id = 1, login = "MiUsuario", avatarUrl = "")
            )
        ))
        reposAdapter.updateRepositories(repos)
    }

    private fun displayNewRepoForm() {
        val intent = Intent(this, RepoForm::class.java)
        formResultLauncher.launch(intent)
    }

    private fun displayEditRepoForm(repo: Repo) {
        val intent = Intent(this, RepoForm::class.java)
        intent.putExtra("repo_key", repo)
        formResultLauncher.launch(intent)
    }

    private fun deleteRepo(repo: Repo) {
        repos.remove(repo)
        reposAdapter.updateRepositories(repos)
        Toast.makeText(this, "Repositorio eliminado", Toast.LENGTH_SHORT).show()
    }
}
