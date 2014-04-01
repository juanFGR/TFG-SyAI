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

import stacks.Stack4D5D;

public class MenuCanvas extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 6042629063683509113L;

	private ImagePlus ipxy = null; 
	private ImagePlus ipyz = null; 
	private ImagePlus ipxz = null; 
	private JSlider slicesSliderxy = null; 
	private JSlider slicesSlideryz = null; 
	private JSlider slicesSliderxz = null; 
	Stack4D5D datos;
	ImageProcessor xy ;
	ImageProcessor yz ;
	ImageProcessor xz ;
	ImagePlus bxy ;
	ImagePlus byz ;
	ImagePlus bxz ;
	String typeView = null;

	public MenuCanvas(Stack4D5D stack4d5d) {
		datos = stack4d5d;



		xy = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectorxy(1));
		yz = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectoryz(1));
		xz = new FloatProcessor(datos.getWidth(), datos.getSlices(), datos.getImgVectorxz(1));
		
		ipxy = new ImagePlus("e",xy);
		ipyz = new ImagePlus("eh",yz);
		ipxz = new ImagePlus("ehh",xz);

		
		bxy = new ImagePlus("", xy);
		bxz = new ImagePlus("", xz);
		byz = new ImagePlus("", yz);

		slicesSliderxy = new JSlider(JSlider.HORIZONTAL,0,ipxy.getStackSize()-1,0); 
		slicesSliderxy.setName("XY");
		slicesSliderxy.setBounds(154, 2, 252, 26);
		slicesSliderxy.setMaximum(20);
		slicesSliderxy.addChangeListener(this); 

		
		slicesSlideryz = new JSlider(JSlider.HORIZONTAL,0,ipyz.getStackSize()-1,0); 
		slicesSlideryz.setName("YZ");
		slicesSlideryz.setBounds(154, 30, 252, 26);
		slicesSlideryz.setMaximum(20);
		slicesSlideryz.addChangeListener(this); 

		
	
		
		slicesSliderxz = new JSlider(JSlider.HORIZONTAL,0,ipxz.getStackSize()-1,0); 
		slicesSliderxz.setName("XZ");
		slicesSliderxz.setBounds(154, 70, 252, 26);
		slicesSliderxz.setMaximum(20);
		slicesSliderxz.addChangeListener(this); 

		
		
		setLayout(null);

		add(slicesSliderxy); 
		add(slicesSlideryz); 
		add(slicesSliderxz); 
		slicesSliderxy.setMaximum(20);
	
			/*   add(slicesSliderxy,BorderLayout.SOUTH); 
        add(slicesSlideryz,BorderLayout.SOUTH); */

		/*short[] aux= (short[])(ip.getProcessor().getPixels());
    	 byte[] vector = new byte[2*aux.length];
    	 vector = (byte[])aux;*/


		//   b.show();
		//canvasxy = new ImageCanvas(b);
		/*canvasxz =new ImageCanvas(b);

		canvasyz = new ImageCanvas(b); 
   /*    add(canvasxz,BorderLayout.EAST); */
		//  add(canvasxy);
		/* add(canvasyz,BorderLayout.WEST);

		 */


	}


	@Override
	public void stateChanged(ChangeEvent e) {
		// Someone has dragged the image slice slider.  We need 
		// to update the frame being shown 


		int frame = ((JSlider)e.getSource()).getValue(); 

		String name = ((JSlider)e.getSource()).getName(); 
		
		if(name.equals("XY")){
			updatexy(frame);
			
		}  else if (name.equals("XZ")){
			updatexz(frame);
			
		}
		else if(name.equals("YZ")){
			updateyz(frame);
		}
		// ImageStack ss = new ImageStack(datos.getWidth(), datos.getHeight());
		
	
		
	
	

	}


	private void updatexy(int frame) {
		xy = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectorxy(frame+1));
		ImagePlus txy = new ImagePlus("", xy);
		bxy.setImage(txy);
		bxy.getProcessor().setInterpolate(true); 
		bxy.setProcessor( bxy.getProcessor().resize(datos.getWidth()*5, datos.getHeight()*5)); 
		bxy.updateImage();		
		bxy.show(); 
	}


	private void updateyz(int frame) {
		yz = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectoryz(frame+1));
		ImagePlus tyz = new ImagePlus("", yz);
		byz.setImage(tyz);
		byz.getProcessor().setInterpolate(true); 
		byz.setProcessor( byz.getProcessor().resize(datos.getWidth()*5, datos.getHeight()*5)); 
		byz.updateImage();		
		byz.show(); 
	}


	private void updatexz(int frame) {
		xz = new FloatProcessor(datos.getWidth(), datos.getSlices(), datos.getImgVectorxz(frame+1));
		ImagePlus txz = new ImagePlus("", xz);
		bxz.setImage(txz);
		bxz.getProcessor().setInterpolate(true); 
		bxz.setProcessor( bxz.getProcessor().resize(datos.getWidth()*5, datos.getHeight()*5)); 
		bxz.updateImage();		
		bxz.show(); 
	} 
} 