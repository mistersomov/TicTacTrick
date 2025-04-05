package com.mistersomov.tictactrick.presentation.di

import com.mistersomov.tictactrick.domain.algorithm.Algorithm
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.presentation.screen.match.entity.ai.AiPlayer
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchStateMutator
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchStateMutatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface PresentationBindsModule {

    @Binds
    @ViewModelScoped
    fun bindMatchStateMutator(impl: MatchStateMutatorImpl): MatchStateMutator

}

@Module
@InstallIn(ViewModelComponent::class)
object PresentationProvidesModule {

    @Provides
    @ViewModelScoped
    fun provideAiPlayer(
        algorithm: Algorithm,
        moveUseCase: MoveUseCase,
    ): AiPlayer = AiPlayer(algorithm, moveUseCase)
}