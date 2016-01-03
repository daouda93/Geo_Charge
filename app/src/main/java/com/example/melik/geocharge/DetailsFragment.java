package com.example.melik.geocharge;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class DetailsFragment extends Fragment {

        public DetailsFragment(){

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.activity_details_fragment, container, false);

            ImageButton close = (ImageButton) view.findViewById(R.id.close_details);
            ImageButton fav = (ImageButton) view.findViewById(R.id.favorite_infoWindow);
            ImageButton itin = (ImageButton) view.findViewById(R.id.itineraire_infoWindow);
            ImageButton report = (ImageButton) view.findViewById(R.id.report_infowindow);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentById(R.id.details_frag)).commit();
                }
            });
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return view;
        }
}
