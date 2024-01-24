package com.example.tmdb.ui.features.login

import androidx.lifecycle.ViewModel
import com.example.tmdb.navigations.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

}