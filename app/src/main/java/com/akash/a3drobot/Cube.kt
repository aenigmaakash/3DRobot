package com.akash.a3drobot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class Cube @JvmOverloads constructor(parent: Cube? = null) {
    private var parent: Cube? = null
    private var vertices //the vertices of a 3D cube
            : Array<Coordinate?>
    private lateinit var draw_vertices: Array<Coordinate?>
    lateinit var paint: Paint
    var tx = 0.0
        private set
    var ty = 0.0
        private set
    var tz = 0.0
        private set

    private var angle = 0.0
    private var ax = 0.0
    private var ay = 0.0
    private var az = 0.0
    private var rx = 0.0
    private var ry = 0.0
    private var rz = 0.0
    var sx = 0.0
        private set
    var sy = 0.0
        private set
    var sz = 0.0
        private set

    var maxZ = 1.0
        private set

    private fun DrawFace(canvas: Canvas, vertices: Array<Coordinate?>, v0: Int, v1: Int, v2: Int, v3: Int, paint: Paint) {
        val path = Path()
        path.moveTo(vertices[v0]!!.x.toFloat(), vertices[v0]!!.y.toFloat())
        path.lineTo(vertices[v1]!!.x.toFloat(), vertices[v1]!!.y.toFloat())
        path.lineTo(vertices[v2]!!.x.toFloat(), vertices[v2]!!.y.toFloat())
        path.lineTo(vertices[v3]!!.x.toFloat(), vertices[v3]!!.y.toFloat())
        path.close()
        canvas.drawPath(path, paint)
    }

    fun setup(sx: Double, sy: Double, sz: Double) {
        this.sx = sx
        this.sy = sy
        this.sz = sz
        vertices = Transformation.scale(vertices, sx, sy, sz)
    }

    fun setTranslate(tx: Double, ty: Double, tz: Double) {
        this.tx = tx
        this.ty = ty
        this.tz = tz
    }

    fun setRotate(angle: Double, ax: Double, ay: Double, az: Double) {
        this.angle = angle
        this.ax = ax
        this.ay = ay
        this.az = az
    }

    fun setRotateOffset(rx: Double, ry: Double, rz: Double) {
        this.rx = rx
        this.ry = ry
        this.rz = rz
    }

    fun transform(vertices: Array<Coordinate?>): Array<Coordinate?> {
        var draw_cube_vertices: Array<Coordinate?> = vertices
        draw_cube_vertices = Transformation.translate(draw_cube_vertices, tx, ty, tz)
        draw_cube_vertices = Transformation.translate(draw_cube_vertices, rx, ry, rz)
        draw_cube_vertices = Transformation.rotate(draw_cube_vertices, angle, ax, ay, az)
        draw_cube_vertices = Transformation.translate(draw_cube_vertices, -rx, -ry, -rz)
        if (parent != null) {
            draw_cube_vertices = parent!!.transform(draw_cube_vertices)
        }
        return draw_cube_vertices
    }

    fun preDraw(paint: Paint): Double {
        this.paint = paint
        draw_vertices = transform(vertices)
        maxZ = draw_vertices[0]!!.z
        for (c in draw_vertices) {
            if (maxZ < c!!.z) {
                maxZ = c.z
            }
        }
        return maxZ
    }

    fun draw(canvas: Canvas) {
        //draw a cube on the screen
        DrawFace(canvas, draw_vertices, 0, 1, 3, 2, paint)
        DrawFace(canvas, draw_vertices, 0, 1, 5, 4, paint)
        DrawFace(canvas, draw_vertices, 0, 2, 6, 4, paint)
        DrawFace(canvas, draw_vertices, 1, 3, 7, 5, paint)
        DrawFace(canvas, draw_vertices, 2, 3, 7, 6, paint)
        DrawFace(canvas, draw_vertices, 4, 5, 7, 6, paint)
    }

    init {
        this.parent = parent
        vertices = arrayOfNulls<Coordinate>(8)
        vertices[0] = Coordinate(-1.0, -1.0, -1.0, 1.0)
        vertices[1] = Coordinate(-1.0, -1.0, 1.0, 1.0)
        vertices[2] = Coordinate(-1.0, 1.0, -1.0, 1.0)
        vertices[3] = Coordinate(-1.0, 1.0, 1.0, 1.0)
        vertices[4] = Coordinate(1.0, -1.0, -1.0, 1.0)
        vertices[5] = Coordinate(1.0, -1.0, 1.0, 1.0)
        vertices[6] = Coordinate(1.0, 1.0, -1.0, 1.0)
        vertices[7] = Coordinate(1.0, 1.0, 1.0, 1.0)
        setup(1.0, 1.0, 1.0)
        setTranslate(0.0, 0.0, 0.0)
        setRotate(0.0, 0.0, 1.0, 0.0)
        setRotateOffset(0.0, 0.0, 0.0)
    }
}