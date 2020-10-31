package editing

class Pixel(val red: Int, val green: Int, val blue: Int) {

    constructor(argb8: Int) :
            this(argb8 and 0x0000FF00 shr 8, argb8 and 0x00FF0000 shr 16, argb8 and 0x000000FF)

    constructor() : this(0, 0, 0)

    fun toARGB() = (red shl 16) or (green shl 8) or blue
}