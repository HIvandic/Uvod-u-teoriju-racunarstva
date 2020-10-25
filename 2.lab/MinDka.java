import java.io.*;
import java.util.HashMap;
import java.util.*;

public class MinDka {
	String [] skupStanja;
	String [] abeceda;
	String [] prihvatljivaStanja;
	String pocetnoStanje;
	HashMap<String, String> prijelazi=new HashMap<>();
	public void setSkupStanja(String[] skupStanja) {
		this.skupStanja=skupStanja;
	}
	public String[] getSkupStanja() {
		return skupStanja;
	}
	public void setAbeceda(String[] abeceda) {
		this.abeceda=abeceda;
	}
	public String[] getAbeceda() {
		return abeceda;
	}
	public void setPrihvatljivaStanja(String[] prihvatljivaStanja) {
		this.prihvatljivaStanja=prihvatljivaStanja;
	}
	public String[] getPrihvatljivaStanja() {
		return prihvatljivaStanja;
	}
	public void setPocetnoStanje(String pocetnoStanje) {
		this.pocetnoStanje=pocetnoStanje;
	}
	public String getPocetnoStanje() {
		return pocetnoStanje;
	}
	public HashMap<String,String> getPrijelazi() {
		return this.prijelazi;
	}
	public static void main(String[] args) {
		MinDka minDka=new MinDka();
		minDka.popuni();
		minDka.ukloniNedohvatljiva();
		minDka.ukloniIstovjetna();
		minDka.ispis();
	}
	public void popuni() {
		String pom;
		try (BufferedReader buf=new BufferedReader(new InputStreamReader(
				//new BufferedInputStream(new FileInputStream("t.ul")),"UTF-8"))) {
				 System.in))) {
			this.setSkupStanja(buf.readLine().split(","));
			this.setAbeceda(buf.readLine().split(","));
			this.setPrihvatljivaStanja(buf.readLine().split(","));
			this.setPocetnoStanje(buf.readLine());
			while ((pom=buf.readLine())!=null) this.puniMapu(pom);
		} catch (IOException exc) {}
	}
	public void puniMapu(String pom) {
		String[] red=new String[2];
		red=pom.split("->");
		this.getPrijelazi().put(red[0], red[1]);
	}
	public void ukloniNedohvatljiva() {
		int indeks=0, i, j, velicinaAbecede=this.getAbeceda().length;
		String pomocni;
		String[] dohvatljiva=new String[this.getSkupStanja().length];
		dohvatljiva[0]=this.getPocetnoStanje();
		//stavljam u polje dohvatljiva ona stanja do kojih postoji put
		for (i=0; i<(indeks+1);++i) {
			for (j=0; j<velicinaAbecede; ++j) {
				pomocni=this.getPrijelazi().get(dohvatljiva[i]+","+this.getAbeceda()[j]);
				if (!contains(dohvatljiva,pomocni)) {
					++indeks;
					dohvatljiva[indeks]=pomocni;
				}
			}
		}
		//micem nedohvatljiva stanja
		for (String jedan : this.getSkupStanja()) {
			if (!contains(dohvatljiva,jedan)) {
				obrisi(this.getSkupStanja(),jedan);
				obrisi(this.getPrihvatljivaStanja(),jedan);
				obrisi(this.getPrijelazi(),jedan);
			}
		}
		//micem null elemente
		this.setSkupStanja(uredi(this.getSkupStanja()));
		this.setPrihvatljivaStanja(uredi(this.getPrihvatljivaStanja()));
	}
	public static boolean contains (String[] dohvatljiva, String pomocni) {
		int i, iteracije=dohvatljiva.length;
		for (i=0; i<iteracije; ++i) {
			if (dohvatljiva[i]!=null && dohvatljiva[i].equals(pomocni)) return true;
		}
		return false;
	}
	public void obrisi (String [] polje,String jedan) {
		int i,iteracije=polje.length;
		for (i=0; i<iteracije; ++i) {
			if (polje[i]!=null && polje[i].equals(jedan)) { 
				polje[i]=null;
				return;
			}
		}
	}
//	public void uredi (String [] polje) {
//		int i, j=0, iteracije=polje.length;
//		for (i=0; i<iteracije; ++i) {
//			if (polje[i]!=null) polje[j++]=polje[i];
//		}
//		
//	}
	public String[] uredi(String[] a) {
		   ArrayList<String> removedNull = new ArrayList<String>();
		   for (String str : a)
		      if (str != null)
		         removedNull.add(str);
		   return removedNull.toArray(new String[0]);
		}
	public void obrisi (HashMap<String,String> prijelazi, String jedan) {
		for (String znak : this.getAbeceda()) {
			prijelazi.remove(jedan+","+znak);
		}
	}
	public void ukloniIstovjetna() {
		String [] neprihvatljiva= new String[this.getSkupStanja().length-this.getPrihvatljivaStanja().length];
		int indeks=0, i, j, velicina=this.getSkupStanja().length-1;
		int[] polje=new int[velicina*velicina];
		HashMap<String,HashSet<String>> liste=new HashMap<>();
		String prvi,drugi,novo1,novo2;
		String[] pom=new String[2];
		HashSet<String> pomocni=new HashSet<>();
		
		polje=init(polje, velicina);
		for (String jedan : this.getSkupStanja()) {
			if (!contains(getPrihvatljivaStanja(), ""))
			if (!contains(this.getPrihvatljivaStanja(),jedan)) neprihvatljiva[indeks++]=jedan;
		}
		for(i=0; i<this.getSkupStanja().length-1; ++i) {
			prvi=this.getSkupStanja()[i+1];
			for(j=0; j<this.getSkupStanja().length-1; ++j) {
				drugi=this.getSkupStanja()[j];
				if (polje[i*velicina+j]!=2) {
					if (contains(neprihvatljiva,prvi)&& !contains(neprihvatljiva,drugi) || !contains(neprihvatljiva,prvi) && contains(neprihvatljiva,drugi)) {
						polje[i*velicina+j]=1;
					}
				}
			}
		}
		for(i=0; i<this.getSkupStanja().length-1; ++i) {
			prvi=this.getSkupStanja()[i+1];
			for(j=0; j<this.getSkupStanja().length-1; ++j) {
				drugi=this.getSkupStanja()[j];
				if (polje[i*velicina+j]==0) {
					for (String ulaz: this.getAbeceda()) {
						novo1=this.getPrijelazi().get(prvi+","+ulaz);
						novo2=this.getPrijelazi().get(drugi+","+ulaz);
						if (polje[getIndeks(novo1,novo2,velicina, polje)]==1) {
							polje[i*velicina+j]=1;
							pomocni=liste.get(prvi+','+drugi);
							if(pomocni != null) {
								Iterator<String> iterator=pomocni.iterator();
								while (iterator.hasNext()) {
									pom=iterator.next().split(",");
									novo1=pom[0];
									novo2=pom[1];
									polje[getIndeks(novo1,novo2, velicina, polje)]=1;
								}
							}
							break;
						} else if(polje[getIndeks(novo1,novo2,velicina, polje)]!=2) {
							pomocni=liste.get(novo1+','+novo2);
							if(pomocni == null) pomocni = new HashSet<>();
							pomocni.add(prvi+','+drugi);
							liste.put(novo1+','+novo2, pomocni);
						}
					}
				}
			}
		}
		String[] pomocnaStanja = getSkupStanja();
		for (i=0; i<this.getSkupStanja().length-1; ++i) {
			prvi=this.getSkupStanja()[i+1];
			for (j=0; j<this.getSkupStanja().length-1; ++j) {
				drugi=this.getSkupStanja()[j];
				if(prvi!=null && drugi!=null) {
					if (polje[i*velicina+j]==0) {
						//ako je clan polja 0 -> stanja su istovjetna
						if (prvi.compareTo(drugi)<0) {
							zamijeni(pomocnaStanja, drugi, prvi);
						} else {
							zamijeni(pomocnaStanja, prvi, drugi);
						}
					}
				}
			}
		}
		setSkupStanja(uredi(pomocnaStanja));
	}
	public void zamijeni (String[] stanja, String micem, String stavljam) {
		obrisi(stanja,micem);
		stanja = uredi(stanja);
		if(contains(getPrihvatljivaStanja(), micem)) {
			obrisi(this.getPrihvatljivaStanja(), micem);
			this.setPrihvatljivaStanja(uredi(this.getPrihvatljivaStanja()));
		}
		if(pocetnoStanje.equals(micem))
			pocetnoStanje = stavljam;
		for (String ulaz : this.getAbeceda()) {
			this.getPrijelazi().remove(micem+","+ulaz);
			for(Map.Entry<String, String> entry : this.getPrijelazi().entrySet()) {
				if(entry.getValue().equals(micem))
					this.getPrijelazi().replace(entry.getKey(), stavljam);
			}
		}
	}
	public int getIndeks (String prvi, String drugi, int velicina, int[] polje) {
		int i,j;
		String pom1, pom2;
		if(prvi.compareTo(drugi) < 0) {
			pom1 = prvi;
			prvi = drugi;
			drugi = pom1;
		}
		for(i=0; i<this.getSkupStanja().length-1; ++i) {
			pom1=this.getSkupStanja()[i+1];
			if (pom1.equals(prvi)) {
				for(j=0; j<this.getSkupStanja().length-1; ++j) {
					pom2=this.getSkupStanja()[j];
					if (pom2.equals(drugi)) {
							return i*velicina+j;
					}
				}
			}
		}
		return 0;
	}
	
