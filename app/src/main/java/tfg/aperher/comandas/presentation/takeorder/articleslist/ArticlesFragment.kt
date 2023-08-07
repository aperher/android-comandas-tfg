package tfg.aperher.comandas.presentation.takeorder.articleslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentArticlesListBinding
import tfg.aperher.comandas.domain.model.Article

@AndroidEntryPoint
class ArticlesFragment : Fragment(R.layout.fragment_articles_list) {

    private var _binding: FragmentArticlesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ArticlesViewModel>()
    private val args: ArticlesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticlesListBinding.bind(view)

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        loadArticles()
        initRecyclerAdapter()
        initObservers()
    }

    private fun loadArticles() {
        viewModel.getArticles(args.categoryId)
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
        val action = ArticlesFragmentDirections.actionArticlesFragmentToArticleDetailsFragment(
            article,
            article.name
        )
        findNavController().navigate(action)
    }
}