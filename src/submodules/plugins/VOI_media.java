package submodules.plugins;

import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.gui.Roi;
import ij.process.FloatProcessor;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import stacks.Stack4D5D;

public class VOI_media extends JPanel implements ChangeListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    int numberOfPixels;
    int slices;
    int firstPixel;
    float[] media;
    ImagePlus a;
    HistogramWindow hist;
    private JSlider slicesSlider;


    public VOI_media( Roi roi) {
	numberOfPixels = (roi.getWidthOfRoi()/5) * (roi.getHeightOfRoi()/5);
	System.out.println((roi.getImage().getWidth()/5));
	System.out.println((roi.getImage().getHeight()/5));
	slices = Stack4D5D.getSlices();
	firstPixel = Stack4D5D.getposPixelInVector(Stack4D5D.getWidth(), Stack4D5D.getHeight(), (int)roi.getXBase()/5, (int)roi.getYBase()/5);
	media = new float[numberOfPixels];
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

	path.moveTo( p.x, 0f);
	path.lineTo( p.x, p.y);	


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

	for (int i = 0; i < media.length; i++) {
	    media[i]= media[i]/slices;
	}
	
	/*FloatProcessor g = new FloatProcessor(numberOfPixels,1, media);
	a.setImage(new ImagePlus("ww", g));*/
	a.getCanvas().setSize(300, 300);
	
	for (int i = 0; i < media.length; i++) {
	    p =  new Point(i , (int)media[i]);
	    drawCross(a,p,path);
	    a.repaintWindow();
	}



    }













}
