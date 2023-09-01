package tfg.aperher.comandas.presentation.orders.recordsorders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import tfg.aperher.comandas.R
import tfg.aperher.comandas.databinding.FragmentRecordsBottomSheetFilterBinding
import tfg.aperher.comandas.domain.model.User

@AndroidEntryPoint
class WaitersBottomSheet : BottomSheetDialogFragment(R.layout.fragment_records_bottom_sheet_filter) {

    interface OnWaiterSelectedListener {
        fun onWaiterSelected(waiter: User)
    }

    private var _binding: FragmentRecordsBottomSheetFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WaitersBottomSheetViewModel by viewModels()
    private var listener: OnWaiterSelectedListener? = null

    fun setOnWaiterSelectedListener(l: OnWaiterSelectedListener) {
        listener = l
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecordsBottomSheetFilterBinding.bind(view)

        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listener = null
    }

    private fun initUI() {
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun initObservers() {
        viewModel.waiters.observe(viewLifecycleOwner) { waiters ->
            binding.rvBottomSheet.adapter = WaitersBottomSheetAdapter(waiters) { waiter ->
                listener?.onWaiterSelected(waiter)
                dismiss()
            }
        }
    }
}
