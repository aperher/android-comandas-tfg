package tfg.aperher.comandas.presentation.orders.recordsorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentRecordsOrdersBinding
import tfg.aperher.comandas.domain.model.User
import tfg.aperher.comandas.presentation.orders.OrdersFragmentDirections

@AndroidEntryPoint
class RecordOrdersFragment : Fragment(R.layout.fragment_records_orders) {

    private var _binding: FragmentRecordsOrdersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecordOrdersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecordsOrdersBinding.bind(view)

        initUI()
    }

    override fun onStart() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.record_order_title)
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        initRecyclerAdapter()
        initListeners()
        initObservers()
    }

    private fun initRecyclerAdapter() {
        binding.rvRecordsOrders.adapter = OrdersAdapter { orderId ->
            goToOrderDetails(orderId)
        }
    }

    private fun initListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.chipWaiter.setOnClickListener {
            openBottomSheetDialog()
        }

        binding.chipDate.setOnClickListener {
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
                .apply {
                    addOnPositiveButtonClickListener { date ->
                        viewModel.onDateSelected(date)
                    }
                }
                .show(requireActivity().supportFragmentManager, "datePicker")
        }
    }

    private fun initObservers() {
        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            (binding.rvRecordsOrders.adapter as OrdersAdapter).submitList(orders)
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            binding.swipeRefreshLayout.isRefreshing = isRefreshing
        }

        viewModel.filterWaiter.observe(viewLifecycleOwner) { waiter ->
            val isOneSelected = waiter != null
            binding.chipWaiter.apply {
                text = waiter?.name ?: getString(R.string.waiter)
                isCheckable = isOneSelected
                isChecked = isOneSelected
                isCheckable = false
            }
        }

        viewModel.filterDate.observe(viewLifecycleOwner) { date ->
            val isOneSelected = date != null
            binding.chipDate.apply {
                text = date ?: getString(R.string.date)
                isCheckable = isOneSelected
                isChecked = isOneSelected
                isCheckable = false
            }
        }
    }

    private fun goToOrderDetails(orderId: String) {
        val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(orderId)
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(action)
    }

    private fun openBottomSheetDialog() {
        val bottomSheetFragment = WaitersBottomSheet()
        bottomSheetFragment.setOnWaiterSelectedListener(
            object : WaitersBottomSheet.OnWaiterSelectedListener {
                override fun onWaiterSelected(waiter: User) {
                    viewModel.onWaiterSelected(waiter)
                }
            }
        )
        bottomSheetFragment.show(requireActivity().supportFragmentManager, bottomSheetFragment.tag)
    }
}