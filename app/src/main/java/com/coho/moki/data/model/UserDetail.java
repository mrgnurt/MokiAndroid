package com.coho.moki.data.model;

import java.util.List;

/**
 * Created by trung on 05/10/2017.
 */

public class UserDetail extends User{
    private String phoneNumber;

    private String url;

    private int status;

    private String address;

    private String city;

    private int active;

    private List<String> blockingList;

    private List<String> followingList;

    private List<String> followedList;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<String> getBlockingList() {
        return blockingList;
    }

    public void setBlockingList(List<String> blockingList) {
        this.blockingList = blockingList;
    }

    public List<String> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<String> followingList) {
        this.followingList = followingList;
    }

    public List<String> getFollowedList() {
        return followedList;
    }

    public void setFollowedList(List<String> followedList) {
        this.followedList = followedList;
    }
}
