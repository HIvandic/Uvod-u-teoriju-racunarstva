import java.io.*;
import java.util.HashMap;
import java.util.TreeSet;

public class SimEnka {
	String[] nizovi, stanja, abeceda, prihvatljivo;
	String pocetno;
	HashMap<String,String> prijelaz=new HashMap<>();
	public void setNizovi(String[] nizovi) {
		this.nizovi=nizovi;
	}
	public String[] getNizovi() {
		return this.nizovi;
	}
	public void setStanja(String[] stanja) {
		this.stanja=stanja;
	}
	public String[] getStanja() {
		return this.stanja;
	}
	public void setAbeceda(String[] abeceda) {
		this.abeceda=abeceda;
	}
	public String[] getAbeceda() {
		return this.abeceda;
	}
	public void setPrihvatljivo(String[] prihvatljivo) {
		this.prihvatljivo=prihvatljivo;
	}
	public String[] getPrihvatljivo() {
		return this.prihvatljivo;
	}
	public void setPocetno(String pocetno) {
		this.pocetno=pocetno;
	}
	public String getPocetno() {
		return this.pocetno;
	}
	public HashMap<String,String> getHashMap() {
		return this.prijelaz;
	}
	
	public static void main (String[] args) {
		SimEnka simEnka=new SimEnka();
		//citanje iz stdin
		simEnka.popuni();
		//pozivanje fcije za ispis
		simEnka.ispis();
	}
	public void popuni() {
		String pom;
		try (BufferedReader buf= new BufferedReader(new InputStreamReader(System.in))) {
			//citanje ulaznih podataka
			this.setNizovi(buf.readLine().split("\\|"));
			this.setStanja(buf.readLine().split(","));
			this.setAbeceda(buf.readLine().split(","));
			this.setPrihvatljivo(buf.readLine().split(","));
			this.setPocetno(buf.readLine());
			while ((pom=buf.readLine())!=null) this.puniMapu(pom);
		} catch (IOException exc) {}
	}
	public void puniMapu(String pom) {
		String [] red=pom.split("->");
		this.getHashMap().put(red[0], red[1]);
	}
	public void ispis() {
		int i, j, promjena, pom;
		String[] izlazi, pomocni=this.getNizovi();
		String ispis= new String();
		String znakAbecede;
		String izlaz;
		
		//uzima jedan po jedan niz s ulaza
		for (i=0; i<pomocni.length; ++i) {
			String [] trenutniNiz=pomocni[i].split(",");
			TreeSet<String> stanjaUKojaIde=new TreeSet<>();
			TreeSet<String> stanjaSad= new TreeSet<>();
			stanjaSad.add(this.getPocetno());
			TreeSet<String> eStanjaUKojaIde=new TreeSet<>();
			
			for (j=0; j<trenutniNiz.length;++j) {
				//uzima znak po znak iz ulaznog niza
				znakAbecede=trenutniNiz[j];
				
				//provjera ima li epsilon prijelaza za trenutna stanja
				promjena=1;
				if (!stanjaSad.contains("#")) {
					while (promjena!=0) {
						promjena=0;
						for (String jedno : stanjaSad) {
							if ((izlaz=this.getHashMap().get(jedno+","+"$"))!=null) {
								izlazi=izlaz.split(",");
								for (String epsilon : izlazi) {
									if (!epsilon.equals("#") && !stanjaSad.contains(epsilon)) {
										promjena=1;
										eStanjaUKojaIde.add(epsilon);
									}
								}
							}
						}
						stanjaSad.addAll(eStanjaUKojaIde);
						eStanjaUKojaIde.clear();
					}
				}
				pom=0;
				for (String jedno : stanjaSad) {
					if (pom==0) {
						ispis+=jedno;
						pom=1;
					}
					else ispis+=","+jedno;
				}
				ispis+="|";
				
				//prijelazi za ulazni znak
				if (!stanjaSad.contains("#")) {
					for (String jedno : stanjaSad) {
						if ((izlaz=this.getHashMap().get(jedno+","+znakAbecede))!=null) {
							izlazi=izlaz.split(",");
							for (String iduce : izlazi) {
								if (!iduce.equals("#") && !stanjaUKojaIde.contains(iduce)) stanjaUKojaIde.add(iduce);
							}
						}
					}
				}
				stanjaUKojaIde.remove("#");
				if (stanjaUKojaIde.size()==0) stanjaUKojaIde.add("#");
				stanjaSad.clear();
				stanjaSad.addAll(stanjaUKojaIde);
				stanjaUKojaIde.clear();
				
				if (j==(trenutniNiz.length-1)) {
					//provjera ima li epsilon prijelaza za trenutna stanja za zadnji prijelaz
					promjena=1;
					if (!stanjaSad.contains("#")) {
						while (promjena!=0) {
							promjena=0;
							for (String jedno : stanjaSad) {
								if ((izlaz=this.getHashMap().get(jedno+","+"$"))!=null) {
									izlazi=izlaz.split(",");
									for (String epsilon : izlazi) {
										if (!epsilon.equals("#") && !stanjaSad.contains(epsilon)) {
											promjena=1;
											eStanjaUKojaIde.add(epsilon);
										}
									}
								}
							}
							stanjaSad.addAll(eStanjaUKojaIde);
							eStanjaUKojaIde.clear();
						}
						
					}
					pom=0;
					for (String jedno : stanjaSad) {
						if (pom==0) {
							ispis+=jedno;
							pom=1;
						}
						else ispis+=","+jedno;
					}
				}
			}
			ispis+="\n";
		}
		System.out.print(ispis);
	}
}