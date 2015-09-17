package com.fitnesstime.fitnesstime.dominio;

import java.sql.Date;

public class Record {

	private Date fecha;
	private int kilaje;
	
	public boolean esMayor(Record unRecord){
		return(this.getKilaje() > unRecord.getKilaje());
	}
	
	public boolean esMenor(Record unRecord){
		return(this.getKilaje() < unRecord.getKilaje());
	}
	
	public Date getFecha() {
		return fecha;
	}

	public int getKilaje(){
		return(this.kilaje);
	}
}
