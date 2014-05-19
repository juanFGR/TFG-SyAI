package submodules.plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.gui.Roi;

import java.util.Arrays;

import stacks.Stack4D5D;
import submodules.histograms.Chart;
import submodules.histograms.ChartForMedia;
import core.inter.inter;

public class VOI_media  {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    int numberOfPixels;
    int firstPixel;
    ImagePlus a;
    HistogramWindow hist;
    static ChartForMedia drawChartForMedia;
    public static double[] buffer;



    public static int nextPotencia2(int n) 
    { 
	int nPotencia=0,val =0;
	while(val<=n){
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


    public void initialize( Roi roi,Boolean is_viewChart) {
	numberOfPixels = (roi.getWidthOfRoi()/Stack4D5D.TAM_RESIZE) * (roi.getHeightOfRoi()/Stack4D5D.TAM_RESIZE);
	firstPixel = Stack4D5D.getposPixelInVector(Stack4D5D.getWidth(), Stack4D5D.getHeight(), (int)roi.getXBase()/Stack4D5D.TAM_RESIZE, (int)roi.getYBase()/Stack4D5D.TAM_RESIZE);
	int []roiSizes = {roi.getWidthOfRoi()/Stack4D5D.TAM_RESIZE,roi.getHeightOfRoi()/Stack4D5D.TAM_RESIZE};
	buffer = new double[Stack4D5D.getFrames()-(int) Stack4D5D.actualPlane[3]];


	float[] tmp;


	for (int j = (int) Stack4D5D.actualPlane[3]; j < Stack4D5D.getFrames(); j++) {
	    float _media = 0;
	    for (int i = (int) Stack4D5D.actualPlane[0]; i < Stack4D5D.getSlices(); i++) {

		tmp = thatImage(i,j-(int) Stack4D5D.actualPlane[3]); 
		for (int y = 0; y < roiSizes[1]; y++) {
		    for (int x = firstPixel+(roiSizes[0]*y)+(Stack4D5D.getWidth()-(firstPixel+roiSizes[0])); x < firstPixel+(roiSizes[0]*y)+(Stack4D5D.getWidth()-(firstPixel+roiSizes[0]))+roiSizes[0]; x++) {	    
			_media += tmp[x]; 
		    }
		}
		tmp = null;
	    }
	    _media = _media/(numberOfPixels*Stack4D5D.getSlices());
	    buffer[j-(int) Stack4D5D.actualPlane[3]] = _media;
	}

	if(is_viewChart){
	    drawChartForMedia = new ChartForMedia();
	    drawChartForMedia.initialize(buffer,1);
	   
	}
    }


   public static void addPointInChartMedia(double[] intensity){
	float[] x,y;
	x = new float[buffer.length];
	int cont = buffer.length;
	y = new float[buffer.length];
	Arrays.fill(y, 0);
	for (int i = buffer.length-1; i >= 0; i--) {
	    x[i] = cont--;
	    for (int j = 0; j < intensity.length; j++) {
		if((i-j)>=0)
		   y[i]+= (float) buffer[i-j]*intensity[j];
	    }
	}
	Chart rr = new Chart("RRRRRR","ttt","ee", x, y);
	rr.show();
    }

}
