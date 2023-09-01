package tfg.aperher.comandas.presentation.orders.readyarticles

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentReadyArticlesBinding
import tfg.aperher.comandas.utils.viewLifecycleScopeLaunch

@AndroidEntryPoint
class ReadyArticlesFragment : Fragment(R.layout.fragment_ready_articles), MenuProvider {
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.top_app_bar, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.help -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.help_guide))
                    .setMessage(getString(R.string.ready_articles_help))
                    .show()
                true
            }
            else -> false
        }
    }

    private fun initUI() {
        initAdapter()
        initObservers()
        initMenuProvider()
    }

    private fun initAdapter() {
        binding.rvReadyArticles.adapter = ReadyArticlesAdapter()
        val swipeHelper = ItemTouchHelper(SwipeToServeHelperCallback(requireContext(),
            onSwipe = { position ->
                viewModel.setServedArticleAtPosition(position)
            }
        ))
        swipeHelper.attachToRecyclerView(binding.rvReadyArticles)
    }

    private fun initObservers() {
        viewLifecycleScopeLaunch(Lifecycle.State.STARTED) {
            viewModel.readyArticles.collectLatest { readyArticlesList ->
                (binding.rvReadyArticles.adapter as ReadyArticlesAdapter).submitList(
                    readyArticlesList
                )
            }
        }
    }

    private fun initMenuProvider() {
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.STARTED)
    }
}
