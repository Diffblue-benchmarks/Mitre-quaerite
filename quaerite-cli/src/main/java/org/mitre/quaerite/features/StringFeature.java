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
package org.mitre.quaerite.features;

import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public class StringFeature implements Feature<StringFeature> {

    private String feature;

    public StringFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        return feature;
    }

    @Override
    public Pair<Set<StringFeature>, Set<StringFeature>> crossover(Set<StringFeature> parentA, Set<StringFeature> parentB) {
        return Pair.of(parentA, parentB);
    }
}
