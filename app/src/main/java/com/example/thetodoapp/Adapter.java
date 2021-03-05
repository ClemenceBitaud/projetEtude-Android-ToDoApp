package com.example.thetodoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adapter extends BaseAdapter {

    private int color1 = 0xaae1ffad;
    private int color2 = 0xaac9edff;
    private int color3 = 0xaacd9f9f;
    private int color4 = 0xaaffd5ad;
    private int color5 = 0xaaff8585;
    private int color6 = 0xaaffc9eb;
    private int color7 = 0xaadec9ff;
    private int color8 = 0xaafffcad;
    private int color9 = 0xaad1daff;
    private int color10 = 0xaa9dcfa9;
    private int color11 = 0xaad6d6d6;
    private List<Task> listTask;
    private Context context;
    private LayoutInflater inflater;
    private Date dateDebut;
    private String dateDebutString;

    public Adapter(Context context, List<Task> list){
        this.context = context;
        listTask = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listTask.size();
    }

    @Override
    public Object getItem(int position) {
        return listTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView == null){
            view = (View) inflater.inflate(R.layout.task_list_item,parent,false);

        }
        else {
            view = (View) convertView;
        }

        TextView inti = (TextView) view.findViewById(R.id.taskInt);
        TextView context = (TextView) view.findViewById(R.id.taskCont);
        TextView date = (TextView) view.findViewById(R.id.taskDate);
        TextView heure = (TextView) view.findViewById(R.id.taskHeure);
        TextView url = (TextView) view.findViewById(R.id.taskUrl);

        dateDebut = listTask.get(position).getDateDebut();
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        dateDebutString = dateFormat.format(dateDebut);

        inti.setText(listTask.get(position).getIntitule());
        context.setText(listTask.get(position).getContext());
        date.setText(dateDebutString);
        heure.setText(listTask.get(position).getHeureDebut());
        url.setText(listTask.get(position).getUrl());

        if(listTask.get(position).getContext().equals("Maison")){
            view.setBackgroundColor(color4);
        }
        if(listTask.get(position).getContext().equals("Salle de bain")){
            view.setBackgroundColor(color2);
        }
        if(listTask.get(position).getContext().equals("Travail")){
            view.setBackgroundColor(color3);
        }
        if(listTask.get(position).getContext().equals("Salon")){
            view.setBackgroundColor(color6);
        }
        if(listTask.get(position).getContext().equals("Cuisine")){
            view.setBackgroundColor(color5);
        }
        if(listTask.get(position).getContext().equals("Chambre")){
            view.setBackgroundColor(color7);
        }
        if(listTask.get(position).getContext().equals("Jardin")){
            view.setBackgroundColor(color1);
        }
        if(listTask.get(position).getContext().equals("Magasin")){
            view.setBackgroundColor(color8);
        }
        if(listTask.get(position).getContext().equals("Sport")){
            view.setBackgroundColor(color9);
        }
        if(listTask.get(position).getContext().equals("Ecole")){
            view.setBackgroundColor(color10);
        }
        if(listTask.get(position).getContext().equals("Autre")){
            view.setBackgroundColor(color11);
        }



        return view;
    }
}
