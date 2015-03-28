package com.freeloaers.hikeathon.app;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by exort on 29/3/15.
 */
public class ContactAdapter extends ArrayAdapter<Contacts> {
  public ContactAdapter(Context context, int resource,
                        ArrayList<Contacts> objects) {
    super(context, resource, objects);
  }
}
