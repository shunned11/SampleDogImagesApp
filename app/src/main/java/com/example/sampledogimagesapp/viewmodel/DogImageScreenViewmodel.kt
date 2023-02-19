package com.example.sampledogimagesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogimagelibrary.DogImages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DogImageScreenViewmodel : ViewModel() {
    private val _property = MutableLiveData<List<String>>()
    val property: LiveData<List<String>> get() = _property

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _imageIndex = MutableLiveData<Int>()
    val imageIndex: LiveData<Int> get() = _imageIndex
    init {
        getDogProperty()
    }

    fun getNext(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _property.postValue(DogImages.getNextImage())
                _imageIndex.postValue(DogImages.currentIndex)
                _status.postValue("Success")
            } catch (e: Exception) {
                _status.postValue("Failure")
            }
        }
    }
    fun getPrev(){
        _property.value=DogImages.getPreviousImage()
        _status.value="Success"
        _imageIndex.postValue(DogImages.currentIndex)
    }
    fun getDogProperties(number:Int=1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _property.postValue(DogImages.getImages(number))
                _imageIndex.postValue(DogImages.currentIndex)
                _status.postValue("Success")
            } catch (e: Exception) {
                _status.postValue("Failure")
            }
        }
    }
    private fun getDogProperty() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _property.postValue(DogImages.getImage())
                _imageIndex.postValue(DogImages.currentIndex)
                _status.postValue("Success")
            } catch (e: Exception) {
                _status.postValue("Failure")
            }
        }
    }
}

