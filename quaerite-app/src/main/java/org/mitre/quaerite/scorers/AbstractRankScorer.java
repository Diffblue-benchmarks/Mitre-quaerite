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
package org.mitre.quaerite.scorers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mitre.quaerite.RankScorer;

public abstract class AbstractRankScorer implements RankScorer {

    static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public final int atN;

    public AbstractRankScorer(int atN) {
        this.atN = atN;
    }


    @Override
    public String getName() {
        if (getAtN() > -1) {
            return _getName() + "_" + getAtN();
        }
        return _getName();
    }

    abstract String _getName();

    public int getAtN() {
        return atN;
    }
}
