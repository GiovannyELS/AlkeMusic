package com.example.reproductorbasico
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reproductorbasico.AppConstant.Companion.LOG_MAIN_ACTIVITY
import com.example.reproductorbasico.AppConstant.Companion.MEDIA_PLAYER_PLAYSTATUS
import com.example.reproductorbasico.AppConstant.Companion.MEDIA_PLAYER_POSITION
import com.example.reproductorbasico.AppConstant.Companion.MEDIA_PLAYER_SONG_INDEX
import com.example.reproductorbasico.databinding.ActivityMainBinding
import java.lang.reflect.Array.getInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private var position:Int = 0
    private var currentSongIndex: Int = 0
    private lateinit var currentSong: Song
    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.i(LOG_MAIN_ACTIVITY, "Estoy onCreate")
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playPauseButton.setOnClickListener { playOrPauseMusic() }
        binding.playNextButton.setOnClickListener{ playNextSong() }
        binding.playPreviousButton.setOnClickListener { playPreviousSong() }

        savedInstanceState?.let {
            position = it.getInt(MEDIA_PLAYER_POSITION)
            currentSongIndex = it.getInt(MEDIA_PLAYER_SONG_INDEX)
            isPlaying = it.getBoolean(MEDIA_PLAYER_PLAYSTATUS)
        }
        Log.i(LOG_MAIN_ACTIVITY, "**Estoy en $isPlaying**")

        currentSong= AppConstant.song[currentSongIndex]
        updateUiSong()

    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_MAIN_ACTIVITY, "Estoy en onStart")
        mediaPlayer = MediaPlayer.create(this,currentSong.audioResId)
        if(isPlaying) mediaPlayer?.start()
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_MAIN_ACTIVITY, "Estoy en onResume")
        mediaPlayer?.seekTo(position)

        if (isPlaying) {
          //  isPlaying = false
            mediaPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_MAIN_ACTIVITY, "Estoy en onPause")
        if(mediaPlayer != null)
            position = mediaPlayer!!.currentPosition

        if(isPlaying) mediaPlayer?.pause()
        //isPlaying = false
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_MAIN_ACTIVITY, "Estoy en onStop")

        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_MAIN_ACTIVITY, "Estoy en onRestart")

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_MAIN_ACTIVITY, "Estoy en onDestroy")
    }


    /**
     * ****************************Hasta aqui ciclo de vida***************************
     */


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(MEDIA_PLAYER_POSITION, position)
        outState.putInt(MEDIA_PLAYER_SONG_INDEX, currentSongIndex)
        outState.putBoolean(MEDIA_PLAYER_PLAYSTATUS, isPlaying)
        Log.i(LOG_MAIN_ACTIVITY, "**Estoy salvando el estado $isPlaying**")
    }

    private fun updateUiSong() {
        binding.titleTextView.text = currentSong.title
        binding.albumCoverImageView.setImageResource(currentSong.imageResId)
        updatePlayPauseButton()
    }


    private fun playOrPauseMusic(){

        if (isPlaying){
            mediaPlayer?.pause()
        }else {
            mediaPlayer?.start()
        }
        isPlaying = !isPlaying
        updatePlayPauseButton()
    }

    private fun updatePlayPauseButton(){
        binding.playPauseButton.text = if (isPlaying) "Pause" else "Play"
    }

    private fun playNextSong(){
        currentSongIndex = (currentSongIndex + 1) % AppConstant.song.size
        currentSong=AppConstant.song[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer= MediaPlayer.create(this,currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying=true
        updateUiSong()
    }

    private fun playPreviousSong() {
        currentSongIndex = (currentSongIndex - 1 + AppConstant.song.size) % AppConstant.song.size
        currentSong = AppConstant.song[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        mediaPlayer?.start()
        isPlaying = true
        updateUiSong()
    }

}
