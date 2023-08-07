package tfg.aperher.comandas.presentation.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentOrdersBinding

@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private var _navController : NavController? = null
    private val navController get() = _navController!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOrdersBinding.bind(view)

        _navController = binding.navHostFragmentOrders.getFragment<NavHostFragment>().navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _navController = null
    }
}