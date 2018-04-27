package com.example.iclu.bilbioapp.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.example.iclu.biblioapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception


class LoginActivityKotlin : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        private val TAG = "LoginActivityKotlin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseAuth.getInstance()

        etPassword.inputType = TYPE_TEXT_VARIATION_PASSWORD
        etPassword.transformationMethod = PasswordTransformationMethod()

        //TODO verify that view synthetic can have array
        arrayOf(btnLogin, btnRegister, btnTest).forEach {
            it.setOnClickListener(this)
        }
    }

    private fun startLogin() {
        if (etEmail.text.toString().isBlank() || etPassword.text.toString().isBlank()) {
            Toast.makeText(this, R.string.login_error_regex, Toast.LENGTH_SHORT).show()
        } else {
            logUser()
        }
    }

    private fun startRegister() {
        if (etEmail.text.toString().isBlank() || etPassword.text.toString().isBlank()) {
            displayToast(R.string.login_error_empty_spaces)
        } else if (!etEmail.text.toString().matches(Regex(EMAIL_REGEX))) {
            displayToast(R.string.login_error_regex)
        } else {
            registerUser()
        }
    }

    private fun startTest() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun logUser() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(this, getCompleteListener())
    }

    private fun registerUser() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(this, getCompleteListener())
    }

    private fun getCompleteListener(): OnCompleteListener<AuthResult> {
        return OnCompleteListener {
            if (it.isSuccessful) {
                proceedToMainMenu()
            } else {
                treatExceptions(it.exception)
            }
        }
    }

    private fun proceedToMainMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun treatExceptions(exception: Exception?) {
        val stringId = when (exception) {
            is FirebaseAuthWeakPasswordException -> R.string.fb_weak_password
            is FirebaseAuthInvalidCredentialsException -> R.string.fb_invalid_credentials
            is FirebaseAuthUserCollisionException -> R.string.fb_collisions_exception
            is FirebaseAuthInvalidUserException -> R.string.fb_invalid_user
            else -> 0
        }
        displayToast(stringId)
    }

    private fun displayToast(stringId: Int) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show()
    }

    //TODO Regex??
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnRegister -> startRegister()
            R.id.btnLogin -> startLogin()
            R.id.btnTest -> startTest()
        }
    }
}