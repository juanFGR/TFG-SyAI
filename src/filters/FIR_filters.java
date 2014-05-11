package filters;

import ij.IJ;
import ij.gui.GenericDialog;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import stacks.Stack4D5D;
import submodules.histograms.ChartForFFT;

public class FIR_filters {

    double[] buffer;
    
    public FIR_filters(double[] buffer) {
	this.buffer = buffer;
    }

    public static double sinc(final double x)
    {
	if(x != 0)
	{
	    final double xpi = Math.PI * x;
	    return Math.sin(xpi) / xpi;
	}
	return 1.0;
    }

    
    
    double[] filterResponse;
    /** @param order The filter order (taps - 1)
     *  @param fc: The cut off frequency
     *  @param fs: The sample rate
     **/
    public static double[] createLowpass
    (final int order, final double fc, double fs)
    {
	final double cutoff = fc / fs;
	final double[] fir = new double[order + 1];
	final double factor = 2.0 * cutoff;
	final int half = order >> 1;
	for(int i = 0; i < fir.length; i++)
	{
	    fir[i] = factor * sinc(factor * (i - half));
	    IJ.log(fir[i]+"");
	}
	return fir;
    }


    public static double[] createHighpass
    (final int order, final double fc, double fs)
    {
	final double cutoff = fc / fs;
	final double[] fir = new double[order + 1];
	final double factor = 2.0 * cutoff;
	final int half = order >> 1;
	for(int i = 0; i < fir.length; i++)
	{
	    fir[i] = (i == half ? 1.0 : 0.0) 
		    - factor * sinc(factor * (i - half));
	}
	return fir;
    }

    public static double[] createBandstop
    (final int order, 
	    final double fcl, final double fch, final double fs)
    {
	final double[] low = createLowpass(order, fcl, fs);
	final double[] high = createHighpass(order, fch, fs);
	for(int i = 0; i < low.length; i++)
	{
	    low[i] += high[i];
	}
	return low;
    }




    public static double[] createBandpass
    (final int order, 
	    final double fcl, final double fch, final double fs)
    {
	final double[] fir = createBandstop(order, fcl, fch, fs);
	final int half = order >> 1;
	for(int i = 0; i < fir.length; i++)
	{
	    fir[i] = (i == half ? 1.0 : 0.0) - fir[i];
	}
	return fir;
    }




    public static double[] windowBartlett(final double[] fir)
    {
	final int m = fir.length - 1;
	final int m2 = m >> 1;
	for(int i = 0; i < fir.length; i++)
	{
	    fir[i] *= 1.0 - 2.0 * (i - m2) / m;
	}
	return fir;
    }




    public static double[] windowSinc(final double[] fir)
    {
	final int m = fir.length - 1;
	for(int i = 0; i < fir.length; i++)
	{
	    fir[i] *= sinc(2.0 * i / m - 1.0);
	}
	return fir;
    }

    public static double[] windowHamming(final double[] fir)
    {
	final int m = fir.length - 1;
	for(int i = 0; i < fir.length; i++)
	{
	    fir[i] *= 0.54 - 0.46 * Math.cos(2.0 * Math.PI * i / m);
	}
	return fir;
    }


    public static double[] windowBlackman(final double[] fir)
    {
	final int m = fir.length - 1;
	for(int i = 0; i < fir.length; i++)
	{
	    fir[i] *= 0.42 - 0.5 * Math.cos(2.0 * Math.PI * i / m) + 0.08 * Math.cos(4.0 * Math.PI * i / m);
	}
	return fir;
    }

    
    double[] filterResponse2;
    public  void Plotting_frequency( double d,final double[] fir)
    {
	 filterResponse2 = new double[fir.length];
	for(int x = 0; x < fir.length; x++)
	{
	    final double w = (fir[x]*d) * Math.PI *2;
	    double re = 0, im = 0;
	    for(int i = 0; i < fir.length; i++)
	    {
		re += fir[i] * Math.cos(i * w);
		im -= fir[i] * Math.sin(i * w);
	    }
	    
	    filterResponse2[x]=Math.sqrt(re * re + im * im);
	    // v now contains the 'magnitude' of the given frequency
	    // a good thing would be: transform 'v' from linear to 
	    // decibel scale and plot it (e.g. on a BufferedImage)
	}
    }


    
    
    
   

    TextField[] tf = new TextField[3];
    Label[]tt = new Label[3];
    double[] value = new double[3];

    public void mostrar( ) {
        if (IJ.versionLessThan("1.31l"))
            return;
        if (showDialog())
            displayValues();
    }

    boolean showDialog() {
        GenericDialog gd = new GenericDialog("Fir filters");
        gd.addPanel(makePanel(gd));
        gd.showDialog();
        if (gd.wasCanceled())
            return false;
        getValues();
        return true;
    }

    Panel makePanel(GenericDialog gd) {
        Panel panel = new Panel();
            panel.setLayout(new GridLayout(3,2));
       
            tt[0] = new Label("Order");   
            panel.add(tt[0]);
            tf[0] = new TextField(""+(Stack4D5D.getFrames()-1)); 
            panel.add(tf[0]);
            tt[1] = new Label("Frequency");   
            panel.add(tt[1]);
            tf[1] = new TextField(""+value[1]);
            panel.add(tf[1]);
            tt[2] = new Label("S.rate");   
            panel.add(tt[2]);
            tf[2] = new TextField(""+Stack4D5D.getSlices());
            panel.add(tf[2]);
        
        return panel;
    }

    void getValues() {
        for (int i=0; i<3; i++) {
            String s = tf[i].getText();
            value[i] = getValue(s);
        }           
    }

    void displayValues() {
	double [] fir = createLowpass((int)value[0],value[1],value[2]);
	
	filterResponse = new double[fir.length];
	System.out.println(fir.length);
        for (int i=0; i<fir.length; i++)      {   
           filterResponse[i] = operationWithFir(fir,buffer,i);
        }
    //    fir =windowSinc(filterResponse);
        
        ChartForFFT chartForFFT = new ChartForFFT();
        Plotting_frequency(((1.0/(Stack4D5D.formatVTC.getTr()/1000.0))/fir.length), fir);
       
        chartForFFT.initialize(filterResponse2,1);
    }

   

    private double operationWithFir(double[] fir, double[] buffer2, int i) {
	double tmp=0;
	for (int j = 0; j < i+1; j++) {
	   tmp+= fir[j]*buffer2[j]; 
	}
	
	return tmp;
    }

    double getValue(String theText) {
        Double d;
        try {d = new Double(theText);}
        catch (NumberFormatException e){
            d = null;
        }
        return d==null?Double.NaN:d.doubleValue();
    }

}

