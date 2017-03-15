package com.mhw.audiobucket.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by michaelwomack on 2/27/17.
 */
public class CloudStorageManager {


    public Storage getStorageService() {
        return StorageOptions.getDefaultInstance()
                .getService();
    }

    public Iterator<Blob> getBucketBlobs(String bucketName) {
        Storage storage = getStorageService();
        Bucket bucket = storage.get(bucketName);
        return bucket.list().iterateAll();
    }


    public List<String> listBucketBlobNames(String bucketName) {
        Iterator<Blob> storageBlobs = getBucketBlobs(bucketName);
        List<String> blobNames = new ArrayList<>();

        while (storageBlobs.hasNext()) {
            Blob storageBlob = storageBlobs.next();
            blobNames.add(storageBlob.getName());
        }
        return blobNames;
    }

    public Bucket getBucket(String bucketName) {
        Storage storage = getStorageService();
        return storage.get(bucketName);
    }

    public Blob getBlob(String bucketName, String blobName) {
        Bucket bucket = getBucket(bucketName);
        return bucket.get(blobName);
    }

    public Blob uploadBlobFromInputStream(String bucketName, String blobName, InputStream inputStream, String contentType) {
        Bucket bucket = getBucket(bucketName);
        return bucket.create(blobName, inputStream, contentType);
    }

    public boolean deleteBlob(String bucketName, String blobName) {
        Bucket bucket = getBucket(bucketName);
        Blob blob = getBlob(bucketName, blobName);
        return blob.delete(Blob.BlobSourceOption.metagenerationMatch());
    }

    public String getStorageNameForImage(String userId, String fileName) {
        return "users/" + userId + "/images/" + fileName;
    }

    public String getStorageNameForAudio(String userId, String fileName) {
        return "users/" + userId + "/audio/" + fileName;
    }
}
