package submodules.plugins;

import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.gui.Roi;
import ij.plugin.Slicer;
import ij.process.FloatProcessor;
import ij.util.Tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import stacks.Stack4D5D;
import submodules.histograms.Chart;
import submodules.histograms.ChartWindow;
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




    private void saveInBuffer(int i,float _media) {
	int cont = 0;	

	  
	    cont +=1;
	
    }


    private void mediaOfValues() {
	/*System.out.println("before "+buffer[0]);
	int tmp = 0;
	for (int l = 0; l < media.length; l++) { //Cada pixel
	    for (int k = l; k < buffer.length; k+=(k+1+l)*media.length) {//salto de ese pixel
	    tmp += buffer[k];
	    }
	    media[l]=tmp/media.length;
	    for (int i = 0; i < buffer.length; i++) {

	    }
	}	*/
	System.out.println("after "+buffer[0]);

	for (int i = 0; i < buffer.length; i++) {
	    buffer[i] = buffer[i]/Stack4D5D.getFrames();
	}
    }



    public int nextPotencia2(int n) 
    { 
	int cont =0;
	while(cont<n){
	    cont+=2;
	}
	/* System.out.println(Math.pow(cont, 2));
        if(Math.pow(cont, 2) == n)
            return (int) Math.pow(cont, 2); 
        else
            return (int) Math.pow(cont+1, 2);*/
	return cont;
    } 


    public void initialize( Roi roi) {

	numberOfPixels = (roi.getWidthOfRoi()/Stack4D5D.TAM_RESIZE) * (roi.getHeightOfRoi()/Stack4D5D.TAM_RESIZE);
	frames = Stack4D5D.getFrames();
	firstPixel = Stack4D5D.getposPixelInVector(Stack4D5D.getWidth(), Stack4D5D.getHeight(), (int)roi.getXBase()/Stack4D5D.TAM_RESIZE, (int)roi.getYBase()/Stack4D5D.TAM_RESIZE);

	buffer = new double[Stack4D5D.getFrames()];
	a = new ImagePlus("e");

	//ChartWindow.slicesSlider = new JSlider(JSlider.HORIZONTAL,0,Stack4D5D.getFrames()-1,0); 

	//ChartWindow.slicesSlider.setMaximum(Stack4D5D.getFrames()-1);
	//add(slicesSlider);
	//ChartWindow.slicesSlider.addChangeListener(this);
	float[] tmp;
	
	
	for (int j = 0; j < Stack4D5D.getFrames(); j++) {
	    float _media = 0;
	    for (int i = 0; i < Stack4D5D.getSlices(); i++) {
		tmp = Arrays.copyOf (Stack4D5D.getImgVectorxy(i,j ),Stack4D5D.getImgVectorxy(i,j).length );
		for (int z = 0; z < numberOfPixels; z++) {
		    _media += tmp[firstPixel+z]; 
		}
	    }
	    _media = _media/(numberOfPixels*Stack4D5D.getSlices());
	    buffer[j] = _media;
	}


	//mediaOfValues();

	//Panel_Window t = new Panel_Window(a);



	oop = new ChartWithSliders();

	oop.initialize(buffer,buffer.length);

	/*for (int j = 0; j < buffer.length; j++) {
	   System.out.print(buffer[j]/frames+"->"); 
	}*/

	/*System.out.println("@@@@@@@@@"+buffer.length);
	float [] auxi = new float[media.length*500];



	Arrays.fill(auxi, 0);
	FloatProcessor g = new FloatProcessor(media.length,500,auxi);
	a.setImage(new ImagePlus("ww", g));
	//a.getCanvas().setSize(300, 300);
*//*
	for (int i = 0; i < media.length; i++) {
	    p =  new Point(i , (int)media[i]);
	    drawCross(a,p,path);
	    a.setOverlay(path, Color.YELLOW, new BasicStroke(1));

	}
	a.repaintWindow();*/
	/*FFT transform = new FFT(128);
	double[] imaginaria = new double[128];
	double[] real =  new double[128];
	Arrays.fill(imaginaria, 0);
	Arrays.fill(real, 0);
	for (int i = 0; i < real.length; i++) {
	    real[i] = buffer[i];
	}
	transform.fft(real, imaginaria);
*/
	FFT transform = new FFT(8);
	ChartWithSliders oopi = new ChartWithSliders();
	transform.lanza();
	oopi.initialize(transform.getWindow(),transform.getWindow().length);

    }





    GeneralPath path = new GeneralPath();
    Point p;

    void drawCross(ImagePlus imp, Point p, GeneralPath path) {
	path.moveTo( p.x, imp.getHeight());
	path.lineTo( p.x, imp.getHeight()-p.y);	


    }















    /*  @Override
    public void stateChanged(ChangeEvent e) {
	media = new double[numberOfPixels];

	float[] tmp;

	for (int i = 0; i < frames; i++) {

	    tmp = Arrays.copyOf (Stack4D5D.getImgVectorxy(i,((JSlider)e.getSource()).getValue() ),Stack4D5D.getImgVectorxy(i,((JSlider)e.getSource()).getValue()).length );
	    System.out.println("//////////////"+tmp.length);
	    System.out.println("\\\\\\\\\\\\\\"+firstPixel);
	    System.out.println("\\\\\\\\\\\\\\"+numberOfPixels);

	    for (int j = 0; j < numberOfPixels; j++) {
		media[j] += tmp[firstPixel+j]; 
	    }
	}
	//Panel_Window t = new Panel_Window(a);

	float [] auxi = new float[media.length*500];


	Arrays.fill(auxi, 0);
	FloatProcessor g = new FloatProcessor(media.length,500,auxi);
	a.setImage(new ImagePlus("ww", g));
	//a.getCanvas().setSize(300, 300);

	for (int i = 0; i < media.length; i++) {
	    p =  new Point(i , (int)media[i]);
	    drawCross(a,p,path);
	    a.setOverlay(path, Color.YELLOW, new BasicStroke(1));

	}
	    a.repaintWindow();
	FFT transform = new FFT(128);
	double[] imaginaria = new double[128];
	double[] real =  new double[128];
	 Arrays.fill(imaginaria, 0);
	 Arrays.fill(real, 0);
	 for (int i = 0; i < real.length; i++) {
	    real[i] = media[i];
	}
	 	transform.fft(real, imaginaria);
	 	Arrays.fill(media, 0.0);
	 	oop.change(media);
    }
     */











}
