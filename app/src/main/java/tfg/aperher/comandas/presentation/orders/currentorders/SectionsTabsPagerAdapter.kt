package tfg.aperher.comandas.presentation.orders.currentorders

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import tfg.aperher.comandas.domain.model.Section
import tfg.aperher.comandas.presentation.orders.currentorders.tablespersection.TablesFragment

class SectionsTabsPagerAdapter(private val list: List<Section>, tf: SectionTabsFragment) :
    FragmentStateAdapter(tf) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = TablesFragment().apply {
        arguments = Bundle().apply {
            putString(TablesFragment.ARG_SECTION_ID, list[position].id)
            putString(TablesFragment.ARG_SECTION_NAME, list[position].name)
        }
    }
}
