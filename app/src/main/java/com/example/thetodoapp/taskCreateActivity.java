package com.example.thetodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class taskCreateActivity extends AppCompatActivity {

    private EditText mIntText;
    private EditText mDesText;

    private Button mDaDebutButton;
    private Button mHeureDebutButton;
    private Button mDaFinButton;
    private Button mheureFinButton;
    private FloatingActionButton fabAddButton;
    private String intitule;
    private String dateDebut;
    private String dateFin;
    private Date dateDebutDate;
    private Date dateFinDate;
    private boolean isDaDebutButtonClicked = false;
    private boolean isHeureDebutButtonClicked = false;
    private boolean isDaFinButtonClicked = false;
    private boolean isHeureFinButtonClicked = false;

    private String des;
    private String context;
    private DatePickerDialog pickerDaDebut;
    private DatePickerDialog pickerDaFin;
    private TimePickerDialog pickerHeureDebut;
    private TimePickerDialog pickerHeureFin;
    private String heureDebut;
    private String heureFin;

    private Spinner spinner;
    final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        fabAddButton = (FloatingActionButton) findViewById(R.id.fabCreate);
        mDaDebutButton = (Button) findViewById(R.id.daDebutButton);
        mHeureDebutButton = (Button) findViewById(R.id.heureDebutButton);
        mDaFinButton = (Button) findViewById(R.id.daFinButton);
        mheureFinButton = (Button) findViewById(R.id.heureFinButton);
        spinner = (Spinner) findViewById(R.id.spinner);

        String[] spinContext = {"Choisissez un context","Cuisine","Salon","Salle de bain","Chambre",
        "Maison","Jardin","Magasin","Travail","Sport","Ecole","Autre"};
        ArrayAdapter<String> contextAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinContext);
        contextAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(contextAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                context = String.valueOf(spinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDaDebutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDaDebutButtonClicked = true;
                final Calendar clrd = Calendar.getInstance();
                int day = clrd.get(Calendar.DAY_OF_MONTH);
                final int month = clrd.get(Calendar.MONTH);
                int year = clrd.get(Calendar.YEAR);
                //le date picker
                pickerDaDebut = new DatePickerDialog(taskCreateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                clrd.set(Calendar.YEAR,year);
                                clrd.set(Calendar.MONTH,month);
                                clrd.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                dateDebutDate = clrd.getTime();
                                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                                dateDebut = dateFormat.format(dateDebutDate);
                                mDaDebutButton.setText(dateDebut);

                            }
                        },year,month,day);
                pickerDaDebut.show();
            }
        });


       mDaFinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDaFinButtonClicked = true;
                final Calendar clrd = Calendar.getInstance();
                int day = clrd.get(Calendar.DAY_OF_MONTH);
                final int month = clrd.get(Calendar.MONTH);
                int year = clrd.get(Calendar.YEAR);
                //le date picker
                pickerDaFin = new DatePickerDialog(taskCreateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                clrd.set(Calendar.YEAR,year);
                                clrd.set(Calendar.MONTH,month);
                                clrd.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                dateFinDate = clrd.getTime();
                                if(dateDebutDate.before(dateFinDate)){
                                    DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                                    dateFin = dateFormat.format(dateFinDate);
                                    mDaFinButton.setText(dateFin);
                                }else {
                                    Toast.makeText(taskCreateActivity.this,"La date de fin est inférieur à la date de début",Toast.LENGTH_LONG).show();
                                }


                            }
                        },year,month,day);
                pickerDaFin.show();
            }
        });

        mHeureDebutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeureDebutButtonClicked = true;
                final Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);
                //le time picker
                pickerHeureDebut = new TimePickerDialog(taskCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heureDebut = hourOfDay + " : " + minute;
                        mHeureDebutButton.setText(heureDebut);
                    }
                },hour,minute,true);
                pickerHeureDebut.show();
            }
        });


        mheureFinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeureFinButtonClicked = true;
                final Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);
                //le time picker
                pickerHeureFin = new TimePickerDialog(taskCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        heureFin = hourOfDay + " : " + minute;
                        mheureFinButton.setText(heureFin);
                    }
                },hour,minute,true);
                pickerHeureFin.show();
            }
        });


        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    @Override
    public void finish(){
        mIntText = (EditText) findViewById(R.id.intEdit);
        mDesText = (EditText) findViewById(R.id.desEdit);

        intitule = mIntText.getText().toString();
        des = mDesText.getText().toString();

        if(!isDaFinButtonClicked){
            dateFin = "";
        }
        if(!isHeureFinButtonClicked){
            heureFin = "";
        }


        if(intitule.matches("")|| des.matches("") || context.equals("") || !isDaDebutButtonClicked || !isHeureDebutButtonClicked){
            Toast.makeText(this,"Toutes les informations doivent être remplies à part la date et l'heure de fin",Toast.LENGTH_LONG).show();
        }else{
            Intent returnIntent = new Intent();
            returnIntent.putExtra("task_inti",intitule);
            returnIntent.putExtra("task_des",des);
            returnIntent.putExtra("task_cont",context);
            returnIntent.putExtra("task_date_debut",dateDebut);
            returnIntent.putExtra("task_date_fin",dateFin);
            returnIntent.putExtra("task_heure_debut",heureDebut);
            returnIntent.putExtra("task_heure_fin",heureFin);
            setResult(RESULT_OK, returnIntent);
            super.finish();
        }

    }

}
