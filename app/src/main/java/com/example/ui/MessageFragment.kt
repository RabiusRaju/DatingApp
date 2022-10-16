package com.example.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.datingapp.adapter.MessageUserAdapter
import com.example.datingapp.databinding.FragmentMessageBinding
import com.example.ui.DatingFragment.Companion.list

class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMessageBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        binding.recyclerView.adapter = MessageUserAdapter(requireContext(), list!!)
    }


}