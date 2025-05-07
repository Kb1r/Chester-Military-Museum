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
        binding.introductionTitle1.text = "Turn Of The Century"
        binding.introductionText.text = introText

        // Arrows
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, NineteenthCenturyInfoActivity::class.java))
        }
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, WW1RemembranceInfoActivity::class.java))
        }

        // Collapse content
        binding.collapseButton.setOnClickListener {
            val visible = binding.introductionText.visibility == View.VISIBLE
            binding.introductionText.visibility = if (visible) View.GONE else View.VISIBLE
        }

        // TTS playback
        binding.textToSpeechPlayButton.setOnClickListener {
            speakText(binding.introductionText.text.toString())
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
