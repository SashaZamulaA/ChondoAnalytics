package com.example.aleksandr.myapplication

interface IView {

    fun showLoading()
    fun hideLoading()
    fun loadError(e: Throwable)
    fun loadError(msg: String)
}