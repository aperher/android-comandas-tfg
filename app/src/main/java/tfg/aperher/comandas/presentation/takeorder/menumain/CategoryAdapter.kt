package tfg.aperher.comandas.presentation.takeorder.menumain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import tfg.aperher.comandas.databinding.ItemArticleBinding
import tfg.aperher.comandas.domain.model.Category

class CategoryAdapter(
    private val onClick: (categoryId: String, categoryName: String) -> Unit
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiff) {

    object CategoryDiff : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val category = getItem(absoluteAdapterPosition)
                onClick(category.id, category.name)
            }
        }

        fun bind(category: Category) {
            binding.tvName.text = category.name
            binding.tvArticleDescription.text = category.description

            Glide.with(itemView)
                .load(category.imageURL)
                .transform(CenterCrop(), RoundedCorners(24))
                .into(binding.ivImage)
        }
    }
}
