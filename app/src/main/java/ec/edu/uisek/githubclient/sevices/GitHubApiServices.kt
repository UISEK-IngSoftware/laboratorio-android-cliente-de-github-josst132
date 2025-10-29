package ec.edu.uisek.githubclient.sevices

import retrofit2.Call
import ec.edu.uisek.githubclient.models.Repo
import retrofit2.http.GET


interface GitHubApiServices {
    @GET("user/repos")
    fun getRepos() : Call<List<Repo>>
}