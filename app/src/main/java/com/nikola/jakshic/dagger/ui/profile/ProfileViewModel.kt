package com.nikola.jakshic.dagger.ui.profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.nikola.jakshic.dagger.data.local.BookmarkDao
import com.nikola.jakshic.dagger.data.local.PlayerDao
import com.nikola.jakshic.dagger.model.Bookmark
import com.nikola.jakshic.dagger.model.Player
import com.nikola.jakshic.dagger.repository.PlayerRepository
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
        private val playerDao: PlayerDao,
        private val bookmarkDao: BookmarkDao,
        private val repo: PlayerRepository) : ViewModel() {

    lateinit var profile: LiveData<Player>
        private set
    lateinit var bookmark: LiveData<Player>
        private set
    private var initialFetch = false
    private val disposables = CompositeDisposable()

    fun getProfile(id: Long) {
        if (!initialFetch) {
            initialFetch = true
            profile = playerDao.getPlayer(id)
            bookmark = bookmarkDao.getPlayer(id)
            disposables.add(repo.getProfile(id))
        }
    }

    fun addToBookmark(id: Long) {
        disposables.add(Completable
                .fromAction { bookmarkDao.addToBookmark(Bookmark(id)) }
                .subscribeOn(Schedulers.io())
                .subscribe())
    }

    fun removeFromBookmark(id: Long) {
        disposables.add(Completable
                .fromAction { bookmarkDao.removeFromBookmark(id) }
                .subscribeOn(Schedulers.io())
                .subscribe())
    }

    override fun onCleared() {
        disposables.clear()
    }
}