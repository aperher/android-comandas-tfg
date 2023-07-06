package tfg.aperher.comandas.ui.takeorder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.databinding.ActivityTakeOrderBinding
import tfg.aperher.comandas.model.Order
import tfg.aperher.comandas.ui.orders.EXTRA_TABLE_ID

@AndroidEntryPoint
class TakeOrderActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context) = Intent(context, TakeOrderActivity::class.java)
    }

    private lateinit var binding: ActivityTakeOrderBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tableId = intent.extras?.getInt(EXTRA_TABLE_ID)

        setSupportActionBar(binding.tbTakeOrder)

        navController =
            binding.navHostFragmentTakeOrder.getFragment<NavHostFragment>().navController

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.orderBottomSheet).apply {
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.previewBottomSheet.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        val orderAdapter = OrderAdapter()
        orderAdapter.submitList(
            listOf(
                Order.Article("1", "Coca Cola", 2.5, 1),
                Order.Article("1", "Coca Cola", 2.5, 1),
                Order.Article("1", "Coca Cola", 2.5, 1),
                Order.Article("1", "Coca Cola", 2.5, 1)
            )
        )
        binding.rvOrderItems.adapter = orderAdapter
    }

    override fun onSupportNavigateUp(): Boolean = navigateUp(
        navController,
        AppBarConfiguration(navController.graph)
    ) || super.onSupportNavigateUp()


    override fun onDestroy() {
        super.onDestroy()
    }
}