/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.atom;

import java.text.SimpleDateFormat;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.Registry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Unit test for AtomEntryPollingConsumer
 */
public class AtomEntryPollingConsumerTest extends CamelTestSupport {

    @Test
    public void testResult() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result1");
        mock.expectedMessageCount(7);
        mock.assertIsSatisfied();
    }

    @Test
    public void testResult2() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result2");
        mock.expectedMessageCount(7);
        mock.assertIsSatisfied();
    }

    @Test
    public void testResult3() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result3");
        mock.expectedMessageCount(4);
        mock.assertIsSatisfied();
    }

    @Override
    protected void bindToRegistry(Registry registry) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        registry.bind("myDate", df.parse("2007-11-13 14:35:00 +0100"));
    }

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from("atom:file:src/test/data/feed.atom?splitEntries=true&consumer.delay=500").to("mock:result1");

                from("atom:file:src/test/data/feed.atom?splitEntries=true&filter=false&consumer.delay=500").to("mock:result2");

                from("atom:file:src/test/data/feed.atom?splitEntries=true&filter=true&lastUpdate=#myDate&consumer.delay=500").to("mock:result3");
            }
        };
    }
}
