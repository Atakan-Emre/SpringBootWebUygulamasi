/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.artifact;

import junit.framework.TestCase;
import org.apache.maven.artifact.handler.ArtifactHandlerMock;
import org.apache.maven.artifact.versioning.VersionRange;

public class DefaultArtifactTest extends TestCase {

    private DefaultArtifact artifact;

    private DefaultArtifact snapshotArtifact;

    private String groupId = "groupid",
            artifactId = "artifactId",
            version = "1.0",
            scope = "artifactScope",
            type = "type",
            classifier = "classifier";

    private String snapshotSpecVersion = "1.0-SNAPSHOT";
    private String snapshotResolvedVersion = "1.0-20070606.010101-1";

    private VersionRange versionRange;
    private VersionRange snapshotVersionRange;

    private ArtifactHandlerMock artifactHandler;

    protected void setUp() throws Exception {
        super.setUp();
        artifactHandler = new ArtifactHandlerMock();
        versionRange = VersionRange.createFromVersion(version);
        artifact = new DefaultArtifact(groupId, artifactId, versionRange, scope, type, classifier, artifactHandler);

        snapshotVersionRange = VersionRange.createFromVersion(snapshotResolvedVersion);
        snapshotArtifact = new DefaultArtifact(
                groupId, artifactId, snapshotVersionRange, scope, type, classifier, artifactHandler);
    }

    public void testGetVersionReturnsResolvedVersionOnSnapshot() {
        assertEquals(snapshotResolvedVersion, snapshotArtifact.getVersion());

        // this is FOUL!
        //        snapshotArtifact.isSnapshot();

        assertEquals(snapshotSpecVersion, snapshotArtifact.getBaseVersion());
    }

    public void testGetDependencyConflictId() {
        assertEquals(groupId + ":" + artifactId + ":" + type + ":" + classifier, artifact.getDependencyConflictId());
    }

    public void testGetDependencyConflictIdNullGroupId() {
        artifact.setGroupId(null);
        assertEquals(null + ":" + artifactId + ":" + type + ":" + classifier, artifact.getDependencyConflictId());
    }

    public void testGetDependencyConflictIdNullClassifier() {
        artifact = new DefaultArtifact(groupId, artifactId, versionRange, scope, type, null, artifactHandler);
        assertEquals(groupId + ":" + artifactId + ":" + type, artifact.getDependencyConflictId());
    }

    public void testGetDependencyConflictIdNullScope() {
        artifact.setScope(null);
        assertEquals(groupId + ":" + artifactId + ":" + type + ":" + classifier, artifact.getDependencyConflictId());
    }

    public void testToString() {
        assertEquals(
                groupId + ":" + artifactId + ":" + type + ":" + classifier + ":" + version + ":" + scope,
                artifact.toString());
    }

    public void testToStringNullGroupId() {
        artifact.setGroupId(null);
        assertEquals(artifactId + ":" + type + ":" + classifier + ":" + version + ":" + scope, artifact.toString());
    }

    public void testToStringNullClassifier() {
        artifact = new DefaultArtifact(groupId, artifactId, versionRange, scope, type, null, artifactHandler);
        assertEquals(groupId + ":" + artifactId + ":" + type + ":" + version + ":" + scope, artifact.toString());
    }

    public void testToStringNullScope() {
        artifact.setScope(null);
        assertEquals(groupId + ":" + artifactId + ":" + type + ":" + classifier + ":" + version, artifact.toString());
    }

    public void testComparisonByVersion() {
        Artifact artifact1 = new DefaultArtifact(
                groupId, artifactId, VersionRange.createFromVersion("5.0"), scope, type, classifier, artifactHandler);
        Artifact artifact2 = new DefaultArtifact(
                groupId, artifactId, VersionRange.createFromVersion("12.0"), scope, type, classifier, artifactHandler);

        assertTrue(artifact1.compareTo(artifact2) < 0);
        assertTrue(artifact2.compareTo(artifact1) > 0);

        Artifact artifact = new DefaultArtifact(
                groupId, artifactId, VersionRange.createFromVersion("5.0"), scope, type, classifier, artifactHandler);
        assertTrue(artifact.compareTo(artifact1) == 0);
        assertTrue(artifact1.compareTo(artifact) == 0);
    }

    public void testNonResolvedVersionRangeConsistentlyYieldsNullVersions() throws Exception {
        VersionRange vr = VersionRange.createFromVersionSpec("[1.0,2.0)");
        artifact = new DefaultArtifact(groupId, artifactId, vr, scope, type, null, artifactHandler);
        assertEquals(null, artifact.getVersion());
        assertEquals(null, artifact.getBaseVersion());
    }
}
