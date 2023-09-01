package tfg.aperher.comandas.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope

fun Fragment.viewLifecycleScopeLaunch(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend () -> Unit
): Job = viewLifecycleScope.launch {
    repeatOnLifecycle(lifecycleState) {
        block()
    }
}
