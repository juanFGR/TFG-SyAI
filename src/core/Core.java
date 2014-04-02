package core;

import java.util.Arrays;

import stacks.FormatVOI;
import stacks.FormatVTC;
import stacks.Stack4D5D;
import submodules.files.ImportContentOfFiles;

public class Core {
	public FormatVTC formatVTC;
	public FormatVOI formatVOI;
	public Stack4D5D stack4d5d;
	ImportContentOfFiles import_vtc_Sequence;
	byte[] fileContent;

	public void launch_Import_vtc_Sequence(){
		import_vtc_Sequence = new ImportContentOfFiles();
		import_vtc_Sequence.importSequence("vtc");
		fileContent = Arrays.copyOf(import_vtc_Sequence.bFile, import_vtc_Sequence.bFile.length);
		formatVTC = new FormatVTC(fileContent);
	/*	import_vtc_Sequence.importSequence("voi");
		fileContent = Arrays.copyOf(import_vtc_Sequence.bFile, import_vtc_Sequence.bFile.length);
		formatVOI= new FormatVOI(fileContent); */
		stack4d5d = new Stack4D5D(formatVTC, "vtc");
	}


}
