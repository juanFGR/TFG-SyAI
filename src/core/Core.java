package core;

import java.util.Arrays;
import files.Import_vtc_Sequence;
import stacks.FormatVTC;
import stacks.Stack4D5D;

public class Core {
	public FormatVTC formatVTC;
	public Stack4D5D stack4d5d;
	Import_vtc_Sequence import_vtc_Sequence;
	byte[] fileContent;

	public void launch_Import_vtc_Sequence(){
		import_vtc_Sequence = new Import_vtc_Sequence();
		import_vtc_Sequence.importSequence();
		fileContent = Arrays.copyOf(import_vtc_Sequence.bFile, import_vtc_Sequence.bFile.length);
		formatVTC = new FormatVTC(fileContent);
		
		stack4d5d = new Stack4D5D(formatVTC, "vtc");
	}


}