	public int[] init (int[] polje, int velicina) {
		int i,j;
		for (i=0; i<velicina; ++i) {
			for (j=0; j<velicina; ++j) {
				if (i<j) polje[i*velicina+j]=2;
				else polje[i*velicina+j]=0;
			}
		}
		return polje;
	}
	
	public void ispis() {
		String ispis=new String();
		ispis=dodaj(ispis,this.getSkupStanja());
		ispis=dodaj(ispis,this.getAbeceda());
		ispis=dodaj(ispis,this.getPrihvatljivaStanja());
		ispis+=this.getPocetnoStanje()+"\n";
		ispis=dodaj(ispis,this.getPrijelazi());
		System.out.print(ispis);
	}
	public String dodaj(String ispis, String[] niz) {
		int prvo=1;
		for (String stanje : niz) {
			if (stanje!=null) {
				if (prvo!=0) {
					prvo=0;
					ispis+=stanje;
				} else ispis+=","+stanje;
			}
		}
		ispis+="\n";
		return ispis;
	}
	public String dodaj(String ispis, HashMap<String, String> prijelazi) {
		for (String stanje : this.getSkupStanja()) {
			for (String znak : this.getAbeceda()) {
				ispis+=stanje+","+znak+"->"+prijelazi.get(stanje+","+znak)+"\n";
			}
		}
		return ispis;
	}
}