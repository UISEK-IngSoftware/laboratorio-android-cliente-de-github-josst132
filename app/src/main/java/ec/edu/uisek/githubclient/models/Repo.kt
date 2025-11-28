package ec.edu.uisek.githubclient.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo (
    val id: Long,
    var name: String,
    var description: String,
    val language: String?,
    val owner: RepoOwner
): Parcelable

data class RepoRequest (
    val name: String,
    val description: String,
)
