package views;

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

	private ImagePlus ip = null; 
	private JSlider slicesSliderxy = null; 
	Stack4D5D datos;
	ImageProcessor a ;
	ImagePlus b ;
	String typeView = null;

	public MenuCanvas(Stack4D5D stack4d5d) {
		datos = stack4d5d;



		a = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectorxy(1));
		
		ip = new ImagePlus("e",a);



		slicesSliderxy = new JSlider(JSlider.HORIZONTAL,0,ip.getStackSize()-1,0); 
		slicesSliderxy.setBounds(154, 2, 252, 26);
		slicesSliderxy.addChangeListener(this); 

		setLayout(null);
		add(slicesSliderxy); 

		JCheckBox chckbxXy = new JCheckBox("XY");
		chckbxXy.setBounds(53, 5, 45, 23);
		add(chckbxXy);
		JCheckBox chckbxXz = new JCheckBox("XZ");
		chckbxXz.setBounds(6, 5, 45, 23);
		add(chckbxXz);

		JCheckBox chckbxYz = new JCheckBox("YZ");
		chckbxYz.setBounds(103, 5, 45, 23);
		add(chckbxYz);




		/*   add(slicesSliderxy,BorderLayout.SOUTH); 
        add(slicesSlideryz,BorderLayout.SOUTH); */

		/*short[] aux= (short[])(ip.getProcessor().getPixels());
    	 byte[] vector = new byte[2*aux.length];
    	 vector = (byte[])aux;*/

		
		b = new ImagePlus("", a);

		//   b.show();
		//canvasxy = new ImageCanvas(b);
		/*canvasxz =new ImageCanvas(b);

		canvasyz = new ImageCanvas(b); 
   /*    add(canvasxz,BorderLayout.EAST); */
		//  add(canvasxy);
		/* add(canvasyz,BorderLayout.WEST);

		 */




		// Define ChangeListener
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton =
						(AbstractButton)changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				boolean armed = buttonModel.isArmed();
				boolean pressed = buttonModel.isPressed();
				boolean selected = buttonModel.isSelected();
				System.out.println("Changed: " + armed + "/" + pressed + "/" +
						selected);
				typeView="XY";
			}
		};

		chckbxXy.addChangeListener(changeListener);


		ChangeListener changeListener1 = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton =
						(AbstractButton)changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				boolean armed = buttonModel.isArmed();
				boolean pressed = buttonModel.isPressed();
				boolean selected = buttonModel.isSelected();
				System.out.println("Changed1: " + armed + "/" + pressed + "/" +
						selected);
				slicesSliderxy.setMaximum(300);
				typeView="XZ";
			}
		};

		chckbxXz.addChangeListener(changeListener1);


		ChangeListener changeListener2 = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton =
						(AbstractButton)changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				boolean armed = buttonModel.isArmed();
				boolean pressed = buttonModel.isPressed();
				boolean selected = buttonModel.isSelected();
				System.out.println("Changed2: " + armed + "/" + pressed + "/" +
						selected);
				slicesSliderxy.setMaximum(300);
				typeView="YZ";
			}
		};

		chckbxYz.addChangeListener(changeListener2);

	}


	@Override
	public void stateChanged(ChangeEvent e) {
		// Someone has dragged the image slice slider.  We need 
		// to update the frame being shown 


		int frame = ((JSlider)e.getSource()).getValue(); 

		System.out.println("Udating to show frame "+frame); 
		if(typeView.equals("XY")){
			a = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectorxy(frame+1));

		}  else if (typeView.equals("XZ")){
			a = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectorxz(frame+1));

		}
		else if(typeView.equals("YZ")){
			a = new FloatProcessor(datos.getWidth(), datos.getHeight(), datos.getImgVectoryz(frame+1));


		}
		// ImageStack ss = new ImageStack(datos.getWidth(), datos.getHeight());
		
		System.out.println(a.getWidth() +"-------------------------------------->>>>>>>>>>>>>><");
	
		ImagePlus t = new ImagePlus("", a);
		System.out.println(a.getWidth() +"++-------------------------------------->>>>>>>>>>>>>><");
		
		
		
		b.setImage(t);
		//    b = new ImagePlus("", a);

		//b.setSlice(frame+1);
		
		
		//b.show();

		b.getProcessor().setInterpolate(true); 
		b.setProcessor( b.getProcessor().resize(datos.getWidth()*4, datos.getHeight()*4)); 
		b.updateImage();
		//b.updateAndDraw();
		
		b.show(); 

	} 
} 