package com.example.chestermilitarymuseum

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.chestermilitarymuseum.databinding.EighteenthCenturyInformationLayoutBinding
import java.util.*

class EighteenthCenturyInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: EighteenthCenturyInformationLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EighteenthCenturyInformationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        // Text
        binding.title1.text = getString(R.string.eighteenth_century)

        binding.sectionName1.text = getString(R.string.eighteenth_section_name1)
        binding.mainTextBody1.text = getString(R.string.eighteenth_main_text1)

        binding.sectionName2.text = getString(R.string.eighteenth_section_name2)
        binding.mainTextBody2.text = getString(R.string.eighteenth_main_text2)

        binding.sectionName3.text = getString(R.string.eighteenth_section_name3)
        binding.mainTextBody3.text = getString(R.string.eighteenth_main_text3)

        binding.sectionName4.text = getString(R.string.eighteenth_section_name4)
        binding.mainTextBody4.text = getString(R.string.eighteenth_main_text4)

        binding.sectionName5.text = getString(R.string.eighteenth_section_name5)
        binding.mainTextBody5.text = getString(R.string.eighteenth_main_text5)

        binding.sectionName6.text = getString(R.string.eighteenth_section_name6)
        binding.mainTextBody6.text = getString(R.string.eighteenth_main_text6)

        // Navigation arrows
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, NineteenthCenturyInfoActivity::class.java))
        }
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, SeventeenthCenturyInfoActivity::class.java))
        }

        // Prepare collapse/expand animation
        val collapseButtons = listOf(
            binding.collapseButton1,
            binding.collapseButton2,
            binding.collapseButton3,
            binding.collapseButton4,
            binding.collapseButton5,
            binding.collapseButton6
        )
        val mainTextBodies = listOf(
            binding.mainTextBody1,
            binding.mainTextBody2,
            binding.mainTextBody3,
            binding.mainTextBody4,
            binding.mainTextBody5,
            binding.mainTextBody6
        )
        val fastTransition = AutoTransition().apply { duration = 100L }
        val container = binding.sectionsContainer

        //set an on-click listener to each button
        collapseButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                // Animate text
                TransitionManager.beginDelayedTransition(container, fastTransition)

                // Hides all text
                val target = mainTextBodies[index]
                val wasVisible = target.visibility == View.VISIBLE
                mainTextBodies.forEach { it.visibility = View.GONE }

                // Resets all buttons to 0 degrees
                collapseButtons.forEach { cb ->
                    if (cb.rotation != 0f) {
                        ObjectAnimator
                            .ofFloat(cb, View.ROTATION, cb.rotation, 0f)
                            .setDuration(200)
                            .start()
                    }
                }

                // If the clicked section was hidden, show it and rotate that button
                if (!wasVisible) {
                    target.visibility = View.VISIBLE
                    ObjectAnimator
                        .ofFloat(button, View.ROTATION, 0f, 90f)
                        .setDuration(200)
                        .start()
                }
            }


            //set on-click listeners for the linear layouts
            val buttonParent = button.parent as LinearLayout
            buttonParent.setOnClickListener {
                // Animate text
                TransitionManager.beginDelayedTransition(container, fastTransition)

                // Hides all text
                val target = mainTextBodies[index]
                val wasVisible = target.visibility == View.VISIBLE
                mainTextBodies.forEach { it.visibility = View.GONE }

                // Resets all buttons to 0 degrees
                collapseButtons.forEach { cb ->
                    if (cb.rotation != 0f) {
                        ObjectAnimator
                            .ofFloat(cb, View.ROTATION, cb.rotation, 0f)
                            .setDuration(200)
                            .start()
                    }
                }

                // If the clicked section was hidden, show it and rotate that button
                if (!wasVisible) {
                    target.visibility = View.VISIBLE
                    ObjectAnimator
                        .ofFloat(button, View.ROTATION, 0f, 90f)
                        .setDuration(200)
                        .start()
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
            binding.textToSpeechPlayButton6
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
