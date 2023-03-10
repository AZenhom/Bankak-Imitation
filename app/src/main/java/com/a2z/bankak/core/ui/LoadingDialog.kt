package com.a2z.bankak.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.a2z.bankak.databinding.DialogLoadingBinding

class LoaderDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "LoaderDialogFragment"
    }

    private var _binding: DialogLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLoadingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        isCancelable = false
    }

/*    override fun onResume() {
        //binding.spinKit.setIndeterminateDrawable(getAnimation())
        super.onResume()
    }

    private fun getAnimation(): Sprite {
        val randomNumber = Random.nextInt(0, animationsList.size)
        return animationsList[randomNumber]
    }*/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}