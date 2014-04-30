package stacks;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import core.tools.Pair;

public class FormatVOI {

    int fileVersion;
    String referenceSpace;
    int originalVMRResolutionX;
    int originalVMRResolutionZ;
    int originalVMRResolutionY;
    int originalVMROffsetX;
    int originalVMROffsetY;
    int originalVMROffsetZ;
    int originalVMRFramingCubeDim;
    int leftRightConvention;
    String subjectVOINamingConvention;
    int nrOfVOIs;
    String nameOfVOI;
    Color colorOfVOI;
    int nrOfVoxels;
    int nrOfVOIVTCs;
   
    public FormatVOI(byte[] fileContent) {
	String wrapped = new String(fileContent);
	String []wrapped1 = wrapped.split("\r\n");
	for (int i = 0; i < wrapped1.length; i++) {
	    if( pairNameAndValue(wrapped1[i]) != null){
		String key = pairNameAndValue(wrapped1[i]).t;
		String a = pairNameAndValue(wrapped1[i]).u;
		switch (key) {
		case "FileVersion": setFileVersion(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "ReferenceSpace": setReferenceSpace(a.replaceAll("\\s+", ""));
		break;
		case "OriginalVMRResolutionX": setOriginalVMRResolutionX(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "OriginalVMRResolutionY": setOriginalVMRResolutionY(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "OriginalVMRResolutionZ": setOriginalVMRResolutionZ(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "OriginalVMROffsetX": setOriginalVMROffsetX(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "OriginalVMROffsetY": setOriginalVMROffsetY(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "OriginalVMROffsetZ": setOriginalVMROffsetZ(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "OriginalVMRFramingCubeDim": setOriginalVMRFramingCubeDim(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "LeftRightConvention": setLeftRightConvention(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;
		case "NrOfVOIs": setNrOfVOIs(Integer.parseInt(a.replaceAll("\\s+", "")));
		break;

		default:
		    break;
		}


	    }else if (PointOfVoxes(wrapped1[i])!=null){
		Stack4D5D.ListOfVoid.add(new Point3d(PointOfVoxes(wrapped1[i])));
	    }
	}


	printvalues();

    }


    private double[] PointOfVoxes(String string) {
	String [] tempVal = string.split(" ");
	if(tempVal.length ==3){
	    double [] values = new double[]{Double.parseDouble(tempVal[0])-Stack4D5D.formatVTC.getxStart(),Double.parseDouble(tempVal[1])-Stack4D5D.formatVTC.getyStart(),Double.parseDouble(tempVal[2])-Stack4D5D.formatVTC.getzStart()};
	    return values;
	}

	return null;
    }


    private void printvalues() {
	System.out.println("FileVersion"+getFileVersion());
	System.out.println("ReferenceSpace"+getReferenceSpace());
	System.out.println("OriginalVMRResolutionX"+getOriginalVMRResolutionX());
	System.out.println("OriginalVMRResolutionY"+getOriginalVMRResolutionY());
	System.out.println("OriginalVMRResolutionZ"+getOriginalVMRResolutionZ());
	System.out.println("OriginalVMROffsetX"+getOriginalVMROffsetX());
	System.out.println("OriginalVMROffsetY"+getOriginalVMROffsetY());
	System.out.println("OriginalVMROffsetZ"+getOriginalVMROffsetZ());
	System.out.println("OriginalVMRFramingCubeDim"+getOriginalVMRFramingCubeDim());
	System.out.println("NrOfVOIs"+getNrOfVOIs());

	for (int i = 0; i < Stack4D5D.ListOfVoid.size(); i++) {
	    System.out.println(Stack4D5D.ListOfVoid.get(i).toString());
	}
    }


    Pair<String,String> pairNameAndValue(String val){

	String [] tempVal = val.split(":");

	return (tempVal.length ==2) ? new Pair<String, String>(tempVal[0], tempVal[1]) : null;
    }

    public int getFileVersion() {
	return fileVersion;
    }
    public void setFileVersion(int fileVersion) {
	this.fileVersion = fileVersion;
    }
    public String getReferenceSpace() {
	return referenceSpace;
    }
    public void setReferenceSpace(String referenceSpace) {
	this.referenceSpace = referenceSpace;
    }
    public int getOriginalVMRResolutionX() {
	return originalVMRResolutionX;
    }
    public void setOriginalVMRResolutionX(int originalVMRResolutionX) {
	this.originalVMRResolutionX = originalVMRResolutionX;
    }
    public int getOriginalVMRResolutionZ() {
	return originalVMRResolutionZ;
    }
    public void setOriginalVMRResolutionZ(int originalVMRResolutionZ) {
	this.originalVMRResolutionZ = originalVMRResolutionZ;
    }
    public int getOriginalVMRResolutionY() {
	return originalVMRResolutionY;
    }
    public void setOriginalVMRResolutionY(int originalVMRResolutionY) {
	this.originalVMRResolutionY = originalVMRResolutionY;
    }
    public int getOriginalVMROffsetX() {
	return originalVMROffsetX;
    }
    public void setOriginalVMROffsetX(int originalVMROffsetX) {
	this.originalVMROffsetX = originalVMROffsetX;
    }
    public int getOriginalVMROffsetY() {
	return originalVMROffsetY;
    }
    public void setOriginalVMROffsetY(int originalVMROffsetY) {
	this.originalVMROffsetY = originalVMROffsetY;
    }
    public int getOriginalVMROffsetZ() {
	return originalVMROffsetZ;
    }
    public void setOriginalVMROffsetZ(int originalVMROffsetZ) {
	this.originalVMROffsetZ = originalVMROffsetZ;
    }
    public int getOriginalVMRFramingCubeDim() {
	return originalVMRFramingCubeDim;
    }
    public void setOriginalVMRFramingCubeDim(int originalVMRFramingCubeDim) {
	this.originalVMRFramingCubeDim = originalVMRFramingCubeDim;
    }
    public int getLeftRightConvention() {
	return leftRightConvention;
    }
    public void setLeftRightConvention(int leftRightConvention) {
	this.leftRightConvention = leftRightConvention;
    }
    public String getSubjectVOINamingConvention() {
	return subjectVOINamingConvention;
    }
    public void setSubjectVOINamingConvention(String subjectVOINamingConvention) {
	this.subjectVOINamingConvention = subjectVOINamingConvention;
    }
    public int getNrOfVOIs() {
	return nrOfVOIs;
    }
    public void setNrOfVOIs(int nrOfVOIs) {
	this.nrOfVOIs = nrOfVOIs;
    }
    public String getNameOfVOI() {
	return nameOfVOI;
    }
    public void setNameOfVOI(String nameOfVOI) {
	this.nameOfVOI = nameOfVOI;
    }
    public Color getColorOfVOI() {
	return colorOfVOI;
    }
    public void setColorOfVOI(Color colorOfVOI) {
	this.colorOfVOI = colorOfVOI;
    }
    public int getNrOfVoxels() {
	return nrOfVoxels;
    }
    public void setNrOfVoxels(int nrOfVoxels) {
	this.nrOfVoxels = nrOfVoxels;
    }
    public int getNrOfVOIVTCs() {
	return nrOfVOIVTCs;
    }
    public void setNrOfVOIVTCs(int nrOfVOIVTCs) {
	this.nrOfVOIVTCs = nrOfVOIVTCs;
    }

}

