package editing

import java.awt.image.BufferedImage

class ArrayPicture(width: Int, height: Int, type: Int, func: (Int, Int) -> Pixel) :
    AbstractPicture(width, height, type) {

    private val image: Array<Array<Pixel>>

    init {
        image = Array(width) { x -> Array(height) { y -> func(x, y) } }
    }

    constructor(bufferedImage: BufferedImage) : this(bufferedImage.width, bufferedImage.height, bufferedImage.type,
        { x, y -> Pixel(bufferedImage.getRGB(x, y)) })

    constructor(width: Int, height: Int, type: Int) : this(width, height, type, { _, _ -> Pixel()})

    override fun getInstance(width: Int, height: Int, type: Int, func: (Int, Int) -> Pixel): Picture {
        return ArrayPicture(width, height, type, func)
    }

    override operator fun set(x: Int, y: Int, pixel: Pixel) {
        checkArguments(x, y)
        image[x][y] = pixel
    }

    override operator fun get(x: Int, y: Int): Pixel {
        checkArguments(x, y)
        return image[x][y]
    }

    override fun getBufferedImage(): BufferedImage {
        val result = BufferedImage(width, height, type)
        for (x in 0 until width) {
            for (y in 0 until height) {
                result.setRGB(x, y, this[x, y].toARGB())
            }
        }
        return result
    }
}