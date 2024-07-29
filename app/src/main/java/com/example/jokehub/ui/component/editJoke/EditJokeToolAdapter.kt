package com.example.jokehub.ui.component.editJoke

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokehub.databinding.ItemEditJokeToolBinding
import com.example.jokehub.ui.component.editJoke.EditJokeToolAdapter.EditJOkeViewHolder

class EditJokeToolAdapter(): RecyclerView.Adapter<EditJOkeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditJOkeViewHolder {
        val binding =  ItemEditJokeToolBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EditJOkeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: EditJOkeViewHolder, position: Int) {
       // val data=jokelist.get(position)
      //  holder.bind(data)
    }



    inner class EditJOkeViewHolder(binding: ItemEditJokeToolBinding) :RecyclerView.ViewHolder(binding.root) {

    }
}