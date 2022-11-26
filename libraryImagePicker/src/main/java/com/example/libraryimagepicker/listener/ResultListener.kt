package com.example.libraryimagepicker.listener

internal interface ResultListener<T> {

    fun onResult(t: T?)
}