package tfg.aperher.comandas.presentation

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.ActivityLoginBinding
import tfg.aperher.comandas.utils.dismissKeyboard

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.goToMain.observe(this) {
            it.getContentIfNotHandled()?.let { goToMain ->
                if (goToMain) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }

        viewModel.exception.observe(this) {
            it.getContentIfNotHandled()?.let { error ->
                binding.tilPassword.error = getString(R.string.can_not_sign_in)
            }
        }
    }

    private fun initListeners() {
        binding.etEmail.setOnEditorActionListener { et, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                et.clearFocus()
                true
            } else false
        }
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.etPassword.setOnEditorActionListener { et, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                et.dismissKeyboard()
                true
            } else false
        }
        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }

        binding.btnSignIn.setOnClickListener {
            viewModel.onLoginClicked()
        }
    }
}