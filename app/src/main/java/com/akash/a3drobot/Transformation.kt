package com.akash.a3drobot

import kotlin.math.cos
import kotlin.math.sin

object Transformation {
    //*********************************
    //matrix and transformation functions
    fun GetIdentityMatrix(): DoubleArray { //return an 4x4 identity matrix
        val matrix = DoubleArray(16)
        matrix[0] = 1.0
        matrix[1] = 0.0
        matrix[2] = 0.0
        matrix[3] = 0.0
        matrix[4] = 0.0
        matrix[5] = 1.0
        matrix[6] = 0.0
        matrix[7] = 0.0
        matrix[8] = 0.0
        matrix[9] = 0.0
        matrix[10] = 1.0
        matrix[11] = 0.0
        matrix[12] = 0.0
        matrix[13] = 0.0
        matrix[14] = 0.0
        matrix[15] = 1.0
        return matrix
    }

    private fun transform(vertex: Coordinate?, matrix: DoubleArray): Coordinate { //affine transformation with homogeneous coordinates
        //i.e. a vector (vertex) multiply with the transformation matrix
        // vertex - vector in 3D
        // matrix - transformation matrix
        val result = Coordinate()
        result.x = matrix[0] * vertex!!.x + matrix[1] * vertex.y + matrix[2] * vertex.z + matrix[3]
        result.y = matrix[4] * vertex.x + matrix[5] * vertex.y + matrix[6] * vertex.z + matrix[7]
        result.z = matrix[8] * vertex.x + matrix[9] * vertex.y + matrix[10] * vertex.z + matrix[11]
        result.w = matrix[12] * vertex.x + matrix[13] * vertex.y + matrix[14] * vertex.z + matrix[15]
        return result
    }

    private fun transform(vertices: Array<Coordinate?>, matrix: DoubleArray): Array<Coordinate?> {   //Affine transform a 3D object with vertices
        // vertices - vertices of the 3D object.
        // matrix - transformation matrix
        val result: Array<Coordinate?> = arrayOfNulls<Coordinate>(vertices.size)
        for (i in vertices.indices) {
            result[i] = transform(vertices[i], matrix)
            result[i]?.Normalise()
        }
        return result
    }

    //***********************************************************
    //Affine transformation
    fun translate(vertices: Array<Coordinate?>, tx: Double, ty: Double, tz: Double): Array<Coordinate?> {
        val matrix = GetIdentityMatrix()
        matrix[3] = tx
        matrix[7] = ty
        matrix[11] = tz
        return transform(vertices, matrix)
    }

    fun scale(vertices: Array<Coordinate?>, sx: Double, sy: Double, sz: Double): Array<Coordinate?> {
        val matrix = GetIdentityMatrix()
        matrix[0] = sx
        matrix[5] = sy
        matrix[10] = sz
        return transform(vertices, matrix)
    }

    fun rotate(vertices: Array<Coordinate?>, angle: Double, x: Double, y: Double, z: Double): Array<Coordinate?> {
        var angle = angle
        var x = x
        var y = y
        var z = z
        angle = angle * 3.141 / 180
        val w = cos(angle / 2)
        x *= sin(angle / 2)
        y *= sin(angle / 2)
        z *= sin(angle / 2)
        return quaternion(vertices, w, x, y, z)
    }

    fun quaternion(vertices: Array<Coordinate?>, w: Double, x: Double, y: Double, z: Double): Array<Coordinate?> {
        val matrix = GetIdentityMatrix()
        matrix[0] = w * w + x * x - y * y - z * z
        matrix[1] = 2 * x * y - 2 * w * z
        matrix[2] = 2 * x * z + 2 * w * y
        matrix[3] = 0.0
        matrix[4] = 2 * x * y + 2 * w * z
        matrix[5] = w * w - x * x + y * y - z * z
        matrix[6] = 2 * y * z - 2 * w * x
        matrix[7] = 0.0
        matrix[8] = 2 * x * z - 2 * w * y
        matrix[9] = 2 * y * z + 2 * w * x
        matrix[10] = w * w - x * x - y * y + z * z
        matrix[11] = 0.0
        matrix[12] = 0.0
        matrix[13] = 0.0
        matrix[14] = 0.0
        matrix[15] = 1.0
        return transform(vertices, matrix)
    }
}