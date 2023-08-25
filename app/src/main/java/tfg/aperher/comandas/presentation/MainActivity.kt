package tfg.aperher.comandas.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MenuProvider {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val navController: NavController by lazy {
        binding.navHostFragment.getFragment<NavHostFragment>().navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActionBar()
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(binding.drawerLayout) || super.onSupportNavigateUp()

    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, binding.drawerLayout)
        binding.navigationView.setupWithNavController(navController)

        addMenuProvider(this@MainActivity)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.top_app_bar, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.help -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.help_guide))
                    .setView(R.layout.dialog_help)
                    .show()
                true
            }
            else -> false
        }
    }
}
