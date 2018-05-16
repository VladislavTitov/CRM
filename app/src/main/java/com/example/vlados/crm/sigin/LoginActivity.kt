package com.example.vlados.crm.sigin

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.CASHIER
import com.example.vlados.crm.R
import com.example.vlados.crm.ui.NavigationActivity
import com.example.vlados.crm.utils.afterTextChanged
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : MvpAppCompatActivity(), LoginInterface{

    @InjectPresenter
    lateinit var presenter : LoginPresenter

    private var loginText : String = ""
    private var passwordText : String = ""

    private val loadingKey = "loading_key"

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val currentUser = getCurrentUser()
        if (currentUser != null) {
            goToMainActivity(currentUser.role ?: CASHIER)
        }

        // Set up the login form.
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }

        password.afterTextChanged {
            passwordText = it
            if (isPasswordValid(it)) {
                password.error = null
            } else {
                password.error = "Password is invalid"
            }
        }

        email.afterTextChanged {
            loginText = it
            if (isEmailValid(it)) {
                email.error = null
            } else {
                email.error = "Login is invalid"
            }
        }

        savedInstanceState?.getBoolean(loadingKey, false)?.let { showProgress(it) }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(
                    Manifest.permission.INTERNET
            ), 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(loadingKey, login_progress.visibility == View.VISIBLE)
        super.onSaveInstanceState(outState)
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        if (email.error != null || password.error != null) {
            return
        }

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordText)) {
            password.error = getString(R.string.non_specified_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(loginText)) {
            email.error = getString(R.string.non_specified_login)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            presenter.signIn(loginText, passwordText)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return true
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return true
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    override fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })

    }

    override fun goToMainActivity(role: String) {
        startActivity(NavigationActivity(role))
        finish()
    }

    override fun showMessage(text: String) {
//        Snackbar.make(login_layout, text, Snackbar.LENGTH_LONG).show()
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

}
