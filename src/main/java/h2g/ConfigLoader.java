package h2g;

import java.awt.*;
import java.io.*;
import javax.json.*;

public class ConfigLoader {
    public JsonObject obj;
    boolean hasLoaded = false;

    public Object get(String path) throws Exception {
        if (!hasLoaded) throw new Exception("Json file hasn't been loaded!");
        String[] tokens = path.split("\\.");
        Object o = (Object) obj;
        for (String token : tokens) {
            if (o instanceof JsonArray) {
                int index = Integer.valueOf(token);
                o = (Object) (((JsonArray) o).get(index));
            } else if (o instanceof JsonObject) {
                o = (Object) (((JsonObject) o).get((Object) token));
            } else {
                return null;
                //throw new Exception("Path is not valid!");
            }
        }
        return o;
    }

    public String getStr(String path) throws Exception {
        Object o = get(path);
        if (o instanceof JsonString) return ((JsonString) o).getString();
        else {
            return null;
        }
    }

    public String setStr(String init, String path) throws Exception {
        String _O = getStr(path);
        if (_O == null) {
            System.out.println(path + " is not in json. Has been set to Default value " + init);
            return init;
        } else {
            return _O;
        }
    }

    public int getInt(String path) throws Exception {
        Object o = get(path);
        if (o instanceof JsonNumber) return ((JsonNumber) o).intValue();
        else {
            return -404;
        }
    }

    public int setInt(int init, String path) throws Exception {
        int _O = getInt(path);
        if (_O == -404) {
            System.out.println(path + " is not in json. Has been set to Default value " + init);
            return init;
        } else {
            return _O;
        }
    }

    public double getDouble(String path) throws Exception {
        Object o = get(path);
        if (o instanceof JsonNumber) return ((JsonNumber) o).doubleValue();
        else {
            return -404;
        }
    }

    public double setDouble(double init, String path) throws Exception {
        double _O = getDouble(path);
        if (_O == -404) {
            System.out.println(path + " is not in json. Has been set to Default value " + init);
            return init;
        } else {
            return _O;
        }
    }

    public boolean getBool(String path) throws Exception {
        Object o = get(path);
        if (o instanceof JsonValue) {
            switch (((JsonValue) o).getValueType()) {
                case TRUE:
                    return true;
                case FALSE:
                    return false;
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public boolean setBool(boolean init, String path) throws Exception {
        boolean _O = getBool(path);
        return (init || _O);
    }

    public ConfigLoader(String fileName) throws IOException {
        InputStream is = new FileInputStream(new File(fileName));
        JsonReader rdr = Json.createReader(is);
        obj = rdr.readObject();
        hasLoaded = true;
            /*
            JsonArray results = obj.getJsonArray("data");
            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                System.out.print(result.getJsonObject("from").getString("name"));
                System.out.print(": ");
                System.out.println(result.getString("message", ""));
                System.out.println("-----------");
            }*/
    }

    Color getColor(String pattern) throws Exception {
        if (get(pattern) == null) return null;
        return new Color(getInt(pattern + ".0"), getInt(pattern + ".1"), getInt(pattern + ".2"));
    }
    
    Color setColor(Color init,String pattern) throws Exception {
        Color _O = getColor(pattern);
        if (_O == null) {
            return init;
        } else {
            return _O;
        }
    }

    @Deprecated
    Color getTColor(String pattern) throws Exception {
        if (get(pattern) == null) return null;
        return new Color(getInt(pattern + ".0"), getInt(pattern + ".1"), getInt(pattern + ".2"), getInt(pattern + ".3"));
    }

    Font getFont(String pattern) throws Exception {
        String rulerFontName = getStr(pattern + ".name");
        if (null == rulerFontName) return null;
        int rulerFontForm = getInt(pattern + ".form");
        if (rulerFontForm == 0) return null;
        int rulerFontSize = getInt(pattern + ".size");
        if (rulerFontSize == 0) return null;
        return new Font(rulerFontName, rulerFontForm, rulerFontSize);
    }

    Font setFont(Font init, String pattern) throws Exception {
        Font _O = getFont(pattern);
        if (_O == null) {
            return init;
        } else {
            return _O;
        }
    }

    double[] getDoubleArray(String pattern) throws Exception {
        Object _jsa = get(pattern);
        if (_jsa == null) {
            return null;
        }
        JsonArray jsa = (JsonArray) _jsa;
        double[] a = new double[jsa.size()];
        for (int i = 0; i < jsa.size(); i++)
            a[i] = jsa.getJsonNumber(i).doubleValue();
        return a;
    }

    double[] setDoubleArray(double[] init,String pattern) throws Exception {
        double[] _O = getDoubleArray(pattern);
        if (_O == null) {
            return init;
        } else {
            return _O;
        }
    }

    String[] getStringArray(String pattern) throws Exception {
        Object _jsa = get(pattern);
        if (_jsa == null) {
            return null;
        }
        JsonArray jsa = (JsonArray) _jsa;
        String[] a = new String[jsa.size()];
        for (int i = 0; i < jsa.size(); i++) a[i] = jsa.getJsonString(i).getString();
        return a;
    }

    String[] setStringArray(String[] init,String pattern) throws Exception {
        String[] _O = getStringArray(pattern);
        if (_O == null) {
            return init;
        } else {
            return _O;
        }
    }

    int[] getIntegerArray(String pattern) throws Exception {
        Object _jsa = get(pattern);
        if (_jsa == null) {
            return null;
        }
        JsonArray jsa = (JsonArray) _jsa;
        int[] a = new int[jsa.size()];
        for (int i = 0; i < jsa.size(); i++) {
            a[i] = jsa.getJsonNumber(i).intValue();
        }
        return a;
    }

    int[] setIntegerArray(int[] init,String pattern) throws Exception {
        int[] _O = getIntegerArray(pattern);
        if (_O == null) {
            return init;
        } else {
            return _O;
        }
    }
    
}
