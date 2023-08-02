package model.applicationfunctions

import model.classes.TwoDVector
import model.classes.UnionFind
import java.awt.image.BufferedImage
import java.awt.image.ColorModel
import java.awt.image.WritableRaster
import java.io.File
import javax.imageio.ImageIO


class WorkshopImage(
	private val bufferedImage: BufferedImage
) {
	fun getImage():BufferedImage {
		return bufferedImage
	}
	fun setColorOf(element : UnionFind<TwoDVector>, color : Int) {
		val vector = element.getValue()
		val x = vector.getComp(0)
		val y = vector.getComp(1)
		bufferedImage.setRGB(x,y,color)
	}

	fun colorOf(element : UnionFind<TwoDVector>):Int {
		val vector = element.getValue()
		val x = vector.getComp(0)
		val y = vector.getComp(1)
		return bufferedImage.getRGB(x,y)
	}

	fun save(pathOut:String) {
		val outputFile = File(pathOut)
		ImageIO.write(getImage(), "png", outputFile)
	}

	fun copy():WorkshopImage {
		//https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
		val bi = bufferedImage
		val cm = bi.colorModel
		val isAlphaPremultiplied = cm.isAlphaPremultiplied
		val raster = bi.copyData(null)
		val newImage = BufferedImage(cm, raster, isAlphaPremultiplied, null)
		return WorkshopImage(newImage)
	}
}