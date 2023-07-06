package tfg.aperher.comandas.ui.takeorder.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentArticlesBinding

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticlesBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}