package day6

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

internal fun writeImg(it: Array<IntArray>) {
    val img = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
    (0..999).forEach { x ->
        (0..999).forEach { y ->
            img.setRGB(x, y, if (it[x][y] == 1) 0xffffff else 0)
        }
    }
    ImageIO.write(img, "PNG", File("local/day6part1.png"))
}
