package com.example.docemomento.ui.login

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import com.example.docemomento.R
import com.example.docemomento.MainActivity
import android.content.Intent
import com.example.docemomento.registration.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar




class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        var loginBtn = findViewById<Button>(R.id.login)
        var registerBtn = findViewById<Button>(R.id.register)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginBtn.setOnClickListener{view ->
            if (username.text.toString().isBlank() || password.text.toString().isBlank()){
                showMessage(view, "Email ou Senha estão faltando")
            }
            else {
                signIn(view, username.text.toString(), password.text.toString())
                username.text.clear()
                password.text.clear()
            }
        }

        registerBtn.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(view: View, email:String, password: String){
        showMessage(view, "Verificando...")

        fbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if(task.isSuccessful){
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", fbAuth.currentUser?.email)
                startActivity(intent)

            }else{
                showMessage(view,"Error: ${task.exception?.message}")
            }
        })
    }

    private fun showMessage(view:View, message: String){
        Snackbar.make(view, message, Snackbar
            .LENGTH_INDEFINITE)
            .setAction("Action", null)
            .show()
    }
}

//        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
//            .get(LoginViewModel::class.java)
//
//        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
//            val loginState = it ?: return@Observer
//
//            // disable login button unless both username / password is valid
//            login.isEnabled = loginState.isDataValid
//
//            if (loginState.usernameError != null) {
//                username.error = getString(loginState.usernameError)
//            }
//            if (loginState.passwordError != null) {
//                password.error = getString(loginState.passwordError)
//            }
//        })
//
//        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
//            val loginResult = it ?: return@Observer
//
//            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }
//            setResult(Activity.RESULT_OK)
//
//            //Complete and destroy login activity once successful
//            finish()
//        })
//
//        username.afterTextChanged {
//            loginViewModel.loginDataChanged(
//                username.text.toString(),
//                password.text.toString()
//            )
//        }
//
//        password.apply {
//            afterTextChanged {
//                loginViewModel.loginDataChanged(
//                    username.text.toString(),
//                    password.text.toString()
//                )
//            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        loginViewModel.login(
//                            username.text.toString(),
//                            password.text.toString()
//                        )
//                }
//                false
//            }
//
//            login.setOnClickListener {
//                loading.visibility = View.VISIBLE
//                loginViewModel.login(username.text.toString(), password.text.toString())
//            }
//        }
//    }
//
//    private fun updateUiWithUser(model: LoggedInUserView) {
//        val welcome = getString(R.string.welcome)
//        val displayName = model.displayName
//        // TODO : initiate successful logged in experience
//
//        val myIntent = Intent(this, MainActivity::class.java)
//        startActivity(myIntent)
//
//        Toast.makeText(
//            applicationContext,
//            "$welcome $displayName",
//            Toast.LENGTH_LONG
//        ).show()
//    }
//
//    private fun showLoginFailed(@StringRes errorString: Int) {
//        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
//    }
//}
//
///**
// * Extension function to simplify setting an afterTextChanged action to EditText components.
// */
//fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//    this.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(editable: Editable?) {
//            afterTextChanged.invoke(editable.toString())
//        }
//
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//    })
//}
