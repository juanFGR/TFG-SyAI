package submodules.histograms;



import ij.util.Tools;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import stacks.Stack4D5D;


public class ChartWithSliders   {

    Chart   plot;
    public Chart getPlot() {
	return plot;
    }

    public void setPlot(Chart plot) {
	this.plot = plot;
    }

    public void initialize(double[] real, double[] imaginaria) {

	float[] x;
	float[] y;
	x = new float[real.length];

	if(imaginaria == null){
	    y = new float[real.length];  
	    for (int i = 0; i < real.length; i++) {
		y[i]= (float) real[i];
		x[i]=(float) (i*(Stack4D5D.formatVTC.getTr()/1000.0));
	    }

	}else{
	    y = new float[imaginaria.length];
	    for (int i = 0; i < imaginaria.length; i++) {
		y[i]=(float) imaginaria[i];
	    }
	    for (int i = 0; i < real.length; i++) {
		x[i]=(float) real[i];
	    }
	}




	//  float[] e = {.8f,.6f,.5f,.4f,.3f,.5f,.6f,.7f,.8f}; // error bars

	ChartWindow.noGridLines = false; // draw grid lines
	plot  = new Chart("Example Plot","X Axis","Y Axis",x,y);
	if(imaginaria == null){
	    plot.setLimits(0, real.length*Stack4D5D.formatVTC.getTr()/1000.0,Tools.getMinMax(real)[0] , Tools.getMinMax(real)[1]);
	}else{
	    plot.setLimits(0, real.length*Stack4D5D.formatVTC.getTr()/1000.0,Tools.getMinMax(imaginaria)[0] , Tools.getMinMax(imaginaria)[1]);
	}
	//plot.setLimits(0, length, 0, Tools.getMinMax(auxi)[1]);
	plot.setLineWidth(1);
	//  plot.addErrorBars(e);

	// add a second curve
	/*  float x2[] = {.4f,.5f,.6f,.7f,.8f};
        float y2[] = {4,3,3,4,5};
        plot.setColor(Color.red);
        plot.addPoints(x2,y2,ChartWindow.X);
        plot.addPoints(x2,y2,ChartWindow.LINE);*/

	// add label
	plot.setColor(Color.black);
	/*  plot.changeFont(new Font("Helvetica", Font.PLAIN, 24));
        plot.addLabel(0.15, 0.95, "This is a label");*/

	plot.changeFont(new Font("Helvetica", Font.PLAIN, 16));
	plot.setColor(Color.blue);
	plot.show();
    }

    public void change(double[] auxi) {

	float[] x;
	System.out.println(auxi.length);
	float[] y = new float[auxi.length];
	for (int i = 0; i < y.length; i++) {
	    y[i]= (float) auxi[i];
	}
	x= new float[auxi.length];
	for (int i = 0; i < x.length; i++) {
	    x[i]=i;
	}
	//  float[] e = {.8f,.6f,.5f,.4f,.3f,.5f,.6f,.7f,.8f}; // error bars


	//  plot.addErrorBars(e);
	Arrays.fill(x, 0);
	plot.initialize(x, y);
	plot.show2();

	// add a second curve
	/*  float x2[] = {.4f,.5f,.6f,.7f,.8f};
        float y2[] = {4,3,3,4,5};
        plot.setColor(Color.red);

	 */	
    }





    /*public void initialize() {

        float[] x = {0.1f, 0.25f, 0.35f, 0.5f, 0.61f,0.7f,0.85f,0.89f,0.95f}; // x-coordinates
        float[] y = {2f,5.6f,7.4f,9f,9.4f,8.7f,6.3f,4.5f,1f}; // x-coordinates
        float[] e = {.8f,.6f,.5f,.4f,.3f,.5f,.6f,.7f,.8f}; // error bars

        ChartWindow.noGridLines = false; // draw grid lines
        Chart plot = new Chart("Example Plot","X Axis","Y Axis",x,y);
        plot.setLimits(0, 1, 0, 10);
        plot.setLineWidth(2);
        plot.addErrorBars(e);

        // add a second curve
        float x2[] = {.4f,.5f,.6f,.7f,.8f};
        float y2[] = {4,3,3,4,5};
        plot.setColor(Color.red);
        plot.addPoints(x2,y2,ChartWindow.X);
        plot.addPoints(x2,y2,ChartWindow.LINE);

        // add label
        plot.setColor(Color.black);
        plot.changeFont(new Font("Helvetica", Font.PLAIN, 24));
        plot.addLabel(0.15, 0.95, "This is a label");

        plot.changeFont(new Font("Helvetica", Font.PLAIN, 16));
        plot.setColor(Color.blue);
        plot.show();
    }*/

}
