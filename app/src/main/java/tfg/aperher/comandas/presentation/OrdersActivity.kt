package tfg.aperher.comandas.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ActivityOrdersBinding

@AndroidEntryPoint
class OrdersActivity : AppCompatActivity(), MenuProvider {

    private lateinit var binding: ActivityOrdersBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        binding.bottomNavigationView.setupWithNavController(navController)

        addMenuProvider(this@OrdersActivity)

        initTabs()
    }

    private fun initTabs() {
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayout.close()
            true
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.top_app_bar, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = menuItem.itemId == R.id.help
}
