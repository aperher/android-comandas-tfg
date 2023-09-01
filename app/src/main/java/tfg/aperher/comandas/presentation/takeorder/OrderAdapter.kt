package tfg.aperher.comandas.presentation.takeorder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ItemArticleTakeOrderBinding
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.State

class OrderAdapter(
    private val changeAmount: (Int, Int) -> Unit,
    private val onClickToServe: (article: ArticleInOrder) -> Unit
) : ListAdapter<ArticleInOrder, OrderAdapter.OrderViewHolder>(ArticleInOrderDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleTakeOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(private val binding: ItemArticleTakeOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnAdd.setOnClickListener {
                changeAmount(absoluteAdapterPosition, 1)
            }
            binding.btnRemove.setOnClickListener {
                changeAmount(absoluteAdapterPosition, -1)
            }
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

                tvMonogramState.backgroundTintList = ContextCompat.getColorStateList(
                    binding.root.context,
                    getColorState(article.state)
                )

                if (article.state == State.READY) {
                    binding.root.setOnClickListener {
                        onClickToServe(article)
                    }
                } else if (article.state == State.DELIVERED) {
                    binding.served.visibility = RecyclerView.VISIBLE
                }
            }
        }

        @SuppressLint("PrivateResource")
        private fun getColorState(state: State): Int = when (state) {
            State.PREPARING -> R.color.orangeArticleState
            State.READY, State.DELIVERED -> R.color.greenArticleState
            else -> {
                com.google.android.material.R.color.m3_sys_color_light_surface_container_high
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
