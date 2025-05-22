package com.example.chestermilitarymuseum

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.IntroductionInformationLayoutBinding
import java.util.*
import androidx.transition.TransitionManager
import androidx.transition.AutoTransition
import com.example.chestermilitarymuseum.databinding.SeventeenthCenturyInformationLayoutBinding


class SeventeenthCenturyInfoActivity : BaseActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: SeventeenthCenturyInformationLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SeventeenthCenturyInformationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        // Text
        binding.title1.text = getString(R.string.seventeenth_century)

        // Section 2
        binding.mainTextBody1.text  = getString(R.string.seventeenth_main_text1)
        binding.sectionName1.text   = getString(R.string.seventeenth_section_name1)

        // Section 3
        binding.mainTextBody2.text  = getString(R.string.seventeenth_main_text2)
        binding.sectionName2.text   = getString(R.string.seventeenth_section_name2)

        // Arrows
        binding.rightArrow.setOnClickListener {
            startActivity(Intent(this, EighteenthCenturyInfoActivity::class.java))
        }
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, IntroductionInfoActivity::class.java))
        }

        //Collapse content
        //Create lists of all buttons and main texts
        val collapseButtons = listOf(
            binding.collapseButton1,
            binding.collapseButton2,
        )
        val mainTextBodies = listOf(
            binding.mainTextBody1,
            binding.mainTextBody2,
            binding.mainTextBody3
        )

        //setting custom shorter animation time for the text visibility
        val fastTransition = AutoTransition().apply {
            duration = 100L
        }

        val container = binding.sectionsContainer

        //set an on-click listener to each button
        collapseButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                // Animate text
                TransitionManager.beginDelayedTransition(container, fastTransition)

                // Assigns images based on which section is open
                when(index) {
                    0 -> binding.topImage.setImageResource(R.drawable.raising_the_cheshires)
                    1 -> binding.topImage.setImageResource(R.drawable.the_cheshire_regiment)
                }


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

                // Assigns images based on which section is open
                when(index) {
                    0 -> binding.topImage.setImageResource(R.drawable.raising_the_cheshires)
                    1 -> binding.topImage.setImageResource(R.drawable.raising_the_cavalry)
                }

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
