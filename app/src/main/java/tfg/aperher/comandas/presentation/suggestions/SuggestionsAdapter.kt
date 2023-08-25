package tfg.aperher.comandas.presentation.suggestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.animation.AnimationUtils.lerp
import com.google.android.material.carousel.MaskableFrameLayout
import tfg.aperher.comandas.databinding.ItemSuggestionsBinding
import tfg.aperher.comandas.domain.model.Article

class SuggestionsAdapter(private val list: List<Article>, private val onClick: (Article) -> Unit) : RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuggestionsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSuggestionsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemSuggestionsBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(list[absoluteAdapterPosition])
            }

            (itemView as MaskableFrameLayout).setOnMaskChangedListener { maskRect ->
                binding.carouselItemTitle.apply {
                    translationX = maskRect.left
                    alpha = lerp(1F, 0F, 0F, 80F, maskRect.left)
                }
            }
        }

        fun bind(article: Article) {
            binding.carouselItemTitle.text = article.name

            Glide.with(itemView)
                .load(article.image)
                //.transform(CenterCrop(), RoundedCorners(24))
                .into(binding.carouselImageView)
        }
    }

}