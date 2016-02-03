package com.fitnesstime.fitnesstime.Activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Flujos.FlujoLoggin;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.R;

public class ActivityLoggin extends ActivityFlujo {

    private EditText email;
    private EditText password;
    private Button iniciarSesion;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        setFlujo(new FlujoLoggin());

        email = (EditText) findViewById(R.id.edit_text_email);
        password = (EditText) findViewById(R.id.edit_text_password);
        iniciarSesion = (Button) findViewById(R.id.boton_loggin);
        spinner = (ProgressBar) findViewById(R.id.progressBarLoggin);
        spinner.setVisibility(View.INVISIBLE);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                String[] params = {email.getText().toString(), password.getText().toString()};
                new LogginTask().execute(params);
            }
        });
    }

    @Override
    public void guardarDatos() {

    }

    @Override
    public void cargarDatos() {

    }

    @Override
    public boolean esElPrimero() {
        return true;
    }

    @Override
    public boolean tieneSiguiente() {
        return false;
    }

    @Override
    public boolean tieneAnterior() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LogginTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            SecurityToken securityToken = FitnessTimeApplication.getLogginServicio().autenticar(strings[0],strings[1]);
            return securityToken.getEmailUsuario();
        }
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            Toast.makeText(ActivityLoggin.this, "Usuario loggeado: " + string, Toast.LENGTH_SHORT).show();
            spinner.setVisibility(View.INVISIBLE);
        }
    }
}
