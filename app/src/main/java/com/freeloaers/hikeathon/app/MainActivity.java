package com.freeloaers.hikeathon.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class MainActivity extends ActionBarActivity implements XmppClient.MyConnectionListener{

    public static final String HOST = "hackathon.hike.in";
    public static final int PORT = 8282;
    public static final String USERNAME = "pop.chetan";
    public static final String PASSWORD = "password";

    private XmppClient xmppClient;
    private PacketListener messageListener;

  @InjectView( R.id.list) ListView list;
  ContactAdapter mAdapter ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
      ButterKnife.inject(this);
      xmppClient = new XmppClient(this);
      PacketFilter filter = new MessageTypeFilter(org.jivesoftware.smack.packet.Message.Type.chat);
      messageListener = new PacketListener() {
          @Override
          public void processPacket(Packet packet) {
              org.jivesoftware.smack.packet.Message message = (org.jivesoftware.smack.packet.Message) packet;
              if (message.getBody() != null) {
                  String fromName = StringUtils.parseBareAddress(message
                          .getFrom());
                  Log.e("XMPPChatDemoActivity", "Text Recieved " + message.getBody()
                          + " from " + fromName);
//                  messages.add(fromName + ":");
//                  messages.add(message.getBody());
//                  // Add the incoming message to the list view
//                  mHandler.post(new Runnable() {
//                      public void run() {
//                          setListAdapter();
//                      }
//                  });
              }
          }
      };
      xmppClient.setMessageListener(messageListener, filter);
      xmppClient.logIn(USERNAME, PASSWORD);

    mAdapter = new ContactAdapter(this,new ArrayList<Contacts>());
    list.setAdapter(mAdapter);
    mAdapter.notifyDataSetChanged();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @OnItemClick(R.id.list)
  void openConversation(int position)
  {
    Toast.makeText(this,"Clicked on "+ mAdapter.getItem(position).getUserName(),Toast.LENGTH_SHORT).show();
    getFragmentManager().beginTransaction().replace(R.id.selected_conversation, ConversationFragment
        .newInstance(mAdapter.getItem(position),xmppClient)).commit();
  }

    @Override
    public void onAuthenticationSuccess() {
        Toast.makeText(this, "Authentication success", Toast.LENGTH_SHORT).show();
        xmppClient.getContactList();
    }

    @Override
    public void onAuthenticationFailure() {
        Toast.makeText(this, "Authentication failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetFriendList(LinkedList<Contacts> friendList) {
        Toast.makeText(this,"Got Friend List",Toast.LENGTH_SHORT).show();
        mAdapter.clear();
        mAdapter.addAll(friendList);
        mAdapter.notifyDataSetChanged();
    }
}
