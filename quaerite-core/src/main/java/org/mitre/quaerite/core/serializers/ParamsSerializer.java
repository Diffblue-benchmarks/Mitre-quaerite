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
package org.mitre.quaerite.core.serializers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.mitre.quaerite.core.features.Feature;
import org.mitre.quaerite.core.features.FloatFeature;
import org.mitre.quaerite.core.features.ParamsMap;
import org.mitre.quaerite.core.features.StringFeature;
import org.mitre.quaerite.core.features.WeightableField;
import org.mitre.quaerite.core.features.WeightableListFeature;
import org.mitre.quaerite.core.features.factories.FeatureFactory;
import org.mitre.quaerite.core.features.factories.FloatFeatureFactory;
import org.mitre.quaerite.core.features.factories.StringFeatureFactory;
import org.mitre.quaerite.core.features.factories.WeightableListFeatureFactory;

public class ParamsSerializer extends AbstractFeatureSerializer
        implements JsonSerializer<ParamsMap>, JsonDeserializer<ParamsMap> {

    @Override
    public ParamsMap deserialize(JsonElement jsonElement, Type type,
                         JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ParamsMap paramsMap = new ParamsMap();
        for (String name : jsonObject.keySet()) {
            Feature param = buildParam(name, jsonObject.get(name));
            if (param != null) {
                paramsMap.put(name, param);
            }
        }
        return paramsMap;
    }

    private Feature buildParam(String parameterName, JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        if (element.isJsonArray() && ((JsonArray)element).size() == 0) {
            return null;
        }
        Class paramClass = determineClass(parameterName);
        if (WeightableListFeature.class.isAssignableFrom(paramClass)) {
            return buildWeightableListFeature(parameterName, element);
        } else if (StringFeature.class.isAssignableFrom(paramClass)) {
            return buildStringFeature(parameterName, element);
        } else if (FloatFeature.class.isAssignableFrom(paramClass)) {
            return buildFloatFeature(parameterName, element);
        } else {
            throw new IllegalArgumentException("I regret I don't know how to build: "+parameterName);
        }
    }

    private Feature buildFloatFeature(String name, JsonElement element) {
        try {
            Class clazz = Class.forName(getClassName(name));
        if (!(FloatFeature.class.isAssignableFrom(clazz))) {
            throw new IllegalArgumentException(getClassName(name) + " must be assignable from WeightableListFeatureFactory");
        }
        Constructor constructor = null;

            constructor = clazz.getConstructor(float.class);
            return (FloatFeature)constructor.newInstance(element.getAsFloat());
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    private Feature buildStringFeature(String name, JsonElement element) {
        try {
        Class clazz = Class.forName(getClassName(name));
        if (!(FloatFeature.class.isAssignableFrom(clazz))) {
            throw new IllegalArgumentException(getClassName(name) + " must be assignable from WeightableListFeatureFactory");
        }
        Constructor constructor = null;
            constructor = clazz.getConstructor(Float.class);
            return (StringFeature)constructor.newInstance(element.getAsString());
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    private WeightableListFeature buildWeightableListFeature(String name, JsonElement element) {
        WeightableListFeature weightableListFeature;
        try {
            Class clazz = Class.forName(getClassName(name));
            if (!(WeightableListFeature.class.isAssignableFrom(clazz))) {
                throw new IllegalArgumentException(getClassName(name) + " must be assignable from WeightableListFeatureFactory");
            }
            weightableListFeature = (WeightableListFeature)clazz.newInstance();
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }



        if (element.isJsonPrimitive()) {
            weightableListFeature.add(new WeightableField(element.getAsString()));
        } else if (element.isJsonArray()) {
            for (JsonElement e : (JsonArray)element) {
                weightableListFeature.add(new WeightableField(e.getAsString()));
            }
        }
        return weightableListFeature;
    }


    @Override
    public JsonElement serialize(ParamsMap paramsMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, Feature> e : paramsMap.getParams().entrySet()) {
            String name = e.getKey();
            JsonElement jsonFeature = serializeFeature(e.getValue());
            if (jsonFeature != JsonNull.INSTANCE) {
                jsonObject.add(name.toLowerCase(Locale.US), jsonFeature);
            }
        }
        return jsonObject;
    }

    private JsonElement serializeFeature(Feature feature) {
        if (feature == null) {
            return new JsonArray();
        }

        if (feature instanceof WeightableListFeature) {
            JsonObject ret = new JsonObject();
            JsonElement jsonFields;
            List<WeightableField> fields = ((WeightableListFeature)feature).getWeightableFields();
            if (fields.size() > 1) {
                jsonFields = new JsonArray();
                for (WeightableField f : ((WeightableListFeature) feature).getWeightableFields()) {
                    ((JsonArray)jsonFields).add(f.toString());
                }
            } else if (fields.size() == 1){
                jsonFields = new JsonPrimitive(fields.get(0).toString());
            } else {
                jsonFields = JsonNull.INSTANCE;
            }
            return jsonFields;
        } else if (feature instanceof FloatFeature ||
            feature instanceof StringFeature) {
                return new JsonPrimitive(feature.toString());

        } else {
            throw new IllegalArgumentException("not yet implemented: "+feature);
        }
    }

    /****** Helper method to get the className of the object to be deserialized *****/
    private FeatureFactory buildWeightableFeatureSet(String clazzName, List<String> fields, List<Float> defaultWeights) {
        if (!clazzName.contains(".")) {
            clazzName = getClassName(clazzName);
        }
        try {
            Class cl = Class.forName(clazzName);
            if (!(WeightableListFeatureFactory.class.isAssignableFrom(cl))) {
                throw new IllegalArgumentException(clazzName + " must be assignable from WeightableListFeatureFactory");
            }
            Constructor con = cl.getConstructor(List.class, List.class);
            return (FeatureFactory) con.newInstance(fields, defaultWeights);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}


