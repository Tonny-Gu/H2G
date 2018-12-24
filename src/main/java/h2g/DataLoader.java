package h2g;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {
    private String pattern = "Data.json";
    private ConfigLoader loader;

    public DataLoader(String pattern) throws IOException {
        this.pattern = pattern;
        loader = new ConfigLoader(pattern);
    }

    public DataLoader() throws IOException {
        loader = new ConfigLoader(pattern);
    }

    double[][] loadRawData() throws Exception {
        ArrayList<double[]> _Data = new ArrayList<>();
        for (int x = 0; ; ++x) {
            double[] _row = loader.getDoubleArray("bar." + x + ".data");
            if (_row == null) break;
            _Data.add(_row);
        }
        double[][] DATA = new double[_Data.size()][];
        for (int i = 0; i < _Data.size(); i++) {
            DATA[i] = _Data.get(i);
        }
        return DATA;
    }
    String[] loadKeys() throws Exception {
        ArrayList<String> data = new ArrayList<>();
        for (int x = 0; ; ++x) {
            String row = loader.getStr("bar." + x + ".key");
            if (row == null) break;
            data.add(row);
        }
        return data.toArray(new String[ data.size() ]);
    }
    String[] loadSkins() throws Exception {
        ArrayList<String> data = new ArrayList<>();
        for (int x = 0; ; ++x) {
            String row = loader.getStr("bar." + x + ".skin");
            if (row == null) break;
            data.add(row);
        }
        return data.toArray(new String[ data.size() ]);
    }
}
