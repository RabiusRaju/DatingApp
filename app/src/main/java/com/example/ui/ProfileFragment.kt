package com.example.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.datingapp.R
import com.example.datingapp.activity.EditProfileActivity
import com.example.datingapp.auth.LoginActivity
import com.example.datingapp.databinding.FragmentProfileBinding
import com.example.datingapp.model.UserModel
import com.example.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {


        fetchData()

        binding.logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(),LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.editProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }


    }

    private fun fetchData() {
        Config.showDialog(requireContext())

        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.getValue(UserModel::class.java)

                    binding.userName.setText(data!!.name.toString())
                    binding.userEmail.setText(data!!.email.toString())
                    binding.userCity.setText(data!!.city.toString())
                    binding.userNumber.setText(data!!.number.toString())


                    Glide.with(requireContext()).load(data.image).placeholder(R.drawable.user)
                        .into(binding.userImage)

                    Config.hideDialog()


                }
            }
    }


}