package listasLigadas;

public class Chave {
	private int ano;
	private int semana;
	private int bola1;
	private int bola2;
	private int bola3;
	private int bola4;
	private int bola5;
	private int estrela1;
	private int estrela2;
	
	public Chave (int ano , int semana , int bola1 , int bola2, int bola3 ,int bola4 ,int bola5 , int estrela1, int estrela2 ) {
		setAno(ano);
		setSemana(semana);
		setBola1(bola1);
		setBola2(bola2);
		setBola3(bola3);
		setBola4(bola4);
		setBola5(bola5);
		setEstrela1(estrela1);
		setEstrela2(estrela2);
	}
	
	public int compareTo(Chave c) {
		if (c.getAno() > this.ano  || c.getAno() == this.ano  && c.getSemana() > this.semana )
			return 0;
		else 
			return -1;
	}

	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getSemana() {
		return semana;
	}
	public void setSemana(int semana) {
		this.semana = semana;
	}
	public int getBola1() {
		return bola1;
	}
	public void setBola1(int bola1) {
		this.bola1 = bola1;
	}
	public int getBola2() {
		return bola2;
	}
	public void setBola2(int bola2) {
		this.bola2 = bola2;
	}
	public int getBola3() {
		return bola3;
	}
	public void setBola3(int bola3) {
		this.bola3 = bola3;
	}
	public int getBola4() {
		return bola4;
	}
	public void setBola4(int bola4) {
		this.bola4 = bola4;
	}
	public int getBola5() {
		return bola5;
	}
	public void setBola5(int bola5) {
		this.bola5 = bola5;
	}
	public int getEstrela1() {
		return estrela1;
	}
	public void setEstrela1(int estrela1) {
		this.estrela1 = estrela1;
	}
	public int getEstrela2() {
		return estrela2;
	}
	public void setEstrela2(int estrela2) {
		this.estrela2 = estrela2;
	}

}
