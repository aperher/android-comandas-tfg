package tfg.aperher.comandas.ui.tables

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentTablesBinding
import tfg.aperher.comandas.ui.orders.OrdersPagerAdapter

@AndroidEntryPoint
class TablesFragment : Fragment(R.layout.fragment_tables) {
    private var _binding: FragmentTablesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTablesBinding.bind(view)

        binding.apply {
            viewPager.adapter = OrdersPagerAdapter()
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = "Tab $position"
            }.attach()
        }

        val list = listOf(1,2,3,4)
        binding.tabLayout.tabMode = if (list.size < 5)  TabLayout.MODE_FIXED else TabLayout.MODE_SCROLLABLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}