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
package org.mitre.quaerite.analysis;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("need to have Solr/ES tmdb instance running")
public class TestCompareAnalyzers {
    private static String TMDB_URL = "http://localhost:8983/solr/tmdb";

    @Test
    public void IntegrationTMDBTest() throws Exception {
        CompareAnalyzers.main(new String[]{
            "-s", TMDB_URL,
                "-bf", "production_companies_facet_lc",
                "-ff", "production_companies_facet",
                "-minSetSize", "1"

        });
        //TODO -- catch stdout and turn this into a real unit test
    }
}
