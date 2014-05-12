package views;

import filters.FFT;
import filters.FIR_filters;
import ij.IJ;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.inter.inter;
import submodules.plugins.VOI_media;

public class Filters implements DialogListener {

    public static VOI_media filter_media = new VOI_media();
    public static FFT filter_fft;
    
    public void initialize () {
	GenericDialog  gd = new GenericDialog("Menu de Filtros");
	   Panel panel = new Panel();
           panel.setLayout(new GridLayout(5,1));
           
	gd.setModal(false);
	Button media = new Button(inter.texts.getString("F_media"));
	Button fourier = new Button(inter.texts.getString("F_media"));
	Button fir_pasabaja = new Button(inter.texts.getString("F_media"));
	Button fir_pasabanda = new Button(inter.texts.getString("F_media"));
	Button fir_pasaalta = new Button(inter.texts.getString("F_media"));
	

	panel.add(media);
	panel.add(fourier);
	panel.add(fir_pasabaja);
	panel.add(fir_pasabanda);
	panel.add(fir_pasaalta);

	

	media.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (IJ.getImage().getRoi() != null){
		    filter_media.initialize(IJ.getImage().getRoi());
		}else{
		    IJ.error(inter.texts.getString("E_NullVOI"));
		}
		    
	    }
	});
	fourier.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int _bufferSize = filter_media.buffer.length;
		if(_bufferSize>0){
		    filter_fft = new FFT(filter_media.nextPotencia2(filter_media.buffer.length));
		    filter_fft.initializeAndDrawPlot(filter_media.buffer,filter_media.nextPotencia2(filter_media.buffer.length));
		}else
		    IJ.error(inter.texts.getString("E_previusMedia"));
	    }
	});

	fir_pasabaja.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int _bufferSize = filter_fft.real.length;
		if(_bufferSize>0){
			FIR_filters filters = new FIR_filters(filter_fft.real);
			filters.mostrar();
		}else
		    IJ.error(inter.texts.getString("E_previusFFT"));
	    }
	});
	
	fir_pasabanda.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int _bufferSize = filter_fft.real.length;
		if(_bufferSize>0){
			FIR_filters filters = new FIR_filters(filter_fft.real);
			filters.mostrar();
		}else
		    IJ.error(inter.texts.getString("E_previusFFT"));
	    }
	});
	
	fir_pasaalta.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		int _bufferSize = filter_fft.real.length;
		if(_bufferSize>0){
			FIR_filters filters = new FIR_filters(filter_fft.real);
			filters.mostrar();
		}else
		    IJ.error(inter.texts.getString("E_previusFFT"));
	    }
	});
	


	gd.addPanel(panel);
	gd.hideCancelButton();
	gd.setOKLabel("Cerrar");
	gd.addDialogListener(this);
	gd.setBounds(200,0, 400, 400);
	gd.setLocation(10, 200);
	gd.showDialog(); 

    }

    @Override
    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
	// TODO Auto-generated method stub
	return false;
    }
}
