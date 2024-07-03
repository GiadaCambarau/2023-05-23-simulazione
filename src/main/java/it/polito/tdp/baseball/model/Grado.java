package it.polito.tdp.baseball.model;

import java.util.Objects;

public class Grado {
	private People p;
	private int grado;
	public Grado(People p, int grado) {
		super();
		this.p = p;
		this.grado = grado;
	}
	public People getP() {
		return p;
	}
	public void setP(People p) {
		this.p = p;
	}
	public int getGrado() {
		return grado;
	}
	public void setGrado(int grado) {
		this.grado = grado;
	}
	@Override
	public int hashCode() {
		return Objects.hash(grado, p);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grado other = (Grado) obj;
		return grado == other.grado && Objects.equals(p, other.p);
	}

	
	
}
