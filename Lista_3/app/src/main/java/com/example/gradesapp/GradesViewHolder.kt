package com.example.gradesapp
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.databinding.GradesBinding

class GradesViewHolder(private val binding: GradesBinding) :
    RecyclerView.ViewHolder(binding.root){

    fun bind(subjectItem: String, averageItem: String, numItem: String){
        binding.subject.text = subjectItem
        binding.average.text = averageItem
        binding.num.text = numItem
    }
}