package submodules.plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.gui.Roi;

import java.util.Arrays;

import core.inter.inter;
import stacks.Stack4D5D;
import submodules.histograms.ChartWithSliders;

public class VOI_media  {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    int numberOfPixels;
    int frames;
    int firstPixel;
    double[] media;
    ImagePlus a;
    HistogramWindow hist;
    ChartWithSliders oop;
    double[] buffer;



    public int nextPotencia2(int n) 
    { 
	int nPotencia=0,val =0;
	while(val<n){
	    val =  (int) Math.pow(2, nPotencia);
	    nPotencia++;
	}
	return val;
    } 

    private float[] thatImage(int i, int j) {
	if (IJ.getImage().getTitle().equals(inter.texts.getString("window_xy"))){
	    System.out.println(inter.texts.getString("window_xy"));
	    return Arrays.copyOf (Stack4D5D.getImgVectorxy(i,j ),Stack4D5D.getImgVectorxy(i,j).length );
	}else if (IJ.getImage().getTitle().equals(inter.texts.getString("window_xz"))){
	    System.out.println(inter.texts.getString("window_xz"));
	    return Arrays.copyOf (Stack4D5D.getImgVectorxz(i,j ),Stack4D5D.getImgVectorxy(i,j).length );
	}else if (IJ.getImage().getTitle().equals(inter.texts.getString("window_yz"))){
	    System.out.println(inter.texts.getString("window_yz"));
	    return Arrays.copyOf (Stack4D5D.getImgVectoryz(i,j ),Stack4D5D.getImgVectorxy(i,j).length );
	}
	return null;
    }


    public void initialize( Roi roi) {
	numberOfPixels = (roi.getWidthOfRoi()/Stack4D5D.TAM_RESIZE) * (roi.getHeightOfRoi()/Stack4D5D.TAM_RESIZE);
	frames = Stack4D5D.getFrames();
	firstPixel = Stack4D5D.getposPixelInVector(Stack4D5D.getWidth(), Stack4D5D.getHeight(), (int)roi.getXBase()/Stack4D5D.TAM_RESIZE, (int)roi.getYBase()/Stack4D5D.TAM_RESIZE);
	int []roiSizes = {roi.getWidthOfRoi()/Stack4D5D.TAM_RESIZE,roi.getHeightOfRoi()/Stack4D5D.TAM_RESIZE};
	buffer = new double[Stack4D5D.getFrames()];


	//ChartWindow.slicesSlider = new JSlider(JSlider.HORIZONTAL,0,Stack4D5D.getFrames()-1,0); 

	//ChartWindow.slicesSlider.setMaximum(Stack4D5D.getFrames()-1);
	//add(slicesSlider);
	//ChartWindow.slicesSlider.addChangeListener(this);
	float[] tmp;


	for (int j = 0; j < Stack4D5D.getFrames(); j++) {
	    float _media = 0;
	    for (int i = 0; i < Stack4D5D.getSlices(); i++) {

		tmp = thatImage(i,j); 
		for (int y = 0; y < roiSizes[1]; y++) {
		    for (int x = firstPixel+(roiSizes[0]*y)+(Stack4D5D.getWidth()-(firstPixel+roiSizes[0])); x < firstPixel+(roiSizes[0]*y)+(Stack4D5D.getWidth()-(firstPixel+roiSizes[0]))+roiSizes[0]; x++) {	    
			_media += tmp[x]; 
		    }
		}
		tmp = null;
	    }
	    _media = _media/(numberOfPixels*Stack4D5D.getSlices());
	    buffer[j] = _media;
	}


	//mediaOfValues();

	//Panel_Window t = new Panel_Window(a);



	oop = new ChartWithSliders();

	oop.initialize(buffer,null);



	System.out.println(nextPotencia2(buffer.length));
	FFT transform = new FFT(nextPotencia2(buffer.length));
	double[] imaginaria = new double[nextPotencia2(buffer.length)];
	double[] real =  new double[nextPotencia2(buffer.length)];
	Arrays.fill(imaginaria, 0);
	Arrays.fill(real, 0);
	for (int i = 0; i < buffer.length; i++) {
	    real[i] = buffer[i];
	}
	transform.fft(real, imaginaria);


	ChartWithSliders oopi = new ChartWithSliders();

	/*for (int i = 0; i < real.length; i++) {
	    double modulo = Math.sqrt(Math.pow(real[i],2)+Math.pow(imaginaria[i],2));
	    real[i] = modulo*(Math.cos(real[i]));
	    imaginaria[i] = modulo*(Math.sin(real[i]));
	}*/
	oopi.initialize(real,imaginaria);

    }



}
