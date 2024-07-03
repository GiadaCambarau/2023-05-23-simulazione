package it.polito.tdp.baseball.model;

import java.util.Objects;

public class PlayerTeam {
	private People p;
	private int teamID;
	
	public PlayerTeam(People p, int teamID) {
		super();
		this.p = p;
		this.teamID = teamID;
	}
	
	public People getP() {
		return p;
	}
	public void setP(People p) {
		this.p = p;
	}
	public int getTeamID() {
		return teamID;
	}
	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(p, teamID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerTeam other = (PlayerTeam) obj;
		return Objects.equals(p, other.p) && teamID == other.teamID;
	}
	
	
	
	

}
