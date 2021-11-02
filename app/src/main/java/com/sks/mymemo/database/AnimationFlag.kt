package com.sks.mymemo.database

data class AnimationFlag(
    val doneGone : Int= 0,
    val doSlideOutGone : Int= 1,
    val doSlideInVisible: Int =2,
    val doneVisible: Int = 3
)