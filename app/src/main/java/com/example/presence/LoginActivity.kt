package com.example.presence

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email=intent.getStringExtra("em")
        val password=intent.getStringExtra("ps")


        findViewById<TextInputEditText>(R.id.email).setText(email)
        findViewById<TextInputEditText>(R.id.password).setText(password)


        val textView = findViewById<TextView>(R.id.doNotHaveAccount)
        textView.setOnClickListener{
            val intent = Intent(this , Register :: class.java)
            startActivity(intent)
        }

        forgotPassword.setOnClickListener{
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }

        val button=findViewById<Button>(R.id.signIn)
        button.setOnClickListener{
            var count=0
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.email).text.toString())){
                count+=1
                Toast.makeText(this@LoginActivity, "Email Address required",Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.password).text.toString())){
                count+=1
                Toast.makeText(this@LoginActivity,"Password required",Toast.LENGTH_SHORT).show()
            }
            if(count==0)
            {
                val em=findViewById<TextInputEditText>(R.id.email).text.toString()
                val pass=findViewById<TextInputEditText>(R.id.password).text.toString()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(em,pass).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        Toast.makeText(this@LoginActivity,"Login Successful.",Toast.LENGTH_SHORT).show()
                        val intent= Intent(this,MainActivity::class.java)
                        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id",firebaseUser.uid)
                        intent.putExtra("email",em)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}