package tfg.aperher.comandas.presentation.orders.recordsorders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ItemOrderBinding
import tfg.aperher.comandas.domain.model.Order

class OrdersAdapter(private val onClick: (orderId: String) -> Unit) : ListAdapter<Order, OrdersAdapter.ViewHolder>(OrderDiff) {
    object OrderDiff : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val order = getItem(absoluteAdapterPosition)
                onClick(order.id!!)
            }
        }

        fun bind(order: Order) {
            binding.root.context.apply {
                binding.tvSection.text = getString(R.string.show_section, order.section)
                binding.tvDate.text = getString(R.string.show_date, order.day)
                binding.tvInitHour.text = getString(R.string.show_hour, order.endTime)
                binding.tvTableNum.text = order.table.toString()
            }
        }
    }
}