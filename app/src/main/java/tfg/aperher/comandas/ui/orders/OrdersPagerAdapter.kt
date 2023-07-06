package tfg.aperher.comandas.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tfg.aperher.comandas.databinding.ItemSectionBinding


class OrdersPagerAdapter : RecyclerView.Adapter<OrdersPagerAdapter.ViewHolder>() {
    // Intern item recyclerview always uses same viewPool so it can recycle views
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSectionBinding.inflate(inflater, parent, false)
        binding.rvTables.apply {
            setRecycledViewPool(viewPool)
            (layoutManager as GridLayoutManager).recycleChildrenOnDetach = true
            addItemDecoration(SpacingItemDecoration(3, 15))
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 5

    class ViewHolder(private val binding: ItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.rvTables.apply {
                adapter = TablesAdapter()
            }
        }
    }
}



