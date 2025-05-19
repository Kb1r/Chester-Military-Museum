package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.chestermilitarymuseum.databinding.EighteenthCenturyInformationLayoutBinding
import com.example.chestermilitarymuseum.databinding.NineteenthCenturyInformationLayoutBinding
import com.example.chestermilitarymuseum.databinding.Ww1RemembranceLayoutBinding
import java.util.*

class WW1RemembranceInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: Ww1RemembranceLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Ww1RemembranceLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        // Text
        binding.title1.text = getString(R.string.remembrance_ww1)

        binding.sectionName1.text   = getString(R.string.ww1_remembrance_section_name1)
        binding.mainTextBody1.text  = getString(R.string.ww1_remembrance_main_text1)

        binding.sectionName2.text   = getString(R.string.ww1_remembrance_section_name2)
        binding.mainTextBody2.text  = getString(R.string.ww1_remembrance_main_text2)

        binding.sectionName3.text   = getString(R.string.ww1_remembrance_section_name3)
        binding.mainTextBody3.text  = getString(R.string.ww1_remembrance_main_text3)

        binding.sectionName4.text   = getString(R.string.ww1_remembrance_section_name4)
        binding.mainTextBody4.text  = getString(R.string.ww1_remembrance_main_text4)

        binding.sectionName5.text   = getString(R.string.ww1_remembrance_section_name5)
        binding.mainTextBody5.text  = getString(R.string.ww1_remembrance_main_text5)

        binding.sectionName6.text   = getString(R.string.ww1_remembrance_section_name6)
        binding.mainTextBody6.text  = getString(R.string.ww1_remembrance_main_text6)

        binding.sectionName7.text   = getString(R.string.ww1_remembrance_section_name7)
        binding.mainTextBody7.text  = getString(R.string.ww1_remembrance_main_text7)

        binding.sectionName8.text   = getString(R.string.ww1_remembrance_section_name8)
        binding.mainTextBody8.text  = getString(R.string.ww1_remembrance_main_text8)


        // Navigation arrows
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, WW2AndPostWarInfoActivity::class.java))
        }
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, TurnOfTheCenturyInfoActivity::class.java))
        }

        // Prepare collapse/expand animation
        val collapseButtons = listOf(
            binding.collapseButton1,
            binding.collapseButton2,
            binding.collapseButton3,
            binding.collapseButton4,
            binding.collapseButton5,
            binding.collapseButton6,
            binding.collapseButton7,
            binding.collapseButton8
        )
        val mainTextBodies = listOf(
            binding.mainTextBody1,
            binding.mainTextBody2,
            binding.mainTextBody3,
            binding.mainTextBody4,
            binding.mainTextBody5,
            binding.mainTextBody6,
            binding.mainTextBody7,
            binding.mainTextBody8
        )
        val fastTransition = AutoTransition().apply { duration = 100L }
        val container = binding.sectionsContainer

        collapseButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                TransitionManager.beginDelayedTransition(container, fastTransition)
                val targetBody = mainTextBodies[index]
                val wasVisible = targetBody.visibility == View.VISIBLE

                // Hide all bodies
                mainTextBodies.forEach { it.visibility = View.GONE }

                // Toggle clicked section
                if (!wasVisible) {
                    targetBody.visibility = View.VISIBLE
                }
            }
        }

        // Text-to-speech listeners for each section
        val ttsButtons = listOf(
            binding.textToSpeechPlayButton1,
            binding.textToSpeechPlayButton2,
            binding.textToSpeechPlayButton3,
            binding.textToSpeechPlayButton4,
            binding.textToSpeechPlayButton5,
            binding.textToSpeechPlayButton6,
            binding.textToSpeechPlayButton7,
            binding.textToSpeechPlayButton8
        )
        ttsButtons.forEachIndexed { idx, btn ->
            btn.setOnClickListener {
                speakText(mainTextBodies[idx].text.toString())
            }
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
