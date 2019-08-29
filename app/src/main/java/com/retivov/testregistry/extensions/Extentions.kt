package com.retivov.testregistry.extensions

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.retivov.testregistry.screens.RegistryActivity

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.onActionDone(block: () -> Unit) {
    onAction(EditorInfo.IME_ACTION_DONE, block)
}

fun EditText.onActionNext(block: () -> Unit) {
    onAction(EditorInfo.IME_ACTION_NEXT, block)
}

fun EditText.onTouchRightDrawable(block: () -> Unit) {
    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (right - compoundDrawables[2].bounds.width())) {
                block.invoke()
                return@setOnTouchListener true
            }
        }
        false
    }
}

fun EditText.onAction(action: Int, block: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener when (actionId) {
            action -> {
                block.invoke()
               false
            }
            else -> false
        }
    }
}