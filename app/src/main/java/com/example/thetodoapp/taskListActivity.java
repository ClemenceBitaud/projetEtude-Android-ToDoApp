package com.example.thetodoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class taskListActivity extends AppCompatActivity {

    private String intitule;
    private String context;
    private String des;
    private String heureDebut;
    private String heureFin;
    private String dateDebutString;
    private String dateFinString;
    private Date dateDebut;
    private Date dateFin;
    private String url;
    private Task task;
    final int REQUEST_CODE_ADD = 1;
    final int REQUEST_CODE_MODIF = 2;
    private ArrayList<Task> taskArrayList;
    private ListView list;
    private Adapter adapter;
    private final String SHARED_PREFS="task";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        //Recharge les tâches à chaque ouverture de l'application
        loadTask();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskActivity = new Intent(taskListActivity.this, taskCreateActivity.class);
                startActivityForResult(taskActivity,REQUEST_CODE_ADD);
            }
        });

        taskArrayList = new ArrayList<>();
        adapter = new Adapter(this,taskArrayList);
        list = (ListView) findViewById(R.id.taskList);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Ajout d'une vibration pour le click long
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    v.vibrate(VibrationEffect.createOneShot(100,VibrationEffect.DEFAULT_AMPLITUDE));
                }else {
                    v.vibrate(100);
                }

                //Boîte de confirmation de la suppression
                AlertDialog.Builder builder = new AlertDialog.Builder(taskListActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmation");
                builder.setMessage("Etes-vous sûr de vouloir supprimer cette tâche ?");
                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskArrayList.remove(adapter.getItem(position));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(taskListActivity.this,"Tâche supprimée",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){
        super.onActivityResult(requestCode,resultCode,data);


        //Retour des informations pour la création
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD){
            intitule = data.getExtras().getString("task_inti");
            context = data.getExtras().getString("task_cont");
            heureDebut = data.getExtras().getString("task_heure_debut");
            heureFin = data.getExtras().getString("task_heure_fin");
            dateDebutString = data.getExtras().getString("task_date_debut");
            dateFinString = data.getExtras().getString("task_date_fin");
            des = data.getExtras().getString("task_des");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");

            try{
                dateDebut = simpleDateFormat.parse(dateDebutString);
                dateFin = simpleDateFormat.parse(dateFinString);

            }catch (ParseException e){
                e.printStackTrace();
            }

            Toast.makeText(this,"Appuyer longuement sur la tâche pour la supprimer",Toast.LENGTH_LONG).show();

            task = new Task(intitule,des,heureDebut,heureFin,dateDebut,dateFin,context,"Il n'y a pas d'URL pour le moment");
            taskArrayList.add(task);
            adapter.notifyDataSetChanged();
            saveTask();

        }

        //Retour des informations pour la modification
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_MODIF){
            intitule = data.getExtras().getString("modif_inti");
            context = data.getExtras().getString("modif_cont");
            des = data.getExtras().getString("modif_des");
            dateDebutString = data.getExtras().getString("modif_date_debut");
            heureDebut = data.getExtras().getString("modif_heure_debut");
            dateFinString = data.getExtras().getString("modif_date_fin");
            heureFin = data.getExtras().getString("modif_heure_fin");
            url = data.getExtras().getString("modif_url");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");

            try{
                dateDebut = simpleDateFormat.parse(dateDebutString);
                dateFin = simpleDateFormat.parse(dateFinString);
            }catch (ParseException e){
                e.printStackTrace();
            }

            task.setIntitule(intitule);
            task.setContext(context);
            task.setDescription(des);
            task.setDateDebut(dateDebut);
            task.setHeureDebut(heureDebut);
            task.setDateFin(dateFin);
            task.setHeureFin(heureFin);
            task.setUrl(url);
            adapter.notifyDataSetChanged();
            saveTask();
        }

        //Envoi des informations pour le détails de la tâche
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent detailActivity = new Intent(taskListActivity.this,taskDesActivity.class);
                    detailActivity.putExtra("detail_inti",intitule);
                    detailActivity.putExtra("detail_cont",context);
                    detailActivity.putExtra("detail_heure_debut",heureDebut);
                    detailActivity.putExtra("detail_heure_fin",heureFin);
                    detailActivity.putExtra("detail_date_debut",dateDebutString);
                    detailActivity.putExtra("detail_date_fin",dateFinString);
                    detailActivity.putExtra("detail_des",des);
                    detailActivity.putExtra("detail_url",url);
                    startActivityForResult(detailActivity, REQUEST_CODE_MODIF);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.v("onCreateOptionMenu","onCreateOptionMenu");

        //Tri par dates
        if (id == R.id.menu_date) {
            Collections.sort(taskArrayList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    Log.v("compareTo","compareTo");
                    Date date1 = o1.getDateDebut();
                    Date date2 = o2.getDateDebut();
                    int resultD = date1.compareTo(date2);
                    return resultD;
                }
            });
            adapter.notifyDataSetChanged();
            return true;
        }

        //Tri par titre
        if(id == R.id.menu_titre){
            Collections.sort(taskArrayList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    String titre1 = o1.getIntitule();
                    String titre2 = o2.getIntitule();
                    int resultT = titre1.compareTo(titre2);
                    return resultT;
                }
            });
            adapter.notifyDataSetChanged();
            return true;
        }

        //Tri par context
        if(id == R.id.menu_context){
            Collections.sort(taskArrayList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    String context1 = o1.getContext();
                    String context2 = o2.getContext();
                    int resultC = context1.compareTo(context2);
                    return resultC;
                }
            });
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Permet de sauvegarder les tâches*/
    public void saveTask() {
        if (null == taskArrayList) {
            taskArrayList = new ArrayList<Task>();
        }

        // Sauvegarde la liste des tâches dans les préférences
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("task", ObjectSerializer.serialize(taskArrayList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    /**
     * Permet de recharger les tâches enregistrées*/
    private void loadTask(){
        if (null == taskArrayList) {
            taskArrayList = new ArrayList<Task>();
        }
        // Récupère les tâches dans les préférences
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        try {
            taskArrayList = (ArrayList<Task>) ObjectSerializer.deserialize(prefs.getString("task", ObjectSerializer.serialize(new ArrayList<Task>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
