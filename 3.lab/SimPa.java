import java.io.*;
import java.util.HashMap;
public class SimPa {
	String [] ulazniNizovi, stanja, ulazniZnakovi, stog, prihvatljivaStanja;
	String pocetnoStanje, pocetnoNaStogu;
	HashMap<String, String> prijelazi = new HashMap<>();
	
	public void setUlazniNizovi (String [] ulazniNizovi) {
		this.ulazniNizovi=ulazniNizovi;
	}
	public String [] getUlazniNizovi () {
		return this.ulazniNizovi;
	}
	
	public void setStanja (String [] stanja) {
		this.stanja=stanja;
	}
	public String [] getStanja () {
		return this.stanja;
	}
	
	public void setUlazniZnakovi(String [] ulazniZnakovi) {
		this.ulazniZnakovi=ulazniZnakovi;
	}
	public String [] getUlazniZnakovi() {
		return ulazniZnakovi;
	}
	
	public void setStog (String [] stog) {
		this.stog=stog;
	}
	public String [] getStog () {
		return this.stog;
	}
	
	public void setPrihvatljivaStanja (String [] prihvatljivaStanja) {
		this.prihvatljivaStanja=prihvatljivaStanja;
	}
	public String [] getPrihvatljivaStanja () {
		return this.prihvatljivaStanja;
	}
	
	public void setPocetnoStanje (String pocetno) {
		this.pocetnoStanje=pocetno;
	}
	public String getPocetnoStanje () {
		return this.pocetnoStanje;
	}
	
	public void setPocetnoNaStogu (String pocetno) {
		this.pocetnoNaStogu=pocetno;
	}
	public String getPocetnoNaStogu () {
		return this.pocetnoNaStogu;
	}
	
	public HashMap<String,String> getPrijelazi () {
		return this.prijelazi;
	}
	
	public static void main (String [] args) {
		SimPa simPa=new SimPa();
		simPa.popuni();
		simPa.ispis();
	}
	
