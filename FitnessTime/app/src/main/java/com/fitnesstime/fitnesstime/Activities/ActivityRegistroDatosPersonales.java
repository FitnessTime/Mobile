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
import android.widget.EditText;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityRegistroDatosPersonales extends ActivityFlujo {

    private static String MENSAJE_TOAST = "";
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

    private void generarToast(String mensaje)
    {
        Toast toast = Toast.makeText(ActivityRegistroDatosPersonales.this, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.color.boton_loggin);
        toast.show();
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
    }

    // Inicia las funciones de los botones en el activity.
    private void iniciarBotones()
    {
        siguiente = (Button) findViewById(R.id.boton_siguiente_datos_personales);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    guardarDatos();
                    activitySigiente();
                } else {
                    generarToast(MENSAJE_TOAST);
                }
            }
        });
        if(tieneSiguiente())
            siguiente.setVisibility(View.VISIBLE);
        else
            siguiente.setVisibility(View.INVISIBLE);
    }

    // Crea el dialogo de confirmacion.
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

    private boolean validar()
    {
        String contrasenia = password.getText().toString();
        String mail = email.getText().toString();

        if(!isEmailValid(mail))
        {
            MENSAJE_TOAST = "Debe ingresar un mail valido.";
            return false;
        }
        if(contrasenia.length() < Constantes.getLongitudContrasenia())
        {
            MENSAJE_TOAST = "La contraseña debe tener al menos 6 caracteres";
            return false;
        }
        if(contieneLetrasYNumeros(contrasenia))
        {
            MENSAJE_TOAST = "La contraseña debe tener letras y numeros";
            return false;
        }
        return true;
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    private boolean contieneLetrasYNumeros(String string)
    {
        return !string.matches(".*\\d+.*") || !string.matches(".*[a-zA-Z].*");
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
