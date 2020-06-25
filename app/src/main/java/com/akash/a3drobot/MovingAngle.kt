package com.akash.a3drobot

class MovingAngle(private val angleMin: Double, private val angleMax: Double, private var delta: Double) {

    var angle = 0.0

    var isPause = false

    fun advance() {
        if (isPause) return
        angle += delta
        if (angle >= angleMax) {
            angle = angleMax
            delta *= -1.0
        } else if (angle <= angleMin) {
            angle = angleMin
            delta *= -1.0
        }
    }
}