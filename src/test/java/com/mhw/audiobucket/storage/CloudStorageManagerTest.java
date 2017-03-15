package com.mhw.audiobucket.storage;

import com.google.cloud.storage.Blob;
import com.mhw.audiobucket.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by michaelwomack on 3/14/17.
 */
class CloudStorageManagerTest {

    private CloudStorageManager manager = new CloudStorageManager();
    private String testBucket = "audiobucket-test";

    @AfterEach
    void tearDown() {
        clearTestBucket();
    }

    @Test
    void testGetBucketBlobs() {
        InputStream is = Util.getResourceAsStream("data/test-data.txt");
        manager.uploadBlobFromInputStream(testBucket, "test-data.txt",is, "text/plain");
        Iterator<Blob> blobIterator = manager.getBucketBlobs(testBucket);
        Blob blob = blobIterator.next();

        assertNotNull(blob);
        assertEquals("test-data.txt", blob.getName());
    }

    @Test
    void testListBucketBlobNames() {

    }

    @Test
    void testGetBucket() {

    }

    private void clearTestBucket() {
        Iterator<Blob> blobIterator = manager.getBucketBlobs(testBucket);
        while (blobIterator.hasNext()) {
            manager.deleteBlob(testBucket, blobIterator.next().getName());
        }
    }
}