package com.example.cryptoapp.ui.detail.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.cryptoapp.ui.decorations.BaseVerticalDividerItemDecoration
import com.example.cryptoapp.ui.detail.RefreshTimeAdapter
import com.example.cryptoapp.utils.DeviceUtils
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.DialogRefreshTimeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val SHEET_RATIO = 0.5F

class RefreshTimeBottomSheetDialog(val onDismissed: () -> Unit, val onValueGet: (String) -> Unit) :
    BottomSheetDialogFragment() {
    private var _binding: DialogRefreshTimeBinding? = null
    val binding get() = _binding!!
    private val refreshTimeAdapter: RefreshTimeAdapter by lazy {
        RefreshTimeAdapter(this@RefreshTimeBottomSheetDialog::getValue)
    }

    var value: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogRefreshTimeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRefreshTime.adapter = refreshTimeAdapter
        binding.rvRefreshTime.addItemDecoration(
            BaseVerticalDividerItemDecoration(
                requireContext(),
                paddingInResId = R.dimen.margin_10,
                paddingOutResId = R.dimen.margin_20
            )
        )
        refreshTimeAdapter.submitList((1..60).map { it.toString() })
        binding.btnChoose.setOnClickListener {
            onValueGet.invoke(value)
            dismiss()
        }
        binding.clRefreshTime.let { sheetView ->
            SHEET_RATIO.let { ratio ->
                val clParams = sheetView.layoutParams
                clParams.height = (DeviceUtils.getDeviceHeight(requireActivity()) * ratio).toInt()
                sheetView.layoutParams = clParams
            }
        }
    }

    private fun getValue(text: String) {
        value = text
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            ((it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).let { bottomSheet ->
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissed.invoke()
    }
}