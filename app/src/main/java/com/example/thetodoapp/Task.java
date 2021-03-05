package com.example.thetodoapp;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    private String intitule;
    private String description;
    private String heureDebut;
    private Date dateDebut;
    private String heureFin;
    private Date dateFin;
    private String context;
    private String url;


    public Task(String inti, String des, String hd,String hf, Date dd, Date df,String con, String url){

        this.intitule = inti;
        this.description = des;
        this.heureDebut = hd;
        this.heureFin = hf;
        this.dateDebut = dd;
        this.dateFin = df;
        this.context = con;
        this.url = url;
    }


    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) {this.intitule = intitule;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getHeureDebut() {return heureDebut;}
    public void setHeureDebut(String duree) {this.heureDebut = duree;}

    public Date getDateDebut() {return dateDebut;}
    public void setDateDebut(Date date) {this.dateDebut = date;}

    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }

    public String getHeureFin() { return heureFin; }
    public void setHeureFin(String heureFin) { this.heureFin = heureFin; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String toString(){
        String str = "";
        str += "Intitulé : " + intitule;
        str += " Des : " + description;
        str += " Context : " + context;
        str += " HeureDebut : " + heureDebut;
        str += " HeureFin : " + heureFin;
        str += " DateDebut : " + dateDebut;
        str += " DateFin : " + dateFin;
        return str;
    }
}
