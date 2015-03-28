package com.freeloaers.hikeathon.app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by exort on 29/3/15.
 */
public class ContactAdapter extends ArrayAdapter<Contacts> {
  TextDrawable.IBuilder builder;

  private final LayoutInflater mInflater;

  public ContactAdapter(Context context,
                        ArrayList<Contacts> objects) {
    super(context, R.layout.contact_list_row, objects);
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     builder= TextDrawable.builder()
        .beginConfig()
        .withBorder(4)
        .endConfig()
        .rect();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ContactViewHolder holder ;
    if(convertView == null)
    {
      convertView = mInflater.inflate(R.layout.contact_list_row,parent,false);
      holder = new ContactViewHolder(convertView);
      convertView.setTag(holder);
    }else
      holder = (ContactViewHolder) convertView.getTag();

    ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
    int color = generator.getColor(getItem(position).getUserName());



    holder.img.setImageDrawable(builder.build(getItem(position).getUserName().toUpperCase().substring(0,1), color));

    holder.name.setText(getItem(position).getUserName().substring(0,getItem(position).getUserName().indexOf("@")));

    return  convertView;

  }

  public class ContactViewHolder{
    @InjectView(R.id.contact_image)
    ImageView img;
    @InjectView(R.id.contact_name)
    TextView name;

    public ContactViewHolder(View view) {
      ButterKnife.inject(this,view);
    }
  }
}
