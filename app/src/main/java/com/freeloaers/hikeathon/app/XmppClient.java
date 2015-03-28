package com.freeloaers.hikeathon.app;

import android.os.AsyncTask;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.util.LinkedList;

/**
 * Created by sharat on 28/3/15.
 */
public class XmppClient {

    public static final String HOST = "hackathon.hike.in";
    public static final int PORT = 8282;
    private PacketListener messageListener;
    MyConnectionListener myConnectionListener;
    XMPPConnection connection;
    String userName;
    String password;

    public interface MyConnectionListener{
        public void onAuthenticationSuccess();
        public void onAuthenticationFailure();
        public void onGetFriendList(LinkedList<Contacts> friendList);
    }

    public XmppClient(MyConnectionListener connectionListener){
        this.myConnectionListener = connectionListener;
        ConnectionConfiguration connConfig = new ConnectionConfiguration(
                HOST, PORT,HOST);
        connConfig.setSASLAuthenticationEnabled(true);
        connection = new XMPPConnection(connConfig);
//        xmppConnection = new XMPPConnection()
    }

    public void setMessageListener(PacketListener messageListener, PacketFilter filter){

    }

    public void setPresenceListener(PacketListener presenceListener, PacketFilter filter){

    }

    public String getUser(){
        return userName;
    }


    public void register(String userName, String password){
        this.userName = userName;
        this.password = password;

        //ToDo Write code to register user
    }

    public void logIn(String userName, String password){
        this.userName = userName;
        this.password = password;
        new LoginTask().execute();
    }

    public void addFriend(String id){

    }

    public void getFriendList(){

    }

    public void sendMessage(String to, String message){
        if(connection.isConnected()){
            if(connection.isAuthenticated()){
                Message msg = new Message(to, Message.Type.chat);
                msg.setBody(message);
                connection.sendPacket(msg);
            }
        }

    }


    private  class LoginTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                connection.connect();
                connection.login(userName, password);
                return true;
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if(result){
                myConnectionListener.onAuthenticationSuccess();
                Presence presence = new Presence(Presence.Type.available);
                connection.sendPacket(presence);
            } else {
                myConnectionListener.onAuthenticationFailure();
            }
        }
    }

}
