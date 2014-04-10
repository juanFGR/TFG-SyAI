package submodules.histograms;

import java.util.ArrayList;
import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import stacks.Stack4D5D;
import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.gui.Roi;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class VOI_media {
	float[] media;
	float[] ImageVec;
	public VOI_media( Roi roi) {
	media = new float[roi.getWidthOfRoi()*roi.getHeightOfRoi()];
	
	ImageProcessor a;
	
		for (int i = 0; i < Stack4D5D.getFrames(); i++) {
			ImageVec = Arrays.copyOf(Stack4D5D.getImgVectorxy(0, i), Stack4D5D.getImgVectorxy(0, i).length);
		
		a = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), ImageVec).resize(Stack4D5D.getWidth()*5, Stack4D5D.getHeight()*5);
			for (int j = 0; j < media.length; j++) {
				media[j] += ImageVec[j+Stack4D5D.getposPixelInVector(Stack4D5D.getWidth(), Stack4D5D.getHeight(), (int)roi.getXBase()/5, (int)roi.getYBase()/5)];
			}
		}
		
		
		for (int j = 0; j <media.length; j++) {
			media[j]= media[j]/Stack4D5D.getFrames();
		}
		
		ImagePlus windowImage = new ImagePlus();
		windowImage.setProcessor(new FloatProcessor(media.length, 1, media));
		HistogramWindow aux = new HistogramWindow(windowImage);
		aux.show();
	}
}
