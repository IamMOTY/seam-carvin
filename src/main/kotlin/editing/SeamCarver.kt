package editing

import kotlin.math.pow
import kotlin.math.sqrt

class SeamCarver {
    fun resize(image: Picture, cropWidth: Int, cropHeight: Int): Picture {
        return getCroppedByWidth(getCroppedByWidth(image, cropWidth).getTransposed(), cropHeight).getTransposed()
    }

    private fun getCroppedByWidth(image: Picture, cropWidth: Int): Picture {
        var result = image
        for (i in 0 until cropWidth) {
            result = deleteSeam(result)
        }
        return result
    }


    private fun deleteSeam(image: Picture): Picture {
        val seam = getSeam(image)
        return image.getInstance(image.width - 1, image.height, image.type)
            { x, y -> if (x < seam[y]) image[x, y] else image[x + 1, y] }
    }

    private fun getSeam(image: Picture): Array<Int> {
        fun calculatePathMatrix(energy: Array<Array<Double>>): Array<Array<Double>> {
            val result = Array(energy.size) { Array(energy[0].size) { 0.0 } }
            for (i in result.indices) {
                result[i][0] = energy[i][0]
            }
            for (y in 1 until result[0].size) {
                for (x in result.indices) {
                    result[x][y] = result[x][y - 1]
                    if (x > 0) {
                        result[x][y] = minOf(result[x][y], result[x - 1][y - 1])
                    }
                    if (x < energy.size - 1) {
                        result[x][y] = minOf(result[x][y], result[x + 1][y - 1])
                    }
                    result[x][y] = minOf(result[x][y], result[x][y - 1])
                    result[x][y] += energy[x][y]
                }
            }
            return result
        }

        val paths = calculatePathMatrix(getImageEnergy(image))
        val result = Array(paths[0].size) { 0 }
        val last = result.size - 1
        var min = paths[0][last]
        for (x in paths.indices) {
            if (paths[x][last] < min) {
                result[last] = x
                min = paths[x][last]
            }
        }
        for (y in last - 1 downTo 0) {
            result[y] = result[y + 1]
            var localMin = paths[result[y]][y]
            for (x in result[y + 1] - 1..result[y + 1] + 1) {
                if (x < 0 || x == paths.size) {
                    continue
                }
                if (paths[x][y] < localMin) {
                    result[y] = x
                    localMin = paths[x][y]
                }
            }
        }
        return result
    }

    private fun getImageEnergy(image: Picture): Array<Array<Double>> {
        fun getPixelEnergy(x: Int, y: Int): Double {
            fun deltaFunction(p1: Pixel, p2: Pixel): Double {
                return (p1.red - p2.red).toDouble().pow(2.0) +
                        (p1.green - p2.green).toDouble().pow(2.0) +
                        (p1.blue - p2.blue).toDouble().pow(2.0)
            }

            val firstXPixel = if (x > 0) image[x - 1, y] else image[image.width - 1, y]
            val secondXPixel = if (x + 1 < image.width) image[x + 1, y] else image[0, y]
            val firstYPixel = if (y > 0) image[x, y - 1] else image[x, image.height - 1]
            val secondYPixel = if (y + 1 < image.height) image[x, y + 1] else image[x, 0]
            return sqrt(deltaFunction(firstXPixel, secondXPixel) + deltaFunction(firstYPixel, secondYPixel))
        }

        val result = Array(image.width) { Array(image.height) { 0.0 } }
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                result[x][y] = getPixelEnergy(x, y)
            }
        }
        return result
    }

}