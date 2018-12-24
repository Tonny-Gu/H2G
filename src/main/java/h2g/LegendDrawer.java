package h2g;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LegendDrawer {
    private final static int X = 0;
    private final static int Y = 1;
    private String[] keys;
    private String[] skin;
    private int elementNum;
    private int[] iconSize;
    private double[] iconScale = new double[]{0, 50};
    private int maxLegendBarWidth = 0;
    private Font font;
    private int Height = 60;
    private CanvaStyle canvaStyle;
    private boolean rotated;
    private boolean isStackedBar = false;

    public LegendDrawer(CanvaStyle canvaStyle, HistogramData histogramData) throws Exception {
        this.canvaStyle = canvaStyle;
        isStackedBar = canvaStyle.isStackedBar;
        rotated = canvaStyle.rotated;
        if (!isStackedBar) {
            keys = histogramData.keys;
        } else {
            ConfigLoader loader = new ConfigLoader("Data.json");
            keys = loader.getStringArray("bar.StackedBarTitle");
        }
        skin = canvaStyle.barSkin;
        elementNum = keys.length;
        iconSize = canvaStyle.iconSize;
        iconScale[1] = iconSize[1];
        font = canvaStyle.legendFont;
        Height = canvaStyle.legendHeight;
        for (int i = 0; i < elementNum; i++) {
            maxLegendBarWidth = Math.max(keys[i].length() * font.getSize(), maxLegendBarWidth);
        }
    }

    public BufferedImage getBarLegend(int id) {
        BarGenerator barGenerator = DynamicLoader.get(skin[id], iconSize, iconScale, rotated);
        assert barGenerator != null;
        BufferedImage squareBlock;
        if (!isStackedBar) {
            squareBlock = barGenerator.getBarChart(0, "", Height);
        } else {
            double[] para = new double[id+1];
            for (int i = 0; i < id+1; i++) {
                para[i] = 0;
            }
            para[id] = Height;
            squareBlock = barGenerator.getBarChart(0, "", para);
        }
        int textWidth = maxLegendBarWidth;
        int textHeight = font.getSize();
        SigDraw Legend = new SigDraw(iconSize[X] + textWidth, (int) (textHeight * 1.25), true);
        Legend.setPenColor(Color.BLACK);
        Legend.setFont(font);
        Legend.text(iconSize[X] + textWidth / 2.0, textHeight / 2.0, keys[id]);
        Legend.picture(iconSize[X] / 2.0, iconSize[Y] / 2.0, squareBlock);
        return Legend.getBuffImg();
    }

    public BufferedImage getLegend() {
        int Width = iconSize[X]+ maxLegendBarWidth;
        int rowNum = canvaStyle.legendRowNum;
        int columnNum = canvaStyle.legendColumnNum;

        SigDraw Legend = new SigDraw(Width * columnNum, Height * rowNum, true);
        for (int row = 0, i=0 ; row < rowNum; row++) {
            for(int column = 0; column < columnNum && i<elementNum ; column++, i++) {
                BufferedImage element = getBarLegend(i);
                Legend.picture(Width*(column+1) - Width/2.0, Height*(row+1) - Height/2.0, element);
            }
        }
        BufferedImage _O = Legend.getScaledImage(canvaStyle.legendScaleFactor, Scalr.Method.ULTRA_QUALITY);
        new SigDraw(_O, true).save("LegendText.jpg");
        return _O;
    }

    public static void main(String[] args) throws Exception {

    }
}
