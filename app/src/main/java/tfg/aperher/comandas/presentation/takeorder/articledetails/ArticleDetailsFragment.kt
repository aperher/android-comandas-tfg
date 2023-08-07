package tfg.aperher.comandas.presentation.takeorder.articledetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentArticleDetailsBinding
import tfg.aperher.comandas.domain.model.Article
import tfg.aperher.comandas.domain.model.ArticleInOrder

class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {
    interface ArticleAddedListener {
        fun onAddArticleToOrder(article: ArticleInOrder)
    }

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ArticleDetailsViewModel>()
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private var listener: ArticleAddedListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleDetailsBinding.bind(view)

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        loadArticle()
        initListeners()
        initObservers()
    }

    private fun loadArticle() {
        viewModel.setArticle(args.article)
    }

    private fun initListeners() {
        binding.btnAddArticle.setOnClickListener {
            viewModel.addArticle()
        }

        binding.btnRemoveArticle.setOnClickListener {
            viewModel.removeArticle()
        }

        binding.etObservations.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.setObservations(binding.etObservations.editText?.text.toString())
            }
        }

        binding.fabAddArticle.setOnClickListener {
            viewModel.addArticleToOrder()
        }
    }

    private fun initObservers() {
        viewModel.article.observe(viewLifecycleOwner) {
            setArticleDetailsInUI(it)
        }

        viewModel.quantity.observe(viewLifecycleOwner) {
            binding.tvQuantity.text = it.toString()
        }

        viewModel.observations.observe(viewLifecycleOwner) {
            binding.etObservations.editText?.setText(it)
        }

        viewModel.isRemoveButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnRemoveArticle.isEnabled = it
        }

        viewModel.isAddButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnAddArticle.isEnabled = it
        }

        viewModel.addArticleToOrder.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { article ->
                listener?.onAddArticleToOrder(article)
            }
        }
    }

    private fun setArticleDetailsInUI(article: Article) {
        binding.tvArticleDescription.text = article.description
        binding.tvPriceArticle.text = getString(R.string.price, article.price.toString())

        Glide.with(requireContext()).load(article.image).transform(
            CenterCrop(),
            RoundedCorners(24)
        ).into(binding.ivArticle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ArticleAddedListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}