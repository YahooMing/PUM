package com.example.gradesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.databinding.GradesBinding

//dziedziczymy po GradesViewHolder, zarządzamy listą danych: Przedmiot, Średnia ocen, liczba ocen
class GradesAdapter(private val wordList: List<Triple<String, Double, Int>>) : RecyclerView.Adapter<GradesViewHolder>() {
    //Wywołuje w celu utworzenia obiektu GradesViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradesViewHolder {
        return GradesViewHolder(
                GradesBinding.inflate( //tworzymy widok
                LayoutInflater.from(parent.context), parent, false
            ))
    }

        override fun getItemCount() = wordList.size //zwraca liczbe elementów w liście wordList, tak trzeba żeby Recycler wiedział ile rzeczy wiświetlić

        override fun onBindViewHolder(holder: GradesViewHolder, position: Int) {
            val currentItem = wordList[position]
            val subject = currentItem.first.toString()
            val average = currentItem.second.toString()
            val num = currentItem.third.toString()
            holder.bind(subject,"Średnia: "+average,"Liczba list: "+num)
        }


}
