package tfg.aperher.comandas.presentation.takeorder.menumain

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentMenuBinding

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMenuBinding.bind(view)

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        initRecyclerAdapter()
        initListeners()
        initObservers()
    }

    private fun initRecyclerAdapter() {
        binding.rvCategories.adapter = CategoryAdapter { id, name ->
            goToArticlesList(id, name)
        }
    }

    private fun initListeners() {
        binding.cardSuggestions.setOnClickListener {
            goToSuggestions()
        }
    }

    private fun initObservers() {
        viewModel.categories.observe(viewLifecycleOwner) {
            (binding.rvCategories.adapter as CategoryAdapter).submitList(it)
        }
    }

    private fun goToArticlesList(id: String, name: String) {
        val action = MenuFragmentDirections.actionMenuFragmentToArticlesFragment(id, name)
        findNavController().navigate(action)
    }

    private fun goToSuggestions() {
        val action = MenuFragmentDirections.actionMenuFragmentToSuggestionsFragment()
        findNavController().navigate(action)
    }
}