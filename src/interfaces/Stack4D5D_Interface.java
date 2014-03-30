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
	
	float[] getImgVector();
	void setImgVector(float[] imgVector);
	float[] getStackVector();
	void setStackVector(float[] stackVector);
	
	
	/* Metodos de la clase Stack4D5D */
	float[] getImgVectoryz(int slice);	
	float[] getImgVectorxz(int slice);	
	float[] getImgVectorxy(int slice);

	
	int getposPixelInVector(int ancho,int alto, int x, int y);

}
