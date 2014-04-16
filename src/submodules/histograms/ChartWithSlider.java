package submodules.histograms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @see http://stackoverflow.com/a/15207445/230513
 */
public class ChartWithSlider {

    private  final int N = 25;
    private  final double K = 273.15;
    private  final Random random = new Random();

    private  XYDataset getDataset(int n) {

        final XYSeries series = new XYSeries("Temp (K°)");
        double temperature;
        for (int length = 0; length < N; length++) {
            temperature = K + n * random.nextGaussian();
            series.add(length + 1, temperature);
        }
        return new XYSeriesCollection(series);
    }

    private  JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
            "ChartSliderTest", "Length (m)", "Temp (K°)", dataset,
            PlotOrientation.VERTICAL, false, false, false);
        return chart;
    }

    public  void initialize() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final List<XYDataset> list = new ArrayList<XYDataset>();
        for (int i = 0; i <= 10; i++) {
            list.add(getDataset(i));
        }
        JFreeChart chart = createChart(list.get(5));
        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRangeAxis().setRangeAboutValue(K, K / 5);
        ChartPanel chartPanel = new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 400);
            }
        };
        f.add(chartPanel);
        final JSlider slider = new JSlider(0, 10);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                plot.setDataset(list.get(slider.getValue()));
            }
        });
        Box p = new Box(BoxLayout.X_AXIS);
        p.add(new JLabel("Time:"));
        p.add(slider);
        f.add(p, BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

  
}
