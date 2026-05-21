package com.example.launchersandproviders

import android.content.ContentResolver
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.launchersandproviders.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val cameraThumbnailLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
            binding.resultIv.setImageBitmap(it)
        }

    val cameraFullSizeImageLaunchers: ActivityResultLauncher<Uri> =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                // The full-size image was successfully saved to the URI!
            }
        }

    val pickImageLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()){
            binding.resultIv.setImageURI(it)


        }

    val recognizeSpeechLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                binding.textView.text = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).toString()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.picBtn.setOnClickListener {
            cameraThumbnailLauncher.launch(null)
        }

        binding.galleryBtn.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*", "video/*"))
        }

        binding.speechBtn.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_RESULTS, 5)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "iw")
                putExtra(RecognizerIntent.EXTRA_PROMPT, "אנא דבר לאט")
            }
            recognizeSpeechLauncher.launch(intent)
        }
    }
}