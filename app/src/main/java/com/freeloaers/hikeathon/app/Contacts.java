package com.freeloaers.hikeathon.app;

import org.jivesoftware.smack.packet.Presence;

/**
 * Created by sharat on 28/3/15.
 */
public class Contacts {

    private String userName;
    private String name;
    private Presence presence;

    public Contacts(String userName, String name, Presence presence){
        this.userName = userName;
        this.name = name;
        this.presence = presence;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public Presence getPresence() {
        return presence;
    }
}
