package submodules.histograms;
import ij.ImagePlus;

/** Plugins that generate "Live" profile plots (Profiler and ZAxisProfiler)
	displayed in PlotWindows implement this interface. */
public interface PlotMaker {

   /** Returns a profile plot. */
   public Chart getPlot();
   
   /** Returns the ImagePlus that generates the profile plots. */
   public ImagePlus getSourceImage();

}

