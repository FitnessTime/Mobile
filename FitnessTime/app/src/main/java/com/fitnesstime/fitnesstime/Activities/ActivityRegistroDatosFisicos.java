package com.fitnesstime.fitnesstime.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;

public class ActivityRegistroDatosFisicos extends ActivityFlujo {

    private EditText peso;
    private Button finalizar;
    private ProgressBar spinner;
    private TextView registrando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_datos_fisicos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        desactivarSpinner();
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
        registrando = (TextView)findViewById(R.id.texto_registrando_usuario);
        registrando.setVisibility(View.INVISIBLE);
        peso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYOcultarBotonFinaizado();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Inicia las acciones de los botones del activity.
    private void iniciarBotones()
    {
        finalizar = (Button)findViewById(R.id.boton_finalizar_registro);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
                activarSpinner();
                desactivarCampos();
                Registro entidadRegistro = (Registro)flujo.getEntidad();
                new RegistroTask().execute(entidadRegistro);
            }
        });
        if(!tieneSiguiente())
            finalizar.setVisibility(View.VISIBLE);
        else
            finalizar.setVisibility(View.INVISIBLE);
    }

    private void crearToast(String mensaje)
    {
        Toast toast = Toast.makeText(ActivityRegistroDatosFisicos.this, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.color.boton_loggin);
        toast.show();
    }

    private void desactivarSpinner()
    {
        spinner = (ProgressBar) findViewById(R.id.progressBarRegistro);
        spinner.setVisibility(View.INVISIBLE);
    }

    private void activarSpinner()
    {
        spinner = (ProgressBar) findViewById(R.id.progressBarRegistro);
        spinner.setVisibility(View.VISIBLE);
    }

    // Desactiva edit text, botones, etc del activity.
    protected void desactivarCampos()
    {
        peso.setVisibility(View.INVISIBLE);
        finalizar.setVisibility(View.INVISIBLE);
        registrando.setVisibility(View.VISIBLE);
    }

    // Activa edit text, botones, etc del activity.
    protected void activarCampos()
    {
        peso.setVisibility(View.VISIBLE);
        finalizar.setVisibility(View.VISIBLE);
        registrando.setVisibility(View.INVISIBLE);
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


    private class RegistroTask extends AsyncTask<Registro,Void,Integer> {

        @Override
        protected Integer doInBackground(Registro... registros) {

            int codigo = 0;

            if(Network.isOnline(ActivityRegistroDatosFisicos.this)) {
                codigo = FitnessTimeApplication.getRegistroServicio().registrar(registros[0]);
            }
            else
            {
                codigo = Constantes.getCodigoErrorSinInternet();
            }
            return codigo;
        }

        @Override
        protected void onPostExecute(Integer codigo) {
            super.onPostExecute(codigo);
            desactivarSpinner();
            activarCampos();
            if(codigo != Constantes.getCodigoErrorSinInternet())
            {
                if(codigo == Constantes.getCodigoOk()) {
                    crearToast("Usuario creado con éxito.");
                    finish();
                    startActivity(new Intent(ActivityRegistroDatosFisicos.this, ActivityLoggin.class));
                }
                else{
                    crearToast("Error del servidor, intente nuevamente.");
                }
            }
            else {
                crearToast(ResponseHelper.getMensajeDelResponse(codigo));
            }
        }
    }
}
