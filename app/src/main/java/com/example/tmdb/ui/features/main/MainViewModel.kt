package com.example.tmdb.ui.features.main

import androidx.lifecycle.ViewModel
import com.example.tmdb.navigations.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

}