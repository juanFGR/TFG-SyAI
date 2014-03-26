package views;

import ij.IJ;

import javax.swing.JFrame;

import files.Import_Dicom_Sequence;
import files.Import_vtc_Sequence;

import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame  {
	
	private static final long serialVersionUID = 173125243611514317L;

	public Menu() {
		getContentPane().setLayout(null);
		
		Button importSequenceBtn = new Button("Import Sequence");
		importSequenceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Import_vtc_Sequence a = new Import_vtc_Sequence();
				a.run(null);
			}
		});
		importSequenceBtn.setBounds(10, 10, 111, 22);
		getContentPane().add(importSequenceBtn);
		  
		Button openPerspectiveBtn = new Button("Open Perspective");
		openPerspectiveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    
				JFrame frame = new JFrame(); 
				 ProblemCanvas a = new ProblemCanvas();
				
			        
					frame.add(a); 
			        frame.setSize(400,100); 
			        
			        
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			        frame.setVisible(true); 
				
			}
		});
		openPerspectiveBtn.setBounds(10, 39, 111, 22);
		getContentPane().add(openPerspectiveBtn);
	}
}
