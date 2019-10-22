package com.amm.android.retrofit2withkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var disposable: Disposable? = null
    val wikiApiService by lazy{
        WikiApiService.create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            if (edit_search.text.toString() != "") {
                beginSearch(edit_search.text.toString())
            }
        }
    }

    private fun beginSearch(srsearch: String) {
        disposable =
            wikiApiService.hitCountCheck("query", "json", "search", srsearch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> txt_search_result.text = "${result.query.searchinfo.totalhits} result found" },
                    { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
