/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mitre.quaerite.features.sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.mitre.quaerite.features.Feature;
import org.mitre.quaerite.features.FloatFeature;
import org.mitre.quaerite.features.StringFeature;
import org.mitre.quaerite.util.MathUtil;

/**
 * This implements a basic set of options, where only one
 * option is expected at a time, e.g. server url.
 * Note that permute returns just the list
 */
public abstract class StringFeatureSet implements FeatureSet<StringFeature> {
    Random random = new Random();
    final List<StringFeature> features;

    protected StringFeatureSet(List<StringFeature> features) {
        this.features = features;
    }

    @Override
    public Set<Feature> getEachDefaultFeature() {
        Set<Feature> ret = new TreeSet();
        ret.addAll(features);
        return ret;
    }

    @Override
    public List<Set<Feature>> permute(int maxSize) {
        List<Set<Feature>> ret = new ArrayList<>();
        for (StringFeature feature : features) {
            ret.add(Collections.singleton(feature));
            if (ret.size() >= maxSize) {
                return ret;
            }
        }
        return ret;
    }

    @Override
    public Set<Feature> random() {
        int i = random.nextInt(features.size());
        return Collections.singleton(features.get(i));
    }

    @Override
    public Set<StringFeature> mutate(Set<StringFeature> instanceFeatures, double probability, double amplitude) {
        if (MathUtil.RANDOM.nextDouble() < probability) {
            int i = random.nextInt(features.size());
            return Collections.singleton(features.get(i));
        }
        return instanceFeatures;
    }
}
