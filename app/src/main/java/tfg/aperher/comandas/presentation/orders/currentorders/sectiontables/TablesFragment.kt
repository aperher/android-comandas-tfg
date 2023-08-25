package tfg.aperher.comandas.presentation.orders.currentorders.sectiontables

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentTablesBinding
import tfg.aperher.comandas.domain.model.Table
import tfg.aperher.comandas.presentation.orders.currentorders.SectionsTabFragmentDirections

@AndroidEntryPoint
class TablesFragment : Fragment(R.layout.fragment_tables) {
    companion object {
        const val ARG_SECTION_ID = "sectionId"
        const val ARG_SECTION_NAME = "sectionName"
    }

    private var _binding: FragmentTablesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TablesViewModel by viewModels()

    private var tracker: SelectionTracker<Long>? = null
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sectionId = requireArguments().getString(ARG_SECTION_ID)!!
        viewModel.setSectionId(sectionId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTablesBinding.bind(view)

        initRecyclerAdapter(savedInstanceState)
        initObservers()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        closeActionMode()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerAdapter(savedInstanceState: Bundle?) {
        binding.rvTables.apply {
            addItemDecoration(SpacingItemDecoration(3, 15))

            val tableAdapter = TablesAdapter(onClick = { table -> goToTakeOrderActivity(table) })
            adapter = tableAdapter

            tracker = SelectionTracker.Builder<Long>(
                "mySelection",
                this@apply,
                StableIdKeyProvider(this@apply),
                ItemLookup(this@apply),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()

            savedInstanceState?.let {
                tracker?.onRestoreInstanceState(it)
            }

            tableAdapter.tracker = tracker
        }
    }

    private fun initObservers() {
        viewModel.tables.observe(viewLifecycleOwner) {
            val adapter = binding.rvTables.adapter as TablesAdapter
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                checkStartOrUpdateActionMode()
            }
        })
    }

    private fun checkStartOrUpdateActionMode() {
        fun startActionMode() {
            val activity = requireActivity() as AppCompatActivity
            val actionModeCallback = ActionModeCallback(activity, onDestroy = {
                    tracker?.clearSelection()
                }, onFinishOrders = {
                    val tables = tracker?.selection?.map { id ->
                        (binding.rvTables.adapter as TablesAdapter).currentList[id.toInt()]
                    } ?: emptyList()

                    viewModel.setTablesFinished(tables)
                }
            )
            actionMode = activity.startSupportActionMode(actionModeCallback)
        }

        val isFirstTime = tracker?.hasSelection() == true && actionMode == null
        if (isFirstTime) {
            startActionMode()
        } else if (tracker?.hasSelection() == false) {
            closeActionMode()
        }

        actionMode?.title = getString(R.string.num_selected, tracker?.selection?.size().toString())
    }

    private fun closeActionMode() {
        actionMode?.finish()
        actionMode = null
    }

    private fun goToTakeOrderActivity(table: Table) {
        val action = SectionsTabFragmentDirections.actionSectionTabsFragmentToTakeOrderActivity(
            requireArguments().getString(ARG_SECTION_NAME)!!, table
        )
        findNavController().navigate(action)
    }
}