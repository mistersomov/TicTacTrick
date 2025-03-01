package com.mistersomov.tictactrick.presentation.screen.match.mutator

import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State

interface MatchStateMutator {
    fun mutate(currentState: State, event: MatchMutatorEvent): State
}