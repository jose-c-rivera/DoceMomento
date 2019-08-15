package com.example.docemomento.registration

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.docemomento.MainActivity
import com.example.docemomento.R
import com.example.docemomento.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etPasswordConfirm: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null


    private val TAG = "CreateAccountActivity"

    //global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialise()

        val cancelBtn = findViewById<Button>(R.id.reg_cancel)

        cancelBtn.setOnClickListener{view ->
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initialise(){
        etFirstName = findViewById<View>(R.id.first_name) as EditText
        etLastName = findViewById<View>(R.id.last_name) as EditText
        etEmail = findViewById<View>(R.id.email_reg) as EditText
        etPassword = findViewById<View>(R.id.password_reg_1) as EditText
        etPasswordConfirm = findViewById<View>(R.id.password_reg_2) as EditText
        btnCreateAccount = findViewById<View>(R.id.btnRegister) as Button
        mProgressBar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount(){
        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            print("Validation Occurs here")
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }

        mProgressBar!!.setMessage("Registering User...")
        mProgressBar!!.show()

        mAuth!!
            .createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                mProgressBar!!.hide()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = mAuth!!.currentUser!!.uid
                    //Verify Email
                    ////////verifyEmail();
                    //update user profile information
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)
                    updateUserInfoAndUI()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this@RegisterActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
