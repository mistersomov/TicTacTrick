package com.mistersomov.tictactrick.domain.di

import com.mistersomov.tictactrick.domain.use_case.ApplyTrickyCardUseCase
import com.mistersomov.tictactrick.domain.use_case.ApplyTrickyCardUseCaseImpl
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCaseImpl
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    @ViewModelScoped
    fun bindApplyTrickyCardUseCase(impl: ApplyTrickyCardUseCaseImpl): ApplyTrickyCardUseCase

    @Binds
    @ViewModelScoped
    fun bindGetMatchStatusUseCase(impl: GetMatchStatusUseCaseImpl): GetMatchStatusUseCase

    @Binds
    @ViewModelScoped
    fun bindMoveUseCase(impl: MoveUseCaseImpl): MoveUseCase

}