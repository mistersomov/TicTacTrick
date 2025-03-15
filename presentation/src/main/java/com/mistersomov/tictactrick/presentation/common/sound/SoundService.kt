package com.mistersomov.tictactrick.presentation.common.sound

interface SoundService {
    fun loadSounds(soundList: List<SoundEffect>)
    fun play(sound: SoundEffect)
    fun release()
}