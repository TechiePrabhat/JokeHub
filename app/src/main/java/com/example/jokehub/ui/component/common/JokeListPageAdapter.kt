package com.example.jokehub.ui.component.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jokehub.data.dto.Result
import com.example.jokehub.databinding.ItemJokeListBinding
import com.example.jokehub.utils.ImageHelper

class JokeListPageAdapter(
    private val context: Context,
    private val onClick: (Result, Int, Int, Uri) -> Unit
) : PagingDataAdapter<Result, JokeListPageAdapter.JokeViewHolder>(COMPARATOR) {

    private val mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding =
            ItemJokeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding, onClick, context)
    }


    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, position)
            if (data.isFav == 0) {
                val res = context.resources.getIdentifier(
                    /* name = */ "like",
                    /* defType = */ "drawable",
                    /* defPackage = */ context.packageName
                )
                Glide.with(context).load(res).into(holder.binding.favoriteJokeImg)
            } else {
                val res = context.resources.getIdentifier(
                    /* name = */ "like_red",
                    /* defType = */ "drawable",
                    /* defPackage = */ context.packageName
                )
                Glide.with(context).load(res).into(holder.binding.favoriteJokeImg)
            }
        }

    }

    @SuppressLint("DiscouragedApi")
    class JokeViewHolder(
        val binding: ItemJokeListBinding,
        val onClick: (Result, Int, Int, Uri) -> Unit,
        context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var itemData: Result? = null
        private var clickedPosition: Int? = null
        private val cont = context
        lateinit var uri: Uri
        private val imgHelper = ImageHelper(context)

        init {
            binding.shareJokeImg.setOnClickListener(View.OnClickListener {
                uri = imgHelper.textViewToImage(binding)
                itemData?.let {
                    clickedPosition?.let { it1 -> onClick(it, 1, it1, uri) }
                }
            })
            binding.copyJokeImg.setOnClickListener(View.OnClickListener {
                uri = imgHelper.textViewToImage(binding)
                itemData?.let {
                    clickedPosition?.let { it1 -> onClick(it, 2, it1, uri) }
                }
            })
            binding.favoriteJokeImg.setOnClickListener(View.OnClickListener {
                uri = imgHelper.textViewToImage(binding)
                itemData?.isFav = if (itemData?.isFav == 1) 0 else 1
                itemData?.let {
                    clickedPosition?.let { it1 -> onClick(it, 3, it1, uri) }
                }
            })
            binding.editJokeImg.setOnClickListener(View.OnClickListener {
                uri = imgHelper.textViewToImage(binding)
                itemData?.let {
                    clickedPosition?.let { it1 -> onClick(it, 4, it1, uri) }
                }
            })

        }

        fun bind(joke: Result, position: Int) {
            itemData = joke
            binding.result = joke
            clickedPosition = position
        }

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.joke == newItem.joke
            }

        }
    }


}