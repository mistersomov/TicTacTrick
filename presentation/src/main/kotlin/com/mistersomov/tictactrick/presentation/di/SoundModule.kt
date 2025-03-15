package com.mistersomov.tictactrick.presentation.di

import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_GAME
import android.media.SoundPool
import com.mistersomov.tictactrick.presentation.common.sound.SoundService
import com.mistersomov.tictactrick.presentation.common.sound.SoundServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SoundBindsModule {

    @Binds
    fun bindSoundService(impl: SoundServiceImpl): SoundService

}

@Module
@InstallIn(SingletonComponent::class)
object SoundProvidesModule {

    @Provides
    @Singleton
    fun provideAudioAttributes(): AudioAttributes =
        AudioAttributes.Builder()
            .setUsage(USAGE_GAME)
            .setContentType(CONTENT_TYPE_SONIFICATION)
            .build()

    @Provides
    @Singleton
    fun provideSoundPool(attributes: AudioAttributes): SoundPool =
        SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(attributes)
            .build()

}