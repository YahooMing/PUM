package com.example.gradesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.databinding.ExercisesBinding

class ExercisesAdapter(private val wordList: MutableList<Exercise>) : RecyclerView.Adapter<ExercisesViewHolder>(){ //dziedziczy po RecyclerView.Adapter<ExercisesViewHolder>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesViewHolder { //nadpisuje metode
        return ExercisesViewHolder(
            ExercisesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))

    }

    override fun getItemCount() = wordList.size

    override fun onBindViewHolder(holder: ExercisesViewHolder, position: Int) {
        val currentItem = wordList[position]
        val content = currentItem.content.toString()
        val points = currentItem.points.toString()
        val exerciseCount = position + 1
        holder.bind(content,points,exerciseCount.toString())
    }

}