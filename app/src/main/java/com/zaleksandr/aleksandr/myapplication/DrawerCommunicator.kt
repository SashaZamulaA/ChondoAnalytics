package com.zaleksandr.aleksandr.myapplication

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class DrawerCommunicator @Inject constructor() {

    enum class Action{
        OPEN_DRAWER, CLOSE_DRAWER, LOCK_DRAWER, UNLOCK_DRAWER
    }

    private val subject: PublishSubject<Action> = PublishSubject.create()

    fun openDrawer(){
        subject.onNext(Action.OPEN_DRAWER)
    }

    fun closeDrawer(){
        subject.onNext(Action.CLOSE_DRAWER)
    }

    fun observeIsOpened():Observable<Action> = subject.hide()
    fun setLocked(locked: Boolean) {
        if (locked) subject.onNext(Action.LOCK_DRAWER) else subject.onNext(Action.UNLOCK_DRAWER)
    }


}