package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.IntroductionInformationLayoutBinding
import java.util.*
import androidx.transition.TransitionManager
import androidx.transition.AutoTransition


class IntroductionInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: IntroductionInformationLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IntroductionInformationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        // Text
        binding.title1.text = getString(R.string.introduction)
        // Section 1
        binding.mainTextBody1.text  = getString(R.string.introduction_main_text1)
        binding.sectionName1.text   = getString(R.string.introduction_section_name1)

        // Section 2
        binding.mainTextBody2.text  = getString(R.string.introduction_main_text2)
        binding.sectionName2.text   = getString(R.string.introduction_section_name2)

        // Section 3
        binding.mainTextBody3.text  = getString(R.string.introduction_main_text3)
        binding.sectionName3.text   = getString(R.string.introduction_section_name3)

        // Section 4
        binding.mainTextBody4.text  = getString(R.string.introduction_main_text4)
        binding.sectionName4.text   = getString(R.string.introduction_section_name4)

        // Section 5
        binding.mainTextBody5.text  = getString(R.string.introduction_main_text5)
        binding.sectionName5.text   = getString(R.string.introduction_section_name5)



        // Arrows
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, SeventeenthCenturyInfoActivity::class.java))
        }

        //Collapse content
        //Create lists of all buttons and main texts
        val collapseButtons = listOf(
            binding.collapseButton1,
            binding.collapseButton2,
            binding.collapseButton3,
            binding.collapseButton4,
            binding.collapseButton5
        )
        val mainTextBodies = listOf(
            binding.mainTextBody1,
            binding.mainTextBody2,
            binding.mainTextBody3,
            binding.mainTextBody4,
            binding.mainTextBody5
        )

        //setting custom shorter animation time for the text visibility
        val fastTransition = AutoTransition().apply {
            duration = 100L
        }

        val container = binding.sectionsContainer

        //set an on-click listener to each button
        collapseButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                //Use the fast transition
                TransitionManager.beginDelayedTransition(container, fastTransition)

                val target = mainTextBodies[index]
                val wasVisible = (target.visibility == View.VISIBLE)

                mainTextBodies.forEach { it.visibility = View.GONE }
                if (!wasVisible) {
                    target.visibility = View.VISIBLE
                }
            }
        }

        // Text-to-speech listeners for each section
        val ttsButtons = listOf(
            binding.textToSpeechPlayButton1,
            binding.textToSpeechPlayButton2,
            binding.textToSpeechPlayButton3,
            binding.textToSpeechPlayButton4,
            binding.textToSpeechPlayButton5
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
