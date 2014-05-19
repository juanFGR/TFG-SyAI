package stacks;



import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FormatVMR {

    short FileVersion;
    short Dimx,Dimy,Dimz;
    short xOffset,yOffset,zOffset;
    short framingCubeDimensions;
    int posInfosVerified;
    int typeEntry;
    
    float xFirstSlice,yFirstSlice,zFirstSlice;
    float xLastSlice;
    float yLastSlice;
    float zLastSlice;
    
    float xRowDirection;
    float yRowDirection;
    float zRowDirection;
    float xColDirection;
    float yColDirection;
    float zColDirection;  
    int NumRows;
    int NumCol;
    
    float FoV_RowDirection;
    float FoV_ColDirection;
    
    float sliceThickness;
    float gapThickness;
    
    int NrOfPastSpatialTransf; //NrOfPastSpatialTransformations
    byte LR_Convention;
    byte Ref_SpaceFlag;
    float VoxelResol_X;
    float VoxelResol_Y;
    float VoxelResol_Z;
    byte flagVoxelResol_Verified;
    byte flagTalairach_space;
    
    int minIntensity;
    int MeanIntisity;
    int maxIntensity;
    
    
    
   
    float[] images;

    private void printInfo() {
	System.out.println(getFileVersion());
	System.out.println(getDimx());
	System.out.println(getDimy());
	System.out.println(getDimz());
	System.out.println(getxOffset());
	System.out.println(getyOffset());
	System.out.println(getzOffset());
	System.out.println(getFramingCubeDimensions());
	System.out.println(getPosInfosVerified());
	System.out.println(getTypeEntry());
	System.out.println(getxFirstSlice());
	System.out.println(getyFirstSlice());
	System.out.println(getzFirstSlice());
	System.out.println(getxLastSlice());
	System.out.println(getyLastSlice());
	System.out.println(getzLastSlice());
	System.out.println(getxColDirection());
	System.out.println(getyColDirection());
	System.out.println(getzColDirection());

	
    }

    
    public FormatVMR(byte[] data) {

	try {
	    //convert file into array of bytes


	    ByteBuffer wrapped = ByteBuffer.wrap(data); // big-endian by default
	    wrapped.order(ByteOrder.LITTLE_ENDIAN);
	    setFileVersion(wrapped.getShort());
	    setDimx(wrapped.getShort());
	    setDimy(wrapped.getShort());
	    setDimz(wrapped.getShort());
	    
	    images = new float[getDimx()*getDimy()*getDimz()]; 
	    //save data since end of header to limit
	    int i=0;
	    while(i<images.length){
		images[i] =  wrapped.get();
		i++;
	    }
	    
	    
	    setxOffset(wrapped.getShort());
	    setyOffset(wrapped.getShort());
	    setzOffset(wrapped.getShort());	
	    setFramingCubeDimensions(wrapped.getShort());
	    setPosInfosVerified(wrapped.getInt());
	    setTypeEntry(wrapped.getInt());
	    setxFirstSlice(wrapped.getFloat());
	    setyFirstSlice(wrapped.getFloat());
	    setzFirstSlice(wrapped.getFloat());
	    setxLastSlice(wrapped.getFloat());
	    setyLastSlice(wrapped.getFloat());
	    setyLastSlice(wrapped.getFloat());
	    
	    setxRowDirection(wrapped.getFloat());
	    setyRowDirection(wrapped.getFloat());
	    setzRowDirection(wrapped.getFloat());
	    setxColDirection(wrapped.getFloat());
	    setyColDirection(wrapped.getFloat());
	    setzColDirection(wrapped.getFloat());
	    printInfo();


	    wrapped.clear(); //clear buffer
	}catch(Exception e){
	    e.printStackTrace();
	}
    }






    public float[] getImages() {
	return images;
    }



    public short getFileVersion() {
        return FileVersion;
    }


    public void setFileVersion(short fileVersion) {
        FileVersion = fileVersion;
    }


    public short getDimx() {
        return Dimx;
    }


    public void setDimx(short dimx) {
        Dimx = dimx;
    }


    public short getDimy() {
        return Dimy;
    }


    public void setDimy(short dimy) {
        Dimy = dimy;
    }


    public short getDimz() {
        return Dimz;
    }


    public void setDimz(short dimz) {
        Dimz = dimz;
    }


    public short getxOffset() {
        return xOffset;
    }



    public void setxOffset(short xOffset) {
        this.xOffset = xOffset;
    }



    public short getyOffset() {
        return yOffset;
    }



    public void setyOffset(short yOffset) {
        this.yOffset = yOffset;
    }



    public short getzOffset() {
        return zOffset;
    }



    public void setzOffset(short zOffset) {
        this.zOffset = zOffset;
    }



    public short getFramingCubeDimensions() {
        return framingCubeDimensions;
    }



    public void setFramingCubeDimensions(short framingCubeDimensions) {
        this.framingCubeDimensions = framingCubeDimensions;
    }



    public int getPosInfosVerified() {
        return posInfosVerified;
    }



    public void setPosInfosVerified(int posInfosVerified) {
        this.posInfosVerified = posInfosVerified;
    }



    public int getTypeEntry() {
        return typeEntry;
    }



    public void setTypeEntry(int typeEntry) {
        this.typeEntry = typeEntry;
    }



    public float getxFirstSlice() {
        return xFirstSlice;
    }



    public void setxFirstSlice(float xFirstSlice) {
        this.xFirstSlice = xFirstSlice;
    }



    public float getyFirstSlice() {
        return yFirstSlice;
    }



    public void setyFirstSlice(float yFirstSlice) {
        this.yFirstSlice = yFirstSlice;
    }



    public float getzFirstSlice() {
        return zFirstSlice;
    }



    public void setzFirstSlice(float zFirstSlice) {
        this.zFirstSlice = zFirstSlice;
    }



    public float getxLastSlice() {
        return xLastSlice;
    }



    public void setxLastSlice(float xLastSlice) {
        this.xLastSlice = xLastSlice;
    }



    public float getyLastSlice() {
        return yLastSlice;
    }



    public void setyLastSlice(float yLastSlice) {
        this.yLastSlice = yLastSlice;
    }



    public float getzLastSlice() {
        return zLastSlice;
    }



    public void setzLastSlice(float zLastSlice) {
        this.zLastSlice = zLastSlice;
    }



    public float getxRowDirection() {
        return xRowDirection;
    }



    public void setxRowDirection(float xRowDirection) {
        this.xRowDirection = xRowDirection;
    }



    public float getyRowDirection() {
        return yRowDirection;
    }



    public void setyRowDirection(float yRowDirection) {
        this.yRowDirection = yRowDirection;
    }



    public float getzRowDirection() {
        return zRowDirection;
    }



    public void setzRowDirection(float zRowDirection) {
        this.zRowDirection = zRowDirection;
    }



    public float getxColDirection() {
        return xColDirection;
    }



    public void setxColDirection(float xColDirection) {
        this.xColDirection = xColDirection;
    }



    public float getyColDirection() {
        return yColDirection;
    }



    public void setyColDirection(float yColDirection) {
        this.yColDirection = yColDirection;
    }



    public float getzColDirection() {
        return zColDirection;
    }



    public void setzColDirection(float zColDirection) {
        this.zColDirection = zColDirection;
    }



    public int getNumRows() {
        return NumRows;
    }



    public void setNumRows(int numRows) {
        NumRows = numRows;
    }



    public int getNumCol() {
        return NumCol;
    }



    public void setNumCol(int numCol) {
        NumCol = numCol;
    }



    public float getFoV_RowDirection() {
        return FoV_RowDirection;
    }



    public void setFoV_RowDirection(float foV_RowDirection) {
        FoV_RowDirection = foV_RowDirection;
    }



    public float getFoV_ColDirection() {
        return FoV_ColDirection;
    }



    public void setFoV_ColDirection(float foV_ColDirection) {
        FoV_ColDirection = foV_ColDirection;
    }



    public float getSliceThickness() {
        return sliceThickness;
    }



    public void setSliceThickness(float sliceThickness) {
        this.sliceThickness = sliceThickness;
    }



    public float getGapThickness() {
        return gapThickness;
    }



    public void setGapThickness(float gapThickness) {
        this.gapThickness = gapThickness;
    }



    public int getNrOfPastSpatialTransf() {
        return NrOfPastSpatialTransf;
    }



    public void setNrOfPastSpatialTransf(int nrOfPastSpatialTransf) {
        NrOfPastSpatialTransf = nrOfPastSpatialTransf;
    }



    public byte getLR_Convention() {
        return LR_Convention;
    }



    public void setLR_Convention(byte lR_Convention) {
        LR_Convention = lR_Convention;
    }



    public byte getRef_SpaceFlag() {
        return Ref_SpaceFlag;
    }



    public void setRef_SpaceFlag(byte ref_SpaceFlag) {
        Ref_SpaceFlag = ref_SpaceFlag;
    }



    public float getVoxelResol_X() {
        return VoxelResol_X;
    }



    public void setVoxelResol_X(float voxelResol_X) {
        VoxelResol_X = voxelResol_X;
    }



    public float getVoxelResol_Y() {
        return VoxelResol_Y;
    }



    public void setVoxelResol_Y(float voxelResol_Y) {
        VoxelResol_Y = voxelResol_Y;
    }



    public float getVoxelResol_Z() {
        return VoxelResol_Z;
    }



    public void setVoxelResol_Z(float voxelResol_Z) {
        VoxelResol_Z = voxelResol_Z;
    }



    public byte getFlagVoxelResol_Verified() {
        return flagVoxelResol_Verified;
    }



    public void setFlagVoxelResol_Verified(byte flagVoxelResol_Verified) {
        this.flagVoxelResol_Verified = flagVoxelResol_Verified;
    }



    public byte getFlagTalairach_space() {
        return flagTalairach_space;
    }



    public void setFlagTalairach_space(byte flagTalairach_space) {
        this.flagTalairach_space = flagTalairach_space;
    }



    public int getMinIntensity() {
        return minIntensity;
    }



    public void setMinIntensity(int minIntensity) {
        this.minIntensity = minIntensity;
    }



    public int getMeanIntisity() {
        return MeanIntisity;
    }



    public void setMeanIntisity(int meanIntisity) {
        MeanIntisity = meanIntisity;
    }



    public int getMaxIntensity() {
        return maxIntensity;
    }



    public void setMaxIntensity(int maxIntensity) {
        this.maxIntensity = maxIntensity;
    }



    public void setImages(float[] images) {
        this.images = images;
    }
    
    
    
    
}
