package submodules.histograms;



import ij.util.Tools;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;



public class ChartForMedia   {

    Chart   plot;
    public Chart getPlot() {
	return plot;
    }

    public void setPlot(Chart plot) {
	this.plot = plot;
    }

    public void initialize(double[] intensity,double escala) {

	float[] x;
	float[] y;
	x = new float[intensity.length];
	y = new float[intensity.length];  
	for (int i = 0; i < intensity.length; i++) {
	    y[i]= (float) intensity[i];
	    x[i]=(float) (i*escala);
	}


	ChartWindow.noGridLines = false; // draw grid lines
	plot  = new Chart("Grafica para la media","Time(s)","Intensity",x,y);

	plot.setLimits(Tools.getMinMax(x)[0], Tools.getMinMax(x)[1],Tools.getMinMax(y)[0] , Tools.getMinMax(y)[1]);	
	plot.setLineWidth(1);

	// add a second curve
	/*  float x2[] = {.4f,.5f,.6f,.7f,.8f};
        float y2[] = {4,3,3,4,5};
        plot.setColor(Color.red);
        plot.addPoints(x2,y2,ChartWindow.X);
        plot.addPoints(x2,y2,ChartWindow.LINE);*/

	// add label
	/*plot.setColor(Color.black);
	  plot.changeFont(new Font("Helvetica", Font.PLAIN, 24));
        plot.addLabel(0.15, 0.95, "This is a label");*/

	plot.changeFont(new Font("Helvetica", Font.PLAIN, 16));
	plot.setColor(Color.blue);
	plot.show();
    }
}
