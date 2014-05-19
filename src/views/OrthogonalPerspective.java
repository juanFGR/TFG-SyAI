package views;

import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.gui.ImageCanvas;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.GeneralPath;

import stacks.Stack4D5D;
import core.inter.inter;


public class OrthogonalPerspective implements DialogListener  {


    ImageProcessor xy, yz, xz ;
    ImagePlus bxy , byz, bxz ;
    String typeView = null;
    ImageCanvas canvas;



    public void initialize () {

	GenericDialog  gd = new GenericDialog("Controles");

	gd.addSlider("XY", 0.0, Stack4D5D.getSlices(), 0.0);
	gd.addSlider("XZ", 0.0, Stack4D5D.getHeight(), 0.0);
	gd.addSlider("YZ", 0.0, Stack4D5D.getWidth(), 0.0);
	gd.addSlider("Time", 0.0, Stack4D5D.getFrames(), 0.0);
	gd.setModal(false);
	gd.hideCancelButton();
	gd.setOKLabel("Cerrar");
	gd.addDialogListener(this);
	gd.showDialog();           // user input (or reading from macro) happens here       
    }




    @Override
    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
	double []newStates={0.0,0.0,0.0,0.0};
	for (int i = 0; i < newStates.length; i++) {
	    newStates[i]= gd.getNextNumber();
	}


	GeneralPath path = new GeneralPath();
	Point p = new Point((int)newStates[2] ,(int) newStates[1] );
	drawXYCross(bxy, p, path);
	drawPoints(bxy, path);
	GeneralPath path2 = new GeneralPath();
	drawXZCross(bxz, p, path2);
	GeneralPath path3 = new GeneralPath();
	drawYZCross(byz, p, path3);

	bxy.setOverlay(path, Color.BLUE, new BasicStroke(1));
	byz.setOverlay(path2, Color.BLUE, new BasicStroke(1));
	bxz.setOverlay(path3, Color.BLUE, new BasicStroke(1));


	if(Stack4D5D.actualPlane[0]!=newStates[0]){
	    Stack4D5D.actualPlane[0]=newStates[0];
	    updatexy((int)Stack4D5D.actualPlane[0],(int)Stack4D5D.actualPlane[3]);
	}else if(Stack4D5D.actualPlane[1]!=newStates[1]){
	    Stack4D5D.actualPlane[1]=newStates[1];
	    updatexz((int)Stack4D5D.actualPlane[1],(int)Stack4D5D.actualPlane[3]);
	}else if(Stack4D5D.actualPlane[2]!=newStates[2]){
	    Stack4D5D.actualPlane[2]=newStates[2];
	    updateyz((int)Stack4D5D.actualPlane[2],(int)Stack4D5D.actualPlane[3]);
	}else if(Stack4D5D.actualPlane[3]!=newStates[3]){
	    Stack4D5D.actualPlane[3]=newStates[3];
	    updatexy((int)Stack4D5D.actualPlane[0],(int)Stack4D5D.actualPlane[3]);
	    updatexz((int)Stack4D5D.actualPlane[1],(int)Stack4D5D.actualPlane[3]);
	    updateyz((int)Stack4D5D.actualPlane[2],(int)Stack4D5D.actualPlane[3]);
	}

