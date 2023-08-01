package tfg.aperher.comandas.presentation.ordertake.articleslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ItemArticleBinding
import tfg.aperher.comandas.domain.model.Article

class ArticleAdapter(
    private val onClick: (article: Article) -> Unit
) : ListAdapter<Article, ArticleAdapter.ViewHolder>(ArticleDiff) {

    object ArticleDiff : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        fun bind(article: Article) {
            binding.tvName.text = article.name

            if (article.description.isEmpty()) binding.tvArticleDescription.visibility =
                ViewGroup.GONE
            else {
                binding.tvArticleDescription.visibility = ViewGroup.VISIBLE
                binding.tvArticleDescription.text = article.description
            }

            binding.tvPrice.apply {
                visibility = ViewGroup.VISIBLE
                text = context.getString(R.string.price, article.price.toString())
            }

            Glide.with(itemView)
                .load(article.image)
                .transform(CenterCrop(), RoundedCorners(24))
                .into(binding.ivImage)
        }
    }
}
