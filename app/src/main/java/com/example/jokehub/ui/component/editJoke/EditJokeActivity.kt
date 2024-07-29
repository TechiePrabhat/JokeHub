package com.example.jokehub.ui.component.editJoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.jokehub.R
import com.example.jokehub.databinding.ActivityEditJokeBinding
import com.example.jokehub.databinding.ActivitySearchJokeBinding

class EditJokeActivity : AppCompatActivity() {

    private lateinit var mEditJokeBinding: ActivityEditJokeBinding
    private lateinit var  intent:Intent
    private lateinit var mJoke: String
    private lateinit var mToolRecyclerAdapter:EditJokeToolAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEditJokeBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_joke)

        intent=getIntent()
        mJoke = intent.getStringExtra("joke_data").toString()

        mEditJokeBinding.jokeTextView.text = mJoke

        mToolRecyclerAdapter=EditJokeToolAdapter()
        mEditJokeBinding.toolRecycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        mEditJokeBinding.toolRecycler.adapter=mToolRecyclerAdapter

    }
}