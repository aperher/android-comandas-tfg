package tfg.aperher.comandas.presentation.orders.readyarticles

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentReadyArticlesBinding

@AndroidEntryPoint
class ReadyArticlesFragment : Fragment(R.layout.fragment_ready_articles) {

    private var _binding: FragmentReadyArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReadyArticlesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReadyArticlesBinding.bind(view)

        initUI()
    }

    override fun onStart() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.ready)
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.rvReadyArticles.adapter = ReadyArticlesAdapter()

        val swipeHelper = ItemTouchHelper(SwipeToServeHelperCallback{
            viewModel.setServedArticleAtPosition(it.adapterPosition)
        })

        swipeHelper.attachToRecyclerView(binding.rvReadyArticles)
    }

    private fun initObservers() {
        /*viewModel.readyArticles.observe(viewLifecycleOwner) { readyArticlesList ->
            (binding.rvReadyArticles.adapter as ReadyArticlesAdapter).submitList(readyArticlesList)
        }*/

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.readyArticles.collectLatest { readyArticlesList ->
                    (binding.rvReadyArticles.adapter as ReadyArticlesAdapter).submitList(readyArticlesList)
                }
            }
        }
    }
}