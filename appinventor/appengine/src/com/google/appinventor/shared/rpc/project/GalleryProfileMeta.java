package com.google.appinventor.shared.rpc.project;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class to contain all of a user's meta data
 *
 * @author thomasoropeza@gmail.com (Thomas Oropeza)
 */
public class GalleryProfileMeta implements IsSerializable{

    private int totalUserDownloads;
    private int totalUserLikes;

    public int getTotalUserLikes() {
        return totalUserLikes;
    }

    public void setTotalUserLikes(int totalUserLikes) {
        this.totalUserLikes = totalUserLikes;
    }

    public int getTotalUserDownloads() {
        return totalUserDownloads;
    }

    public void setTotalUserDownloads(int totalUserDownloads) {
        this.totalUserDownloads = totalUserDownloads;
    }


}
