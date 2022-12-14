package com.example.datingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.datingapp.adapter.MessageAdapter
import com.example.datingapp.databinding.ActivityMessageBinding
import com.example.datingapp.model.MessageModel
import com.example.datingapp.model.UserModel
import com.example.datingapp.notification.api.ApiUtilities
import com.example.datingapp.notification.model.NotificationData
import com.example.datingapp.notification.model.PushNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MessageActivity : AppCompatActivity() {

    lateinit var binding: ActivityMessageBinding
    private var senderId: String? = null
    private var receiverId: String? = null
    private var chatId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        //getData(intent.getStringExtra("chat_id"))
        verifyChatId()
        binding.ivSend.setOnClickListener {
            if (binding.yourMessage.text!!.isEmpty()) {
                Toast.makeText(this, "Please Enter your message", Toast.LENGTH_SHORT).show()
            } else {
                storeData(binding.yourMessage.text.toString())
            }
        }
    }

    private fun verifyChatId() {
        receiverId = intent.getStringExtra("userId")
        senderId = FirebaseAuth.getInstance().currentUser!!.phoneNumber
        chatId = senderId + receiverId
        val reverseChatId = receiverId + senderId


        val reference = FirebaseDatabase.getInstance().getReference("chats")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(chatId!!)) {
                    getData(chatId)
                } else if (snapshot.hasChild(reverseChatId)) {
                    chatId = reverseChatId
                    getData(chatId)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MessageActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun getData(chatId: String?) {

        FirebaseDatabase.getInstance().getReference("chats")
            .child(chatId!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = arrayListOf<MessageModel>()

                    for (show in snapshot.children) {
                        list.add(show.getValue(MessageModel::class.java)!!)
                    }

                    binding.messageRecycler.adapter = MessageAdapter(this@MessageActivity, list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MessageActivity, error.message, Toast.LENGTH_SHORT).show()
                }

            })

    }



    private fun storeData(message: String) {

        val currentDate: String =
            SimpleDateFormat("dd-MM-yyyyy", Locale.getDefault()).format(Date())
        val currentTime: String = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())

        val map = hashMapOf<String, String>()
        map["message"] = message
        map["senderId"] = senderId!!
        map["currentDate"] = currentDate
        map["currentTime"] = currentTime
        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId!!)

        reference.child(reference.push().key!!).setValue(map)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    sendNotification(message)

                    Toast.makeText(this, "Message Send", Toast.LENGTH_SHORT).show()
                    binding.yourMessage.text = null
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun sendNotification(message: String) {

        FirebaseDatabase.getInstance().getReference("users")
            .child(receiverId!!).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val data = snapshot.getValue(UserModel::class.java)
                        val notificationData = PushNotification(NotificationData("New Message",message),data!!.fcmToken)

                        ApiUtilities.getInstance().sendNotification(notificationData)
                            .enqueue(object : Callback<PushNotification>{
                                override fun onResponse(
                                    call: Call<PushNotification>,
                                    response: Response<PushNotification>
                                ) {
                                    Toast.makeText(this@MessageActivity, "Notification send!!", Toast.LENGTH_SHORT).show()

                                }

                                override fun onFailure(call: Call<PushNotification>, t: Throwable) {
                                    Toast.makeText(this@MessageActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                                }

                            })

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MessageActivity, error.message, Toast.LENGTH_SHORT).show()
                }

            })


    }
}