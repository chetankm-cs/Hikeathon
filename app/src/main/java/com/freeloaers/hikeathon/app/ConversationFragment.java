package com.freeloaers.hikeathon.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.jivesoftware.smack.XMPPConnection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ConversationFragment extends Fragment {


  @InjectView(R.id.textinput) EditMessage msgEdit;
  static Contacts contact;
  static XmppClient xmppClient;
  private OnFragmentInteractionListener mListener;

  public static ConversationFragment newInstance(Contacts contact,XmppClient client) {
    ConversationFragment fragment = new ConversationFragment();
    ConversationFragment.contact = contact;
    ConversationFragment.xmppClient = client;
    return fragment;
  }

  public ConversationFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view=  inflater.inflate(R.layout.fragment_conversation, container, false);
    ButterKnife.inject(this,view);
    return view;
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
//      throw new ClassCastException(activity.toString()
//                                   + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @OnClick(R.id.textSendButton)
  void sendMessage()
  {
    xmppClient.sendMessage(contact.getUserName(),msgEdit.getText().toString());
    msgEdit.setText("");
  }


  public interface OnFragmentInteractionListener {

    // TODO: Update argument type and name
    public void onFragmentInteraction(Uri uri);
  }

}
