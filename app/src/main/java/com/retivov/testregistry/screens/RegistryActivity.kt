package com.retivov.testregistry.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.retivov.testregistry.R
import com.retivov.testregistry.extensions.*
import kotlinx.android.synthetic.main.activity_registry.*
import org.koin.android.ext.android.inject

class RegistryActivity : AppCompatActivity() {

    private val viewModel: RegistryViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)

        initToolbar()
        initViews()
        initSubscriptions()
    }

    private fun initViews() {

        etPassword.onTouchRightDrawable {
            viewModel.getPasswordHelp()
        }

        etPassword.onActionDone {
            signIn()
        }

        btSignIn.setOnClickListener {
            signIn()
        }

    }

    private fun initSubscriptions() {
        viewModel.weatherLiveData.observeSafe(this) { weather ->
            etPassword.hideKeyboard()
            etEmail.hideKeyboard()
            Snackbar.make(btSignIn, weather, Snackbar.LENGTH_LONG).show()
        }

        viewModel.toastLiveData.observeSafe(this) { text ->
            Toast.makeText(this, getString(text), Toast.LENGTH_LONG).show()
        }
    }

    private fun signIn() {
        viewModel.signIn(etEmail.text.toString(), etPassword.text.toString())
    }


    private fun initToolbar() {
        setSupportActionBar(toolbarRegistry)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.login)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_reg_create -> {
                viewModel.clickCreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_registry, menu)
        return true
    }
}
