package ru.megains.tartess.common.utils



object MathHelper {


    val multiplyDeBruijnBitPosition:List[Int] =  List(0 ,1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9)

    val SIN_TABLE = new Array[Float](65536)


    for(i<-0 until 65536 ){
        SIN_TABLE(i) =  Math.sin(i * Math.PI * 2.0D / 65536.0D) toFloat
    }

    def sin(float: Float): Float = {
        SIN_TABLE((float * 10430.378F).toInt & 65535)
    }

    def cos(float: Float): Float = {
        SIN_TABLE((float * 10430.378F + 16384.0F).toInt & 65535)
    }

    def sqrt_float(float: Float): Float = {
        Math.sqrt(float.toDouble).toFloat
    }

    def floor_double(double: Double): Int = {
        val i = double.toInt
        if (double < i.toDouble) i - 1
        else i
    }
}
