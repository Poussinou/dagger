package com.nikola.jakshic.dagger.di

import android.content.Context
import com.nikola.jakshic.dagger.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class DbModule {
    @Provides
    fun providePlayerBookmarkQueries(database: Database) = database.playerBookmarkQueries

    @Provides
    fun provideMatchBookmarkQueries(database: Database) = database.matchBookmarkQueries

    @Provides
    fun providePlayerStatsQueries(database: Database) = database.playerStatsQueries

    @Provides
    fun provideMatchStatsQueries(database: Database) = database.matchStatsQueries

    @Provides
    fun providePeerQueries(database: Database) = database.peerQueries

    @Provides
    fun provideHeroQueries(database: Database) = database.heroQueries

    @Provides
    fun provideMatchQueries(database: Database) = database.matchQueries

    @Provides
    fun provideSearchHistoryQueries(database: Database) = database.searchHistoryQueries

    @Provides
    fun providePlayerQueries(database: Database) = database.playerQueries

    @Provides
    fun provideCompetitiveQueries(database: Database) = database.competitiveQueries

    @Provides
    fun provideLeaderboardQueries(database: Database) = database.leaderboardQueries

    @Provides
    @Singleton
    fun provideSqlDriver(context: Context): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "dagger.db")
    }

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): Database {
        return Database(driver)
    }
}