package com.example.chestermilitarymuseum

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.IntroductionInformationLayoutBinding
import java.util.*

class IntroductionInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: IntroductionInformationLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IntroductionInformationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up TTS engine
        tts = TextToSpeech(this, this)

        // Sample text
        val introText = "This is the first section of your guided tour. It provides an introduction to the museum's history and collection."
        binding.introductionTitle1.text = "Welcome"
        binding.introductionText.text = introText

        // Arrows
        binding.leftArrow.setOnClickListener { finish() }
        binding.rightArrow.setOnClickListener {
            // TODO: Navigate to next screen
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
