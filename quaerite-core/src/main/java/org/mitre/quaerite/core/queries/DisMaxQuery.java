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
package org.mitre.quaerite.core.queries;

import org.apache.commons.lang3.tuple.Pair;
import org.mitre.quaerite.core.features.BF;
import org.mitre.quaerite.core.features.BQ;
import org.mitre.quaerite.core.features.PF;
import org.mitre.quaerite.core.features.TIE;

public class DisMaxQuery extends MultiMatchQuery {


    protected PF pf;
    protected BQ bq;
    protected BF bf;
    protected TIE tie;

    public DisMaxQuery() {
       super();
    }

    public DisMaxQuery(String queryString) {
        super(queryString);
    }

    public void setPF(PF pf) {
        this.pf = pf;
    }

    @Override
    public String getName() {
        return "dismax";
    }

    @Override
    public Object deepCopy() {
        return null;
    }
}
