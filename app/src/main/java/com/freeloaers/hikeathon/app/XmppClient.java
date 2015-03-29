package com.freeloaers.hikeathon.app;

import android.os.AsyncTask;
import android.util.Log;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by sharat on 28/3/15.
 */
public class XmppClient {

    public static final String HOST = "hackathon.hike.in";
    public static final int PORT = 8282;
    private static final String TAG = "XMPP Client";
    private LinkedList<Contacts> contactList = new LinkedList<Contacts>();
    private PacketListener messageListener;
    MyConnectionListener myConnectionListener;
    XMPPConnection connection;
    String userName;
    String password;

    public interface MyConnectionListener {
        public void onAuthenticationSuccess();

        public void onAuthenticationFailure();

        public void onGetFriendList(LinkedList<Contacts> friendList);
    }

    public XmppClient(MyConnectionListener connectionListener) {
        this.myConnectionListener = connectionListener;
        ConnectionConfiguration connConfig = new ConnectionConfiguration(
                HOST, PORT, HOST);
        connConfig.setSASLAuthenticationEnabled(true);
        connection = new XMPPConnection(connConfig);
//        xmppConnection = new XMPPConnection()
    }

    public void setMessageListener(PacketListener messageListener, PacketFilter filter) {

    }

    public void setPresenceListener(PacketListener presenceListener, PacketFilter filter) {

    }

    public String getUser() {
        return userName;
    }


    public void register(String userName, String password) {
        this.userName = userName;
        this.password = password;

        new RegisterAsync().execute();
    }

    public void logIn(String userName, String password) {
        this.userName = userName;
        this.password = password;
        Log.e(TAG, "username:" + userName + "," + password);

        new LoginTask().execute();
    }

    public void addFriend(String id) {
        new AddFriend().execute(id);

    }


    public void getContactList() {
        new GetFriendListTask().execute();
    }

    public void sendMessage(String to, String message) {
        if (connection.isConnected()) {
            if (connection.isAuthenticated()) {
                Message msg = new Message(to, Message.Type.chat);
                msg.setBody(message);
                connection.sendPacket(msg);
            }
        }

    }


    private class LoginTask extends AsyncTask<Void, Void, Boolean> {

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
            if (result) {
                myConnectionListener.onAuthenticationSuccess();
                Presence presence = new Presence(Presence.Type.available);
                connection.sendPacket(presence);
            } else {
                myConnectionListener.onAuthenticationFailure();
            }
        }
    }

    private class AddFriend extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.accept_all);
            Roster roster = connection.getRoster();

            if (!roster.contains(params[0] + "@" + HOST)) {
                try {
                    roster.createEntry(params[0] + "@" + HOST, params[0], null);
                    Log.e(TAG, "friend added");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                Log.i("error= ", "contains");
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                myConnectionListener.onAuthenticationSuccess();
                Presence presence = new Presence(Presence.Type.available);
                connection.sendPacket(presence);
            } else {
                myConnectionListener.onAuthenticationFailure();
            }
        }
    }

    private class RegisterAsync extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                connection.connect();
                AccountManager accountManager = new AccountManager(connection);
                if (accountManager.supportsAccountCreation()) {
                    accountManager.createAccount(userName, password);
                    Log.i(TAG, "Create New Account");
                } else {
                    Log.i(TAG, "Can't create New Account");
                }
                return true;
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                Presence presence = new Presence(Presence.Type.available);
                connection.sendPacket(presence);
                myConnectionListener.onAuthenticationSuccess();
            } else {
                myConnectionListener.onAuthenticationFailure();
            }
        }
    }


    private class GetFriendListTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Roster roster = connection.getRoster();
            contactList.clear();
            Collection<RosterEntry> entries = roster.getEntries();
            for (RosterEntry entry : entries) {
                String userName = entry.getUser();
                String name = entry.getName();
                Presence presence = roster.getPresence(entry
                        .getUser());
                contactList.add(new Contacts(userName, name, presence));

            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            myConnectionListener.onGetFriendList(contactList);
        }
    }
}
