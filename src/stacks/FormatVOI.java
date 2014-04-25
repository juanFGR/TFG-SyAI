package stacks;

import java.awt.Color;
import java.nio.ByteBuffer;

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
	ByteBuffer wrapped = ByteBuffer.wrap(fileContent);
	System.out.println(wrapped.get());
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

