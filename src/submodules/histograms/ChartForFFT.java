package submodules.histograms;

import ij.util.Tools;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import core.inter.inter;

public class ChartForFFT {
    Chart   plot;
    public Chart getPlot() {
	return plot;
    }

    public void setPlot(Chart plot) {
	this.plot = plot;
    }

	public float[] x;
    public void initialize(double[] real,double escala) {


	float[] y;
	x = new float[real.length/2];
	    y = new float[real.length/2];  
	    for (int i = 0; i < real.length/2; i++) {
		y[i]= (float) real[i];
		x[i]=(float) (i*escala);
	    }

	


	ChartWindow.noGridLines = false; // draw grid lines
	plot  = new Chart(inter.texts.getString("H_fourier"),"Frecuency(Hz)","10log(P)",x,y);
	plot.setLimits(0, Tools.getMinMax(x)[1],Tools.getMinMax(real)[0] , Tools.getMinMax(real)[1]);
	plot.setLineWidth(1);


	plot.changeFont(new Font("Helvetica", Font.PLAIN, 16));
	plot.setColor(Color.blue);
	plot.show();
    }

}
