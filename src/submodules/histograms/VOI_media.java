package submodules.histograms;

import ij.ImagePlus;
import ij.gui.Roi;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

import stacks.Stack4D5D;

public class VOI_media {

	int numberOfPixels;
	int times;
	int firstPixel;
	
	public VOI_media( Roi roi) {
		numberOfPixels = (roi.getWidthOfRoi()/5) * (roi.getHeightOfRoi()/5);
		System.out.println((roi.getImage().getWidth()/5));
		System.out.println((roi.getImage().getHeight()/5));
		times = Stack4D5D.getFrames();
		firstPixel = Stack4D5D.getposPixelInVector(Stack4D5D.getWidth(), Stack4D5D.getHeight(), (int)roi.getXBase()/5, (int)roi.getYBase()/5);
	/*	System.out.println((int)roi.getXBase()/5);
		System.out.println((int)roi.getYBase()/5);
		System.out.println(Stack4D5D.getWidth());*/
		float[] media = new float[numberOfPixels];
		float[] tmp;
		ImagePlus a = new ImagePlus("e");
		for (int i = 0; i < times; i++) {
			
			tmp = Arrays.copyOf (Stack4D5D.getImgVectorxy(1, i),Stack4D5D.getImgVectorxy(1, i).length );
			System.out.println("//////////////"+tmp.length);
			System.out.println("\\\\\\\\\\\\\\"+firstPixel);
			System.out.println("\\\\\\\\\\\\\\"+numberOfPixels);
	
			for (int j = 0; j < numberOfPixels; j++) {
				media[j] += tmp[j]; 
				
			}
		}
	
		
		for (int i = 0; i < media.length; i++) {
			media[i]= media[i]/times;
			
			p =  new Point(i , (int)media[i]);
			drawCross(a,p,path);
		}
		
		a.show();
		/*FloatProcessor g = new FloatProcessor(numberOfPixels, 1, media);
		ImagePlus e = new ImagePlus("ww", g);
		HistogramWindow a = new HistogramWindow(e);*/
	}
	
	GeneralPath path = new GeneralPath();
	Point p;
	
	void drawCross(ImagePlus imp, Point p, GeneralPath path) {
		int width=imp.getWidth();
		int height=imp.getHeight();
		float x = p.x;
		float y = p.y;
		path.moveTo(0f, y);
		path.lineTo(width, y);
		path.moveTo(x, 0f);
		path.lineTo(x, height);	
	}
}
