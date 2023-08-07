package tfg.aperher.comandas.presentation.orders.recordsorders.orderdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ItemArticleOrderBinding
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.Ingredient

class ArticlesAdapter(
    private val articles: List<ArticleInOrder>
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    override fun getItemCount(): Int = articles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleOrderBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    class ViewHolder(private val binding: ItemArticleOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleInOrder) {
            binding.apply {
                tvMonogram.text = article.quantity.toString()
                tvArticleName.text = article.name
                tvArticlePrice.text = formatPrice(article.price)
                tvArticleCost.text = formatPrice(article.price * article.quantity)
                showOrHideExtras(article.extras)
                if (article.extras.isNotEmpty()) {
                    tvExtraArticleNames.text = formatExtraNames(article.extras)
                    tvExtraArticlePrices.text = formatExtraPrices(article.extras.map { it.price })
                    tvExtraArticleCosts.text = formatExtraPrices(article.extras.map { it.price * article.quantity })
                }
            }
        }

        private fun formatPrice(price: Double): String {
            var stringFormat = "%.1f"
            if (price % 1 == 0.0) stringFormat = "%.0f"
            return binding.root.context.getString(R.string.price, String.format(stringFormat, price))
        }

        private fun showOrHideExtras(extras: List<Ingredient>) {
            binding.tvExtraArticleNames.visibility = if (extras.isNotEmpty()) View.VISIBLE else View.GONE
            binding.tvExtraArticlePrices.visibility = if (extras.isNotEmpty()) View.VISIBLE else View.GONE
            binding.tvExtraArticleCosts.visibility = if (extras.isNotEmpty()) View.VISIBLE else View.GONE
        }

        private fun formatExtraNames(extras: List<Ingredient>): String {
            return extras.joinToString("\n") { it.name }
        }

        private fun formatExtraPrices(extras: List<Double>): String {
            return extras.joinToString("\n") { formatPrice(it) }
        }
    }
}