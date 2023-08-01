package tfg.aperher.comandas.presentation.ordertake

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ItemArticleOrderBinding
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.State

class OrderAdapter(private val changeAmount: (Int, Int) -> Unit) :
    ListAdapter<ArticleInOrder, OrderAdapter.OrderViewHolder>(ArticleInOrderDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(private val binding: ItemArticleOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnAdd.setOnClickListener {
                changeAmount(adapterPosition, 1)
            }
            binding.btnRemove.setOnClickListener {
                changeAmount(adapterPosition, -1)
            }
            binding.tvMonogramState
        }

        fun bind(article: ArticleInOrder) {
            binding.apply {
                btnRemove.isEnabled = article.state == State.PENDING
                tvArticleName.text = article.name
                tvArticlePrice.text = binding.root.context.getString(
                        R.string.price,
                        String.format("%.2f", article.price * article.quantity)
                    )
                tvMonogramState.text = article.quantity.toString()
            }
        }
    }

    object ArticleInOrderDiff : DiffUtil.ItemCallback<ArticleInOrder>() {
        override fun areItemsTheSame(oldItem: ArticleInOrder, newItem: ArticleInOrder): Boolean {
            return oldItem.articleId == newItem.articleId && oldItem.state == newItem.state
        }

        override fun areContentsTheSame(oldItem: ArticleInOrder, newItem: ArticleInOrder): Boolean {
            return oldItem == newItem
        }
    }
}
