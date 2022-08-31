package com.example.antisoshapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SigninActivity : AppCompatActivity() {
    private lateinit var edtName:EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignin: Button
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        edtName=findViewById(R.id.edt_name)
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        btnSignin=findViewById(R.id.signin)

        btnSignin.setOnClickListener {
            val name =edtName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            Signin(name,email,password)
        }

    }
    private fun Signin(name: String, email: String, password: String) {
        //logic of creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for signing in user

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent =Intent(this@SigninActivity,UsersmainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SigninActivity,"some error occurred",Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun addUserToDatabase(name: String, email: String, uid: String){
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}