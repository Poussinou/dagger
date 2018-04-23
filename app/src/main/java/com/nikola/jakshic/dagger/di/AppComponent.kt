package com.nikola.jakshic.dagger.di

import com.nikola.jakshic.dagger.ui.competitive.CompetitiveFragment
import com.nikola.jakshic.dagger.ui.leaderboard.RegionFragment
import com.nikola.jakshic.dagger.view.activity.MatchActivity
import com.nikola.jakshic.dagger.view.activity.PlayerActivity
import com.nikola.jakshic.dagger.view.activity.SearchActivity
import com.nikola.jakshic.dagger.view.activity.SettingsActivity
import com.nikola.jakshic.dagger.view.fragment.BookmarkFragment
import com.nikola.jakshic.dagger.view.fragment.HeroFragment
import com.nikola.jakshic.dagger.view.fragment.MatchFragment
import com.nikola.jakshic.dagger.view.fragment.PeerFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(DbModule::class), (ViewModelModule::class)])
interface AppComponent {

    fun inject(activity: SearchActivity)

    fun inject(fragment: MatchFragment)

    fun inject(fragment: HeroFragment)

    fun inject(activity: PlayerActivity)

    fun inject(activity: MatchActivity)

    fun inject(fragment: BookmarkFragment)

    fun inject(fragment: PeerFragment)

    fun inject(fragment: CompetitiveFragment)

    fun inject(fragment: RegionFragment)

    fun inject(activity: SettingsActivity)
}