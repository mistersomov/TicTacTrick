package com.mistersomov.tictactrick.presentation.common.sound

import android.content.Context
import android.media.SoundPool
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SoundServiceImpl @Inject constructor(
    private val context: Context,
    private val soundPool: SoundPool,
): SoundService {

    var sounds: List<SoundEntity> = emptyList()

    override fun loadSounds(soundList: List<SoundEffect>) {
        sounds = soundList.mapNotNull {
            val id = soundPool.load(context, it.resId, 1)

            if (id > 0) {
                SoundEntity(
                    id = id,
                    sound = it,
                )
            } else {
                null
            }
        }
    }

    override fun play(sound: SoundEffect) {
        sounds
            .find { sound.resId == it.sound.resId }
            ?.let {
                soundPool.play(it.id, 1f, 1f, 1, 0, 1f)
            }
    }

    override fun release() {
        sounds = emptyList()
        soundPool.release()
    }

}