package com.google.appinventor.shared.rpc.project;

import com.google.appinventor.shared.rpc.user.User;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A collection of Users
 */
public class Followers implements IsSerializable, Iterable<User> {
    private ArrayList<User> followers;

    //the total amount of followers a user has
    private int totalFollowers;

    public Followers(){
        followers = new ArrayList<User>();
    }

    public void addFollower(User follower){
        followers.add(follower);
    }

    public int size(){
        return followers.size();
    }

    public int getTotalFollowers() {
        return totalFollowers;
    }

    public void setTotalFollowers(int totalFollowers) {
        this.totalFollowers = totalFollowers;
    }

    @Override
    public Iterator<User> iterator() {
        return followers.iterator();
    }
}
