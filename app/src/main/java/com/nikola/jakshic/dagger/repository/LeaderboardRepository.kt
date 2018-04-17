package com.nikola.jakshic.dagger.repository

import android.arch.lifecycle.MutableLiveData
import com.crashlytics.android.Crashlytics
import com.nikola.jakshic.dagger.Status
import com.nikola.jakshic.dagger.data.local.LeaderboardDao
import com.nikola.jakshic.dagger.data.remote.OpenDotaService
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaderboardRepository @Inject constructor(private val dao: LeaderboardDao,
                                                private val service: OpenDotaService) {

    fun fetchData(region: String?, status: MutableLiveData<Status>): Disposable {
        status.value = Status.LOADING
        return service.getLeaderboard(region)
                .subscribeOn(Schedulers.io())
                .flatMap { Observable.fromIterable(it.leaderboard) }
                .take(100)
                .map {
                    it.region = region!!
                    it
                }
                .toList()
                .observeOn(Schedulers.io())
                .subscribe({ list ->
                    dao.deleteLeaderboards(region)
                    dao.insertLeaderboard(list)
                    status.postValue(Status.SUCCESS)
                }, { error ->
                    status.postValue(Status.ERROR)
                    Crashlytics.logException(error)
                })
    }
}

enum class Region {
    AMERICAS,
    CHINA,
    EUROPE,
    SE_ASIA
}