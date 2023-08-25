package tfg.aperher.comandas.presentation.orders.recordsorders.orderdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentOrderDetailsBinding

@AndroidEntryPoint
class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderDetailsViewModel by viewModels()
    private val args: OrderDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderDetailsBinding.bind(view)

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        loadOrderDetails()
        initObservers()
    }

    private fun loadOrderDetails() {
        val orderId = args.orderId
        viewModel.getOrder(orderId)
    }

    private fun initObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.shimmerCardLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.normalCard.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        }

        viewModel.order.observe(viewLifecycleOwner) { order ->
            binding.tvSection.text = context?.getString(R.string.show_section, order.section)
            binding.tvTableNumber.text = context?.getString(R.string.table, order.table.toString())
            binding.tvDate.text = context?.getString(R.string.show_date, order.day)
            binding.tvInitHour.text = context?.getString(R.string.init_hour, order.initTime)
            binding.tvEndHour.text = context?.getString(R.string.end_hour, order.endTime)

            binding.rvArticles.adapter = ArticlesAdapter(order.articles)
        }
    }
}