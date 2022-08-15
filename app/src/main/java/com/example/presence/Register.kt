@file:Suppress("DEPRECATION")

package com.example.presence

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*


class Register : AppCompatActivity() {
    lateinit var photoPath : String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var Image:Uri
    private val storageReference= FirebaseStorage.getInstance("gs://presence-23cbb.appspot.com").reference
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        val dropDown : AutoCompleteTextView = findViewById(R.id.gender)

        val listed  = ArrayList<String>()
        listed.add("Female")
        listed.add("Male")

        val listedAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listed)

        dropDown.setAdapter(listedAdapter)

        val textView = findViewById<TextView>(R.id.alreadyHaveAccount)
        textView.setOnClickListener{
            val intent = Intent(this , LoginActivity :: class.java)
            startActivity(intent)
        }
        val image=findViewById<ImageView>(R.id.profile)
        image.setOnClickListener{
            selectImage()
        }
        val button=findViewById<Button>(R.id.register)
        button.setOnClickListener{
            var count=0
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.fullName).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Full name required", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<AutoCompleteTextView>(R.id.gender).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Gender required", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.employeeNo).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Employee number required", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.phoneNo).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Phone number required", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.email).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Email required", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.officeAddress).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Office Address required", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.password).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Password required", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(findViewById<TextInputEditText>(R.id.designation).text.toString())){
                count+=1
                Toast.makeText(this@Register, "Designation required", Toast.LENGTH_SHORT).show()
            }
            if(count==0)
            {
                val fullName=findViewById<TextInputEditText>(R.id.fullName).text.toString()
                val gender=findViewById<AutoCompleteTextView>(R.id.gender).text.toString()
                val employeeNo=findViewById<TextInputEditText>(R.id.employeeNo).text.toString()
                val phoneNo=findViewById<TextInputEditText>(R.id.phoneNo).text.toString()
                val email=findViewById<TextInputEditText>(R.id.email).text.toString()
                val officeAddress=findViewById<TextInputEditText>(R.id.officeAddress).text.toString()
                val password=findViewById<TextInputEditText>(R.id.password).text.toString()
                val designation=findViewById<TextInputEditText>(R.id.designation).text.toString()

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        databaseReference= FirebaseDatabase.getInstance("https://presence-23cbb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
                        val user = User(fullName,gender,employeeNo,phoneNo,officeAddress,designation)


                        //uploadImage(firebaseUser.uid)

                        databaseReference.child(firebaseUser.uid).setValue(user).addOnCompleteListener{
                            if(it.isSuccessful)
                            {
                                Toast.makeText(this@Register,"data uploaded",Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                Toast.makeText(this@Register,"data not uploaded",Toast.LENGTH_SHORT).show()
                            }
                        }
                        Toast.makeText(this@Register,"Registration Successful.",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,LoginActivity::class.java)
                        intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("em",email)
                        intent.putExtra("ps",password)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this@Register,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun selectImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, 1)
        } catch (e: ActivityNotFoundException) {}
    }
   /*private fun uploadImage(uid:String?=null) {
        val storageReference= FirebaseStorage.getInstance("gs://presence-23cbb.appspot.com").getReference("Profile/$uid")

        storageReference.putFile(Image).addOnSuccessListener {
            Toast.makeText(this@Register,"Image Uploaded",Toast.LENGTH_SHORT).show()
            findViewById<ImageView>(R.id.profile).setImageURI(null)
        }.addOnFailureListener {
            Toast.makeText(this@Register,"Image not uploaded",Toast.LENGTH_SHORT).show()
        }
    }*/
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if(requestCode==100 && resultCode== RESULT_OK)
        {
            Image= data?.data!!
            Toast.makeText(this@Register,"$Image",Toast.LENGTH_SHORT).show()
            findViewById<ImageView>(R.id.profile).setImageURI(Image)
        }*/
        if(requestCode==1 && resultCode== RESULT_OK)
        {
            if (data != null) {
                profile.setImageBitmap(data.extras?.get("data") as Bitmap?)
            }
        }

        }
    }

