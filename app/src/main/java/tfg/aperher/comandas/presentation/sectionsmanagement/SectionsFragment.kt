package tfg.aperher.comandas.presentation.sectionsmanagement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentSectionsBinding

@AndroidEntryPoint
class SectionsFragment : Fragment(R.layout.fragment_sections) {

    private var _binding: FragmentSectionsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSectionsBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}