	return false;
    }



    public OrthogonalPerspective() {

	xy = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectorxy(1,0));
	yz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectoryz(1,0));
	xz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getSlices(), Stack4D5D.getImgVectorxz(1,0));




	bxy = new ImagePlus(inter.texts.getString("window_xy"), xy);
	bxz = new ImagePlus(inter.texts.getString("window_xz"), xz);
	byz = new ImagePlus("YZ", yz);

	updatexy(0,0);
	updatexz(0,0);
	updateyz(0,0);



	bxy.show();
	bxz.show();
	byz.show();
	//ImageWindow win = bxy.getWindow();
	//canvas = win.getCanvas();
	//canvas.addMouseListener(this);
	//canvas.addMouseMotionListener(this);

	bxy.getWindow().setLocation(10, 10);
	byz.getWindow().setLocation(bxy.getWidth()+35, 10);
	bxz.getWindow().setLocation(10, bxy.getHeight()+65);

	// Create a universe and show it

	/*	Image3DUniverse univ = new Image3DUniverse();
		univ.show();
		// Add the image as an isosurface
		Content c = univ.addVoltex(bxy);*/


    }


    private void updatexy(int frame,int time) {
	xy = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectorxy(frame+1,time));
	ImagePlus txy = new ImagePlus("", xy);
	txy.getProcessor().setInterpolate(true); 
	txy.setProcessor( txy.getProcessor().resize(Stack4D5D.getWidth()*Stack4D5D.TAM_RESIZE   , Stack4D5D.getHeight()*Stack4D5D.TAM_RESIZE   )); 
	bxy.setImage(txy);
	bxy.repaintWindow();		

    }


    private void updateyz(int frame,int time) {
	yz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getHeight(), Stack4D5D.getImgVectoryz(frame+1,time));
	ImagePlus tyz = new ImagePlus("", yz);

	tyz.getProcessor().setInterpolate(true); 
	tyz.setProcessor( tyz.getProcessor().resize(Stack4D5D.getWidth() *Stack4D5D.TAM_RESIZE  , Stack4D5D.getHeight()*Stack4D5D.TAM_RESIZE  )); 
	byz.setImage(tyz);
	byz.repaintWindow();		

    }


    private void updatexz(int frame,int time) {
	xz = new FloatProcessor(Stack4D5D.getWidth(), Stack4D5D.getSlices(), Stack4D5D.getImgVectorxz(frame+1,time));
	ImagePlus txz = new ImagePlus("", xz);

	txz.getProcessor().setInterpolate(true); 
	txz.setProcessor( txz.getProcessor().resize(Stack4D5D.getWidth()*Stack4D5D.TAM_RESIZE  , Stack4D5D.getHeight()*Stack4D5D.TAM_RESIZE  )); 
	bxz.setImage(txz);
	bxz.repaintWindow();		
    }

    void drawPoints(ImagePlus bxy2, GeneralPath path2) {

	for(int h=0; h<Stack4D5D.ListOfVoid.size(); h++) {		
	    path2.moveTo(Math.abs(Stack4D5D.ListOfVoid.get(h).x*Stack4D5D.TAM_RESIZE)/3,Math.abs(Stack4D5D.ListOfVoid.get(h).y*Stack4D5D.TAM_RESIZE)/3);
	    path2.lineTo(Math.abs(Stack4D5D.ListOfVoid.get(h).x*Stack4D5D.TAM_RESIZE)/3,Math.abs(Stack4D5D.ListOfVoid.get(h).y*Stack4D5D.TAM_RESIZE)/3);
	}
    }


    //Views 
    /** draws the crosses in the images */
    void drawYZCross(ImagePlus imp, Point p, GeneralPath path) {
	int height=imp.getHeight();
	float x = p.x*Stack4D5D.TAM_RESIZE;
	path.moveTo(x, 0f);
	path.lineTo(x, height);	
    }
    /** draws the crosses in the images */
    void drawXZCross(ImagePlus imp, Point p, GeneralPath path) {
	int width=imp.getWidth();
	float y = p.y*Stack4D5D.TAM_RESIZE;
	path.moveTo(0f, y);
	path.lineTo(width, y);
    }

    /** draws the crosses in the images */
    void drawXYCross(ImagePlus imp, Point p, GeneralPath path) {
	int width=imp.getWidth();
	int height=imp.getHeight();
	float x = p.x * Stack4D5D.TAM_RESIZE;
	float y = p.y*Stack4D5D.TAM_RESIZE;
	path.moveTo(0f, y);
	path.lineTo(width, y);
	path.moveTo(x, 0f);
	path.lineTo(x, height);	
    }
} 