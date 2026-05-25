package com.example.launchersandproviders

import android.content.ContentResolver
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.launchersandproviders.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var file: File

    val cameraThumbnailLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
            it?.let{viewModel.setPhoto(it)}
        }

    val cameraFullSizeImageLaunchers: ActivityResultLauncher<Uri> =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if(it){
                viewModel.setPhotoUri(Uri.fromFile(file))
            }
        }

    val pickImageLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()){
          //  binding.resultIv.setImageURI(it)
            it?.let{viewModel.setPhotoUri(it)}


        }

    val recognizeSpeechLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                viewModel.setRecognition(it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).toString())
               // binding.textView.text = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).toString()
            }
        }

    val takeTestLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(MyTestActivityContract()){
            it?.let{viewModel.setTestResult(it)}
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.photo.observe(this){
            binding.resultIv.setImageBitmap(it)
        }

        viewModel.photoUri.observe(this){
            Glide.with(this).load(file).into(binding.resultIv)

        }

        viewModel.recognition.observe(this){
            binding.textView.text = it
        }

        viewModel.testResult.observe(this){
            binding.textResult.text = "Your grade is $it"
        }

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
        binding.takeTestBtn.setOnClickListener {
            takeTestLauncher.launch(binding.testerNameEt.text.toString())
        }

        binding.fullSizePicBtn.setOnClickListener {
            file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg")
// Generate a secure content:// URI
            val imageUri = FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.provider",
                file
            )
            Toast.makeText(this,imageUri.toString(),Toast.LENGTH_SHORT).show()

            cameraFullSizeImageLaunchers.launch(imageUri)
        }
    }

}
