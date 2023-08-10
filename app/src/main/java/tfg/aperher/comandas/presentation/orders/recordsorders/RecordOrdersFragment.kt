package tfg.aperher.comandas.presentation.orders.recordsorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentRecordsOrdersBinding
import tfg.aperher.comandas.domain.model.User
import tfg.aperher.comandas.presentation.orders.OrdersFragmentDirections
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

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
            openDatePicker()
        }
    }

    private fun initObservers() {
        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            (binding.rvRecordsOrders.adapter as OrdersAdapter).submitList(orders)
        }

        viewModel.noOrdersFound.observe(viewLifecycleOwner) { noOrdersFound ->
            binding.noData.root.visibility = if (noOrdersFound) View.VISIBLE else View.GONE
        }

        viewModel.isError.observe(viewLifecycleOwner) { isError ->
            binding.error.root.visibility = if (isError) View.VISIBLE else View.GONE
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    updateUI(viewState)
                }
            }
        }
    }

    private fun updateUI(viewState: RecordOrdersViewState) {
        viewState.apply {
            binding.swipeRefreshLayout.isRefreshing = isPullToRefresh
            binding.progressBar.visibility = if (isProgressLoading) View.VISIBLE else View.INVISIBLE

            val textWaiter = waiter?.name ?: getString(R.string.waiter)
            initChipBehaviour(binding.chipWaiter, textWaiter, waiter != null)
            val textDate = dateMillis?.let { formatUTCMillisToDate(it) } ?: getString(R.string.date)
            initChipBehaviour(binding.chipDate, textDate, dateMillis != null)
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

    private fun openDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(
                CalendarConstraints.Builder().setEnd(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            )
            .setNegativeButtonText(getString(R.string.deselect))
            .build()
            .apply {
                addOnPositiveButtonClickListener { date -> viewModel.onDateSelected(date) }
                addOnNegativeButtonClickListener { viewModel.onDateSelected(null) }
            }
            .show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun formatUTCMillisToDate(date: Long): String {
        val instant = Instant.ofEpochMilli(date)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        val locale: Locale = resources.configuration.locales[0]
        val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", locale)
        return formatter.format(dateTime)
    }

    private fun initChipBehaviour(chip: Chip, filter: String, isChecked: Boolean) {
        chip.apply {
            text = filter
            isCheckable = true
            this.isChecked = isChecked
            isCheckable = false
        }
    }
}