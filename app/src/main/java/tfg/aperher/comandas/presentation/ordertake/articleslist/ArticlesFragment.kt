package tfg.aperher.comandas.presentation.ordertake.articleslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentArticlesBinding
import tfg.aperher.comandas.domain.model.Article
import tfg.aperher.comandas.presentation.ordertake.articledetails.ArticleDetailsFragment.Companion.ARTICLE

@AndroidEntryPoint
class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ArticlesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticlesBinding.bind(view)

        arguments?.getString(CATEGORY_ID)?.let {
            viewModel.getArticles(it)
        }

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        getArticles()
        initRecyclerAdapter()
        initObservers()
    }

    private fun getArticles() {
        arguments?.apply {
            getString(CATEGORY_ID)?.let {
                viewModel.getArticles(it)
            }
            getString(CATEGORY_NAME)?.let {
                requireActivity().title = it
            }
        }
    }

    private fun initRecyclerAdapter() {
        binding.rvArticles.adapter = ArticleAdapter { article ->
            goToArticleDetail(article)
        }
    }

    private fun initObservers() {
        viewModel.articles.observe(viewLifecycleOwner) {
            (binding.rvArticles.adapter as ArticleAdapter).submitList(it)
        }
    }

    private fun goToArticleDetail(article: Article) {
        findNavController().navigate(
            R.id.action_articlesFragment_to_articleDetailsFragment,
            Bundle().apply { putParcelable(ARTICLE, article) }
        )
    }
}