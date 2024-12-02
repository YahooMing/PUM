package com.example.gradesapp

import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.databinding.ExercisesBinding

class ExercisesViewHolder(private val binding: ExercisesBinding) : //Klasa dziedzicz po RecyclerView
    RecyclerView.ViewHolder(binding.root){
    //metoda bind
    fun bind(contentItem: String,pointsItem:String, idxItem:String){
        binding.singleWord.text = contentItem //ustawiamy tekst widoku singleWord na contentItem
        binding.exerciseCount.text = "Zadanie "+idxItem //ustawiamy widok exerciseCount na index
        binding.points.text = "pkt: "+pointsItem //ustawiamy tekst widoku points na pointsItem
    }
}