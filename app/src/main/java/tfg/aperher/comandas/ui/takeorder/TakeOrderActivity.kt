package tfg.aperher.comandas.ui.takeorder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.databinding.ActivityTakeOrderBinding
import tfg.aperher.comandas.ui.orders.EXTRA_TABLE_ID

@AndroidEntryPoint
class TakeOrderActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context) = Intent(context, TakeOrderActivity::class.java)
    }

    private lateinit var binding: ActivityTakeOrderBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityTakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tableId = intent.extras?.getInt(EXTRA_TABLE_ID)

        Log.d("TakeOrderActivity", "tableId: $tableId")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}