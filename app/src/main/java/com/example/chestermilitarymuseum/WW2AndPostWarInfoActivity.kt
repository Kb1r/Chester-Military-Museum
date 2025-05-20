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
import com.example.chestermilitarymuseum.databinding.NineteenthCenturyInformationLayoutBinding
import com.example.chestermilitarymuseum.databinding.Ww2AndPostWarInformatonLayoutBinding
import java.util.*

class WW2AndPostWarInfoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: Ww2AndPostWarInformatonLayoutBinding
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Ww2AndPostWarInformatonLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        // Text
        binding.title1.text = getString(R.string.WW2_post_war_conflicts)

        binding.sectionName1.text   = getString(R.string.ww2_and_post_war_section_name1)
        binding.mainTextBody1.text  = getString(R.string.ww2_and_post_war_main_text1)

        binding.sectionName2.text   = getString(R.string.ww2_and_post_war_section_name2)
        binding.mainTextBody2.text  = getString(R.string.ww2_and_post_war_main_text2)

        binding.sectionName3.text   = getString(R.string.ww2_and_post_war_section_name3)
        binding.mainTextBody3.text  = getString(R.string.ww2_and_post_war_main_text3)

        binding.sectionName4.text   = getString(R.string.ww2_and_post_war_section_name4)
        binding.mainTextBody4.text  = getString(R.string.ww2_and_post_war_main_text4)

        binding.sectionName5.text   = getString(R.string.ww2_and_post_war_section_name5)
        binding.mainTextBody5.text  = getString(R.string.ww2_and_post_war_main_text5)

        binding.sectionName6.text   = getString(R.string.ww2_and_post_war_section_name6)
        binding.mainTextBody6.text  = getString(R.string.ww2_and_post_war_main_text6)

        binding.sectionName7.text   = getString(R.string.ww2_and_post_war_section_name7)
        binding.mainTextBody7.text  = getString(R.string.ww2_and_post_war_main_text7)

        binding.sectionName8.text   = getString(R.string.ww2_and_post_war_section_name8)
        binding.mainTextBody8.text  = getString(R.string.ww2_and_post_war_main_text8)

        binding.sectionName9.text   = getString(R.string.ww2_and_post_war_section_name9)
        binding.mainTextBody9.text  = getString(R.string.ww2_and_post_war_main_text9)

        binding.sectionName10.text  = getString(R.string.ww2_and_post_war_section_name10)
        binding.mainTextBody10.text = getString(R.string.ww2_and_post_war_main_text10)

        binding.sectionName11.text  = getString(R.string.ww2_and_post_war_section_name11)
        binding.mainTextBody11.text = getString(R.string.ww2_and_post_war_main_text11)

        binding.sectionName12.text  = getString(R.string.ww2_and_post_war_section_name12)
        binding.mainTextBody12.text = getString(R.string.ww2_and_post_war_main_text12)

        binding.sectionName13.text  = getString(R.string.ww2_and_post_war_section_name13)
        binding.mainTextBody13.text = getString(R.string.ww2_and_post_war_main_text13)

        binding.sectionName14.text  = getString(R.string.ww2_and_post_war_section_name14)
        binding.mainTextBody14.text = getString(R.string.ww2_and_post_war_main_text14)

        binding.displayWarningText.text = getString(R.string.are_you_sure1)
        binding.btnSubmitCode2.text = getString(R.string.confirm)
        binding.displayWarningText.textSize = 24F

        // Navigation arrows
        binding.leftArrow.setOnClickListener {
            startActivity(Intent(this, WW1RemembranceInfoActivity::class.java))
        }
        binding.rightArrow.setOnClickListener {
            binding.warningUI.visibility = View.VISIBLE
            binding.dimOverlay.visibility = View.VISIBLE
        }

        // Close warning box via x
        binding.btnClosePopup2.setOnClickListener {
            binding.warningUI.visibility = View.GONE
            binding.dimOverlay.visibility = View.GONE
        }

        //close warning box via clicking off
        binding.dimOverlay.setOnClickListener {
            binding.warningUI.visibility = View.GONE
            binding.dimOverlay.visibility = View.GONE
        }

        // Return to home page via submit button
        binding.btnSubmitCode2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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
            binding.collapseButton8,
            binding.collapseButton9,
            binding.collapseButton10,
            binding.collapseButton11,
            binding.collapseButton12,
            binding.collapseButton13,
            binding.collapseButton14
        )

        val mainTextBodies = listOf(
            binding.mainTextBody1,
            binding.mainTextBody2,
            binding.mainTextBody3,
            binding.mainTextBody4,
            binding.mainTextBody5,
            binding.mainTextBody6,
            binding.mainTextBody7,
            binding.mainTextBody8,
            binding.mainTextBody9,
            binding.mainTextBody10,
            binding.mainTextBody11,
            binding.mainTextBody12,
            binding.mainTextBody13,
            binding.mainTextBody14
        )

        val fastTransition = AutoTransition().apply { duration = 100L }
        val container = binding.sectionsContainer

        //set an on-click listener to each button
        collapseButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                // Animate text
                TransitionManager.beginDelayedTransition(container, fastTransition)

                // Assigns images based on which section is open
                when(index) {
                    0 -> binding.topImage.setImageResource(R.drawable.world_war2)
                    1 -> binding.topImage.setImageResource(R.drawable.ic_placeholder)
                    2 -> binding.topImage.setImageResource(R.drawable.syria)
                    3 -> binding.topImage.setImageResource(R.drawable.cheshire_men_who_also_served_ww2)
                    4 -> binding.topImage.setImageResource(R.drawable.siege_of_malta)
                    5 -> binding.topImage.setImageResource(R.drawable.the_home_front)
                    6 -> binding.topImage.setImageResource(R.drawable.the_home_guard)
                    7 -> binding.topImage.setImageResource(R.drawable.sicily_and_italy)
                    8 -> binding.topImage.setImageResource(R.drawable.normandy_france_germany)
                    9 -> binding.topImage.setImageResource(R.drawable.eaton_hall_ww2)
                    10 -> binding.topImage.setImageResource(R.drawable.korean_war)
                    11 -> binding.topImage.setImageResource(R.drawable.egypt_to_germany)
                    12 -> binding.topImage.setImageResource(R.drawable.exercising_and_modern_life)
                    13 -> binding.topImage.setImageResource(R.drawable.bosnia)
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
                    0 -> binding.topImage.setImageResource(R.drawable.world_war2)
                    1 -> binding.topImage.setImageResource(R.drawable.ic_placeholder)
                    2 -> binding.topImage.setImageResource(R.drawable.syria)
                    3 -> binding.topImage.setImageResource(R.drawable.cheshire_men_who_also_served_ww2)
                    4 -> binding.topImage.setImageResource(R.drawable.siege_of_malta)
                    5 -> binding.topImage.setImageResource(R.drawable.the_home_front)
                    6 -> binding.topImage.setImageResource(R.drawable.the_home_guard)
                    7 -> binding.topImage.setImageResource(R.drawable.sicily_and_italy)
                    8 -> binding.topImage.setImageResource(R.drawable.normandy_france_germany)
                    9 -> binding.topImage.setImageResource(R.drawable.eaton_hall_ww2)
                    10 -> binding.topImage.setImageResource(R.drawable.korean_war)
                    11 -> binding.topImage.setImageResource(R.drawable.egypt_to_germany)
                    12 -> binding.topImage.setImageResource(R.drawable.exercising_and_modern_life)
                    13 -> binding.topImage.setImageResource(R.drawable.bosnia)
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
            binding.textToSpeechPlayButton3,
            binding.textToSpeechPlayButton4,
            binding.textToSpeechPlayButton5,
            binding.textToSpeechPlayButton6,
            binding.textToSpeechPlayButton7,
            binding.textToSpeechPlayButton8,
            binding.textToSpeechPlayButton9,
            binding.textToSpeechPlayButton10,
            binding.textToSpeechPlayButton11,
            binding.textToSpeechPlayButton12,
            binding.textToSpeechPlayButton13,
            binding.textToSpeechPlayButton14
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
