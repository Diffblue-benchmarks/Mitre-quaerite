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
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mitre.quaerite.core.features.CustomHandler;
import org.mitre.quaerite.core.queries.Query;
import org.mitre.quaerite.core.serializers.QuerySerializer;


public class Experiment {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(Query.class, new QuerySerializer())
            .create();
    private String name;
    private String searchServerUrl;
    private CustomHandler customHandler;
    private Query query;
    private final List<Query> filterQueries = new ArrayList<>();

    public Experiment(String name, String searchServerUrl, CustomHandler customHandler, Query query) {
        this.customHandler = customHandler;
        this.name = name;
        this.searchServerUrl = searchServerUrl;
        this.query = query;
    }

    public Experiment(String name, String searchServerUrl, Query query) {
        this(name, searchServerUrl, null, query);
    }

    //consider adding clone to experiment with a new name
    public void setName(String name) {
        this.name = name;
    }


    public String toJson() {
        return GSON.toJson(this);
    }


    public CustomHandler getCustomHandler() {
        return customHandler;
    }

    public static Experiment fromJson(String s) {
        return GSON.fromJson(s, Experiment.class);
    }

    public static Experiment fromJson(Reader r) {
        return GSON.fromJson(r, Experiment.class);
    }


    public String getName() {
        return name;
    }

    public String getSearchServerUrl() {
        return searchServerUrl;
    }

    public Query getQuery() {
        return query;
    }

    public List<Query> getFilterQueries() {
        return filterQueries;
    }

    public void addFilterQueries(List<Query> queries) {
        filterQueries.addAll(queries);
    }


    @Override
    public String toString() {
        return "Experiment{" +
                "name='" + name + '\'' +
                ", searchServerUrl='" + searchServerUrl + '\'' +
                ", customHandler='" + customHandler + '\'' +
                ", query=" + query +
                '}';
    }

    public Experiment deepCopy() {
        //TODO stub -- consider adding a new name as a parameter
        throw new IllegalArgumentException("stub -- must develop");

    }

    public void setSearchServerUrl(String serverUrl) {
        this.searchServerUrl = serverUrl;
    }

    public void setCustomHandler(CustomHandler customHandler) {
        this.customHandler = customHandler;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
