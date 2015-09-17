package com.fitnesstime.fitnesstime.dominio;

import java.util.List;

public class Ejercicio {
	private String nombre;
	private int series;
	private int repeticiones;
	private List<Record> records;
	
	public void registrarRecord(Record unRecord){
		this.records.add(unRecord);
	}
	
	public List<Record> getRecords(){
		return(this.records);
	}

    public Record getLastRecord()
    {
        for(Record r : this.records)
        {

        }
        return null;
    }

	public String getNombre() {
		return nombre;
	}

	public int getSeries() {
		return series;
	}

	public int getRepeticiones() {
		return repeticiones;
	}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public void setPeso(int peso) {
        this.records = records;
    }
}
