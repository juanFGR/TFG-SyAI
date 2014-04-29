package core;

import java.util.Arrays;

import stacks.FormatVOI;
import stacks.FormatVTC;
import stacks.Stack4D5D;
import submodules.files.ImportContentOfFiles;

public class Core {
	public FormatVTC formatVTC;
	public FormatVOI formatVOI;
	ImportContentOfFiles importFile;
	byte[] fileContent;
	
	public void launch_Import_vtc_Sequence(){
	 
	    importFile = new ImportContentOfFiles();
	    importFile.importSequence("vtc");
		fileContent = Arrays.copyOf(importFile.bFile, importFile.bFile.length);
		formatVTC = new FormatVTC(fileContent);
		
		 Stack4D5D.Initialize(formatVTC, "vtc");
	}

	public void launch_Import_Voids(){
	    importFile = new ImportContentOfFiles();
	    importFile.importSequence("voi");
		fileContent = Arrays.copyOf(importFile.bFile, importFile.bFile.length);
		formatVOI= new FormatVOI(fileContent); 
	}

}
