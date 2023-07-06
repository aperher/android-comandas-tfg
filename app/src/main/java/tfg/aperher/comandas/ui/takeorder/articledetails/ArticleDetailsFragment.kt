package tfg.aperher.comandas.ui.takeorder.articledetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentArticleDetailsBinding

class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleDetailsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}