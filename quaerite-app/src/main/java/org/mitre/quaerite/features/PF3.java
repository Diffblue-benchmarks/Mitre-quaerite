package org.mitre.quaerite.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PF3 implements FeatureSet {
    private static final String PF3 = "pf3";
    public static PF3 EMPTY = new PF3(Collections.emptyList(), Collections.emptyList());

    List<String> features;
    List<String> fields = new ArrayList<>();
    List<Float> weights = new ArrayList<>();

    public PF3(List<String> fields, List<Float> weights) {
        this.fields = fields;
        this.weights = weights;
        init();
    }

    private void init() {
        features = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        recurse(0, fields, weights, sb);
    }

    private void recurse(int i, List<String> fields, List<Float> weights, StringBuilder sb) {
        if (i >= fields.size()) {
            return;
        }
        String base = sb.toString();
        for (Float f : weights) {
            StringBuilder feature = new StringBuilder(base);
            if (f > 0.0f) {
                if (feature.length() > 0) {
                    feature.append(",");
                }
                feature.append(fields.get(i)).append("^").append(f);
                features.add(feature.toString());
            }
            recurse(i+1, fields, weights, feature);
        }
    }


    @Override
    public String getParameter() {
        return PF3;
    }

    @Override
    public List<String> getFeatures() {
        if (features == null) {
            init();
        }
        if (features.size() == 0) {
            features.add("");
        }

        return features;
    }

    @Override
    public String toString() {
        return "PF3{" +
                "features=" + getFeatures() +
                ", fields=" + fields +
                ", weights=" + weights +
                '}';
    }
}
