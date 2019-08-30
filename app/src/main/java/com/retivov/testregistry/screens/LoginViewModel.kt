package com.retivov.testregistry.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.retivov.testregistry.R
import com.retivov.testregistry.extensions.into
import com.retivov.testregistry.utils.SingleLiveEvent
import com.retivov.testregistry.weather.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.regex.Pattern

class LoginViewModel(private val weatherApi: WeatherApi) : ViewModel() {

    val weatherLiveData = SingleLiveEvent<String>()
    val toastLiveData = SingleLiveEvent<Int>()
    val progressLiveData = MutableLiveData<Boolean>()

    private val patternPassword = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&\\s\\w*]{6,}")

    private val disposable = SerialDisposable()


    private fun getWeather() {

        weatherApi.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressLiveData.value = true }
                .doFinally { progressLiveData.value = false }
                .subscribe({ weather ->
                    weatherLiveData.value = "${weather.main.temp}"
                }, {
                    Timber.e(it)
                    toastLiveData.value = R.string.internet_error
                }) into disposable
    }


    fun onCreateClicked() {
        toastLiveData.value = R.string.new_funtion
    }

    fun onPasswordHelpClicked() {
        toastLiveData.value = R.string.password_settings
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return patternPassword.matcher(password).matches()
    }

    fun signIn(email: String, password: String) {
        when {
            !isEmailValid(email) -> {
                toastLiveData.value = R.string.email_invalid
            }
            !isPasswordValid(password) -> {
                toastLiveData.value = R.string.password_settings
            }
            else -> {
                getWeather()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}