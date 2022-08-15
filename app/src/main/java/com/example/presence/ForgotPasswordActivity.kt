package com.example.presence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        submit.setOnClickListener{
            val email: String = forgot_Email.text.toString().trim{it <= ' '}
            if(email.isEmpty()){
                Toast.makeText(this@ForgotPasswordActivity, "Please Enter Your Email",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Toast.makeText(this@ForgotPasswordActivity, "Email Sent Successfully",
                                Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            Toast.makeText(this@ForgotPasswordActivity, task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}