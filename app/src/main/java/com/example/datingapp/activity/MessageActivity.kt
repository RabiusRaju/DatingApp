package com.example.datingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityMainBinding
import com.example.datingapp.databinding.ActivityMessageBinding
import com.example.datingapp.databinding.FragmentMessageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class MessageActivity : AppCompatActivity() {

    lateinit var binding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.ivSend.setOnClickListener {
            if (binding.yourMessage.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter your message", Toast.LENGTH_SHORT).show()
            }else{
                sendMessage(binding.yourMessage.text.toString())
            }
        }
    }

    private fun sendMessage(message: String) {
        val receiverId = intent.getStringExtra("userId")
        val senderId = FirebaseAuth.getInstance().currentUser!!.phoneNumber
        val chatId = senderId+receiverId
        val currentDate : String = SimpleDateFormat("dd-MM-yyyyy", Locale.getDefault()).format(Date())
        val currentTime : String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())

        val map = hashMapOf<String,String>()
        map["message"] = message
        map["senderId"] = senderId!!
        map["currentDate"] = currentDate
        map["currentTime"] = currentTime

        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId)

        reference.child(reference.push().key!!).setValue(map)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Message Send", Toast.LENGTH_SHORT).show()
                    binding.yourMessage.text = null
                }else{
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }



    }
}