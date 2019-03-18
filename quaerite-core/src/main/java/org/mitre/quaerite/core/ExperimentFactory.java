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
package org.mitre.quaerite.core;

import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mitre.quaerite.core.features.factories.FeatureFactories;
import org.mitre.quaerite.core.scorecollectors.ScoreCollector;
import org.mitre.quaerite.core.scorecollectors.ScoreCollectorListSerializer;
import org.mitre.quaerite.core.serializers.FeatureFactorySerializer;

public class ExperimentFactory {


    List<ScoreCollector> scoreCollectors;
    FeatureFactories featureFactories;

    public static ExperimentFactory fromJson(Reader reader) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeHierarchyAdapter(ScoreCollector.class, new ScoreCollectorListSerializer.ScoreCollectorSerializer())
                .registerTypeAdapter(FeatureFactories.class, new FeatureFactorySerializer())
                .create();
        return gson.fromJson(reader, ExperimentFactory.class);
    }
    private transient ScoreCollector trainScoreCollector;
    private transient ScoreCollector testScoreCollector;

    public List<ScoreCollector> getScoreCollectors() {
        return scoreCollectors;
    }

    @Override
    public String toString() {
        return "ExperimentFactory{" +
                "scoreCollectors=" + scoreCollectors +
                ", featureFactories=" + featureFactories +
                '}';
    }

    public FeatureFactories getFeatureFactories() {
        return featureFactories;
    }

    public ScoreCollector getTrainScoreCollector() {
        if (trainScoreCollector == null) {
            for (ScoreCollector scoreCollector : scoreCollectors) {
                if (scoreCollector.getUseForTrain()) {
                    trainScoreCollector = scoreCollector;
                    break;
                }
            }
        }
        return trainScoreCollector;
    }

    public ScoreCollector getTestScoreCollector() {
        if (testScoreCollector == null && scoreCollectors.size() == 0) {
            for (ScoreCollector scoreCollector : scoreCollectors) {
                if (scoreCollector.getUseForTest()) {
                    testScoreCollector = scoreCollector;
                    break;
                }
            }
        }
        return testScoreCollector;
    }
}
