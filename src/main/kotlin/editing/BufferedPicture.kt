package editing

import java.awt.image.BufferedImage


class BufferedPicture : AbstractPicture {
    private val image: BufferedImage

    constructor(bufferedImage: BufferedImage): super(bufferedImage) {
        this.image = deepCopy(bufferedImage)
    }

    constructor(width: Int, height: Int, type: Int): super(width, height, type) {
        this.image = BufferedImage(width, height, type)
    }

    override fun getInstance(width: Int, height: Int, type: Int, func: (Int, Int) -> Pixel): Picture {
        val result = BufferedPicture(width, height, type)
        for (x in 0 until width) {
            for (y in 0 until height) {
                result[x, y] = func(x, y)
            }
        }
        return result
    }

    override operator fun set(x: Int, y: Int, pixel: Pixel) {
        checkArguments(x, y)
        image.setRGB(x, y, pixel.toARGB());
    }

    override operator fun get(x: Int, y: Int): Pixel {
        checkArguments(x, y)
        return Pixel(image.getRGB(x, y))
    }

    override fun getBufferedImage(): BufferedImage {
        return deepCopy(image)
    }

    private fun deepCopy(bi: BufferedImage): BufferedImage {
        val cm = bi.colorModel
        val isAlphaPremultiplied = cm.isAlphaPremultiplied
        val raster = bi.copyData(null)
        return BufferedImage(cm, raster, isAlphaPremultiplied, null)
    }

}