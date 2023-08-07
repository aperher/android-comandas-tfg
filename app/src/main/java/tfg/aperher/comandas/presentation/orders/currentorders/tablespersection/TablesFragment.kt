package tfg.aperher.comandas.presentation.orders.currentorders.tablespersection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentTablesBinding
import tfg.aperher.comandas.domain.model.Table
import tfg.aperher.comandas.presentation.orders.currentorders.SectionTabsFragmentDirections
import tfg.aperher.comandas.utils.toast

@AndroidEntryPoint
class TablesFragment : Fragment(R.layout.fragment_tables) {
    companion object {
        const val ARG_SECTION_ID = "sectionId"
        const val ARG_SECTION_NAME = "sectionName"
    }

    private var _binding: FragmentTablesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TablesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTablesBinding.bind(view)

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        loadTables()
        initRecyclerAdapter()
        initObservers()
    }

    private fun loadTables() {
        val sectionId = requireArguments().getString(ARG_SECTION_ID)!!
        viewModel.getTables(sectionId)
    }

    private fun initRecyclerAdapter() {
        binding.rvTables.apply {
            addItemDecoration(SpacingItemDecoration(3, 15))

            adapter = TablesAdapter { table ->
                goToTakeOrderActivity(
                    requireArguments().getString(ARG_SECTION_NAME)!!,
                    table
                )
            }
        }
    }

    private fun initObservers() {
        viewModel.tables.observe(viewLifecycleOwner) {
            (binding.rvTables.adapter as TablesAdapter).submitList(it)
        }

        viewModel.exception.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { exception ->
                requireActivity().toast(exception.message.toString())
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun goToTakeOrderActivity(sectionName: String, table: Table) {
        val action = SectionTabsFragmentDirections.actionSectionTabsFragmentToTakeOrderActivity(
            sectionName, table
        )
        findNavController().navigate(action)
    }
}