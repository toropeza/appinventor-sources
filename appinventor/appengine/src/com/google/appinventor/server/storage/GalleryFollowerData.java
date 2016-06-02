package com.google.appinventor.server.storage;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Parent;

/**
 * this class modelled after those in StoredData.java
 *
 * @author thomasoropeza@gmail.com (Thomas Oropeza)
 *
 */
public class GalleryFollowerData {

    // The Google Account userid
    @Id Long id;
    String userId;
    @Parent Key<GalleryFollowerData> galleryKey;
    @Indexed
    String followerId;
}
