package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.Ww1RemembranceLayoutBinding
import java.util.*

class WW1RemembranceInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: Ww1RemembranceLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Ww1RemembranceLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up TTS engine
        tts = TextToSpeech(this, this)

        // Sample text
        val introText = "PLACEHOLDER"
        binding.introductionTitle1.text = "WW1 Remembrance"
        binding.introductionText.text = introText

        // Arrows
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, TurnOfTheCenturyInfoActivity::class.java))
        }
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, WW2AndPostWarInfoActivity::class.java))
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
