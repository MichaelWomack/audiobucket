package com.mhw.audiobucket.model.conf;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created by michaelwomack on 3/19/17.
 */
public class StorageProperties {

    private String bucketName;
    private String baseStorageUrl;

    @Inject
    public StorageProperties(@Named("bucket_name") String bucketName,
                             @Named("base_storage_url") String baseStorageUrl) {

        this.bucketName = bucketName;
        this.baseStorageUrl = baseStorageUrl;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getBaseStorageUrl() {
        return baseStorageUrl;
    }

    public String getStorageNameForImage(String userId, String fileName) {
        return "users/" + userId + "/images/" + fileName;
    }

    public String getStorageNameForAudio(String userId, String fileName) {
        return "users/" + userId + "/audio/" + fileName;
    }

    public String getFullStorageObjectUrl(String storageItemPath) {
        return this.baseStorageUrl + this.bucketName + "/" + storageItemPath;
    }

    @Override
    public String toString() {
        return "StorageProperties{" +
                "bucketName='" + bucketName + '\'' +
                ", baseStorageUrl='" + baseStorageUrl + '\'' +
                '}';
    }
}
