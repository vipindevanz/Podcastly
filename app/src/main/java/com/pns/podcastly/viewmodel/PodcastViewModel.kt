package com.pns.podcastly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pns.podcastly.remote.model.ListenNoteResponseDTO
import com.pns.podcastly.remote.model.RXModel
import com.pns.podcastly.repo.ListenNotesApiRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class PodcastViewModel() : ViewModel() {
    private val podcastData = MutableLiveData<RXModel>()
    val liveData: LiveData<RXModel> = podcastData
    private lateinit var disposable: Disposable
    private val apiRepo = ListenNotesApiRepo()
    fun showData() {
        apiRepo.getPodcastsFromServer().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ListenNoteResponseDTO> {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: ListenNoteResponseDTO) {
                    t?.run {
                        podcastData.value = RXModel.OnSuccess(this)
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

            })
    }
}