package tfg.aperher.comandas.presentation.orders.readyarticles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.databinding.ItemArticleReadyBinding
import tfg.aperher.comandas.domain.model.ArticleReady

class ReadyArticlesAdapter :
    ListAdapter<ArticleReady, ReadyArticlesAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<ArticleReady>() {
        override fun areItemsTheSame(oldItem: ArticleReady, newItem: ArticleReady): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleReady, newItem: ArticleReady): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleReadyBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemArticleReadyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleReady) {
            binding.apply {
                tvArticleName.text = article.name
                tvSection.text = article.section
                tvTableNum.text = article.table.toString()
                if (article.extras.isEmpty()) tvExtraArticleNames.visibility = ViewGroup.GONE
                else {
                    tvExtraArticleNames.visibility = ViewGroup.VISIBLE
                    tvExtraArticleNames.text = article.extras.joinToString { it.name }
                }
            }
        }
    }
}