package tfg.aperher.comandas.presentation.orders.currentorders.tablespersection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ItemTableBinding
import tfg.aperher.comandas.domain.model.State
import tfg.aperher.comandas.domain.model.Table

class TablesAdapter(private val itemClicked: (Table) -> Unit) :
    ListAdapter<Table, TablesAdapter.ViewHolder>(TableDiff) {

    object TableDiff : DiffUtil.ItemCallback<Table>() {
        override fun areItemsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Table, newItem: Table): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTableBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardTable.setOnClickListener {
                val table = getItem(adapterPosition)
                itemClicked(table)
            }
        }

        fun bind(table: Table) {
            binding.tvTableNumber.text = table.number.toString()

            table.initTime?.let { binding.tvInitTime.text = it }

            val colorState = getTableColorState(table.state)
            binding.ivTable.setColorFilter(colorState)
        }

        private fun getTableColorState(state: State): Int = when (state) {
            State.AVAILABLE, State.DELIVERED -> MaterialColors.getColor(
                binding.root,
                com.google.android.material.R.attr.colorOnSurface
            )
            State.PENDING, State.PREPARING -> ContextCompat.getColor(
                binding.root.context,
                R.color.orangeTableState
            )
            State.READY -> ContextCompat.getColor(binding.root.context, R.color.greenTableState)
        }
    }
}
