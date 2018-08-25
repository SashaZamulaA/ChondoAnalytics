package com.example.aleksandr.myapplication.ui.hdh;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aleksandr.myapplication.R;
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel;

import java.util.List;

public class ArtistList extends ArrayAdapter<HDHModel> {
    private Activity context;
    private List<HDHModel> artists;

    public ArtistList(Activity context, List<HDHModel> artists) {
        super( context, R.layout.item_is_word_list, artists );
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate( R.layout.item_is_word_list, null, true );

        TextView textViewName = listViewItem.findViewById( R.id.name );
        TextView textViewGenre = listViewItem.findViewById( R.id.category );

        HDHModel artist = artists.get( position );
        textViewName.setText( artist.getName() );
        textViewGenre.setText( artist.getCategory() );

        return listViewItem;
    }
}