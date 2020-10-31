import editing.BufferedPicture
import editing.SeamCarver
import java.io.File
import java.lang.IllegalArgumentException
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    var inputPath = ""
    var outPath = ""
    var cropWidth = 0
    var cropHeight = 0

    for (i in args.indices) {
        when (args[i]) {
            "-i", "--inputFile" -> inputPath = args[i + 1]
            "-o", "--outputFile" -> outPath = args[i + 1]
            "-w" -> cropWidth = args[i + 1].toInt()
            "-h" -> cropHeight = args[i + 1].toInt()
        }
    }
    val inputFile = File(inputPath)
    if (!inputFile.exists() || !inputFile.canRead()) {
        throw IllegalArgumentException("File cannot be read")
    }
    val image = ImageIO.read(inputFile)
    val resultImage = SeamCarver().resize(BufferedPicture(image), cropWidth, cropHeight)
    ImageIO.write(resultImage.getBufferedImage(), "png", File(outPath))

}