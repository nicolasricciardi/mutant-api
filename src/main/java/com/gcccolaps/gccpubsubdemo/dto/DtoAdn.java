package com.gcccolaps.gccpubsubdemo.dto;

import java.io.Serializable;

public class DtoAdn implements Serializable{

private String[] dna;
	
	// for deserialisation
    public DtoAdn() {} 

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
}
