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

import java.util.Objects;

import org.mitre.quaerite.core.features.PF2;
import org.mitre.quaerite.core.features.PF3;

public class EDisMaxQuery extends DisMaxQuery {

    PF2 pf2;
    PF3 pf3;
    public EDisMaxQuery() {
        super(null);
    }

    public EDisMaxQuery(String queryString) {
        super(queryString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EDisMaxQuery that = (EDisMaxQuery) o;
        return Objects.equals(pf2, that.pf2) &&
                Objects.equals(pf3, that.pf3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pf2, pf3);
    }

    @Override
    public String toString() {
        return "EDisMaxQuery{" +
                "pf2=" + pf2 +
                ", pf3=" + pf3 +
                ", pf=" + pf +
                ", bq=" + bq +
                ", bf=" + bf +
                ", queryString='" + queryString + '\'' +
                ", qf=" + qf +
                ", tie=" + tie +
                '}';
    }
}
