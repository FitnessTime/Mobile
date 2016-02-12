package com.fitnesstime.fitnesstime.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;

import java.util.Date;

public class ActivityRegistroDatosFisicos extends ActivityFlujo {

    private EditText peso;
    private Button finalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_datos_fisicos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iniciarBotones();
        iniciarEditText();
    }

    private void verificarYOcultarBotonFinaizado()
    {
        if(peso.getText().toString().isEmpty())
        {
            finalizar.setEnabled(false);
        }
        else
        {
            finalizar.setEnabled(true);
        }
    }

    private void iniciarEditText()
    {
        peso = (EditText)findViewById(R.id.registro_peso);

        peso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void iniciarBotones()
    {
        finalizar = (Button)findViewById(R.id.boton_finalizar_registro);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
                new RegistroTask().execute(parametrosDeRegistro());
            }
        });
        if(!tieneSiguiente())
            finalizar.setVisibility(View.VISIBLE);
        else
            finalizar.setVisibility(View.INVISIBLE);
    }

    // Retorna los parametros de registro que estan en la entidad.
    private String[] parametrosDeRegistro()
    {
        Registro registro = (Registro)flujo.getEntidad();
        String[] parametros = {registro.getEmail(), registro.getPassword(), registro.getNombre(), registro.getFecha(), String.valueOf(registro.getPeso())};
        return parametros;
    }

    private void crearToast(String mensaje)
    {
        Toast toast = Toast.makeText(ActivityRegistroDatosFisicos.this, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.color.boton_loggin);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activityAnterior();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void guardarDatos() {
        Registro entidadRegistro = (Registro)flujo.getEntidad();
        peso = (EditText)findViewById(R.id.registro_peso);
        entidadRegistro.setPeso(0);
        if(!peso.getText().toString().equals(""))
            entidadRegistro.setPeso(Integer.parseInt(peso.getText().toString()));
    }

    @Override
    public void cargarDatos() {
        Registro entidadRegistro = (Registro)flujo.getEntidad();
        iniciarEditText();
        if(entidadRegistro.getPeso()==0)
            peso.setText("");
        else
            peso.setText(String.valueOf(entidadRegistro.getPeso()));
    }

    @Override
    public boolean tieneSiguiente() {
        return false;
    }

    @Override
    public boolean tieneAnterior() {
        return true;
    }


    private class RegistroTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String mensaje = "";

            if(Network.isOnline(ActivityRegistroDatosFisicos.this)) {
                mensaje = FitnessTimeApplication.getRegistroServicio().registrar(strings[0], strings[1], strings[2], strings[3], Integer.parseInt(strings[4]));
            }
            else
            {
                mensaje = "No tiene conexión a internet.";
            }
            return mensaje;
        }
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            if(Network.isOnline(ActivityRegistroDatosFisicos.this))
            {
                crearToast(string);
                finish();
                startActivity(new Intent(ActivityRegistroDatosFisicos.this, ActivityLoggin.class));
            }
            else {
                crearToast(string);
            }
        }
    }
}
