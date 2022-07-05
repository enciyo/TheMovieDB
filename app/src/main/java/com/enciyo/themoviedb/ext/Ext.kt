package com.enciyo.themoviedb.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


fun <A> MutableLiveData<A>.asLiveData() = this as LiveData<A>