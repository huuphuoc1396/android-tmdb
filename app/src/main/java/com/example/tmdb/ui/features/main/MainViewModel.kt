package com.example.tmdb.ui.features.main

import com.example.tmdb.navigations.Navigator
import com.example.tmdb.states.NoUiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigator: Navigator
) : NoUiStateViewModel() {

}