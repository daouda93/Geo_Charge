package com.example.melik.geocharge;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Melik on 25/12/2015.
 */
public class Borne   {
    private String type,details,nom;
    private double longitude,latitude;
    private Marker uneBorne;
    private boolean selected;

    public Borne(){
        this.nom = "Inconnu";
    }
    public Borne(String unType, String desDetails, double latitude,double longitude){
        this.type=unType;
        this.details=desDetails;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Marker ajouterBorneMap(GoogleMap uneMap){
        this.uneBorne=uneMap.addMarker(new MarkerOptions().title(type).snippet(details).position(new LatLng(this.latitude,this.longitude)));
        return this.uneBorne;
    }
    public void ajouterBorneBDD(Database db){
        db.ajouterBorne(this);
    }

    public void supprimerBorne(Database db){
        this.uneBorne.remove();
        db.supprimerBorne(this);
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getNom(){
        return nom;
    }

    public Marker getUneBorne() { return uneBorne;    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "Borne{" +
                "type='" + type + '\'' +
                ", details='" + details + '\'' +
                ", nom='" + nom + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}