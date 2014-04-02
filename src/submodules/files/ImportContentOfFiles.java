package submodules.files;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.io.OpenDialog;
import ij.plugin.PlugIn;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import stacks.FormatVTC;
import stacks.Stack4D5D;

public class ImportContentOfFiles {

	public byte[] bFile;

	public void importSequence(String type) {
		OpenDialog od = new OpenDialog("Open Image Sequence...");
		String directory = od.getDirectory();
		String name = od.getFileName();
		if (name==null)
			return;
		String[] list = (new File(directory)).list();
		if (list==null)
			return;
		String title = directory;
		if (title.endsWith(File.separator))
			title = title.substring(0, title.length()-1);
		int index = title.lastIndexOf(File.separatorChar);
		if (index!=-1) title = title.substring(index + 1);
		if (title.endsWith(":"))
			title = title.substring(0, title.length()-1);

		IJ.register(ImportContentOfFiles.class);

		String candidato="";
		for (int i = 0; i < list.length; i++) {
			if(list[i].substring(list[i].length()-5).contains("."+type)){
				IJ.log(directory+list[i]);
				candidato=list[i];
			}
		}
		IJ.log(directory+candidato);
		String path = directory+candidato;

		FileInputStream fileInputStream=null;

		File file = new File(path);

		bFile = new byte[(int) file.length()];

		try {
			//convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
			/*Stack4D5D stack4d5d = new Stack4D5D(bFile, "vtc");
			ImageProcessor a ;
			a = new ByteProcessor(stack4d5d.formatVTC.getDimX(), stack4d5d.formatVTC.getDimY());
			ImagePlus t = new ImagePlus("", a);*/
		
		//	t.show();
		}catch(Exception e){
			e.printStackTrace();
		}

	

	}
	

}