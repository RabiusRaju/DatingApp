package com.example.datingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datingapp.databinding.FragmentProfileBinding
import com.example.datingapp.databinding.UserItemLayoutBinding
import com.example.datingapp.model.UserModel

class MessageUserAdapter(val context: Context, val list: ArrayList<UserModel>) :
    RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {

    inner class MessageUserViewHolder(val binding: UserItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
        return MessageUserViewHolder(UserItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
        Glide.with(context).load(list[position].image).into(holder.binding.userImage)
        holder.binding.userName.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}