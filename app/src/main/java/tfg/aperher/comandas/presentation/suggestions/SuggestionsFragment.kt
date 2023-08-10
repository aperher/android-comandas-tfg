package tfg.aperher.comandas.presentation.suggestions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentSuggestionsBinding
import tfg.aperher.comandas.domain.model.Article
import tfg.aperher.comandas.presentation.takeorder.TakeOrderActivity

@AndroidEntryPoint
class SuggestionsFragment : Fragment(R.layout.fragment_suggestions) {
    private var _binding: FragmentSuggestionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SuggestionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuggestionsBinding.bind(view)

        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        viewModel.popularDishes.observe(viewLifecycleOwner) { articles ->
            binding.rvMostOrderedDishes.adapter = SuggestionsAdapter(articles) {
                goToArticleDetail(it)
            }
        }

        viewModel.prominentDishes.observe(viewLifecycleOwner) { articles ->
            binding.rvProminentDishes.adapter = SuggestionsAdapter(articles) {
                goToArticleDetail(it)
            }
        }
    }

    private fun goToArticleDetail(article: Article) {
        if (activity is TakeOrderActivity) {
            val action = SuggestionsFragmentDirections.actionSuggestionsFragmentToArticleDetailsFragment(
                article,
                article.name // This is the title of the article in action bar
            )
            findNavController().navigate(action)
        }
    }
}