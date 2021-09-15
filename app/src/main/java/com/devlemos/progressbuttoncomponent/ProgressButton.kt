package com.devlemos.progressbuttoncomponent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.devlemos.progressbuttoncomponent.databinding.ProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr){
    private var title: String? = null
    private var loadingTitle: String? = null

    private val binding = ProgressButtonBinding
        .inflate(LayoutInflater.from(context), this, true)

    private var state: ProgressButtonState = ProgressButtonState.Normal
        set(value) {
            field = value
            refreshState()
        }

    init {
        setLayout(attrs)
        refreshState()
    }

    private fun setLayout(attrs: AttributeSet?){
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.ProgressButton
            )

            setBackgroundResource(R.drawable.progress_button_background)

            val titleResId = attributes.getResourceId(R.styleable.ProgressButton_progress_button_title,0)
            if (titleResId != 0){
                title = context.getString(titleResId)
            }

            val loadingtitleResId = attributes.getResourceId(R.styleable.ProgressButton_progress_button_title,0)
            if (loadingtitleResId != 0){
                title = context.getString(titleResId)
            }

            attributes.recycle()
        }

    }

    fun setLoading(){
        state = ProgressButtonState.Loading
    }

    fun setNormal(){
        state = ProgressButtonState.Normal
    }


    private fun refreshState(){
        isEnabled = state.isEnabled
        isClickable = state.isEnabled
        refreshDrawableState()

        binding.textTitle.run {
            isEnabled = state.isEnabled
            isClickable = state.isEnabled
        }

        binding.progressButton.visibility = state.progressVisibility

        when(state){
            ProgressButtonState.Normal -> binding.textTitle.text = title
            ProgressButtonState.Loading -> binding.textTitle.text = loadingTitle
        }
    }

    sealed class ProgressButtonState(val isEnabled: Boolean, val progressVisibility: Int){
        object Normal : ProgressButtonState(true, View.GONE)
        object Loading : ProgressButtonState(false, View.VISIBLE)
    }


}