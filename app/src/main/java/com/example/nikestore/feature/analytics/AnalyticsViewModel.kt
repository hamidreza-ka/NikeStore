package com.example.nikestore.feature.analytics

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.data.AnalyticMD
import com.example.nikestore.data.repository.analytic.AnalyticRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AnalyticsViewModel constructor(analyticRepository: AnalyticRepository) :
    NikeViewModel() {

    private var disposable: Disposable? = null
    val analyticsLiveData = MutableLiveData<List<AnalyticMD>>()
    val errorLiveData = MutableLiveData(false)

    init {
        analyticRepository.getView()
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<List<AnalyticMD>> {

                override fun onSubscribe(d: Disposable) {
                    progressLiveData.postValue(true)
                    disposable = d
                }

                override fun onSuccess(t: List<AnalyticMD>) {
                    progressLiveData.postValue(false)
                    analyticsLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    progressLiveData.postValue(false)
                    errorLiveData.postValue(true)
                }

            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.let {
            if (!it.isDisposed)
                it.dispose()
        }
    }
}