package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.GeneralPath;

import ij.IJ;
import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import stacks.Stack4D5D;
import submodules.histograms.VOI_media;

public class MenuCanvas extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 6042629063683509113L;
	VOI_media voi_media;
	private ImagePlus ipxy, ipyz, ipxz = null; 
	private JSlider slicesSliderxy, slicesSlideryz, slicesSliderxz, slicesSlidertime = null;

	ImageProcessor xy, yz, xz ;
	ImagePlus bxy , byz, bxz ;
	String typeView = null;


	public MenuCanvas() {

		xy = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectorxy(1,0));
		yz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectoryz(1,0));
		xz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getSlices(), Stack4D5D.getImgVectorxz(1,0));

		ipxy = new ImagePlus("e",xy);
		ipyz = new ImagePlus("eh",yz);
		ipxz = new ImagePlus("ehh",xz);


		bxy = new ImagePlus("", xy);
		bxz = new ImagePlus("", xz);
		byz = new ImagePlus("", yz);
		
		updatexy(0,0);
		updatexz(0,0);
		updateyz(0,0);
		
		
bxy.show();
bxz.show();
byz.show();
		slicesSliderxy = new JSlider(JSlider.HORIZONTAL,0,ipxy.getStackSize()-1,0); 
		slicesSliderxy.setName("XY");
		slicesSliderxy.setBounds(154, 2, 252, 26);
		slicesSliderxy.setMaximum(50);
		slicesSliderxy.addChangeListener(this); 


		slicesSlideryz = new JSlider(JSlider.HORIZONTAL,0,ipyz.getStackSize()-1,0); 
		slicesSlideryz.setName("YZ");
		slicesSlideryz.setBounds(154, 30, 252, 26);
		slicesSlideryz.setMaximum(50);
		slicesSlideryz.addChangeListener(this); 


		slicesSliderxz = new JSlider(JSlider.HORIZONTAL,0,ipxz.getStackSize()-1,0); 
		slicesSliderxz.setName("XZ");
		slicesSliderxz.setBounds(154, 70, 252, 26);
		slicesSliderxz.setMaximum(50);
		slicesSliderxz.addChangeListener(this); 


		slicesSlidertime = new JSlider(JSlider.HORIZONTAL,0,ipxz.getStackSize()-1,0); 
		slicesSlidertime.setName("TIME");
		slicesSlidertime.setBounds(154, 100, 252, 26);
		slicesSlidertime.setMaximum(50);
		slicesSlidertime.addChangeListener(this); 


		setLayout(null);

		add(slicesSliderxy); 
		add(slicesSlideryz); 
		add(slicesSliderxz); 
		add(slicesSlidertime); 


	}


	@Override
	public void stateChanged(ChangeEvent e) {
		// Someone has dragged the image slice slider.  We need 
		// to update the frame being shown 
		if (IJ.getImage().getRoi() != null){
			System.out.println(IJ.getImage().getRoi().getXBase());
			System.out.println(IJ.getImage().getRoi().getImage().getWidth());
			System.out.println(IJ.getImage().getRoi().getImage().getWidth()+IJ.getImage().getRoi().getXBase());
		}
		//voi_media = new VOI_media(IJ.getImage().getRoi());
/*		ImagePlus tt = new ImagePlus(); tt.setImage(IJ.getImage().getRoi().getImage());
		tt.show();

		}*/
	//	int frame = ((JSlider)e.getSource()).getValue(); 
		String name = ((JSlider)e.getSource()).getName(); 

		GeneralPath path = new GeneralPath();
		Point p = new Point(slicesSlideryz.getValue()*5, slicesSliderxz.getValue()*5);
		drawCross(bxy, p, path);
		bxy.setOverlay(path, Color.YELLOW, new BasicStroke(1));
		if (name.equals("TIME")){
			updatexy(slicesSliderxy.getValue(),slicesSlidertime.getValue());
			updatexz(slicesSliderxz.getValue(),slicesSlidertime.getValue());
			updateyz(slicesSlideryz.getValue(),slicesSlidertime.getValue());

		}else if(name.equals("XY")){
			updatexy(slicesSliderxy.getValue(),slicesSlidertime.getValue());

		}  else if (name.equals("XZ")){
			updatexz(slicesSliderxz.getValue(),slicesSlidertime.getValue());

		}
		else if(name.equals("YZ")){
			updateyz(slicesSlideryz.getValue(),slicesSlidertime.getValue());
		}
	}


	private void updatexy(int frame,int time) {
		xy = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectorxy(frame+1,time));
		ImagePlus txy = new ImagePlus("", xy);
		bxy.setImage(txy);
		bxy.getProcessor().setInterpolate(true); 
		bxy.setProcessor( bxy.getProcessor().resize(Stack4D5D.getWidth()*5, Stack4D5D.getHeight()*5)); 
		bxy.updateImage();		
		
	}


	private void updateyz(int frame,int time) {
		yz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectoryz(frame+1,time));
		ImagePlus tyz = new ImagePlus("", yz);
		byz.setImage(tyz);
		byz.getProcessor().setInterpolate(true); 
		byz.setProcessor( byz.getProcessor().resize(Stack4D5D.getWidth()*5, Stack4D5D.getHeight()*5)); 
		byz.updateImage();		
	
	}


	private void updatexz(int frame,int time) {
		xz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getSlices(), Stack4D5D.getImgVectorxz(frame+1,time));
		ImagePlus txz = new ImagePlus("", xz);
		bxz.setImage(txz);
		bxz.getProcessor().setInterpolate(true); 
		bxz.setProcessor( bxz.getProcessor().resize(Stack4D5D.getWidth()*5, Stack4D5D.getHeight()*5)); 
		bxz.updateImage();		
	}




	//Views 

	/** draws the crosses in the images */
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