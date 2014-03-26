package stacks;
import java.util.Arrays;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;
import interfaces.Stack4D5D_Interface;



public class Stack4D5D implements Stack4D5D_Interface {
	private	byte []stackVector;
	private int width;
	private int height;
	private byte []ImgVector;
	private int slices;
	private int frames;
	private double factorScale;
	public FormatVTC formatVTC;

	public byte[] getImgVectorxy(int slice) {
		ImgVector = new byte[width*height];
		for (int i = 0; i < ImgVector.length; i++) {
			ImgVector[i] = stackVector[width*height*(slice-1)+i];
		}
		return ImgVector;
	}

	public byte[] getImgVectoryz(int slice) {
		ImgVector = new byte[width*getSlices()];
		int iterator=0;
		for (int j =0; j < height ; j++) {
			for (int i = 0; i < getSlices(); i++) {
				int aux = width*height*j +width*(slice-1)+ i;
				if(aux >0 && aux<stackVector.length)
					ImgVector[iterator] = stackVector[aux];
				iterator++;
			}
		}

		return transformacionInversa();
	}



	public byte[] getImgVectorxz(int slice) {
		ImgVector = new byte[getSlices()*height];
		int iterator=0;
		for (int j =0; j < height ; j++) {
			for (int i = 0; i < getSlices(); i++) {
				int aux = width*height*i +width*j+(slice-1);
				if(aux >0 && aux<stackVector.length)
					ImgVector[iterator] = stackVector[aux];
				iterator++;
			}
		}


		return transformacionInversa();
	}


	private byte[] transformacionInversa() {
		int pos=0;
		int posI=0;	
		byte[] ImgVector2 = new byte[width*height];
		for (int i = 0; i < width; i++) {	
			for (int j = 0; j < height; j++) {
				pos=getposPixelInVector(width,height,i,j);
				posI=(int) Math.round(pos*getFactorScale());
				if(posI >=0 && posI<ImgVector.length && (pos>0 && pos <ImgVector2.length))
					ImgVector2[pos] = ImgVector[posI];

				if(posI>=ImgVector.length  && (pos>0 && pos <ImgVector2.length))
					ImgVector2[pos] = ImgVector[ImgVector.length-1];

				if(posI<0 && (pos>0 && pos <ImgVector2.length))
					ImgVector2[pos] = ImgVector[0];
			}
		}
		return ImgVector2;
	}




	public int getposPixelInVector(int ancho,int alto, int x, int y) {
		return ancho*y-(ancho-x);
	}

	public Stack4D5D(ImagePlus ip) {
		setWidth(ip.getWidth());
		setHeight(ip.getHeight());
		setSlices(ip.getStackSize());
		setFrames(ip.getFrame());
		if(getSlices()<=getWidth())
			setFactorScale((double)((double)getSlices()/(double)getWidth()));	
		else
			setFactorScale((double)((double)getWidth()/(double)getSlices()));

		IJ.showMessage(getFactorScale()+"");
		stackVector = new byte[ip.getStack().getSize()*width*height];
		ImageStack list = new ImageStack(width, height);
		ImageProcessor is;
		for (int i = 1; i <= ip.getStackSize(); i++) {
			is = ip.getStack().getProcessor(i).convertToByte(true);
			list.addSlice("ccc", is);
		}

		for (int i = 0; i < list.getSize(); i++) {

			byte[] aux= (byte[])(list.getPixels(i+1));
			for (int j2 = 0; j2 < list.getHeight()*list.getWidth(); j2++) {

				stackVector[list.getHeight()*list.getWidth()*i +j2] = aux[j2];
			}


		}
		

	}

	public Stack4D5D(byte[] data,String Formatter) {
		formatVTC = new FormatVTC(data);
	//	stackVector = Arrays.copyOf(formatVTC.getImages(), formatVTC.getImages().length);
	
	}


	/** ****************************************
	 * 
	 * Iplementación de los Getters && Setters *
	 ** **************************************** */



	public int getSlices() {
		return slices;
	}

	public void setSlices(int slices) {
		this.slices = slices;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setImgVector(byte[] imgVector) {
		ImgVector = imgVector;
	}

	public byte[] getStackVector() {
		return stackVector;
	}

	public void setStackVector(byte[] stackVector) {
		this.stackVector = stackVector;
	}

	public byte[] getImgVector() {
		return ImgVector;
	}

	public double getFactorScale() {
		return factorScale;
	}

	public void setFactorScale(double factorScale) {
		this.factorScale = factorScale;
	}

}
