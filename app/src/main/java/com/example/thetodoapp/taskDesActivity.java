package com.example.thetodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class taskDesActivity extends AppCompatActivity {

    private String intitule;
    private String context;
    private String des;
    private String dateDebut;
    private String dateFin;
    private String heureDebut;
    private String heureFin;
    private String url;
    private EditText intiText;
    private EditText contText;
    private EditText desText;
    private Date dateDebutDate;
    private Date dateFinDate;
    private Button dateDebutButton;
    private Button heureDebutButton;
    private Button dateFinButton;
    private Button heureFinButton;
    private EditText urlText;
    private TextView urlView;
    private FloatingActionButton fabModifButton;
    private FloatingActionButton fabValidButton;
    private Button goButton;
    private DatePickerDialog pickerDaDebut;
    private DatePickerDialog pickerDaFin;
    private TimePickerDialog pickerHeureDebut;
    private TimePickerDialog pickerHeureFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_des);

        intiText = (EditText) findViewById(R.id.detIntiEdit);
        contText = (EditText) findViewById(R.id.detContEdit);
        desText = (EditText) findViewById(R.id.detDesEdit);
        dateDebutButton = (Button) findViewById(R.id.newDateDebutButton);
        heureDebutButton = (Button) findViewById(R.id.newHeureDebutButton);
        dateFinButton = (Button) findViewById(R.id.newDateFinButton);
        heureFinButton = (Button) findViewById(R.id.newHeureFinButton);
        fabModifButton = (FloatingActionButton) findViewById(R.id.fabModif);
        fabValidButton = (FloatingActionButton) findViewById(R.id.fabValid);
        goButton = (Button) findViewById(R.id.goButton);
        urlText = (EditText) findViewById(R.id.urlEdit);
        urlView = (TextView) findViewById(R.id.urlText);

        Bundle bundle = getIntent().getExtras();

        intitule = bundle.getString("detail_inti");
        intiText.setText(intitule);
        context = bundle.getString("detail_cont");
        contText.setText(context);
        des = bundle.getString("detail_des");
        desText.setText(des);
        dateDebut = bundle.getString("detail_date_debut");
        dateDebutButton.setText(dateDebut);

        heureDebut = bundle.getString("detail_heure_debut");
        heureDebutButton.setText(heureDebut);

        dateFin = bundle.getString("detail_date_fin");
        dateFinButton.setText(dateFin);

        heureFin = bundle.getString("detail_heure_fin");
        heureFinButton.setText(heureFin);

        url = bundle.getString("detail_url");
        urlText.setText(url);

        fabModifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intiText.setEnabled(true);
                contText.setEnabled(true);
                desText.setEnabled(true);
                dateDebutButton.setEnabled(true);
                heureDebutButton.setEnabled(true);
                dateFinButton.setEnabled(true);
                heureFinButton.setEnabled(true);
                urlText.setVisibility(View.VISIBLE);
                urlView.setVisibility(View.VISIBLE);
                goButton.setVisibility(View.VISIBLE);
            }
        });

        dateDebutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar clrd = Calendar.getInstance();
                int day = clrd.get(Calendar.DAY_OF_MONTH);
                final int month = clrd.get(Calendar.MONTH);
                int year = clrd.get(Calendar.YEAR);
                //the date picker
                pickerDaDebut = new DatePickerDialog(taskDesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                clrd.set(Calendar.YEAR,year);
                                clrd.set(Calendar.MONTH,month);
                                clrd.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                dateDebutDate = clrd.getTime();
                                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                                dateDebut = dateFormat.format(dateDebutDate);
                                String dateDebutString = DateFormat.getDateInstance(DateFormat.FULL).format(clrd.getTime());
                                dateDebutButton.setText(dateDebutString);
                            }
                        },year,month,day);
                pickerDaDebut.show();
            }
        });

        heureDebutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);
                pickerHeureDebut = new TimePickerDialog(taskDesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heureDebutButton.setText(hourOfDay + ":" + minute);
                        heureDebut = hourOfDay + " : " + minute;

                    }
                },hour,minute,true);
                pickerHeureDebut.show();
            }
        });

        dateFinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar clrd = Calendar.getInstance();
                int day = clrd.get(Calendar.DAY_OF_MONTH);
                final int month = clrd.get(Calendar.MONTH);
                int year = clrd.get(Calendar.YEAR);
                //the date picker
                pickerDaFin = new DatePickerDialog(taskDesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                clrd.set(Calendar.YEAR,year);
                                clrd.set(Calendar.MONTH,month);
                                clrd.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                dateFinDate = clrd.getTime();
                                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                                dateFin = dateFormat.format(dateFinDate);
                                dateFinButton.setText(dateFin);
                            }
                        },year,month,day);
                pickerDaFin.show();
            }
        });

        heureFinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);
                pickerHeureFin = new TimePickerDialog(taskDesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heureFinButton.setText(hourOfDay + ":" + minute);
                        heureFin = hourOfDay + " : " + minute;

                    }
                },hour,minute,true);
                pickerHeureFin.show();
            }
        });

        fabValidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent pour la webView
                Intent urlActivity = new Intent(taskDesActivity.this,taskWebActivity.class);
                urlActivity.putExtra("url",urlText.getText().toString());
                startActivity(urlActivity);
            }
        });
    }

    @Override
    public void finish(){

        //Récupération des valeur des EditText
        intitule = intiText.getText().toString();
        context = contText.getText().toString();
        des = desText.getText().toString();
        url = urlText.getText().toString();

        //Intent pour renvoyer les informations à l'intent de départ
        Intent returnIntent = new Intent();
        returnIntent.putExtra("modif_inti",intitule);
        returnIntent.putExtra("modif_des",des);
        returnIntent.putExtra("modif_cont",context);
        returnIntent.putExtra("modif_date_debut",dateDebut);
        returnIntent.putExtra("modif_date_fin",dateFin);
        returnIntent.putExtra("modif_heure_debut",heureDebut);
        returnIntent.putExtra("modif_heure_fin",heureFin);
        returnIntent.putExtra("modif_url",url);
        setResult(RESULT_OK, returnIntent);
        super.finish();
    }
}
