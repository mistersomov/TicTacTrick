package com.mistersomov.tictactrick.presentation.di

import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchStateMutator
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchStateMutatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface PresentationModule {

    @Binds
    @ViewModelScoped
    fun bindMatchStateMutator(impl: MatchStateMutatorImpl): MatchStateMutator

}