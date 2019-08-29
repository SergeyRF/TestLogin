package com.retivov.testregistry.screens

import androidx.lifecycle.ViewModel
import com.retivov.testregistry.R
import com.retivov.testregistry.utils.SingleLiveEvent
import com.retivov.testregistry.weather.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.regex.Pattern

class RegistryViewModel(private val weatherApi: WeatherApi) : ViewModel() {

    val weatherLiveData = SingleLiveEvent<String>()
    val toastLiveData = SingleLiveEvent<Int>()

    private val patternPassword = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&\\s\\w*]{6,}")

    private val disposable = SerialDisposable()


    private fun getWeather() {

        weatherApi.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weather ->
                    weatherLiveData.value = "${weather.main.temp}F"
                }, { Timber.e(it) })
                .apply { disposable.set(this) }
    }

    fun clickCreate() {
        toastLiveData.value = R.string.new_funtion
    }

    fun getPasswordHelp() {
        toastLiveData.value = R.string.password_settings
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return patternPassword.matcher(password).matches()
    }

    fun signIn(email: String, password: String) {
        if (!isEmailValid(email)) {
            toastLiveData.value = R.string.email_invalid
            return
        } else {
            if (!isPasswordValid(password)) {
                toastLiveData.value = R.string.password_settings
                return
            }
        }
        getWeather()

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}