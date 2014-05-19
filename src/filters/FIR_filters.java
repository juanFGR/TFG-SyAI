package filters;

import ij.IJ;
import ij.gui.GenericDialog;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.util.Arrays;

import core.inter.inter;
import process3d.Dilate_;
import stacks.Stack4D5D;
import submodules.histograms.Chart;
import submodules.histograms.ChartForFFT;
import submodules.histograms.ChartWindow;

public class FIR_filters {

    double[] buffer,filterResponse, filterResponse2, filterf;

    
    public FIR_filters(double[] buffer, int size) {
	this.buffer = buffer;
	double [] real = new double[buffer.length];
	Arrays.fill(real, 0);

	double _fillFFTwiththisvalue = 0;
	for (int i = 0; i < buffer.length; i++) {
	    real[i] = buffer[i];
	    _fillFFTwiththisvalue +=buffer[i];
	}

	_fillFFTwiththisvalue = (double)(_fillFFTwiththisvalue/(double)buffer.length);
	for (int i = buffer.length-1; i < real.length; i++) {
	    real[i] = _fillFFTwiththisvalue;
	}
	buffer = Arrays.copyOf(real, real.length);
    }





    public  void Plotting_frequency( double d,final double[] fir)
    {
	filterf = new double[1024]; 
	filterResponse2 = new double[1024];
	for(int x = 0; x < 1024; x++)
	{
	    final double w = (x * Math.PI) /1024;
	    filterf[x] = (w*d)/(4*Math.PI);
	    double re = 0, im = 0;

	    for (int i = 0; i < fir.length; i++) {
		re += fir[i] * Math.cos(i * w);
		im -= fir[i] * Math.sin(i * w);
	    }

	    filterResponse2[x]=Math.sqrt(re * re + im * im);
	    filterResponse2[x] = 10*Math.log10(filterResponse2[x]+1);
	}
    }


    TextField[] tf = new TextField[4];
    Label[]tt = new Label[4];
    double[] value = new double[4];

    public void mostrar( String type ) {
	if (IJ.versionLessThan("1.31l"))
	    return;
	if (showDialog(type))
	    displayValues(type);
    }

    boolean showDialog(String type) {
	GenericDialog gd = new GenericDialog("Fir filters");
	if(type.equals(inter.texts.getString("F_lowBand"))){
	    gd.addPanel(makePanel_withLowPass(gd));
	}else if(type.equals(inter.texts.getString("F_averageBand"))){
	    gd.addPanel(makePanel_withBandPass(gd));
	}else if(type.equals(inter.texts.getString("F_highBand"))){
	    gd.addPanel(makePanel_withHighPass(gd));
	}
	gd.showDialog();
	if (gd.wasCanceled())
	    return false;
	getValues();
	return true;
    }


    Panel makePanel_withHighPass(GenericDialog gd) {
	Panel panel = new Panel();
	panel.setLayout(new GridLayout(3,2));

	tt[0] = new Label("Order");   
	panel.add(tt[0]);
	tf[0] = new TextField(""+(Stack4D5D.getFrames()-1)); 
	panel.add(tf[0]);
	tt[1] = new Label("High Pass");   
	panel.add(tt[1]);
	tf[1] = new TextField(""+value[1]);
	panel.add(tf[1]);
	tt[2] = new Label("S.rate");   
	panel.add(tt[2]);
	tf[2] = new TextField(""+3.384);
	panel.add(tf[2]);

	return panel;
    }

    Panel makePanel_withBandPass(GenericDialog gd) {  
	Panel panel = new Panel();
	panel.setLayout(new GridLayout(4,2));

	tt[0] = new Label("Order");   
	panel.add(tt[0]);
	tf[0] = new TextField(""+(Stack4D5D.getFrames()-1)); 
	panel.add(tf[0]);
	
	tt[1] = new Label("Low Frequency");   
	panel.add(tt[1]);
	tf[1] = new TextField(""+value[1]);
	panel.add(tf[1]);
	
	tt[2] = new Label("High Frequency");   
	panel.add(tt[2]);
	tf[2] = new TextField(""+value[2]);
	panel.add(tf[2]);
	
	tt[3] = new Label("S.rate");   
	panel.add(tt[3]);
	tf[3] = new TextField(""+3.384);
	panel.add(tf[3]);

	return panel;
    }

    Panel makePanel_withLowPass(GenericDialog gd) {
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
	tf[2] = new TextField(""+3.384);
	panel.add(tf[2]);

	return panel;
    }

    void getValues() {
	for (int i=0; i<3; i++) {
	    String s = tf[i].getText();
	    value[i] = getValue(s);
	}           
    }

   public static double [] firSuave = null;
    void displayValues(String type) {

	
	if(type.equals(inter.texts.getString("F_lowBand"))){
	    firSuave  = createLowpass((int)value[0],value[1],1/value[2]);
	}else if(type.equals(inter.texts.getString("F_averageBand"))){
	    firSuave  = createBandpass((int)value[0],value[1],value[2],1/value[3]);
	}else if(type.equals(inter.texts.getString("F_highBand"))){
	    firSuave  = createHighpass((int)value[0],value[1],1/value[2]);
	}
	
	firSuave = windowSinc(firSuave);
	Plotting_frequency(((1.0/(Stack4D5D.formatVTC.getTr()/1000.0))), firSuave);        
	Chart e = new Chart("ww", "fr(HZ)", "h",filterf, filterResponse2);
	e.show();

    }


    public static double[] reverseArray(double[] data)
    {
	double[] reversedData = new double[data.length];
	int i,j=data.length/2;
	for(i=0; i < data.length/2; i++)
	{
	    reversedData[i] = data[j];
	    j--;
	}
	return reversedData;
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






    public static double sinc(final double x)
    {
	if(x != 0)
	{
	    final double xpi = Math.PI * x;
	    return Math.sin(xpi) / xpi;
	}
	return 1.0;
    }




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





    public double[] getFilterResponse2() {
        return filterResponse2;
    }





    public void setFilterResponse2(double[] filterResponse2) {
        this.filterResponse2 = filterResponse2;
    }

    
    
}

