package views;

import ij.IJ;

import javax.swing.JFrame;

import stacks.Stack4D5D;
import submodules.plugins.VOI_media;
import core.Core;
import core.inter.inter;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame  {

    private static final long serialVersionUID = 173125243611514317L;
    Core core = new Core();
    Filters menuFilters = new Filters();
    public Menu() {
	getContentPane().setLayout(null);

	Button importSequenceBtn = new Button(inter.texts.getString("Import_Sequence"));
	importSequenceBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		
		core.launch_Import_vtc_Sequence();
	
	    }
	});
	importSequenceBtn.setBounds(0, 0, 120, 25);
	getContentPane().add(importSequenceBtn);


	Button openPerspectiveBtn = new Button(inter.texts.getString("Open_Perspective"));
	openPerspectiveBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		OrthogonalPerspective a = new OrthogonalPerspective();
		a.initialize();

	    }
	});
	openPerspectiveBtn.setBounds(0, 25, 120, 25);
	getContentPane().add(openPerspectiveBtn);


	Button Voi_MediaBtn = new Button("Filters");
	Voi_MediaBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		Stack4D5D.filters.initialize();

	    }
	});
	Voi_MediaBtn.setBounds(0, 50, 120, 25);
	getContentPane().add(Voi_MediaBtn);



	Button VoidBtn = new Button(inter.texts.getString("Load_VOI"));
	VoidBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		core.launch_Import_Voids();

	    }
	});
	VoidBtn.setBounds(0,75, 120, 25);
	getContentPane().add(VoidBtn);


	Button VmrBtn = new Button(inter.texts.getString("Load_VMR"));
	VmrBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		core.launch_Import_Vmr();

	    }
	});
	VmrBtn.setBounds(0,100, 120, 25);
	getContentPane().add(VmrBtn);
    }
}
