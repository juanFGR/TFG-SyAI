package stacks;
import java.util.Arrays;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;
import interfaces.Stack4D5D_Interface;



public class Stack4D5D implements Stack4D5D_Interface {
	private	float []stackVector;
	private int width;
	private int height;
	private float []ImgVector;
	private int slices;
	private int frames;
	private double factorScale;
	public FormatVTC formatVTC;

	public float[] getImgVectorxy(int slice, int time) {
		ImgVector = new float[width*height];
		
		int j =0;
	//	float[]imagesf = new float[width*height]; 
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				//System.out.print(wrapped.getFloat()+" ");
				//	images[j] = (byte) wrapped.getFloat();

				ImgVector[j] =   stackVector[(slice*height*width*frames)+(y*frames*width) +(frames*x)+time];
		
				j++;
				//System.out.println();
			}
		}
		
		return ImgVector;
	}

	public float[] getImgVectoryz(int x, int time) {
		ImgVector = new float[width*height];

		//int z  =20;
		int j =0;
		
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < slices; z++) {
				//System.out.print(wrapped.getFloat()+" ");
				//	images[j] = (byte) wrapped.getFloat();

				ImgVector[j] =   stackVector[(z*height*width*frames)+(y*frames*width) +(frames*x)+time];
		
				j++;
				//System.out.println();
			}
		}

		return transformacionInversa();
	}



	public float[] getImgVectorxz(int y, int time) {
		ImgVector = new float[width*slices];
		
		int j =0;
		
		
			for (int z = 0; z < slices; z++) {
				for (int x = 0; x < width; x++) {
				//System.out.print(wrapped.getFloat()+" ");
				//	images[j] = (byte) wrapped.getFloat();

				ImgVector[j] =   stackVector[(z*height*width*frames)+(y*frames*width) +(frames*x)+time];
				
				j++;
				//System.out.println();
			}
		}

return ImgVector;
		//return transformacionInversa();
	}


	private float[] transformacionInversa() {
		int pos=0;
		int posI=0;	
		float[] ImgVector2 = new float[width*height];
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
		stackVector = new float[ip.getStack().getSize()*width*height];
		ImageStack list = new ImageStack(width, height);
		ImageProcessor is;
		for (int i = 1; i <= ip.getStackSize(); i++) {
			is = ip.getStack().getProcessor(i).convertToByte(true);
			list.addSlice("ccc", is);
		}

		for (int i = 0; i < list.getSize(); i++) {

			float[] aux= (float[])(list.getPixels(i+1));
			for (int j2 = 0; j2 < list.getHeight()*list.getWidth(); j2++) {

				stackVector[list.getHeight()*list.getWidth()*i +j2] = aux[j2];
			}


		}
		

	}

	public Stack4D5D(FormatVTC formatVTC,String Formatter) {
		this.formatVTC = formatVTC;
		stackVector = Arrays.copyOf(formatVTC.getImages(), formatVTC.getImages().length);
		setWidth(formatVTC.getDimX());
		setHeight(formatVTC.getDimY());
		setSlices(formatVTC.getDimZ());
		setFrames(formatVTC.getNumbeOfVolumes());
		if(getSlices()<=getWidth())
			setFactorScale((double)((double)getSlices()/(double)getWidth()));	
		else
			setFactorScale((double)((double)getWidth()/(double)getSlices()));
	
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

	public void setImgVector(float[] imgVector) {
		ImgVector = imgVector;
	}

	public float[] getStackVector() {
		return stackVector;
	}

	public void setStackVector(float[] stackVector) {
		this.stackVector = stackVector;
	}

	public float[] getImgVector() {
		return ImgVector;
	}

	public double getFactorScale() {
		return factorScale;
	}

	public void setFactorScale(double factorScale) {
		this.factorScale = factorScale;
	}

}
