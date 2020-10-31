package editing

import java.awt.image.BufferedImage

interface Picture {
    val width: Int
    val height: Int
    val type: Int
    operator fun set(x: Int, y: Int, pixel: Pixel)
    operator fun get(x: Int, y: Int): Pixel
    fun getBufferedImage(): BufferedImage
    fun getTransposed(): Picture
    fun getInstance(width: Int, height: Int, type: Int, func: (Int, Int) -> Pixel): Picture
}