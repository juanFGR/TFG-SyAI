package views;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;

import stacks.Stack4D5D;

public class MenuCanvas extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 6042629063683509113L;

	private ImagePlus ipxy, ipyz, ipxz = null; 
	private JSlider slicesSliderxy, slicesSlideryz, slicesSliderxz, slicesSlidertime = null;
	Stack4D5D datos;
	ImageProcessor xy, yz, xz ;
	ImagePlus bxy , byz, bxz ;
	String typeView = null;
	
	
	public MenuCanvas(Stack4D5D stack4d5d) {
		datos = stack4d5d;

		xy = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectorxy(1,0));
		yz = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectoryz(1,0));
		xz = new FloatProcessor(datos.getWidth(), datos.getSlices(), datos.getImgVectorxz(1,0));
		
		ipxy = new ImagePlus("e",xy);
		ipyz = new ImagePlus("eh",yz);
		ipxz = new ImagePlus("ehh",xz);

		
		bxy = new ImagePlus("", xy);
		bxz = new ImagePlus("", xz);
		byz = new ImagePlus("", yz);

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


	//	int frame = ((JSlider)e.getSource()).getValue(); 
		String name = ((JSlider)e.getSource()).getName(); 
		
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
		xy = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectorxy(frame+1,time));
		ImagePlus txy = new ImagePlus("", xy);
		bxy.setImage(txy);
		bxy.getProcessor().setInterpolate(true); 
		bxy.setProcessor( bxy.getProcessor().resize(datos.getWidth()*5, datos.getHeight()*5)); 
		bxy.updateImage();		
		bxy.show(); 
	}


	private void updateyz(int frame,int time) {
		yz = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectoryz(frame+1,time));
		ImagePlus tyz = new ImagePlus("", yz);
		byz.setImage(tyz);
		byz.getProcessor().setInterpolate(true); 
		byz.setProcessor( byz.getProcessor().resize(datos.getWidth()*5, datos.getHeight()*5)); 
		byz.updateImage();		
		byz.show(); 
	}


	private void updatexz(int frame,int time) {
		xz = new FloatProcessor(datos.getWidth(), datos.getSlices(), datos.getImgVectorxz(frame+1,time));
		ImagePlus txz = new ImagePlus("", xz);
		bxz.setImage(txz);
		bxz.getProcessor().setInterpolate(true); 
		bxz.setProcessor( bxz.getProcessor().resize(datos.getWidth()*5, datos.getHeight()*5)); 
		bxz.updateImage();		
		bxz.show(); 
	} 
} 