package stacks;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FormatVTC {

    short numberOfversion;
    String nameFile;
    short numberOfProtocol;
    short currentProtocol;
    short dataTypeValues;
    short numbeOfVolumes;
    short resolution;
    short xStart;
    short xEnd;
    short yStart;
    short yEnd;
    short zStart;
    short zEnd;
    byte convention;
    byte spaceflag;
    float tr;
    short dimX;
    short dimY;
    short dimZ;
    short dimT;


    int indexEndOfHeader;
    float[] images;

    public FormatVTC(byte[] data) {

	try {
	    //convert file into array of bytes


	    ByteBuffer wrapped = ByteBuffer.wrap(data); // big-endian by default
	    wrapped.order(ByteOrder.LITTLE_ENDIAN);

	    setNumberOfversion(wrapped.getShort());
	    int pos0 =wrapped.position();
	    int pos1 =wrapped.position();
	    while(wrapped.get() != 00){
		pos1++;	
	    }
	    wrapped.position(pos0);

	    byte []dst = new byte[pos1];
	    wrapped.get(dst,0,dst.length);

	    String file_string = new String(dst);
	    setNameFile(file_string+"|");
	    pos1++;
	    wrapped.position(pos1);

	    setNumberOfProtocol(wrapped.getShort());	
	    pos0 =wrapped.position();
	    pos1 =pos0;

	    if(wrapped.get() != 00){
		pos1++;
		while(wrapped.get() != 00){
		    pos1++;	
		}
		pos1++;
	    }
	    wrapped.position(pos1);
	    setCurrentProtocol(wrapped.getShort());
	    setDataTypeValues(wrapped.getShort());
	    setNumbeOfVolumes(wrapped.getShort());
	    setResolution(wrapped.getShort());
	    setxStart(wrapped.getShort());
	    setxEnd(wrapped.getShort());
	    setyStart(wrapped.getShort());
	    setyEnd(wrapped.getShort());
	    setzStart(wrapped.getShort());
	    setzEnd(wrapped.getShort());
	    setConvention(wrapped.get());
	    setSpaceflag(wrapped.get());
	    setTr(wrapped.getFloat());
	    setDimX();
	    setDimY();
	    setDimZ();


	    setIndexEndOfHeader(wrapped.position());
	    printInfo();


	    images = new float[getDimX()*getDimY()*getDimZ()*getNumbeOfVolumes()*getDataTypeValues()]; 
	    //save data since end of header to limit
	    int i=0;
	    while(wrapped.position()<=wrapped.limit()-getDataTypeValues()){
		images[i] =  wrapped.getFloat();
		i++;
	    }


	    wrapped.clear(); 
	    System.out.println("--->>>"+images.length);
	}catch(Exception e){
	    e.printStackTrace();
	}
    }



    public float[] getImages() {
	return images;
    }
    public short getDimX() {
	return dimX;
    }
    public void setDimX() {
	this.dimX = (short) ((getxEnd() - getxStart()) / getResolution()) ;
    }
    public short getDimY() {
	return dimY;
    }
    public void setDimY() {
	this.dimY = (short) ((getyEnd() - getyStart()) / getResolution()) ;
    }
    public short getDimZ() {
	return dimZ;
    }
    public void setDimZ() {
	this.dimZ = (short) ((getzEnd() - getzStart()) / getResolution()) ;
    }
    public short getDimT() {
	return dimT;
    }
    public void setDimT(short dimT) {
	this.dimT = dimT;
    }
    public void printInfo(){
	System.out.println("Number of version: "+getNumberOfversion());
	System.out.println("Name of file: "+nameFile);
	System.out.println("number Of Protocol: "+numberOfProtocol);
	System.out.println("currentProtocol: "+currentProtocol);
	System.out.println("data Type Values: "+dataTypeValues);
	System.out.println("numbe Of Volumes: "+numbeOfVolumes);
	System.out.println("resolution: "+resolution);
	System.out.println("xStart: "+ xStart);
	System.out.println("xEnd: "+xEnd);
	System.out.println("yStart: "+yStart);
	System.out.println("yEnd: "+yEnd);
	System.out.println("zStart: "+zStart);
	System.out.println("zEnd: "+zEnd);
	System.out.println("convention: "+convention);
	System.out.println("spaceflag: "+spaceflag);
	System.out.println(tr);
	System.out.println("Dimension x: "+dimX);
	System.out.println("Dimension y: "+dimY);
	System.out.println("Dimension z: "+dimZ);
	System.out.println("Index End of Header: "+indexEndOfHeader);
    }

    public short getNumberOfversion() {
	return numberOfversion;
    }
    public void setNumberOfversion(short numberOfversion) {
	this.numberOfversion = numberOfversion;
    }
    public String getNameFile() {
	return nameFile;
    }
    public void setNameFile(String nameFile) {
	this.nameFile = nameFile;
    }
    public short getNumberOfProtocol() {
	return numberOfProtocol;
    }
    public void setNumberOfProtocol(short numberOfProtocol) {
	this.numberOfProtocol = numberOfProtocol;
    }
    public short getCurrentProtocol() {
	return currentProtocol;
    }
    public void setCurrentProtocol(short currentProtocol) {
	this.currentProtocol = currentProtocol;
    }
    public short getDataTypeValues() {
	return dataTypeValues;
    }
    public void setDataTypeValues(short dataTypeValues) {
	switch (dataTypeValues) {
	case 1:	
	    this.dataTypeValues = 2;
	    break;
	case 2:		
	    this.dataTypeValues = 4;
	    break;

	default:
	    break;
	}
    }
    public short getNumbeOfVolumes() {
	return numbeOfVolumes;
    }
    public void setNumbeOfVolumes(short numbeOfVolumes) {
	this.numbeOfVolumes = numbeOfVolumes;
    }
    public short getResolution() {
	return resolution;
    }
    public void setResolution(short resolution) {
	this.resolution = resolution;
    }
    public short getxStart() {
	return xStart;
    }
    public void setxStart(short xStart) {
	this.xStart = xStart;
    }
    public short getxEnd() {
	return xEnd;
    }
    public void setxEnd(short xEnd) {
	this.xEnd = xEnd;
    }
    public short getyStart() {
	return yStart;
    }
    public void setyStart(short yStart) {
	this.yStart = yStart;
    }
    public short getyEnd() {
	return yEnd;
    }
    public void setyEnd(short yEnd) {
	this.yEnd = yEnd;
    }
    public short getzStart() {
	return zStart;
    }
    public void setzStart(short zStart) {
	this.zStart = zStart;
    }
    public short getzEnd() {
	return zEnd;
    }
    public void setzEnd(short zEnd) {
	this.zEnd = zEnd;
    }
    public byte getConvention() {
	return convention;
    }
    public void setConvention(byte convention) {
	this.convention = convention;
    }
    public byte getSpaceflag() {
	return spaceflag;
    }
    public void setSpaceflag(byte spaceflag) {
	this.spaceflag = spaceflag;
    }
    public float getTr() {
	return tr;
    }
    public void setTr(float tr) {
	this.tr = tr;
    }

    public int getIndexEndOfHeader() {
	return indexEndOfHeader;
    }
    public void setIndexEndOfHeader(int indexEndOfHeader) {
	this.indexEndOfHeader = indexEndOfHeader;
    }

}
