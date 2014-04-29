package submodules.files;

import ij.IJ;
import ij.io.OpenDialog;

import java.io.File;
import java.io.FileInputStream;

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
	int i =0;
	boolean flag = false;
	do{
	    if(list[i].substring(list[i].length()-5).contains("."+type)){
	    IJ.log(directory+list[i]);
	    candidato=list[i];flag=true;
	   
	    }
	    i++;
	}while(!flag);

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

	    //	t.show();
	}catch(Exception e){
	    e.printStackTrace();
	}



    }


}