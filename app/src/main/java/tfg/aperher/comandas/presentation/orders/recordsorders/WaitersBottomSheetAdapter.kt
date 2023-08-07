package tfg.aperher.comandas.presentation.orders.recordsorders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.databinding.ItemRecordsBottomSheetBinding
import tfg.aperher.comandas.domain.model.User

class WaitersBottomSheetAdapter(
    private val waiters: List<User>,
    private val onClick: (user: User) -> Unit
) :
    RecyclerView.Adapter<WaitersBottomSheetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecordsBottomSheetBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = waiters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(waiters[position])
    }

    inner class ViewHolder(private val binding: ItemRecordsBottomSheetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onClick(waiters[adapterPosition])
            }
        }

        fun bind(waiter: User) {
            binding.tvWaiterName.text = waiter.name
        }
    }
}