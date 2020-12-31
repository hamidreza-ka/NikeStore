package com.example.nikestore.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class NikeFragment : Fragment(), NikeView {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }
}

abstract class NikeActivity : AppCompatActivity(), NikeView {
    override fun setProgressIndicator(mustShow: Boolean) {
        TODO("Not yet implemented")
    }
}

interface NikeView {
    fun setProgressIndicator(mustShow: Boolean)
}

abstract class NikeViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {

        compositeDisposable.clear()
        super.onCleared()

    }
}