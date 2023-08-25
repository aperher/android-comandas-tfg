package tfg.aperher.comandas.presentation.orders.currentorders.sectiontables

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ItemTableBinding
import tfg.aperher.comandas.domain.model.State
import tfg.aperher.comandas.domain.model.Table

class TablesAdapter(private val onClick: (Table) -> Unit) :
    ListAdapter<Table, TablesAdapter.ViewHolder>(TableDiff) {

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    object TableDiff : DiffUtil.ItemCallback<Table>() {
        override fun areItemsTheSame(oldItem: Table, newItem: Table): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Table, newItem: Table): Boolean =
            oldItem == newItem
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTableBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tracker?.let { holder.bind(getItem(position), it.isSelected(position.toLong())) }
    }

    inner class ViewHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cardTable.setOnClickListener {
                if (tracker?.hasSelection() == true) return@setOnClickListener

                val currentTable = getItem(absoluteAdapterPosition)
                onClick(currentTable)
            }
        }


        fun bind(table: Table, isChecked: Boolean) {

            binding.cardTable.isChecked = isChecked

            binding.tvTableNumber.text = table.number.toString()
            binding.tvInitTime.text = table.initTime ?: ""

            val colorState = getTableColorState(table.state)
            binding.ivTable.setColorFilter(colorState)

            binding.ivTable.setImageResource(
                when (table.state) {
                    State.DELIVERED -> R.drawable.checklist
                    else -> R.drawable.table
                }
            )
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = absoluteAdapterPosition
                override fun getSelectionKey(): Long = itemId
            }

        private fun getTableColorState(state: State): Int = when (state) {
            State.AVAILABLE -> MaterialColors.getColor(
                binding.root, com.google.android.material.R.attr.colorOnSurface
            )

            State.PENDING, State.PREPARING -> ContextCompat.getColor(
                binding.root.context, R.color.orangeTableState
            )

            State.READY, State.DELIVERED -> ContextCompat.getColor(
                binding.root.context, R.color.greenTableState
            )
        }
    }
}