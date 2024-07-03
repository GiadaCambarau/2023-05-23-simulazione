package it.polito.tdp.baseball.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.w3c.dom.ranges.RangeException;

import it.polito.tdp.baseball.db.BaseballDAO;

public class Model {

	private Graph<People, DefaultEdge> graph;
	private Map <String, People> mappa;
	private List<People> giocatori;
	private BaseballDAO dao;
	private List<People> tuttiGiocatori;
	private List<People> soluzione;
	private double best;
	
	
	

	public Model() {
		super();
		this.graph = new SimpleGraph<People, DefaultEdge>(DefaultEdge.class);
		this.mappa = new HashMap<>();
		this.giocatori = new ArrayList<>();
		this.dao = new BaseballDAO();
		this.tuttiGiocatori = dao.readAllPlayers();
	}
	
	public List<People> getDreamTeam(int anno){
		List<People> parziale = new ArrayList<>();
		this.soluzione = new ArrayList<>();
		
		List <People>rimanenti = new ArrayList<>(this.graph.vertexSet());
		ricorsione(parziale, rimanenti, anno);
		return this.soluzione;
	}
	
	private void ricorsione(List<People> parziale, List<People> rimanenti, int anno) {
		//condizione di uscita
		//ci deve essere una lista con tutti i vertici e ad ogni giro ne tolgo uni
		//quindi quando la lista è vuota vedo se èla soluzione ottima 
		
		if (rimanenti.isEmpty()) {
			if (getSalarioTot(parziale, anno)> best) {
				soluzione = new ArrayList<>(parziale);
				this.best=  getSalarioTot(soluzione, anno);
			}
			return;
		}
		//nel resto della ricorsione devo controllare che i due giocatori non abbiano
		//giocato nello stesso team nell'anno preso in considerazione,
		//però ho questa informazione già nel grafo, perchè se hanno giocato 
		//nello stesso team nello stesse anno i due vertici hanno un arco 
		
		//se sono "vicini" non possono essere entrambi nel dt
		List<People> compagni = Graphs.neighborListOf(this.graph, rimanenti.get(0));
		compagni.add(rimanenti.get(0));
		List<People> nuoviRimanenti = new ArrayList<>(rimanenti);
		nuoviRimanenti.removeAll(compagni);
		
		//di questi solo uno ne potra essere aggiunto(forse)
		for (People p: compagni) {
			parziale.add(p);
			ricorsione(parziale, nuoviRimanenti, anno);
			parziale.remove(parziale.size()-1);
			
		}
		
	}

	public double getSalarioTot(List<People> parziale, int anno) {
		double tot =0;
		for (People p: parziale) {
			tot+= dao.getSalarioGiocatore(p, anno);
		}
		return tot;
	}

	public List<People> loadVertici(double salario, int anno) {
		for (People p : tuttiGiocatori) {
			mappa.put(p.getPlayerID(), p);
		}
		if (giocatori.isEmpty()) {
			giocatori = dao.getVertici(salario, anno, mappa);
		}
		return giocatori;
	}

	
	public void creaGrafo(int anno, double salario) {
		List<People> vertici = loadVertici(salario*1000000, anno);
		//System.out.println("Vertici: " +vertici.size());
		Graphs.addAllVertices(this.graph, vertici);
		
		List<PlayerTeam> lista = dao.getPlayerTeam(anno, mappa);
		List<PlayerTeam> filtrata = new ArrayList<>(lista);
		//per creare gli archi posso ciclare due volte la lista e verificare con un 
		//metodo che i due giocatori abbiano giocato nella stessa squadra 
		
		//ciclo la lista totale delle appaerances e se la lista dei vertici non contiene 
		//quel giocatore lo elimino
		//potrei anche invertire le liste così da ciclare su meno elementi
		for (PlayerTeam p: lista ) {
			if (!vertici.contains(p.getP())) {
				filtrata.remove(p);
			}
		}
		
		for (PlayerTeam p1: filtrata) {
			for (PlayerTeam p2: filtrata) {
				if (!p1.equals(p2) && p1.getTeamID() == p2.getTeamID()) {
					Graphs.addEdgeWithVertices(this.graph, p1.getP(), p2.getP());
				}
			}
		}
		
		//System.out.println("Archi: " +this.graph.edgeSet().size());	
		//oppure posso fare una query che mi restituisca tutti le coppie di giocatori 
		//che hanno giocato nello stesso team 
		
		
	}
	
	public void clearGraph() {
		this.graph = new SimpleGraph<People, DefaultEdge>(DefaultEdge.class);
		this.giocatori = new ArrayList<>();
	}


	public int getArchi() {
		if (this.graph== null) {
			throw new RuntimeException("Grafo non esistente");
		}
		
		return this.graph.edgeSet().size();
	}
	
	public Grado getGradoMax() {
		if (this.graph== null) {
			throw new RuntimeException("Grafo non esistente");
		}
		int gradoMax =0;
		int grado =0;
		People conMax = null;
		for (People p: this.graph.vertexSet()) {
			grado = this.graph.degreeOf(p);
			if (grado>gradoMax) {
				gradoMax = grado;
				conMax =p;
				
			}
		}
		Grado g = new Grado (conMax, gradoMax);
		return g;
		
	}
	public int getConnesse() {
		if (graph == null) {
			throw new RuntimeException("Grafo non Esistente");
		}
		ConnectivityInspector<People, DefaultEdge> ci = new ConnectivityInspector<>(this.graph);
		return ci.connectedSets().size();
	}
	
	
	
}