	public void popuni () {
		String pom;
		try (BufferedReader buf=new BufferedReader(new InputStreamReader(
				//new BufferedInputStream(new FileInputStream("primjer.in")),"UTF-8"))) {
				System.in))) {
			this.setUlazniNizovi(buf.readLine().split("\\|"));
			this.setStanja(buf.readLine().split(","));
			this.setUlazniZnakovi(buf.readLine().split(","));
			this.setStog(buf.readLine().split(","));
			this.setPrihvatljivaStanja(buf.readLine().split(","));
			this.setPocetnoStanje(buf.readLine());
			this.setPocetnoNaStogu(buf.readLine());
			while ((pom=buf.readLine())!=null) this.puniMapu(pom);
		} catch (IOException exc) {}
	}
	public void puniMapu(String pom) {
		String[] red=new String[2];
		red=pom.split("->");
		this.getPrijelazi().put(red[0], red[1]);
	}
	public void ispis () {
		String ispis= new String(), stanje=new String();
		String [] ulaznaSt, rez;
		for (String ulaz: this.getUlazniNizovi()) {
			ocistiStog();
			int fail=0;
			int gotovo=0;
			ulaznaSt=ulaz.split(",");
			stanje=this.getPocetnoStanje();
			ispis+=stanje+"#"+this.getPocetnoNaStogu();
			for (String jedno: ulaznaSt) {
				if (this.getPrijelazi().containsKey(stanje+","+jedno+","+this.getNaVrhuStoga())) {
					rez=this.getPrijelazi().get(stanje+","+jedno+","+this.getNaVrhuStoga()).split(",");
					stanje=rez[0];
					this.skiniSaStoga();
					if (!rez[1].equals("$")) {
						this.dodajNaStog(rez[1]);
					}

					ispis+="|"+stanje+"#"+this.ispisStog();
				} else if (this.getPrijelazi().containsKey(stanje+","+"$"+","+this.getNaVrhuStoga())) { 
					rez=this.getPrijelazi().get(stanje+","+"$"+","+this.getNaVrhuStoga()).split(",");
					stanje=rez[0];
					this.skiniSaStoga();
					if (!rez[1].equals("$")) {
						this.dodajNaStog(rez[1]);
					}
					ispis+="|"+stanje+"#"+this.ispisStog();
					while (!this.getPrijelazi().containsKey(stanje+","+jedno+","+this.getNaVrhuStoga())) {
						if (this.getPrijelazi().containsKey(stanje+",$,"+this.getNaVrhuStoga())) {
							rez=this.getPrijelazi().get(stanje+",$,"+this.getNaVrhuStoga()).split(",");
							stanje=rez[0];
							this.skiniSaStoga();
							if (!rez[1].equals("$")) {
								this.dodajNaStog(rez[1]);
							}
							ispis+="|"+stanje+"#"+this.ispisStog();
						} else {
							gotovo=1;
							break;
						}
					}
					if (this.getPrijelazi().containsKey(stanje+","+jedno+","+this.getNaVrhuStoga())) {
						rez=this.getPrijelazi().get(stanje+","+jedno+","+this.getNaVrhuStoga()).split(",");
						stanje=rez[0];
						this.skiniSaStoga();
						if (!rez[1].equals("$")) {
							this.dodajNaStog(rez[1]);
						}

						ispis+="|"+stanje+"#"+this.ispisStog();
					}
				} else {
					fail=1;
					ispis+="|"+"fail";
					break;
				}
				if (gotovo==1) {
					fail=1;
					ispis+="|"+"fail";
					break;
				}
			}
			
			if (fail==1) ispis+="|"+0;
			else if (prihvatljivo(stanje)) ispis+="|"+1;
			else {
				while (this.getPrijelazi().containsKey(stanje+","+"$"+","+this.getNaVrhuStoga())) {
					rez=this.getPrijelazi().get(stanje+","+"$"+","+this.getNaVrhuStoga()).split(",");
					stanje=rez[0];
					this.skiniSaStoga();
					if (!rez[1].equals("$")) {
						this.dodajNaStog(rez[1]);
					}

					ispis+="|"+stanje+"#"+this.ispisStog();
					if (prihvatljivo(stanje)) {
						ispis+="|"+1;
						break;
					}
				}
				if (!prihvatljivo(stanje)) ispis+="|"+0;
			}

			ispis+="\n";
		}
		System.out.print(ispis);
	}
	public String getNaVrhuStoga () {
		int i=this.getStog().length;
		String [] stog=this.getStog();
		if (i>0) return stog[i-1];
		return "$";
	}
	public boolean prihvatljivo (String stanje) {
		for (String stanja: this.getPrihvatljivaStanja()) {
			if (stanje.equals(stanja)) return true;
		}
		return false;
	}
	public String skiniSaStoga() {
		String [] stariStog=this.getStog();
		String [] noviStog= new String[stariStog.length-1];
		int i;
		for (i=0; i<stariStog.length; ++i) {
			if (i!=(stariStog.length-1)) noviStog[i]=stariStog[i];
		}
		setStog(noviStog);
		return stariStog[stariStog.length-1];
	}
	public String ispisStog() {
		String[] stog=this.getStog();
		String rez="";
		int i;
		if (stog.length==0) return "$";
		for (i=stog.length; i>0; --i) {
			rez+=stog[i-1];
		}
		return rez;
	}
	public void ocistiStog () {
		String [] novo=new String [1];
		novo[0]=this.getPocetnoNaStogu();
		setStog(novo);
	}
	public void dodajNaStog (String red) {
		String [] staro=this.getStog();
		String [] novo=new String [staro.length+red.length()];
		int i;
		for (i=0; i<staro.length;++i) novo[i]=staro[i];
		for (i=0;i<red.length();++i) {
			novo[staro.length+i]=red.substring(red.length()-i-1,red.length()-i);
		}
		setStog(novo);
	}
}