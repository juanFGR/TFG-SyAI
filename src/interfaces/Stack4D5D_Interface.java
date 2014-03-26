package interfaces;

public interface Stack4D5D_Interface {



	//Getters && Setters para las variables
	int getSlices();
	void setSlices(int slices);
	int getFrames();
	void setFrames(int frames);
	int getWidth();
	void setWidth(int width);
	int getHeight();
	void setHeight(int height);
	double getFactorScale();
	void setFactorScale(double factorScale) ;
	
	byte[] getImgVector();
	void setImgVector(byte[] imgVector);
	byte[] getStackVector();
	void setStackVector(byte[] stackVector);
	
	
	/* Metodos de la clase Stack4D5D */
	byte[] getImgVectoryz(int slice);	
	byte[] getImgVectorxz(int slice);	
	byte[] getImgVectorxy(int slice);

	
	int getposPixelInVector(int ancho,int alto, int x, int y);

}
