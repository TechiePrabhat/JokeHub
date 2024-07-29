package com.example.jokehub.ui.component.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokehub.data.dto.Result
import com.example.jokehub.databinding.ItemJokeListBinding
import com.example.jokehub.ui.component.common.JokeListAdapter.MyViewHolder

class JokeListAdapter(dataList: List<Result>):RecyclerView.Adapter<MyViewHolder>() {

private val jokelist:List<Result> =dataList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
         val binding =ItemJokeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return jokelist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data=jokelist.get(position)
        holder.bind(data)

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    inner class MyViewHolder(val binding:ItemJokeListBinding):RecyclerView.ViewHolder(binding.root) {


        fun bind(joke:Result){
            binding.result=joke
        }

    }
}