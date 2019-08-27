package com.retivov.testregistry

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registry.*

class RegistryActivity : AppCompatActivity() {

    companion object {
        const val DRAWABLE_RIGHT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)

        etPassword.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (etPassword.right - etPassword.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    Toast.makeText(this, "Image Click", Toast.LENGTH_LONG).show()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }
}
