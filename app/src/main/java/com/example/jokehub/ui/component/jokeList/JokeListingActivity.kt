package com.example.jokehub.ui.component.jokeList

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokehub.JokeApplication
import com.example.jokehub.R
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.pagging.LoadAdapter
import com.example.jokehub.databinding.ActivityJokeListingBinding
import com.example.jokehub.ui.component.common.JokeListPageAdapter
import com.example.jokehub.ui.component.editJoke.EditJokeActivity
import javax.inject.Inject

class JokeListingActivity : AppCompatActivity() {
    lateinit var binding: ActivityJokeListingBinding
    lateinit var jokeListAdapter: JokeListPageAdapter
    lateinit var layotManger: RecyclerView.LayoutManager
  @Inject  lateinit var jokeViewModel: JokeListViewModel
    lateinit var jokeAdapterList: List<Result>
    var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_joke_listing)

        // Calling dependency injection
        (application as JokeApplication).jokeListComponent.inject(this)

        // initialize Adapter
        jokeListAdapter = JokeListPageAdapter(this) { code, clickType, position, uri ->
            adapterOnClick(
                code,
                clickType,
                position,
                uri
            )
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = jokeListAdapter.withLoadStateHeaderAndFooter(
            header = LoadAdapter(),
            footer = LoadAdapter()
        )



        jokeViewModel.updatedJoke.observe(this) {
            binding.progressBar2.visibility = View.GONE
            jokeListAdapter.submitData(lifecycle, it)
        }
        binding.backBtnImg.setOnClickListener(View.OnClickListener { finish() })

    }

    private fun adapterOnClick(shareData: Result, clickType: Int, position: Int,uri:Uri) {

        when (clickType) {

            1 -> {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    // Example: content://com.google.android.apps.photos.contentprovider/...
                    putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
                    putExtra(Intent.EXTRA_TEXT, shareData.joke)
                    putExtra(Intent.EXTRA_STREAM,uri)
                    type = "image/*"
                }
                startActivity(Intent.createChooser(shareIntent, "Joke Hub"))

            }

            2 -> {
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData: ClipData = ClipData.newPlainText("Joke Hub", shareData.joke)
                clipBoard.setPrimaryClip(clipData)
            }

            3 -> {
                likeClicked(shareData, position)
            }

            4 -> {
                editClicked(shareData)
            }


        }


    }


    fun likeClicked(fab: Result, position: Int) {
        jokeListAdapter.notifyItemChanged(position)
        if (fab.isFav == 1)
            jokeViewModel.addFavoriteJoke(fab)
        else
            jokeViewModel.deleteFavourite(fab.id)
    }

    fun editClicked(joke:Result) {
        intent=Intent(this,EditJokeActivity::class.java)
        intent.putExtra("joke_data",joke.joke)
        startActivity(intent)

    }
}