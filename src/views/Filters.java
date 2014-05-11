package views;

import ij.IJ;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Filters implements DialogListener {
    public void initialize () {
	GenericDialog  gd = new GenericDialog("Menu de Filtros");	
	gd.setModal(false);
	Button media = new Button("ee");
	Button fourier = new Button("eerr");
	Button fir_pasabaja = new Button("eerr");
	Button fir_pasabanda = new Button("eerr");
	Button fir_pasaalta = new Button("eerr");
	media.setBounds(0, 0, 20, 10);
	fourier.setBounds(0, 10, 20, 10);
	fir_pasabaja.setBounds(0, 20, 20, 10);
	fir_pasabanda.setBounds(0, 30, 20, 10);
	fir_pasaalta.setBounds(0, 40, 20, 10);
	
	gd.hideCancelButton();
	gd.setOKLabel("Cerrar");
	gd.addDialogListener(this);
	gd.setBounds(200,0, 400, 400);
	gd.setLocation(10, 200);
	
	media.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		IJ.log("ee");
		
	    }
	});
	fourier.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		IJ.log("ee");
		
	    }
	});
	
	
	
	gd.add(media);
	gd.add(fourier);
	gd.add(fir_pasabaja);
	gd.add(fir_pasabanda);
	gd.add(fir_pasaalta);
	gd.showDialog(); 
	
    }

    @Override
    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
	// TODO Auto-generated method stub
	return false;
    }
}
