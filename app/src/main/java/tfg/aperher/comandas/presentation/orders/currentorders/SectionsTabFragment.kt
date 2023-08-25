package tfg.aperher.comandas.presentation.orders.currentorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentSectionTabsBinding
import tfg.aperher.comandas.domain.model.Section
import tfg.aperher.comandas.utils.toast

@AndroidEntryPoint
class SectionsTabFragment : Fragment(R.layout.fragment_section_tabs) {
    private var _binding: FragmentSectionTabsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SectionsTabViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSectionTabsBinding.bind(view)

        initObservers()
    }

    override fun onStart() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.orders)
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        viewModel.sections.observe(viewLifecycleOwner) { sections ->
            setupViewPager(sections)
            setupTabLayout(sections)
        }

        viewModel.isScrollableTab.observe(viewLifecycleOwner) { isScrollable ->
            binding.tabLayout.tabMode =
                if (isScrollable) TabLayout.MODE_SCROLLABLE else TabLayout.MODE_FIXED
        }

        viewModel.exception.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { exception ->
                toast(exception.message.toString())
            }
        }
    }

    private fun setupViewPager(sections: List<Section>) {
        binding.viewPager.adapter = SectionsTabPagerAdapter(sections, this@SectionsTabFragment)
    }

    private fun setupTabLayout(sections: List<Section>) {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = sections[position].name
        }.attach()
    }

    private fun toast(message: String) {
        requireActivity().toast(message)
    }
}
