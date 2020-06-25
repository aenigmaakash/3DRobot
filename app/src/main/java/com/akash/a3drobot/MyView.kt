package com.akash.a3drobot

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import java.util.*

class MyView(context: Context?) : View(context, null) {
    private val redPaint //paint object for drawing the lines
            : Paint
    private val greenPaint: Paint
    private val bluePaint: Paint
    private val cyanPaint: Paint
    private val purplePaint: Paint
    private val grayPaint: Paint
    private val blackPaint: Paint
    private val paintStyle = Paint.Style.FILL_AND_STROKE
    var screenWidth = 0.0
    var screenHeight = 0.0
    private val dummy: Cube
    private val head: Cube
    private val neck: Cube
    private val body: Cube
    private val hip: Cube
    private val leftArm1: Cube
    private val leftArm2: Cube
    private val leftArm3: Cube
    private val rightArm1: Cube
    private val rightArm2: Cube
    private val rightArm3: Cube
    private val leftLeg1: Cube
    private val leftLeg2: Cube
    private val leftLeg3: Cube
    private val rightLeg1: Cube
    private val rightLeg2: Cube
    private val rightLeg3: Cube
    private val hipAngle: MovingAngle
    private val leftLegAngle: MovingAngle
    private val rightLegAngle: MovingAngle
    private val leftArmAngle: MovingAngle
    private val rightArmAngle: MovingAngle
    private val sequence: Array<MovingParam?>
    private var cs // current sequence
            : Int

    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toDouble()
        screenHeight = h.toDouble()
        dummy.setup(screenWidth / 4, screenHeight / 4, 1.0)
        dummy.setTranslate(screenWidth / 2, screenHeight / 2, 0.0)
        dummy.setRotate(180.0, 0.0, 1.0, 0.0)
        dummy.setRotateOffset(-dummy.tx, -dummy.ty, 0.0)
    }

    override fun onDraw(canvas: Canvas) {
        //draw objects on the screen
        super.onDraw(canvas)
        canvas.drawPaint(grayPaint)
        canvas.drawText("Written in Kotlin", (screenWidth/5).toFloat(), (screenHeight/20).toFloat(), blackPaint)
        hip.preDraw(purplePaint)
        body.preDraw(redPaint)
        neck.preDraw(purplePaint)
        head.preDraw(bluePaint)
        leftArm1.preDraw(bluePaint)
        leftArm2.preDraw(greenPaint)
        leftArm3.preDraw(cyanPaint)
        rightArm1.preDraw(bluePaint)
        rightArm2.preDraw(greenPaint)
        rightArm3.preDraw(cyanPaint)
        leftLeg1.preDraw(bluePaint)
        leftLeg2.preDraw(greenPaint)
        leftLeg3.preDraw(redPaint)
        rightLeg1.preDraw(bluePaint)
        rightLeg2.preDraw(greenPaint)
        rightLeg3.preDraw(redPaint)
        val renderCubes: Array<Cube> = arrayOf<Cube>(
            hip,
            body,
            neck,
            head,
            leftArm1, leftArm2, leftArm3,
            rightArm1, rightArm2, rightArm3,
            leftLeg1, leftLeg2, leftLeg3,
            rightLeg1, rightLeg2, rightLeg3)

        Arrays.sort(renderCubes, Comparator<Cube> { o1, o2 -> o2.maxZ.compareTo(o1.maxZ) })
        for (c in renderCubes) {
            c.draw(canvas)
        }
    }

    init {
        val thisView = this
        //create the paint object
        grayPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        grayPaint.color = Color.GRAY
        blackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        blackPaint.color = Color.BLACK
        blackPaint.textSize = 100f
        redPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        redPaint.style = paintStyle
        redPaint.color = Color.RED
        redPaint.strokeWidth = 2f
        greenPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        greenPaint.style = paintStyle
        greenPaint.color = Color.GREEN
        greenPaint.strokeWidth = 2f
        bluePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bluePaint.style = paintStyle
        bluePaint.color = Color.BLUE
        bluePaint.strokeWidth = 2f
        cyanPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        cyanPaint.style = paintStyle
        cyanPaint.color = Color.CYAN
        cyanPaint.strokeWidth = 2f
        purplePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        purplePaint.style = paintStyle
        purplePaint.color = Color.MAGENTA
        purplePaint.strokeWidth = 2f
        dummy = Cube()
        hip = Cube(dummy)
        body = Cube(hip)
        neck = Cube(body)
        head = Cube(neck)
        leftArm1 = Cube(body)
        leftArm2 = Cube(leftArm1)
        leftArm3 = Cube(leftArm2)
        rightArm1 = Cube(body)
        rightArm2 = Cube(rightArm1)
        rightArm3 = Cube(rightArm2)
        leftLeg1 = Cube(hip)
        leftLeg2 = Cube(leftLeg1)
        leftLeg3 = Cube(leftLeg2)
        rightLeg1 = Cube(hip)
        rightLeg2 = Cube(rightLeg1)
        rightLeg3 = Cube(rightLeg2)
        //////////////////////////////////////////////////////////////////
        hip.setup(180.0, 50.0, 60.0)
        hip.setTranslate(0.0, 0.0, 0.0)
        body.setup(hip.sx, 220.0, hip.sz)
        body.setTranslate(0.0, -(body.sy + hip.sy), 0.0)
        neck.setup(40.0, 30.0, 20.0)
        neck.setTranslate(0.0, -(body.sy + neck.sy), 0.0)
        head.setup(80.0, 80.0, 80.0)
        head.setTranslate(0.0, -(neck.sy + head.sy), 0.0)
        leftArm1.setup(body.sx / 3, 90.0, 40.0)
        leftArm1.setTranslate(-(body.sx + leftArm1.sx), -(body.sy - leftArm1.sy), 0.0)
        leftArm2.setup(body.sx / 3, 110.0, 40.0)
        leftArm2.setTranslate(0.0, leftArm1.sy + leftArm2.sy, 0.0)
        leftArm3.setup(body.sx / 3, 30.0, 80.0)
        leftArm3.setTranslate(0.0, leftArm2.sy + leftArm3.sy, 30.0)
        rightArm1.setup(body.sx / 3, 90.0, 40.0)
        rightArm1.setTranslate(body.sx + rightArm1.sx, -(body.sy - rightArm1.sy), 0.0)
        rightArm2.setup(body.sx / 3, 110.0, 40.0)
        rightArm2.setTranslate(0.0, rightArm1.sy + rightArm2.sy, 0.0)
        rightArm3.setup(body.sx / 3, 30.0, 80.0)
        rightArm3.setTranslate(0.0, rightArm2.sy + rightArm3.sy, 30.0)
        leftLeg1.setup(body.sx / 3, 100.0, 40.0)
        leftLeg1.setTranslate(-body.sx / 3 * 2, hip.sy + leftLeg1.sy, 0.0)
        leftLeg1.setRotateOffset(0.0, -leftLeg1.ty + leftLeg1.sy, leftLeg1.sz)
        leftLeg2.setup(body.sx / 3, 130.0, 40.0)
        leftLeg2.setTranslate(0.0, leftLeg1.sy + leftLeg2.sy, 0.0)
        leftLeg2.setRotateOffset(0.0, -leftLeg2.ty + leftLeg2.sy, leftLeg2.sz)
        leftLeg3.setup(body.sx / 3, 30.0, 80.0)
        leftLeg3.setTranslate(0.0, leftLeg2.sy + leftLeg3.sy, 20.0)
        rightLeg1.setup(body.sx / 3, 100.0, 40.0)
        rightLeg1.setTranslate(body.sx / 3 * 2, hip.sy + rightLeg1.sy, 0.0)
        rightLeg1.setRotateOffset(0.0, -rightLeg1.ty + rightLeg1.sy, rightLeg1.sz)
        rightLeg2.setup(body.sx / 3, 130.0, 40.0)
        rightLeg2.setTranslate(0.0, rightLeg1.sy + rightLeg2.sy, 0.0)
        rightLeg2.setRotateOffset(0.0, -rightLeg2.ty + rightLeg2.sy, rightLeg2.sz)
        rightLeg3.setup(body.sx / 3, 30.0, 80.0)
        rightLeg3.setTranslate(0.0, rightLeg2.sy + rightLeg3.sy, 20.0)
        ///////////////////////////////////////////////////////////////////
        hipAngle = MovingAngle(-45.0, 45.0, 0.2)
        leftLegAngle = MovingAngle(0.0, 45.0, 1.0)
        rightLegAngle = MovingAngle(0.0, 45.0, 1.0)
        rightLegAngle.isPause = true
        leftArmAngle = MovingAngle(0.0, 45.0, 1.0)
        rightArmAngle = MovingAngle(0.0, 45.0, 1.0)
        leftArmAngle.isPause = true
        sequence = arrayOfNulls<MovingParam>(16) // LLRRLLRRLLRRLLRR
        for (i in sequence.indices) {
            sequence[i] = MovingParam()
        }
        // sequence 1
        sequence[0]?.ax = 1.0
        sequence[0]?.ay = 0.0
        sequence[0]?.az = 0.0
        sequence[0]?.rx = 0.0
        sequence[0]?.ry = -leftArm1.ty + leftArm1.sy
        sequence[0]?.rz = -leftArm1.sz
        sequence[1]?.ax = 1.0
        sequence[1]?.ay = 0.0
        sequence[1]?.az = 0.0
        sequence[1]?.rx = 0.0
        sequence[1]?.ry = -leftArm2.ty + leftArm2.sy
        sequence[1]?.rz = -leftArm2.sz
        sequence[2]?.ax = 1.0
        sequence[2]?.ay = 0.0
        sequence[2]?.az = 0.0
        sequence[2]?.rx = 0.0
        sequence[2]?.ry = -rightArm1.ty + rightArm1.sy
        sequence[2]?.rz = -rightArm1.sz
        sequence[3]?.ax = 1.0
        sequence[3]?.ay = 0.0
        sequence[3]?.az = 0.0
        sequence[3]?.rx = 0.0
        sequence[3]?.ry = -rightArm2.ty + rightArm2.sy
        sequence[3]?.rz = -rightArm2.sz

        // sequence 2
        sequence[4]?.ax = 0.0
        sequence[4]?.ay = 0.0
        sequence[4]?.az = 1.0
        sequence[4]?.rx = -leftArm1.tx - leftArm1.sx
        sequence[4]?.ry = -leftArm1.ty + leftArm1.sy
        sequence[4]?.rz = 0.0
        sequence[5]?.ax = 0.0
        sequence[5]?.ay = 0.0
        sequence[5]?.az = 1.0
        sequence[5]?.rx = -leftArm2.tx + leftArm2.sx
        sequence[5]?.ry = -leftArm2.ty + leftArm2.sy
        sequence[5]?.rz = 0.0
        sequence[6]?.ax = 0.0
        sequence[6]?.ay = 0.0
        sequence[6]?.az = -1.0
        sequence[6]?.rx = -rightArm1.tx + rightArm1.sx
        sequence[6]?.ry = -rightArm1.ty + rightArm1.sy
        sequence[6]?.rz = 0.0
        sequence[7]?.ax = 0.0
        sequence[7]?.ay = 0.0
        sequence[7]?.az = -1.0
        sequence[7]?.rx = -rightArm2.tx - rightArm2.sx
        sequence[7]?.ry = -rightArm2.ty + rightArm2.sy
        sequence[7]?.rz = 0.0

        // sequence 3
        sequence[8]?.ax = 1.0
        sequence[8]?.ay = 1.0
        sequence[8]?.az = 0.0
        sequence[8]?.rx = 0.0
        sequence[8]?.ry = -leftArm1.ty + leftArm1.sy
        sequence[8]?.rz = 0.0
        sequence[9]?.ax = 1.0
        sequence[9]?.ay = 1.0
        sequence[9]?.az = 0.0
        sequence[9]?.rx = 0.0
        sequence[9]?.ry = -leftArm2.ty + leftArm2.sy
        sequence[9]?.rz = 0.0
        sequence[10]?.ax = 1.0
        sequence[10]?.ay = 1.0
        sequence[10]?.az = 0.0
        sequence[10]?.rx = 0.0
        sequence[10]?.ry = -rightArm1.ty + rightArm1.sy
        sequence[10]?.rz = 0.0
        sequence[11]?.ax = 1.0
        sequence[11]?.ay = 1.0
        sequence[11]?.az = 0.0
        sequence[11]?.rx = 0.0
        sequence[11]?.ry = -rightArm2.ty + rightArm2.sy
        sequence[11]?.rz = 0.0

        // sequence 4
        sequence[12]?.ax = 1.0
        sequence[12]?.ay = -1.0
        sequence[12]?.az = 0.0
        sequence[12]?.rx = 0.0
        sequence[12]?.ry = -leftArm1.ty + leftArm1.sy
        sequence[12]?.rz = 0.0
        sequence[13]?.ax = 1.0
        sequence[13]?.ay = -1.0
        sequence[13]?.az = 0.0
        sequence[13]?.rx = 0.0
        sequence[13]?.ry = -leftArm2.ty + leftArm2.sy
        sequence[13]?.rz = 0.0
        sequence[14]?.ax = 1.0
        sequence[14]?.ay = -1.0
        sequence[14]?.az = 0.0
        sequence[14]?.rx = 0.0
        sequence[14]?.ry = -rightArm1.ty + rightArm1.sy
        sequence[14]?.rz = 0.0
        sequence[15]?.ax = 1.0
        sequence[15]?.ay = -1.0
        sequence[15]?.az = 0.0
        sequence[15]?.rx = 0.0
        sequence[15]?.ry = -rightArm2.ty + rightArm2.sy
        sequence[15]?.rz = 0.0
        cs = 0 // start with first sequence
        thisView.invalidate() //update the view
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                hipAngle.advance()
                //                hipAngle.setAngle(0);
                leftLegAngle.advance()
                rightLegAngle.advance()
                leftArmAngle.advance()
                rightArmAngle.advance()
                if (cs <= 1) {
                    if (!leftLegAngle.isPause && leftLegAngle.angle == 0.0) {
                        rightLegAngle.isPause = false
                        leftLegAngle.isPause = true
                    } else if (!rightLegAngle.isPause && rightLegAngle.angle == 0.0) {
                        leftLegAngle.isPause = false
                        rightLegAngle.isPause = true
                    }
                    if (!rightArmAngle.isPause && rightArmAngle.angle == 0.0) {
                        leftArmAngle.isPause = false
                        rightArmAngle.isPause = true
                    } else if (!leftArmAngle.isPause && leftArmAngle.angle == 0.0) {
                        rightArmAngle.isPause = false
                        leftArmAngle.isPause = true
                        cs = (cs + 1) % 4
                        if (cs >= 2) {
                            leftArmAngle.isPause = false
                            leftLegAngle.isPause = true
                            rightLegAngle.isPause = true
                        }
                    }
                } else {
                    if (leftArmAngle.angle == 0.0) {
                        cs = (cs + 1) % 4
                        if (cs == 0) {
                            leftArmAngle.isPause = true
                            rightLegAngle.isPause = false
                        }
                    }
                }
                hip.setRotate(hipAngle.angle, 0.0, 1.0, 0.0)
                leftArm1.setRotate(leftArmAngle.angle, sequence[4 * cs]!!.ax, sequence[4 * cs]!!.ay, sequence[4 * cs]!!.az)
                leftArm1.setRotateOffset(sequence[4 * cs]!!.rx, sequence[4 * cs]!!.ry, sequence[4 * cs]!!.rz)
                leftArm2.setRotate(leftArmAngle.angle * 2, sequence[4 * cs + 1]!!.ax, sequence[4 * cs + 1]!!.ay, sequence[4 * cs + 1]!!.az)
                leftArm2.setRotateOffset(sequence[4 * cs + 1]!!.rx, sequence[4 * cs + 1]!!.ry, sequence[4 * cs + 1]!!.rz)
                rightArm1.setRotate(rightArmAngle.angle, sequence[4 * cs + 2]!!.ax, sequence[4 * cs + 2]!!.ay, sequence[4 * cs + 2]!!.az)
                rightArm1.setRotateOffset(sequence[4 * cs + 2]!!.rx, sequence[4 * cs + 2]!!.ry, sequence[4 * cs + 2]!!.rz)
                rightArm2.setRotate(rightArmAngle.angle * 2, sequence[4 * cs + 3]!!.ax, sequence[4 * cs + 3]!!.ay, sequence[4 * cs + 3]!!.az)
                rightArm2.setRotateOffset(sequence[4 * cs + 3]!!.rx, sequence[4 * cs + 3]!!.ry, sequence[4 * cs + 3]!!.rz)
                leftLeg1.setRotate(leftLegAngle.angle, 1.0, 0.0, 0.0)
                leftLeg2.setRotate(leftLegAngle.angle, -1.0, 0.0, 0.0)
                rightLeg1.setRotate(rightLegAngle.angle, 1.0, 0.0, 0.0)
                rightLeg2.setRotate(rightLegAngle.angle, -1.0, 0.0, 0.0)
                thisView.invalidate() //update the view
            }
        }
        timer.scheduleAtFixedRate(task, 100, 4)
    }
}