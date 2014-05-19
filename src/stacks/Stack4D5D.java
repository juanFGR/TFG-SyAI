package stacks;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.vecmath.Point3d;

import views.Filters;
import core.Core;
import core.inter.inter;

/**
 * El objetivo de esta clase es dar acceso al resto de clases y metodos a una 
 * estructura unidimensipnal en la cual se almacenan la imagenes obtenidas
 * el fichero vtc
 * 
 * @author JuanFGR
 * @version 0.2
 * @see Core
 * 
 */
public class Stack4D5D {
    private static float[] stackVector;
    private static int width;
    private static int height;
    private static float[] ImgVector;
    private static int slices;
    private static int frames;
    private static double factorScale;
    public static FormatVTC formatVTC;
    public static int TAM_RESIZE = 5;
    public static ArrayList<Point3d> ListOfVoid =  new ArrayList<Point3d>();
    public static Filters filters = new Filters();
    public static double[] actualPlane = {0.0,0.0,0.0,0.0};

    /**
     * Esta funcion saca del vector completo la imagen que nos interesa
     * 
     * @param slice {int} entero que indica el corte seleccionado
     * @param time {int} indica que frame o instante de tiempo nos interesa 
     * @return {@link Vector}{@link Float} Vector dcon la imagen 
     * */
    public static float[] getImgVectorxy(int slice, int time) {
	ImgVector = new float[width * height];

	int j = 0;
	// float[]imagesf = new float[width*height];
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		// System.out.print(wrapped.getFloat()+" ");
		// images[j] = (byte) wrapped.getFloat();

		ImgVector[j] = stackVector[(slice * height * width * frames)
		                           + (y * frames * width) + (frames * x) + time];

		j++;
		// System.out.println();
	    }
	}

	return ImgVector;
    }

    /**
     * Esta funcion saca del vector completo la imagen que nos interesa
     * 
     * @param slice {int} entero que indica el corte seleccionado
     * @param time {int} indica que frame o instante de tiempo nos interesa 
     * @return {@link Vector}{@link Float} Vector dcon la imagen 
     * */
    public static float[] getImgVectoryz(int x, int time) {
	ImgVector = new float[width * height];

	int j = 0;

	for (int y = 0; y < height; y++) {
	    for (int z = 0; z < slices; z++) {

		ImgVector[j] = stackVector[(z * height * width * frames)
		                           + (y * frames * width) + (frames * x) + time];

		j++;
	    }
	}

	return transformacionInversa();
    }

    /**
     * Esta funcion saca del vector completo la imagen que nos interesa
     * 
     * @param slice {int} entero que indica el corte seleccionado
     * @param time {int} indica que frame o instante de tiempo nos interesa 
     * @return {@link Vector}{@link Float} Vector dcon la imagen 
     * */
    public static float[] getImgVectorxz(int y, int time) {
	ImgVector = new float[width * slices];

	int j = 0;

	for (int z = 0; z < slices; z++) {
	    for (int x = 0; x < width; x++) {
		ImgVector[j] = stackVector[(z * height * width * frames)
		                           + (y * frames * width) + (frames * x) + time];
		j++;
	    }
	}

	return ImgVector;
    }

    /**
     * Funcion utilizada para la interpolacion de imagenes
     * medinte la trasnformacion inverdsa
     * @return {@link Vector}{@link Float} Imagen*/
    private static float[] transformacionInversa() {
	int pos = 0;
	int posI = 0;
	float[] ImgVector2 = new float[width * height];
	for (int i = 0; i < width; i++) {
	    for (int j = 0; j < height; j++) {
		pos = getposPixelInVector(width, height, i, j);
		posI = (int) Math.round(pos * getFactorScale());
		if (posI >= 0 && posI < ImgVector.length
			&& (pos > 0 && pos < ImgVector2.length))
		    ImgVector2[pos] = ImgVector[posI];

		if (posI >= ImgVector.length
			&& (pos > 0 && pos < ImgVector2.length))
		    ImgVector2[pos] = ImgVector[ImgVector.length - 1];

		if (posI < 0 && (pos > 0 && pos < ImgVector2.length))
		    ImgVector2[pos] = ImgVector[0];
	    }
	}
	return ImgVector2;
    }

    /**
     * Obtiene la posición del pixel de la imagen dentro del vector 
     * unidimensional en el cual se almacenan las imágenes
     * 
     * @param ancho {@link Integer} contine el ancho de la imagen
     * @param alto {@link Integer} contiene el alto de la imagen
     * @param x {@link Integer} pocision en el eje x del pixel
     * @param y {@link Integer} pocision en el eje y del pixel
     * @return {@link Vector}{@link Float} Imagen
     * */
    public static int getposPixelInVector(int ancho, int alto, int x, int y) {
	return ancho * y - (ancho + x);
    }

    
    
    public static void Initialize(ImagePlus ip) {
	setWidth(ip.getWidth());
	setHeight(ip.getHeight());
	setSlices(ip.getStackSize());
	setFrames(ip.getFrame());
	if (getSlices() <= getWidth())
	    setFactorScale((double) ((double) getSlices() / (double) getWidth()));
	else
	    setFactorScale((double) ((double) getWidth() / (double) getSlices()));

	IJ.showMessage(getFactorScale() + "");
	stackVector = new float[ip.getStack().getSize() * width * height];
	ImageStack list = new ImageStack(width, height);
	ImageProcessor is;
	for (int i = 1; i <= ip.getStackSize(); i++) {
	    is = ip.getStack().getProcessor(i).convertToByte(true);
	    list.addSlice("ccc", is);
	}

	for (int i = 0; i < list.getSize(); i++) {

	    float[] aux = (float[]) (list.getPixels(i + 1));
	    for (int j2 = 0; j2 < list.getHeight() * list.getWidth(); j2++) {

		stackVector[list.getHeight() * list.getWidth() * i + j2] = aux[j2];
	    }

	}

    }

    
    /** 
     * Este metodo recibe el fichero con extension vtc y construye el {@link Stack4D5D} para 
     * el tratamiento de imagenes
     * @param _formatVTC {@link FormatVTC} Diseñado para manejar la estructuta de datos VTC
     *  */
    public static void Initialize(FormatVTC _formatVTC, String Formatter) {
	formatVTC = _formatVTC;
	stackVector = Arrays.copyOf(formatVTC.getImages(),
		formatVTC.getImages().length);
	setWidth(formatVTC.getDimX());
	setHeight(formatVTC.getDimY());
	setSlices(formatVTC.getDimZ());
	setFrames(formatVTC.getNumbeOfVolumes());
	if (getSlices() <= getWidth())
	    setFactorScale((double) ((double) getSlices() / (double) getWidth()));
	else
	    setFactorScale((double) ((double) getWidth() / (double) getSlices()));
	
	IJ.showMessage(inter.texts.getString("INFO_loadComplete"));

    }

 

    public static int getSlices() {
	return slices;
    }

    public static void setSlices(int _slices) {
	slices = _slices;
    }

    public static int getFrames() {
	return frames;
    }

    public static void setFrames(int _frames) {
	frames = _frames;
    }

    public static int getWidth() {
	return width;
    }

    public static void setWidth(int _width) {
	width = _width;
    }

    public static int getHeight() {
	return height;
    }

    public static void setHeight(int _height) {
	height = _height;
    }

    public static void setImgVector(float[] imgVector) {
	ImgVector = imgVector;
    }

    public static float[] getStackVector() {
	return stackVector;
    }

    public static void setStackVector(float[] _stackVector) {
	stackVector = _stackVector;
    }

    public static float[] getImgVector() {
	return ImgVector;
    }

    public static double getFactorScale() {
	return factorScale;
    }

    public static void setFactorScale(double _factorScale) {
	factorScale = _factorScale;
    }

}
