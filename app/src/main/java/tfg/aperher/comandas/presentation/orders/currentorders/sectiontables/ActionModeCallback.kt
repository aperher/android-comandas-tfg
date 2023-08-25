package tfg.aperher.comandas.presentation.orders.currentorders.sectiontables

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import tfg.aperher.comandas.R

class ActionModeCallback(
    private val activity: AppCompatActivity,
    private val onDestroy: () -> Unit,
    private val onFinishOrders: () -> Unit
) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        with(activity) {
            val color = ContextCompat.getColor(this, androidx.appcompat.R.color.material_grey_900)
            activity.window.statusBarColor = MaterialColors.layer(color, color, 0.85f)
            menuInflater.inflate(R.menu.contextual_action_bar, menu)
        }
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.finish_orders -> {
                onFinishOrders()
                onDestroyActionMode(mode)
                true
            }

            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        onDestroy()
        activity.window.statusBarColor = android.graphics.Color.TRANSPARENT
    }
}