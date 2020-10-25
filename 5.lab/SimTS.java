import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class SimTS {
	String [] stanja, ulazniZn, trakaZn, prihvatljiva;
	String prazna, traka, pocetno;
	Integer pocetniPolGlave;
	ArrayList<String> znakoviNaTraci = new ArrayList<> ();
	TreeMap<String,String> prijelazi = new TreeMap <> ();
	
	public void setStanja (String [] stanja) {
		this.stanja = stanja;
	}
	public String [] getStanja () {
		return stanja;
	}
	public void setUlazniZn (String [] ulazniZn) {
		this.ulazniZn = ulazniZn;
	}
	public String [] getUlazniZn () {
		return ulazniZn;
	}
	public void setTrakaZn (String [] trakaZn) {
		this.trakaZn = trakaZn;
	}
	public String [] getTrakaZn () {
		return trakaZn;
	}
	public void setPrazna (String prazna) {
		this.prazna = prazna;
	}
	public String getPrazna () {
		return prazna;
	}
	public void setTraka (String traka) {
		this.traka = traka;
	}
	public String getTraka () {
		return traka;
	}
	public void setPrihvatljiva (String [] prihvatljiva) {
		this.prihvatljiva = prihvatljiva;
	}
	public String [] getPrihvatljiva () {
		return prihvatljiva;
	}
	public void setPocetno (String pocetno) {
		this.pocetno = pocetno;
	}
	public String getPocetno () {
		return pocetno;
	}
	public void setPocetniPolGlave (Integer pocetniPolGlave) {
		this.pocetniPolGlave = pocetniPolGlave;
	}
	public Integer getPocetniPolGlave () {
		return pocetniPolGlave;
	}
	public void setZnakoviNaTraci (ArrayList<String> znakoviNaTraci) {
		this.znakoviNaTraci = znakoviNaTraci;
	}
	public ArrayList<String> getZnakoviNaTraci () {
		return znakoviNaTraci;
	}
	public void setPrijelazi (TreeMap<String,String> prijelazi) {
		this.prijelazi = prijelazi;
	}
	public TreeMap<String,String> getPrijelazi () {
		return prijelazi;
	}
	public void dodajNaTraku(String jedno) {
		this.znakoviNaTraci.add(jedno);
	}
	public void dodajPrijelaz (String unos) {
		String [] pom = unos.split("->");
		this.prijelazi.put(pom[0], pom[1]);
	}
	
	public static void main (String [] args) {
		SimTS simTS=new SimTS();
		String pom1=new String ();
		try (BufferedReader buf=new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream("test.in")),"UTF-8"))) {
				//System.in))) {
			simTS.setStanja(buf.readLine().split(","));
			simTS.setUlazniZn(buf.readLine().split(","));
			simTS.setTrakaZn(buf.readLine().split(","));
			simTS.setPrazna(buf.readLine());
			simTS.setTraka(buf.readLine());
			for (char jedno : simTS.getTraka().toCharArray()) {
				simTS.dodajNaTraku(String.valueOf(jedno));
			}
			simTS.setPrihvatljiva(buf.readLine().split(","));
			simTS.setPocetno(buf.readLine());
			simTS.setPocetniPolGlave(Integer.parseInt(buf.readLine()));
			while ((pom1=buf.readLine())!=null) {
				simTS.dodajPrijelaz(pom1);
			}
		} catch (IOException exc) {}
		simTS.ispis();
	}
	
	public void ispis () {
		String ispis = new String();
		String traka = new String ();
		String [] pomocni = new String [3];
		int pom = 0;
		ArrayList<String> znakoviNaTraci = this.getZnakoviNaTraci();
		TreeMap<String,String> prijelazi = this.getPrijelazi();
		
		String trenutnoStanje = this.getPocetno();
		Integer glavaTrenutno = this.getPocetniPolGlave();
		String lijevo = trenutnoStanje + "," + znakoviNaTraci.get(glavaTrenutno);
		
		//obavljanje prijelaza
		while (prijelazi.containsKey(lijevo)) {
			pomocni = prijelazi.get(lijevo).split(",");
			if (glavaTrenutno.equals(0) && pomocni[2].equals("L") || glavaTrenutno.equals(69) && pomocni[2].equals("R")) break;
			trenutnoStanje = pomocni[0];
			znakoviNaTraci.set(glavaTrenutno, pomocni[1]);
			if (pomocni[2].equals("R"))
				glavaTrenutno++;
			else
				glavaTrenutno--;
			lijevo = trenutnoStanje + "," + znakoviNaTraci.get(glavaTrenutno);	
		}
		//ispis dobivenog
		traka = "";
		for (String jedanZn : znakoviNaTraci) traka += jedanZn;
		this.setTraka(traka);
		ispis += trenutnoStanje + "|" + glavaTrenutno + "|" + traka + "|";
		for (String jedno : this.getPrihvatljiva()) {
			if (jedno.equals(trenutnoStanje)) {
				ispis += "1";
				pom = 1;
				break;
			}
		}
		if (pom == 0) ispis += "0";
		System.out.println(ispis);
	}
}
