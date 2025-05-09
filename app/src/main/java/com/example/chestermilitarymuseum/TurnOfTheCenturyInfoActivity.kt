package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.TurnOfTheCenturyInformationLayoutBinding
import java.util.*

class TurnOfTheCenturyInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: TurnOfTheCenturyInformationLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TurnOfTheCenturyInformationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up TTS engine
        tts = TextToSpeech(this, this)

        // Sample text
        val introText = "PLACEHOLDER"
        binding.title1.text = "Turn Of The Century"
        binding.mainTextBody1.text = introText

        // Arrows
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, NineteenthCenturyInfoActivity::class.java))
        }
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, WW1RemembranceInfoActivity::class.java))
        }

        // Collapse content
        binding.collapseButton.setOnClickListener {
            val visible = binding.mainTextBody1.visibility == View.VISIBLE
            binding.mainTextBody1.visibility = if (visible) View.GONE else View.VISIBLE
        }

        // TTS playback
        binding.textToSpeechPlayButton.setOnClickListener {
            speakText(binding.mainTextBody1.text.toString())
        }
    }

    private fun speakText(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.UK
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
