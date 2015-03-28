package com.freeloaers.hikeathon.app;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;

import java.util.LinkedList;

/**
 * Created by sharat on 28/3/15.
 */
public class XmppClient {

    public static final String HOST = "hackathon.hike.in";
    public static final int PORT = 8282;
    private PacketListener messageListener;
    private MyConnectionListener myConnectionListener;
    private XMPPConnection xmppConnection;
    private String userName;
    private String password;

    public interface MyConnectionListener{
        public void onAuthenticationSuccess();
        public void onAuthenticationFailure();
        public void onGetFriendList(LinkedList<Contacts> friendList);
    }

    public XmppClient(PacketListener messageListener){
//        xmppConnection = new XMPPConnection()
    }

    public void logIn(String username, String password){

    }

    public void addFriend(String id){

    }

    public void getFriendList(){
    }

}
