package ru.gb.weatherproject.repository

import ru.gb.weatherproject.viewmodel.ResponseState

interface OnServerResponseListener {

    fun interface OnServerResponseListener {
        fun onError(error: ResponseState)
    }
}