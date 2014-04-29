package views;

import ij.IJ;

import javax.swing.JFrame;

import submodules.plugins.VOI_media;
import core.Core;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame  {

    private static final long serialVersionUID = 173125243611514317L;
    Core core = new Core();

    public Menu() {
	getContentPane().setLayout(null);

	Button importSequenceBtn = new Button("Import Sequence");
	importSequenceBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		core.launch_Import_vtc_Sequence();
	    }
	});
	importSequenceBtn.setBounds(10, 10, 111, 22);
	getContentPane().add(importSequenceBtn);


	Button openPerspectiveBtn = new Button("Open Perspective");
	openPerspectiveBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {


		JFrame frame = new JFrame(); 
		OrthogonalPerspective a = new OrthogonalPerspective();


		frame.add(a); 
		frame.setBounds(600, 0, 400, 200);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 

	    }
	});
	openPerspectiveBtn.setBounds(10, 39, 111, 22);
	getContentPane().add(openPerspectiveBtn);


	Button Voi_MediaBtn = new Button("Signal");
	Voi_MediaBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		if (IJ.getImage().getRoi() != null){
		VOI_media a = new VOI_media();
		a.initialize(IJ.getImage().getRoi());
		}

	    }
	});
	Voi_MediaBtn.setBounds(10, 60, 111, 22);
	getContentPane().add(Voi_MediaBtn);
	
	
	
	Button VoidBtn = new Button("Cargar Void");
	VoidBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		core.launch_Import_Voids();

	    }
	});
	VoidBtn.setBounds(10,85, 111, 22);
	getContentPane().add(VoidBtn);

    }
}
