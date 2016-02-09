package com.fitnesstime.fitnesstime.Activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Flujos.FlujoRegistro;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.R;

public class ActivityRegistroDatosPersonales extends ActivityFlujo {

    private Button siguiente;
    private EditText nombre;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_datos_personales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniciarBotones();
        iniciarEditText();
    }

    private void verificarYOcultarBotonSiguiente()
    {
        if(nombre.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
        {
            siguiente.setEnabled(false);
        }
        else
        {
            siguiente.setEnabled(true);
        }
    }

    private void iniciarEditText()
    {
        nombre = (EditText)findViewById(R.id.registro_nombre);
        password = (EditText)findViewById(R.id.registro_password);
        email = (EditText)findViewById(R.id.registro_email);
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYOcultarBotonSiguiente();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYOcultarBotonSiguiente();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYOcultarBotonSiguiente();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void iniciarBotones()
    {
        siguiente = (Button) findViewById(R.id.boton_siguiente_datos_personales);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
                activitySigiente();
            }
        });
        if(tieneSiguiente())
            siguiente.setVisibility(View.VISIBLE);
        else
            siguiente.setVisibility(View.INVISIBLE);
    }

    private void crearDialogoDeConfirmacion()
    {
        new AlertDialog.Builder(this)
                .setTitle("Registro")
                .setMessage("Desea cancelar el registro de su cuenta?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                        startActivity(new Intent(ActivityRegistroDatosPersonales.this, ActivityLoggin.class));
                    }})
                .setNegativeButton("Cancelar", null).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                crearDialogoDeConfirmacion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void guardarDatos() {
        Registro entidadRegistro = (Registro)flujo.getEntidad();
        entidadRegistro.setNombre(nombre.getText().toString());
        entidadRegistro.setPassword(password.getText().toString());
        entidadRegistro.setEmail(email.getText().toString());

    }

    @Override
    public void cargarDatos() {
        Registro entidadRegistro = (Registro)flujo.getEntidad();
        iniciarEditText();
        nombre.setText(entidadRegistro.getNombre());
        email.setText(entidadRegistro.getEmail());
        password.setText(entidadRegistro.getPassword());
    }

    @Override
    public boolean esElPrimero() {return true;}

    @Override
    public boolean tieneSiguiente() {
        return true;
    }

    @Override
    public boolean tieneAnterior() {
        return false;
    }

}
