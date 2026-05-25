package com.example.launchersandproviders

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val _photo = MutableLiveData<Bitmap>()
    val photo : LiveData<Bitmap> get() = _photo

    private val _recognition = MutableLiveData<String>()
    val recognition : LiveData<String> get() = _recognition

    private val _photoUri = MutableLiveData<Uri>()
    val photoUri : LiveData<Uri> get() = _photoUri

    private val _testResult = MutableLiveData<Int>()
    val testResult : LiveData<Int> get() = _testResult

    fun setPhoto(bitmap: Bitmap){
        _photo.value = bitmap
    }

    fun setRecognition(str: String){
        _recognition.value = str
    }

    fun setPhotoUri(uri: Uri){
        _photoUri.value = uri
    }

    fun setTestResult(result: Int){
        _testResult.value = result
    }
}
