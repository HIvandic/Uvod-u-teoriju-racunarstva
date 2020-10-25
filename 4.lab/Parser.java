import java.util.ArrayList;
import java.io.*;

public class Parser {
	ArrayList<String> ulaz=new ArrayList<>();
	String ispis=new String();
	int da_ne;
	
	public String getIspis () {
		return ispis;
	}
	public void setIspis (String ispis) {
		this.ispis=ispis;
	}
	public int getDa_ne() {
		return da_ne;
	}
	public void setDa_ne(int da_ne)  {
		this.da_ne=da_ne;
	}
	public ArrayList<String> getUlaz() {
		return ulaz;
	}
	public void setUlaz(ArrayList<String> ulaz)  {
		this.ulaz=ulaz;
	}
	public static void main (String [] args) {
		Parser parser=new Parser();
		try (BufferedReader buf=new BufferedReader(new InputStreamReader(
				//new BufferedInputStream(new FileInputStream("primjer.in")),"UTF-8"))) {
				System.in))) {
			for (char jedno: buf.readLine().toCharArray()) {
				parser.ulaz.add(Character.toString(jedno));
			}
		} catch (IOException exc) {}
		parser.ispis();
	}
	
	public void ispis () {
		stanjeS();
		if (this.getDa_ne()==1 && this.ulaz.isEmpty()) this.setIspis(this.getIspis()+"\n"+"DA");
		else this.setIspis(this.getIspis()+"\n"+"NE");
		System.out.println(this.ispis);
	}
	public void stanjeS () {
		this.setDa_ne(0);
		this.setIspis(this.getIspis()+"S");
		if (ulaz.isEmpty()) {
			this.setDa_ne(0);
			return;
		}
		else {
			String jedno=ulaz.get(0);
			ulaz.remove(0);
			if (jedno.equals("a")) {
				stanjeA();
				if (this.getDa_ne()==1) stanjeB();
			} else if (jedno.equals("b")) {
				stanjeB();
				if (this.getDa_ne()==1) stanjeA();
			} else this.setDa_ne(0);
		}
	}
	public void stanjeA () {
		this.setDa_ne(0);
		this.setIspis(this.getIspis()+"A");
		if (ulaz.isEmpty()) return;
		else {
			String jedno=ulaz.get(0);
			ulaz.remove(0);
			if (jedno.equals("b")) {
				stanjeC();
			} else if (jedno.equals("a")) {
				this.setDa_ne(1);
			} else {
				this.setDa_ne(0);
			}
		}
	}
	public void stanjeB () {
		this.setDa_ne(0);
		this.setIspis(this.getIspis()+"B");
		if (ulaz.isEmpty()) {
			this.setDa_ne(1);
			return;
		}
		else {
			if (!ulaz.isEmpty() && ulaz.get(0).equals("c")) {
				ulaz.remove(0);
				if (!ulaz.isEmpty() && ulaz.get(0).equals("c")) {
					ulaz.remove(0);
					stanjeS();
					if (!ulaz.isEmpty() && ulaz.get(0).equals("b")) {
						ulaz.remove(0);
						if (!ulaz.isEmpty() && ulaz.get(0).equals("c")) {
							ulaz.remove(0);
							this.setDa_ne(1);
						} else this.setDa_ne(0);
					} else this.setDa_ne(0);
				} else this.setDa_ne(0);
			} else this.setDa_ne(1);
		}
	}
	public void stanjeC () {
		this.setDa_ne(0);
		this.setIspis(this.getIspis()+"C");
		stanjeA();
		if (this.getDa_ne()==1) stanjeA();
	}
}
