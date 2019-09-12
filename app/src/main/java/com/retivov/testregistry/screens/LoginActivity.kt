package com.retivov.testregistry.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.retivov.testregistry.R
import com.retivov.testregistry.extensions.*
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initToolbar()
        initViews()
        initSubscriptions()
    }

    private fun initViews() {

        etPassword.onTouchRightDrawable {
            viewModel.onPasswordHelpClicked()
        }

        etPassword.onActionDone {
            login()
        }

        btSignIn.setOnClickListener {
            login()
        }

    }

    private fun initSubscriptions() {
        viewModel.weatherLiveData.observeSafe(this) { weather ->
            etPassword.hideKeyboard()
            etEmail.hideKeyboard()
            Snackbar.make(root, getString(R.string.weather,weather), Snackbar.LENGTH_LONG).show()
        }

        viewModel.toastLiveData.observeSafe(this) { text ->
            Toast.makeText(this, getString(text), Toast.LENGTH_LONG).show()
        }

        viewModel.progressLiveData.observeSafe(this) {
            progressLogin.setVisibility(it)
        }
    }

    private fun login() {
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
                viewModel.onCreateClicked()
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
