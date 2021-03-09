package org.jlab.harut;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class YParser {
    private JSONObject dataObject = null;
    private static final String[] array1n = {"Qg", "xg", "zg", "qToQg"};
    private static final String array2n = "Prefactor";
    private static final String array3n = "StructureFunction";

    public YParser(String configFilePath) {
        try (InputStream input = new FileInputStream(configFilePath)) {
            Yaml yaml = new Yaml();
            @SuppressWarnings("unchecked")
            Map<String, Object> config = (Map<String, Object>) yaml.load(input);
            dataObject = new JSONObject(config);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open configuration file" + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassCastException | YAMLException e) {
            System.out.println("Invalid YAML configuration file" + e.getMessage());
        }
    }

    private List<Double> parse1(String name) {
        List<Double> result = new ArrayList<>();
        JSONArray data = dataObject.optJSONArray(name);
        if (data != null) {
            for (int i = 0; i < data.length(); i++) {
                try {
                    result.add(data.getDouble(i));
                } catch (JSONException e) {
                    System.out.println("invalid array of doubles");
                }
            }
        }
        return result;
    }

    private List<List<Double>> parse2(String name) {
        List<List<Double>> finalResult = new ArrayList<>();
        JSONArray obj = dataObject.optJSONArray(name);

        if (obj != null) {
            for (int i = 0; i < obj.length(); i++) {
                List<Double> result = new ArrayList<>();
                JSONArray data = obj.optJSONArray(i);
                for (int j = 0; j < data.length(); j++) {
                    try {
                        result.add(data.getDouble(j));
                    } catch (JSONException e) {
                        System.out.println("invalid array of doubles");
                    }
                }
                finalResult.add(result);
            }
        }
        return finalResult;
    }

    private List<List<List<Double>>> parse3(String name) {
        List<List<List<Double>>> finalResult = new ArrayList<>();
        JSONArray obj = dataObject.optJSONArray(name);

        if (obj != null) {
            for (int i = 0; i < obj.length(); i++) {
                List<List<Double>> result0 = new ArrayList<>();
                JSONArray data0 = obj.optJSONArray(i);
                for (int j = 0; j < data0.length(); j++) {
                    List<Double> result = new ArrayList<>();
                    JSONArray data = data0.optJSONArray(j);
                    for (int k = 0; k < data.length(); k++) {
                        try {
                            result.add(data.getDouble(k));
                        } catch (JSONException e) {
                            System.out.println("invalid array of doubles");
                        }
                    }
                    result0.add(result);
                }
                finalResult.add(result0);
            }
        }
        return finalResult;
    }

    private List<List<List<List<Double>>>> parse4() {
        List<List<List<List<Double>>>> finalResult = new ArrayList<>();
        JSONArray obj = dataObject.optJSONArray(YParser.array3n);

        if (obj != null) {
            for (int l = 0; l < obj.length(); l++) {
                List<List<List<Double>>> result1 = new ArrayList<>();
                JSONArray data1 = obj.optJSONArray(l);
                for (int i = 0; i < data1.length(); i++) {
                    List<List<Double>> result0 = new ArrayList<>();
                    JSONArray data0 = data1.optJSONArray(i);
                    for (int j = 0; j < data0.length(); j++) {
                        List<Double> result = new ArrayList<>();
                        JSONArray data = data0.optJSONArray(j);
                        for (int k = 0; k < data.length(); k++) {
                            try {
                                result.add(data.getDouble(k));
                            } catch (JSONException e) {
                                System.out.println("invalid array of doubles");
                            }
                        }
                        result0.add(result);
                    }
                    result1.add(result0);
                }
                finalResult.add(result1);
            }
        }
        return finalResult;
    }

    public static void main(String[] args) {
        YParser y = new YParser(args[0]);
        for (String s : array1n) {
            List<Double> data = y.parse1(s);
            System.out.println("\n" + s);
            System.out.println("--------------------------");
            for (Double d : data)
                System.out.println(d);
        }

        System.out.println("\n" + array2n);
        System.out.println("--------------------------");
        List<List<Double>> data2 = y.parse2(array2n);
        for (List<Double> l : data2) {
            for (Double d : l)
                System.out.print(d + " ");
            System.out.println();
        }

        System.out.println("\n" + array3n);
        System.out.println("--------------------------");
        List<List<List<List<Double>>>> data4 = y.parse4();
        for (List<List<List<Double>>> lll : data4) {
            System.out.println();
            for (List<List<Double>> ll : lll) {
                System.out.println();
               for (List<Double> l : ll) {
                    for (Double d : l)
                        System.out.print(d + " ");
                    System.out.println();
                }
            }
        }
    }
}

