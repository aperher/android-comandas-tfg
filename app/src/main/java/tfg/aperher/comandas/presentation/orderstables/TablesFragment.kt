package tfg.aperher.comandas.presentation.orderstables

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentTablesBinding
import tfg.aperher.comandas.presentation.ordertake.TakeOrderActivity
import tfg.aperher.comandas.presentation.ordertake.TakeOrderActivity.Companion.ORDER_ACTIVE
import tfg.aperher.comandas.presentation.ordertake.TakeOrderActivity.Companion.SECTION
import tfg.aperher.comandas.presentation.ordertake.TakeOrderActivity.Companion.TABLE
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

        val sectionId = requireArguments().getString(ARG_SECTION_ID)!!
        viewModel.getTables(sectionId)

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        initRecyclerAdapter()
        initObservers()
    }

    private fun initRecyclerAdapter() {
        binding.rvTables.apply {
            addItemDecoration(SpacingItemDecoration(3, 15))

            adapter = TablesAdapter { table, isActive ->
                val context = requireContext()
                val intent = TakeOrderActivity.create(context).apply {
                    putExtra(SECTION, requireArguments().getString(ARG_SECTION_NAME))
                    putExtra(TABLE, table)
                    putExtra(ORDER_ACTIVE, isActive)
                }
                context.startActivity(intent)
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
}