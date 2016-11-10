/*
 * Copyright (c) 2013-2016 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.module.guid;

import com.graphaware.test.integration.GraphAwareIntegrationTest;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.Iterables;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GuidModuleEndToEndMinimalTest extends GraphAwareIntegrationTest {

    @Override
    protected String configFile() {
        return "neo4j-guid-minimal.conf";
    }

    @Test
    public void testMinimalConfig() {
        getDatabase().execute("CREATE (p:Person {name:'Luanne'})-[:WORKS_FOR]->(c:Company {name:'GraphAware'})");

        try (Transaction tx = getDatabase().beginTx()) {
            for (Node node : Iterables.asResourceIterable(getDatabase().getAllNodes())) {
                assertTrue(node.hasProperty("guid"));
            }

            for (Relationship rel : Iterables.asResourceIterable(getDatabase().getAllRelationships())) {
                assertFalse(rel.hasProperty("guid"));
            }

            tx.success();
        }
    }
}
