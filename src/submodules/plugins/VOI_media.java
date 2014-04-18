package submodules.plugins;

import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.gui.Roi;
import ij.process.FloatProcessor;

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
import submodules.histograms.ChartWithSliders;

public class VOI_media extends JPanel implements ChangeListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    int numberOfPixels;
    int slices;
    int firstPixel;
    double[] media;
    ImagePlus a;
    HistogramWindow hist;
    private JSlider slicesSlider;


    public VOI_media( Roi roi) {
	numberOfPixels = (roi.getWidthOfRoi()/5) * (roi.getHeightOfRoi()/5);
	System.out.println((roi.getImage().getWidth()/5));
	System.out.println((roi.getImage().getHeight()/5));
	slices = Stack4D5D.getSlices();
	firstPixel = Stack4D5D.getposPixelInVector(Stack4D5D.getWidth(), Stack4D5D.getHeight(), (int)roi.getXBase()/5, (int)roi.getYBase()/5);
	media = new double[numberOfPixels];
	a = new ImagePlus("e");
	slicesSlider = new JSlider(JSlider.HORIZONTAL,0,Stack4D5D.getFrames()-1,0); 
	slicesSlider.setName("XY");
	slicesSlider.setBounds(154, 2, 252, 26);
	slicesSlider.setMaximum(Stack4D5D.getFrames()-1);
	slicesSlider.addChangeListener(this); 
	add(slicesSlider);
	
    }

    GeneralPath path = new GeneralPath();
    Point p;

    void drawCross(ImagePlus imp, Point p, GeneralPath path) {

	path.moveTo( p.x, imp.getHeight());
	path.lineTo( p.x, imp.getHeight()-p.y);	


    }

    @Override
    public void stateChanged(ChangeEvent e) {


	float[] tmp;

	for (int i = 0; i < slices; i++) {

	    tmp = Arrays.copyOf (Stack4D5D.getImgVectorxy(i,((JSlider)e.getSource()).getValue() ),Stack4D5D.getImgVectorxy(i,((JSlider)e.getSource()).getValue()).length );
	    System.out.println("//////////////"+tmp.length);
	    System.out.println("\\\\\\\\\\\\\\"+firstPixel);
	    System.out.println("\\\\\\\\\\\\\\"+numberOfPixels);

	    for (int j = 0; j < numberOfPixels; j++) {
		media[j] += tmp[j]; 

	    }
	}
	Panel_Window t = new Panel_Window(a);
ChartWithSliders lll = new ChartWithSliders();
lll.initialize();
	for (int i = 0; i < media.length; i++) {
	    media[i]= media[i]/slices;
	}
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
	 	transform.lanza();

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










}
