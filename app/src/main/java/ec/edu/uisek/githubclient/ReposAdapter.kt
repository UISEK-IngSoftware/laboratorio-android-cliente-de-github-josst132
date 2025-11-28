package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ec.edu.uisek.githubclient.databinding.FragmentRepoItemBinding
import ec.edu.uisek.githubclient.models.Repo

class ReposViewHolder(private val binding: FragmentRepoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(repo: Repo, onEdit: (Repo) -> Unit, onDelete: (Repo) -> Unit) {
        binding.repoName.text = repo.name
        binding.repoDescription.text = repo.description
        binding.repoLang.text = repo.language
        Glide.with(binding.root.context)
            .load(repo.owner.avatarUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .circleCrop()
            .into(binding.repoOwnerImage)

        binding.editRepoButton.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.edit_blue))
        binding.deleteRepoButton.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.delete_red))

        binding.editRepoButton.setOnClickListener { onEdit(repo) }
        binding.deleteRepoButton.setOnClickListener { onDelete(repo) }
    }
}

class ReposAdapter(
    private val onEdit: (Repo) -> Unit,
    private val onDelete: (Repo) -> Unit
) : RecyclerView.Adapter<ReposViewHolder>() {
    private var repositories: List<Repo> = emptyList()

    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val binding = FragmentRepoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReposViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(repositories[position], onEdit, onDelete)
    }

    fun updateRepositories(newRepositories: List<Repo>) {
        repositories = newRepositories
        notifyDataSetChanged()
    }
}
