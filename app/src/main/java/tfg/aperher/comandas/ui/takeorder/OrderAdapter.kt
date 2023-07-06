package tfg.aperher.comandas.ui.takeorder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.databinding.ItemArticleOrderBinding
import tfg.aperher.comandas.model.Order

class OrderAdapter : ListAdapter<Order.Article, OrderAdapter.OrderViewHolder>(ArticleDiff) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind()
    }

    class OrderViewHolder(private val binding : ItemArticleOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    object ArticleDiff : DiffUtil.ItemCallback<Order.Article>() {
        override fun areItemsTheSame(oldItem: Order.Article, newItem: Order.Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order.Article, newItem: Order.Article): Boolean {
            return oldItem == newItem
        }
    }
}
