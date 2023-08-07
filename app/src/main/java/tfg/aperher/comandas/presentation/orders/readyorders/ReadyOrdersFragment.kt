package tfg.aperher.comandas.presentation.orders.readyorders

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentReadyOrdersBinding

@AndroidEntryPoint
class ReadyOrdersFragment : Fragment(R.layout.fragment_ready_orders) {

    private var _binding: FragmentReadyOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReadyOrdersBinding.bind(view)
    }

    override fun onStart() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.ready)
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}