package com.example.jokehub.ui.component.favouriteJoke

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jokehub.JokeApplication
import com.example.jokehub.R
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.pagging.LoadAdapter
import com.example.jokehub.databinding.ActivityFavouriteJokesBinding
import com.example.jokehub.ui.component.editJoke.EditJokeActivity
import com.example.jokehub.ui.component.common.JokeListPageAdapter
import javax.inject.Inject

class FavouriteJokesActivity : AppCompatActivity() {

    private lateinit var mActivityFavouriteJokesBinding: ActivityFavouriteJokesBinding
   @Inject lateinit var mFavouriteJokeViewModel: FavouriteJokeViewModel
    private lateinit var mJokeListPageAdapter: JokeListPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mActivityFavouriteJokesBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_favourite_jokes)
        super.onCreate(savedInstanceState)

        (application as JokeApplication).jokeListComponent.inject(this)

        mActivityFavouriteJokesBinding.favouriteRecyclerView.layoutManager =
            LinearLayoutManager(this)

        mJokeListPageAdapter =
            JokeListPageAdapter(this) { code, clickType, position,uri ->
                adapterOnClick(
                    code,
                    clickType,
                    position,
                    uri
                )
            }

        mActivityFavouriteJokesBinding.favouriteRecyclerView.adapter =
            mJokeListPageAdapter.withLoadStateHeaderAndFooter(
                header = LoadAdapter(),
                footer = LoadAdapter()
            )


        mFavouriteJokeViewModel.jokes.observe(this) {
            mJokeListPageAdapter.submitData(lifecycle, it)
            mActivityFavouriteJokesBinding.progressBar2.visibility = View.GONE
        }



        mActivityFavouriteJokesBinding.backBtnImg.setOnClickListener(OnClickListener {
            finish()
        })
    }

    private fun adapterOnClick(shareData: Result, clickType: Int, position: Int, uri: Uri) {

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
        mJokeListPageAdapter.notifyDataSetChanged()
        mFavouriteJokeViewModel.deleteFavourite(fab.id)
    }

    fun editClicked(joke:Result) {
        intent=Intent(this, EditJokeActivity::class.java)
        intent.putExtra("joke_data",joke.joke)
        startActivity(intent)
    }
}