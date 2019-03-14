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
 *
 */
package org.mitre.quaerite.core.scorecollectors;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mitre.quaerite.core.Judgments;
import org.mitre.quaerite.core.QueryInfo;
import org.mitre.quaerite.core.ResultSet;

public interface ScoreCollector {

    void add(Judgments judgments, ResultSet resultSet);

    //summary statistics for a given querySet
    Map<String, Double> getSummaryStatistics(String querySet);

    //key is the query, with Double being the value for that query
    Map<QueryInfo, Double> getScores();

    /**
     *
     * @return list of statistics used by the collector (e.g. mean, median, stdevp)
     */
    List<String> getStatistics();

    int getK();

    String getName();

    Collection<? extends String> getQuerySets();
}
