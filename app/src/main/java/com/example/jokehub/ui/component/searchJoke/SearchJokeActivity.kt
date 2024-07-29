package com.example.jokehub.ui.component.searchJoke

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.jokehub.JokeApplication
import com.example.jokehub.R
import com.example.jokehub.data.dto.Result
import com.example.jokehub.data.pagging.LoadAdapter
import com.example.jokehub.databinding.ActivitySearchJokeBinding
import com.example.jokehub.ui.component.editJoke.EditJokeActivity
import com.example.jokehub.ui.component.common.JokeListPageAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchJokeActivity : AppCompatActivity() {
    lateinit var searchJokeBinding: ActivitySearchJokeBinding
    @Inject lateinit var searchViewModel: SearchViewModel
    lateinit var searchJokeListAdapter: JokeListPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchJokeBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_joke)

        /*val repository = (application as JokeApplication).jokeRepo

        searchViewModel = ViewModelProvider(
            this,
            SearchJokeViewModelFactory(repository)
        ).get(SearchViewModel::class.java)*/

        (application as JokeApplication).jokeListComponent.inject(this)
        searchJokeBinding.searchJokeRecycler.visibility = View.GONE
        searchJokeListAdapter = JokeListPageAdapter(this) { data, typeClick, position,uri ->
            adapterOnClick(
                data,
                typeClick,
                position,
                uri
            )
        }
        searchJokeBinding.searchJokeRecycler.layoutManager = LinearLayoutManager(this)
        searchJokeBinding.searchJokeRecycler.adapter =
            searchJokeListAdapter.withLoadStateHeaderAndFooter(
                header = LoadAdapter(),
                footer = LoadAdapter()
            )


        searchJokeBinding.serchBtnTv.setOnClickListener(View.OnClickListener {
            Log.d("555pmt", "click listner called")
            closeKeyBoard()
            lifecycleScope.launch {

                searchViewModel.searchJoke(searchJokeBinding.editTextText.text.toString())
                    .observe(this@SearchJokeActivity) {
                        searchJokeListAdapter.submitData(lifecycle, it)
                        searchJokeBinding.searchGroup.visibility = View.GONE
                        searchJokeBinding.searchJokeRecycler.visibility = View.VISIBLE
                    }
            }


        })

        searchJokeBinding.editTextText.setOnClickListener(View.OnClickListener {
            searchJokeBinding.searchGroup.visibility = View.VISIBLE
            searchJokeBinding.searchJokeRecycler.visibility = View.GONE
        })


        searchJokeBinding.searchBackBtnImg.setOnClickListener(View.OnClickListener { finish() })


        Glide.with(this).load(R.drawable.searching).into(searchJokeBinding.searchPageImg)


    }

    fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
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
                likeClicked(shareData,position)
            }

            4 -> {
                editClicked(shareData)
            }


        }


    }


    fun likeClicked(fab: Result, position: Int) {
        searchJokeListAdapter.notifyItemChanged(position)
        if (fab.isFav == 1)
            searchViewModel.addFavoriteJoke(fab)
        else
            searchViewModel.deleteFavourite(fab.id)
    }

    fun editClicked(joke:Result) {
        intent=Intent(this, EditJokeActivity::class.java)
        intent.putExtra("joke_data",joke.joke)
        startActivity(intent)
    }
}