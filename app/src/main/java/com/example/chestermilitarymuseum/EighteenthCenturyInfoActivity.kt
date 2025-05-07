package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.EighteenthCenturyInformationLayoutBinding
import java.util.*

class EighteenthCenturyInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: EighteenthCenturyInformationLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EighteenthCenturyInformationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up TTS engine
        tts = TextToSpeech(this, this)

        // Sample text
        val introText = "PLACEHOLDER"
        binding.mainTextBody1.text = "18th Century"
        binding.mainTextBody1.text = introText

        // Arrows
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, SeventeenthCenturyInfoActivity::class.java))
        }
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, NineteenthCenturyInfoActivity::class.java))
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
