package com.app.leonardo.bancocrud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.graphics.Color.YELLOW;
import static android.widget.Toast.makeText;


public class BancoDeDados extends AppCompatActivity {

    // INTERFACE
    CalendarView calendario;
    TextView dataSelecionada;
    Spinner spi;
    CheckBox contrato;
    Button reservar;
    private String[] local;
    ArrayAdapter<String> adapter;


    //Acesso ao Banco de Dados

        EditText dataAp;
        private String ss;
        private DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("ReservarEspaço");
        private DatabaseReference myRef = dataRef;
        private Button bto;
        private Button consultar;
        Usuario u;
        private DatabaseReference objRef ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_de_dados);
        Firebase.setAndroidContext(this);

        inicGrafica();
        acaoCom();


        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spi.getSelectedItemId() != 0 && contrato.isChecked() == true && dataSelecionada.getText() != "Selecione uma Data") {

                    Toast.makeText(BancoDeDados.this, "Valor do spi" + spi.getSelectedItem(), Toast.LENGTH_LONG).show();
                    Toast.makeText(BancoDeDados.this, "Valor da Data" + dataSelecionada.getText(), Toast.LENGTH_LONG).show();
                    Toast.makeText(BancoDeDados.this, "Valor do Contrato" + contrato.isChecked(), Toast.LENGTH_LONG).show();

                    // aqui entra o link do banco de dados Realtime Database
                   // Firebase acesso = new Firebase("https://bancodedados-2b625.firebaseio.com/ReservarEspaço");
                    Firebase acesso = new Firebase("https://bancodedados-2b625.firebaseio.com/ReservarEspaço/"+spi.getSelectedItem());


                    acesso.addValueEventListener(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            String No = String.valueOf(spi.getSelectedItem());

                            String validarData = String.valueOf(dataSelecionada.getText());

                            Toast.makeText(BancoDeDados.this, "Dentro do Acesso" + validarData, Toast.LENGTH_LONG).show();

                          //  boolean ValidarChild  = dataSnapshot.hasChild(No);
                            boolean VerData  = dataSnapshot.hasChild(validarData);

                            Toast.makeText(BancoDeDados.this, "vddd" + VerData, Toast.LENGTH_LONG).show();


                                if(VerData == true){
                                    Toast.makeText(BancoDeDados.this, "Data Já está Reservada!!", Toast.LENGTH_LONG).show();

                                    contrato.setChecked(false);

                                }else{
                                    Toast.makeText(BancoDeDados.this, "Reservando data.....", Toast.LENGTH_LONG).show();

                                    u = new Usuario();
                                    u.setNome("Ustj");
                                    dataRef.child(No).child(validarData).setValue("reservada");
                                    myRef = dataRef;
                                    myRef.child("Nome").setValue(u.getNome());

                                }



                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }else {
                    Toast.makeText(BancoDeDados.this, "Verifique a Data, Contrato ou Local", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void inicGrafica(){

        calendario = (CalendarView) findViewById(R.id.calendar1);
        dataSelecionada = (TextView) findViewById(R.id.textData);
        spi = (Spinner) findViewById(R.id.spinner01);
        contrato = (CheckBox) findViewById(R.id.checkBoxContrato);
        reservar = (Button) findViewById(R.id.btoReservar);
        local = new String[]{"Selecione o local",
                "Salão de Festa 01",
                "Salão de Festa 02",
                "Churrasqueira 01", "Churrasqueira 02",
                "Churrasqueira 03", "Churrasqueira 04"};

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,local);
        spi.setAdapter(adapter);

    }

    public void acaoCom(){
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String data = i2 + "-" + (i1 + 1) + "-" + i;
                String msn = "Data selecionada ";
                dataSelecionada.setText(data);
            }
        });

    }

}





