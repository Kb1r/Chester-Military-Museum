package com.example.chestermilitarymuseum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.ContactFormBinding

class ContactFormActivity : BaseActivity() {

    private lateinit var binding: ActivityBaseBinding
    private lateinit var formBinding: ContactFormBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate main layout
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.headerTitle.text = "Contact Us"

        // Inflate contact form
        formBinding = ContactFormBinding.inflate(layoutInflater)
        binding.container.addView(formBinding.root)
        setContactFormText()

        // Handle submit
        formBinding.submitButton.setOnClickListener {
            val name = formBinding.nameEditText.text.toString().trim()
            val email = formBinding.emailEditText.text.toString().trim()
            val phone = formBinding.phoneEditText.text.toString().trim()
            val message = formBinding.messageEditText.text.toString().trim()

            // Validation checks
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (message.isEmpty()) {
                Toast.makeText(this, "Please enter your message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Clear fields after successful validation
            formBinding.nameEditText.text?.clear()
            formBinding.emailEditText.text?.clear()
            formBinding.phoneEditText.text?.clear()
            formBinding.messageEditText.text?.clear()

            Toast.makeText(this, "Submitted!", Toast.LENGTH_SHORT).show()
        }

        // Logo click → Home
        binding.logoImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity((Intent(this, MainActivity::class.java)))
                    true
                }
                R.id.navigation_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.navigation_news -> {
                    startActivity(Intent(this, NewsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        formBinding.btnTwitter.setOnClickListener{
            val twitter = Intent(Intent.ACTION_VIEW, Uri.parse("https://x.com/ChesMilMuseum/"))
            startActivity(twitter)
        }

        formBinding.btnFacebook.setOnClickListener{
            val facebook = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/cheshiremilitarymuseum"))
            startActivity(facebook)
        }

        formBinding.btnInstagram.setOnClickListener{
            val instagram = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/cheshiremilitary_museum/"))
            startActivity(instagram)
        }
    }

    private fun setContactFormText(){
        //Code adapted from A--C (January 2013).
        formBinding.nameEditText.setHint(R.string.nameEditText)
        formBinding.emailEditText.setHint(R.string.emailEditText)
        formBinding.phoneEditText.setHint(R.string.phoneEditText)
        formBinding.messageEditText.setHint(R.string.messageEditText)
        formBinding.submitButton.text = getText(R.string.submitButton)
        binding.headerTitle.text = getText(R.string.headerTitle)

        //End of adapted code.
    }
}
