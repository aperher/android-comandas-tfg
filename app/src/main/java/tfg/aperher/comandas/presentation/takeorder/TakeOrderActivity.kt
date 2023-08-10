package tfg.aperher.comandas.presentation.takeorder

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ActivityTakeOrderBinding
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.usecases.OrderError
import tfg.aperher.comandas.presentation.takeorder.articledetails.ArticleDetailsFragment.ArticleAddedListener

@AndroidEntryPoint
class TakeOrderActivity : AppCompatActivity(), MenuProvider, ArticleAddedListener {
    private lateinit var binding: ActivityTakeOrderBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<TakeOrderViewModel>()
    private val args: TakeOrderActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            binding.navHostFragmentTakeOrder.getFragment<NavHostFragment>().navController

        initUI()
    }

    private fun initUI() {
        loadOrder()
        initOnBackPressed()
        initActionBar()
        initBottomSheet()
        initObservers()
    }

    private fun loadOrder() {
        viewModel.initOrder(args.sectionName, args.table)
    }

    private fun initActionBar() {
        setSupportActionBar(binding.tbTakeOrder)
        setupActionBarWithNavController(navController, AppBarConfiguration(setOf()))

        addMenuProvider(this@TakeOrderActivity)
    }

    private fun initBottomSheet() {
        binding.previewBottomSheet.setOnClickListener {
            viewModel.showBottomSheet(true)
        }

        binding.rvOrderItems.adapter = OrderAdapter { articlePosition, quantity ->
            viewModel.updateArticle(articlePosition, quantity)
        }
    }

    private fun initObservers() {
        viewModel.articlesInOrder.observe(this) {
            (binding.rvOrderItems.adapter as OrderAdapter).submitList(it)
        }

        viewModel.totalPrice.observe(this) {
            binding.tvOrderPrice.text = getString(R.string.price, it.toString())
        }

        viewModel.articleNumber.observe(this) {
            binding.tvArticleNumber.text = getString(R.string.number_articles, it.toString())
        }

        viewModel.invalidateElementList.observe(this) {
            (binding.rvOrderItems.adapter as OrderAdapter).notifyItemChanged(it.getContentIfNotHandled()!!)
        }

        viewModel.navigateToOrderActivity.observe(this) {
            finish()
        }

        viewModel.orderError.observe(this) {
            it.getContentIfNotHandled()?.let { error ->
                informError(error)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.viewState.collect { viewState ->
                    updateUI(viewState)
                }
            }
        }
    }

    private fun updateUI(viewState: TakeOrderViewState) {
        binding.tvSection.text = viewState.sectionName
        binding.tvTableNumber.text = viewState.tableNumber.toString()

        viewState.showBottomSheet.getContentIfNotHandled()?.let { shouldHide ->
            val bottomSheetBehavior = BottomSheetBehavior.from(binding.orderBottomSheet)
            bottomSheetBehavior.state = when (shouldHide) {
                true -> BottomSheetBehavior.STATE_EXPANDED
                false -> BottomSheetBehavior.STATE_HIDDEN
            }
        }

        viewState.progressBarLoading.let {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun informError(error: OrderError) {
        when (error) {
            OrderError.EmptyOrderError -> MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.empty_order))
                .setMessage(getString(R.string.empty_order_msg))
                .setPositiveButton(R.string.accept) { _, _ -> }.show()

            OrderError.OrderNotSavedError -> MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.changes_not_saved))
                .setMessage(getString(R.string.changes_not_saved_msg))
                .setPositiveButton(getString(R.string.send)) { _, _ -> viewModel.sendOrder() }
                .setNegativeButton(R.string.cancel) { _, _ -> finish() }
                .show()

            OrderError.SameOrderError -> MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.same_order))
                .setMessage(getString(R.string.same_order_msg))
                .setPositiveButton(R.string.accept) { _, _ -> }.show()

            OrderError.SendOrderError -> {
                viewModel.showBottomSheet(false)
                Snackbar.make(
                    binding.orderBottomSheet,
                    getString(R.string.sent_error),
                    Snackbar.LENGTH_SHORT
                ).setAnchorView(binding.previewBottomSheet)
                .setAction(getString(R.string.retry)) { viewModel.sendOrder() }.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean = goBack()

    private fun initOnBackPressed() {
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bottomSheetBehavior = BottomSheetBehavior.from(binding.orderBottomSheet)
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
                    viewModel.showBottomSheet(false)
                } else {
                    goBack()
                }
            }
        })
    }

    private fun goBack(): Boolean {
        if (navController.currentDestination?.id == R.id.menuFragment) {
            viewModel.checkOrderSaved()
        } else {
            navController.popBackStack()
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.top_app_bar_take_order, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.share -> true
        R.id.print -> true
        R.id.send -> {
            viewModel.sendOrder(); true
        }

        else -> false
    }

    override fun onAddArticleToOrder(article: ArticleInOrder) {
        viewModel.addArticleToOrder(article)
    }
}
