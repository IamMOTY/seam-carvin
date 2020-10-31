package editing

import java.awt.image.BufferedImage

abstract class AbstractPicture
    (final override val width: Int, final override val height: Int, override val type: Int) : Picture {

    init {
        if (width <= 0 || height <= 0) {
            throw IllegalArgumentException("Incorrect size: width=$width, height=height")
        }
    }

    constructor(bufferedImage: BufferedImage) :
            this(bufferedImage.width, bufferedImage.height, bufferedImage.type)


    override fun getTransposed(): Picture {
        return getInstance(height, width, type) { x, y -> this[y, x] }
    }

    protected fun checkArguments(x: Int, y: Int) {
        if (x < 0 || y < 0 || x > width || y > height) {
            throw IllegalArgumentException("x = $x width = $width  y = $y  height = $height")
        }
    }
}