package com.zamulaaleksandr.aleksandr.myapplication

interface IPresenter<in V:Any> {
    fun bindView(view: V)
    fun unbindView(view: V)
}