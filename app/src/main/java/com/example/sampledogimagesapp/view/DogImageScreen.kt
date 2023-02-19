package com.example.sampledogimagesapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions
import com.example.dogimagelibrary.DogImages
import com.example.dogimagelibrary.network.DogProperty

import com.example.sampledogimagesapp.R
import com.example.sampledogimagesapp.viewmodel.DogImageScreenViewmodel

@Composable
fun DogImageScreen(dogViewModel:DogImageScreenViewmodel= viewModel()) {
    val dogImages by dogViewModel.property.observeAsState()
    val imageIndex by dogViewModel.imageIndex.observeAsState()
    val status by dogViewModel.status.observeAsState()
    var sliderPosition by remember { mutableStateOf(0f) }

    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        if(status!="Failure") {
            LazyColumn(modifier = Modifier.fillMaxHeight(0.75f)) {
                dogImages?.size?.let { count ->
                    items(count) { index ->
                        dogImages?.elementAt(index)?.let { uri -> DogImage(dogImage = uri) }
                    }
                }
            }
        }else{
            Text(
                modifier = Modifier.fillMaxHeight(0.7f),
                text="Please connect to the Internet and Try Again", fontSize = 25.sp,
                textAlign= TextAlign.Center
            )

        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(modifier=Modifier.fillMaxWidth(0.2f),
                text = sliderPosition.toInt().toString()
            )
            Slider(
                modifier=Modifier.fillMaxWidth(0.8f),
                value = sliderPosition,
                valueRange = 0f..10f,
                steps=10,
                onValueChange = { sliderPosition = it }
            )
        }
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Button(
                enabled=imageIndex!=0,
                onClick = {dogViewModel.getPrev()}) {
                Text(text = "Previous")
            }
            Button(
                enabled=sliderPosition.toInt()!=0,
                onClick = {dogViewModel.getDogProperties(sliderPosition.toInt())}) {
                Text(text = "Get ${sliderPosition.toInt()}")
            }
            Button(onClick = {dogViewModel.getNext()}) {
                Text(text = "Next")
            }
        }


    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DogImage(dogImage:String){
    GlideImage(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .height(350.dp)
            .clip(RoundedCornerShape(20.dp)),
        model = dogImage,
        contentScale = ContentScale.Crop,
        contentDescription ="Picture of Dog"

    ){
        it.apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image))
    }
    
}

