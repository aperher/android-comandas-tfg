package tfg.aperher.comandas.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.databinding.ItemTableBinding
import tfg.aperher.comandas.ui.takeorder.TakeOrderActivity
import java.util.concurrent.atomic.AtomicInteger

const val EXTRA_TABLE_ID = "EXTRA_TABLE_ID"

class TablesAdapter : RecyclerView.Adapter<TablesAdapter.ViewHolder>() {

    companion object {
        private val count: AtomicInteger = AtomicInteger(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTableBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 20

    class ViewHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { root ->
                root.context.let {
                    val intent = TakeOrderActivity.create(it).apply {
                        putExtra(EXTRA_TABLE_ID, adapterPosition + 1)
                    }
                    it.startActivity(intent)
                }
            }
        }

        fun bind() {
            //binding.
        }
    }
}
