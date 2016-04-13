package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Modelo.Rutina;
import com.fitnesstime.fitnesstime.R;

public class ActivityPrincipalRutina extends ActivityFlujo {

    private EditText aclaracion;
    private EditText descripcion;
    private CheckBox esRutinaDeCarga;
    private Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_rutina);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rutinas");

        iniciarBotones();
        iniciarEditText();
    }

    @Override
    public void guardarDatos() {
        Rutina entidadRutina= (Rutina)flujo.getEntidad();
        entidadRutina.setAclaracion(aclaracion.getText().toString());
        entidadRutina.setDescripcion(descripcion.getText().toString());
        entidadRutina.setEsDeCarga(esRutinaDeCarga.isChecked()
        );
    }

    @Override
    public void cargarDatos() {
        Rutina entidadRutina = (Rutina)flujo.getEntidad();
        iniciarEditText();
        aclaracion.setText(entidadRutina.getAclaracion());
        descripcion.setText(entidadRutina.getDescripcion());
        esRutinaDeCarga.setChecked(entidadRutina.getEsDeCarga());
    }

    @Override
    public boolean tieneSiguiente() {
        return true;
    }

    @Override
    public boolean tieneAnterior() {
        return false;
    }

    @Override
    public boolean esElPrimero()
    {
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(esElPrimero())
        {
            crearDialogoDeConfirmacion();
        }
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

    private void iniciarFlujoPrincipal()
    {
        setGuardaDatos(false);
        FlujoPrincipal flujo = new FlujoPrincipal();
        flujo.setPosicionFragment(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityPrincipalRutina.this, ActivityPrincipal.class));
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Desea cancelar la creación de la rutina?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();
    }

    // Inicia las acciones de los botones del activity.
    private void iniciarBotones()
    {
        siguiente = (Button)findViewById(R.id.boton_siguiente_principal_rutina);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
                activitySigiente();
            }
        });
    }

    // Inicia los edit text del ativity
    private void iniciarEditText()
    {
        aclaracion = (EditText)findViewById(R.id.aclaracion_rutina);
        descripcion = (EditText)findViewById(R.id.descripcion_rutina);
        descripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYOcultarBotonSiguiente();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        aclaracion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificarYOcultarBotonSiguiente();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        esRutinaDeCarga = (CheckBox)findViewById(R.id.es_rutina_de_carga);
    }

    private void verificarYOcultarBotonSiguiente()
    {
        if(aclaracion.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty())
        {
            siguiente.setEnabled(false);
        }
        else
        {
            siguiente.setEnabled(true);
        }
    }
}
