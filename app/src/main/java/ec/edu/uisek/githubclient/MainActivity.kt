package ec.edu.uisek.githubclient

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.uisek.githubclient.databinding.ActivityMainBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.services.RetrofitClient
import ec.edu.uisek.githubclient.sevices.GitHubApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        reposAdapter = ReposAdapter()
        binding.reposRecyclerView.adapter = reposAdapter
    }

    private fun fetchRepositories() {
        val apiServices: GitHubApiServices = RetrofitClient.GitHubApiServices
        val call = apiServices.getRepos()
        call.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
                if (response.isSuccessful){
                val repos = response.body()
                if (response != null && repos.isNotEmpty()) {
                    reposAdapter.updateRepositories(repos)

                } else {
                    showMessage("No se encontraron repositorios")
                }
            } else{
                val errorMesage = when(response.code()) {
                    401 -> "no autorizado"
                    403 -> "prohibido"
                    404 -> "no encontrado"
                    else -> "Error ${response.code()}"
                }
                    showMessage("Error: $errorMesage")
            }
        }

            override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
                showMessage("No se pudieron cargar los repositorios")
            }
        })
    }
    private fun showMessage (message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}