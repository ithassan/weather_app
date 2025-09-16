package com.example.myweatherforcast.data

class DateOrException<T,Boolean,E: Exception> (
    var data:T? = null,
    var loading : Boolean? = null,
    var e: E? = null
    )