package listasLigadas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Arranque {

	public static final int NTOTALELEMENTOS = 62;//50 BOLAS + 12 ESTRELAS
	public static final int POSICOES = 7;//PARA O ARRAY 2D fAbsPos
	private static final int NBOLAS = 50;// NUM TOTAL DE BOLAS
	private static final int NTOTALCHAVES = 2043;//NUM TOTAL DE ELEMENTOS DA LISTA
	private static final int BOLAS = 5;//5 BOLAS POR CHAVE
	private static final int ESTRELAS = 2;//2 ESTRELAS POR CHAVE

	//POSICOES NO ARRAI 2D fAbsPos
	private static final int POSICAO1 = 0;
	private static final int POSICAO2 = 1;
	private static final int POSICAO3 = 2;
	private static final int POSICAO4 = 3;
	private static final int POSICAO5 = 4;
	private static final int POSICAO6 = 5;
	private static final int POSICAO7 = 6;


	public static void main(String[] args) {

		ListasLigadas listaDeChaves = new ListasLigadas();

		int fAbs []  = new int [NTOTALELEMENTOS];//Para guardar as frequencias absolutas
		int nAus [] = new int [NTOTALELEMENTOS];//Para guardar as ausencias
		int fAbsPos [][] = new int [POSICOES][NTOTALELEMENTOS];//Para guardar as frequencias absolutas em cada posição
		double fRel [] = new double [NTOTALELEMENTOS];//para guardar as frequencias relativas

		executarMenuPrincipal(listaDeChaves , fAbs , nAus , fAbsPos , fRel);

	}
	//---------------------------------------------------------------------------------------------------------------------
	private static int lerInt(Scanner sc,String msg,int min,int max){
		int res=0;
		boolean ok=true;
		do{
			ok=true;
			System.out.println(msg);
			try{
				res=sc.nextInt();
			}
			catch(Exception e){
				System.out.println("ERRO DE INPUT - Por favor introduza um numero inteiro");
				ok=false;
				sc.next();
			}
			if(ok&&(res<min||res>max)){
				System.out.println("ERRO DE INPUT - o valor a introduzir deve estar no intervalo ["+min+","+max+"]");
				ok=false;
			}
		}while(!ok);	
		sc.nextLine();
		return res;
	}

	//---------------------------------------------------------------------------------------------------------------------	
	//Metodo para imprimir o menuPrincipal
	private static void imprimirMenuPrincipal(){
		System.out.println("1 - Ler Ficheiro");
		System.out.println("2 - Listar Chaves");
		System.out.println("3 - Estatisticas Gerais");
		System.out.println("4 - Estatisticas Especificas");
		System.out.println("5 - Consulta de Chaves");
		System.out.println("0 - Terminar");
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para imprimir o segundoMenu
	private static void imprimirSegundoMenu(){
		System.out.println("1 - Frequencias Absolutas");
		System.out.println("2 - Frequencias Relativas");
		System.out.println("0 - Voltar ao Menu Principal");
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para imprimir o terceiroMenu
	private static void imprimirTerceiroMenu(){
		System.out.println("1 - Consultar Bolas");
		System.out.println("2 - Consultar Estrelas");
		System.out.println("0 - Voltar ao Menu Principal");
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para executar o menuPrincipal
	private static void executarMenuPrincipal(ListasLigadas listaDeChaves ,  int  fAbs [] , int nAus [] , int fAbsPos [][] , double fRel []) {
		Scanner sc=new Scanner(System.in);

		int opcao=0;		
		do{
			imprimirMenuPrincipal();
			opcao=lerInt(sc,"Opcao:",0,5);
			switch(opcao){
			case 0:
				break;
			case 1:
				lerFicheiro( listaDeChaves , fAbs , nAus , fAbsPos , fRel);
				break;
			case 2:
				listarChaves(listaDeChaves);
				break;
			case 3:
				executarMenuEstatisticasGerais(listaDeChaves , fAbs , nAus , fAbsPos , fRel);
				break;
			case 4:
				executarMenuEstatisticasEspecificas(listaDeChaves , fAbs , nAus , fAbsPos , fRel);
				break;
			case 5:
				consultarChaves(listaDeChaves);
				break;
			}
		}while(opcao!=0);
		sc.close();
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para ler o ficheiro e fazer as contas da frequencia absoluta,
	//o numero de ausencias e a frequencia relativa
	private static void lerFicheiro(ListasLigadas listaDeChaves , int fAbs [] , int nAus [] , int fAbsPos [][] ,double fRel [] ) {
		String linha;
		
		int ano,semana,bola1,bola2,bola3,bola4,bola5,estrela1,estrela2;

		File file=new File("euromilhoes.txt");
		Scanner leitor=null;
		try {
			leitor = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Ficheiro não encontrado");
			e.printStackTrace();
		}
		while(leitor.hasNextLine()){
			linha=leitor.nextLine();

			Scanner lerString=new Scanner(linha);
			
			//Ir buscar ao ficheiro TXT
			ano=lerString.nextInt();
			semana=lerString.nextInt();
			bola1=lerString.nextInt();
			bola2=lerString.nextInt();
			bola3=lerString.nextInt();
			bola4=lerString.nextInt();
			bola5=lerString.nextInt();
			estrela1=lerString.nextInt();
			estrela2=lerString.nextInt();
			
			//Criacao da chave para inserir na lista
			Chave c1 = new Chave (ano,semana,bola1,bola2, bola3, bola4, bola5, estrela1, estrela2);
			listaDeChaves.inserirOrdem( c1 );
			lerString.close();

		} 

		leitor.close();
		System.out.println("Foram lidas " + listaDeChaves.getTamanho() + " chaves e inseridas na lista");
		System.out.println();

		//|--------------------------------CALCULOS-------------------------------------------------|\\
		boolean vereficarAunsencia [] = new boolean [NTOTALELEMENTOS]; //criado para guardar se ha ou nao ausencia em cada elemento

		//PARA AS BOLAS
		for (int i = 0; i < listaDeChaves.getTamanho(); i++ ) {	//para percorrer a lista
			for ( int nElementos = 1; nElementos < 51; nElementos ++) {		//50 numeros de bolas
				if (nElementos == ((Chave) listaDeChaves.get(i)).getBola1()){	//se o numero da bola foi encontrado
					fAbs[nElementos-1]++;						//contagem de frequencia absoluta 
					fAbsPos[POSICAO1][nElementos-1]++;			//contagem de frequencia absoluta para as posicoes
					vereficarAunsencia[nElementos-1]=true;
				}
				if (nElementos == ((Chave) listaDeChaves.get(i)).getBola2()){
					fAbs[nElementos-1]++;
					fAbsPos[POSICAO2][nElementos-1]++;
					vereficarAunsencia[nElementos-1]=true;
				}
				if (nElementos == ((Chave) listaDeChaves.get(i)).getBola3()){
					fAbs[nElementos-1]++;
					fAbsPos[POSICAO3][nElementos-1]++;
					vereficarAunsencia[nElementos-1]=true;
				}
				if (nElementos == ((Chave) listaDeChaves.get(i)).getBola4()){
					fAbs[nElementos-1]++;
					fAbsPos[POSICAO4][nElementos-1]++;
					vereficarAunsencia[nElementos-1]=true;
				}
				if (nElementos == ((Chave) listaDeChaves.get(i)).getBola5()){
					fAbs[nElementos-1]++;
					fAbsPos[POSICAO5][nElementos-1]++;
					vereficarAunsencia[nElementos-1]=true;
				}	
				if (!vereficarAunsencia[nElementos-1])
					//contagem das ausencias
					nAus[nElementos-1]++ ;             
			}

			//PARA AS ESTRELAS
			for (int nElementos = 1 ; nElementos < 13 ; nElementos ++) { //12 numeros de estrelas
				if (nElementos == ((Chave) listaDeChaves.get(i)).getEstrela1()){
					fAbs[nElementos + NBOLAS - 1 ]++;
					fAbsPos[POSICAO6][nElementos + NBOLAS - 1]++;
					vereficarAunsencia[nElementos + NBOLAS - 1]=true;
				}
				if (nElementos == ((Chave) listaDeChaves.get(i)).getEstrela2()){
					fAbs[nElementos + NBOLAS - 1 ]++;
					fAbsPos[POSICAO7][nElementos + NBOLAS - 1]++;
					vereficarAunsencia[nElementos + NBOLAS - 1]=true;
				}
				if (!vereficarAunsencia[nElementos + NBOLAS - 1])
					//contagem das ausencias
					nAus[nElementos + NBOLAS - 1]++ ;
			}
		}
		//FREQUENCIAS REALATIVAS
		//PARA AS BOLAS
		for (int nElementos = 0; nElementos < NBOLAS ; nElementos++) {
			fRel[nElementos] = 100 * (double) fAbs[nElementos]/(NTOTALCHAVES*BOLAS);
		}
		//PARA AS ESTRELAS
		for (int nElementos = NBOLAS; nElementos < NTOTALELEMENTOS ; nElementos++) {
			fRel[nElementos] = 100 * (double) fAbs[nElementos]/(NTOTALCHAVES*ESTRELAS);
		}
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para imprimir as chaves na lista
	private static void listarChaves(ListasLigadas listaDeChaves) {
		for (int i = 0; i < listaDeChaves.getTamanho(); i++){
			if (i>0 && i%20==0){	//Se o i = 0 sendo 0 as vezes que imprimiu imprime ate quando o resto de i divido por 20 é zero
				inserirEnterContinue();	//metodo para parar o programa de 20 em 20 linhas imprimidas
			}
			System.out.printf("{%02d/%02d}\t|%02d|%02d|%02d|%02d|%02d|\t|%02d|%02d|\n", 
					(((Chave) listaDeChaves.get(i)).getAno()),
					((Chave) listaDeChaves.get(i)).getSemana(),
					((Chave) listaDeChaves.get(i)).getBola1(),
					((Chave) listaDeChaves.get(i)).getBola2(),
					((Chave) listaDeChaves.get(i)).getBola3(),
					((Chave) listaDeChaves.get(i)).getBola4(),
					((Chave) listaDeChaves.get(i)).getBola5(),
					((Chave) listaDeChaves.get(i)).getEstrela1(),
					((Chave) listaDeChaves.get(i)).getEstrela2());
		}
		System.out.println();
	}
	//---------------------------------------------------------------------------------------------------------------------	
	//Metodo para executar o menuEstatisticasGerais
	private static void executarMenuEstatisticasGerais(ListasLigadas listaDeChaves , int  fAbs [] , int nAus [] , int fAbsPos [][] , double fRel []) {
		Scanner sc=new Scanner(System.in);

		int opcao=0;		
		do{
			imprimirSegundoMenu();
			opcao=lerInt(sc,"Opcao:",0,2);
			switch(opcao){
			case 0:
				executarMenuPrincipal( listaDeChaves ,  fAbs , nAus , fAbsPos ,fRel);
				break;
			case 1:
				imprimirFrequenciasAbsolutas(fAbs , nAus , fAbsPos);
				break;
			case 2:
				imprimirFrequenciasRelativas(fRel);
				break;
			}
		}while(opcao!=0);
		sc.close();
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para imprimir as frequencias absolutas
	private static void imprimirFrequenciasAbsolutas(int fAbs [] ,int nAus [] , int fAbsPos [][]) {
		System.out.printf("|Bola\t|F.Abs\t|Aus.\t|\n");
		//PARA AS BOLAS
		for (int nElementos = 0; nElementos < NBOLAS; nElementos++){
			if (nElementos>0 && nElementos%20==0){ 			//Se o i = 0 sendo 0 as vezes que imprimiu imprime ate quando o resto de i divido por 20 é zero
				inserirEnterContinue();
			}
			if (nElementos<NBOLAS){
				System.out.printf("|%02d\t|%03d\t|%03d\t|\n", nElementos+1, fAbs[nElementos], nAus[nElementos]);
			} 
		}
		System.out.println(); 

		//PARA AS POSICOES DAS BOLAS
		inserirEnterContinue();
		System.out.printf("|Bola\t|Pos. 1\t|Pos. 2\t|Pos. 3\t|Pos. 4\t|Pos. 5\t|\n");
		for (int nElementos = 0; nElementos < NBOLAS; nElementos++){
			if (nElementos>0 && nElementos%20==0){//Se o i = 0 sendo 0 as vezes que imprimiu imprime ate quando o resto de i divido por 20 é zero
				inserirEnterContinue();
			}
			System.out.printf("|%02d\t|%03d\t|%03d\t|%03d\t|%03d\t|%03d\t|\n", nElementos+1, 
					fAbsPos[POSICAO1][nElementos],
					fAbsPos[POSICAO2][nElementos],
					fAbsPos[POSICAO3][nElementos],
					fAbsPos[POSICAO4][nElementos],
					fAbsPos[POSICAO5][nElementos]);
		}
		System.out.println();

		//PARA AS ESTRELAS
		inserirEnterContinue();
		System.out.printf("|Etrelas|F.Abs\t|Aus.\t|\n");
		for (int nElementos = NBOLAS; nElementos < fAbs.length; nElementos++){			//para ir das bolas para as estrelas
			System.out.printf("|%02d\t|%03d\t|%03d\t|\n", nElementos-49, fAbs[nElementos],nAus[nElementos]);
		}
		System.out.println();

		//PARA AS POSICOES DAS ESTRELAS
		inserirEnterContinue();
		System.out.printf("|Estrela|Pos. 1\t|Pos. 2\t|\n");

		for (int nElementos = NBOLAS; nElementos < NTOTALELEMENTOS; nElementos++){		//para ir das bolas para as estrelas
			System.out.printf("|%02d\t|%03d\t|%03d\t|\n", nElementos - 49, 
					fAbsPos[POSICAO6][nElementos],
					fAbsPos[POSICAO7][nElementos]);
		}
		System.out.println();
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para imprimir as frequencias relativas
	private static void imprimirFrequenciasRelativas(double fRel [] ) {
		//Para as bolas
		System.out.printf("|Bola\t|F.Rel.\t|\n");
		for (int nElementos = 0; nElementos < NBOLAS; nElementos++){
			if (nElementos>0 && nElementos%20==0){//Se o i = 0 sendo 0 as vezes que imprimiu imprime ate quando o resto de i divido por 20 é zero
				inserirEnterContinue();
			}
			System.out.printf("|%02d\t|%05.2f%s\t|\n", nElementos+1, fRel[nElementos], '%');
		}
		System.out.println();
		inserirEnterContinue();
		
		//Para as estrelas
		System.out.printf("|Estrela|F.Rel.\t|\n");
		for (int nElementos = NBOLAS; nElementos < fRel.length; nElementos++){
			System.out.printf("|%02d\t|%05.2f%s\t|\n", nElementos-49, fRel[nElementos], '%');
		}
		System.out.println();
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para executar menuEstatisticasEspecificas
	private static void executarMenuEstatisticasEspecificas(ListasLigadas listaDeChaves , int fAbs [] , int nAus [] , int fAbsPos [][] , double fRel []) {
		Scanner sc=new Scanner(System.in);

		int opcao=0;		
		do{
			imprimirTerceiroMenu();
			opcao=lerInt(sc,"Opcao:",0,2);
			switch(opcao){
			case 0:
				executarMenuPrincipal(listaDeChaves , fAbs , nAus , fAbsPos ,fRel );
				break;
			case 1:
				consultarBolas(fAbs ,  nAus  ,  fAbsPos  ,  fRel);
				break;
			case 2:
				consultarEstrelas(fAbs ,  nAus  ,  fAbsPos  ,  fRel);
				break;
			}
		}while(opcao!=0);
		sc.close();
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para imprimir a consulta da bola desejada
	private static void consultarBolas( int fAbs [] , int nAus [] , int fAbsPos [][] , double fRel []) {
		Scanner sc=new Scanner(System.in);
		int procura = 0;
		procura = lerInt( sc ,"Qual o numero da bola a pesquisar? ", 1 , 50 );
		System.out.println();
		System.out.printf("|Bola\t|F.Abs\t|Pos. 1\t|Pos. 2\t|Pos. 3\t|Pos. 4\t|Pos. 5\t|F.Rel.\t|Aus.\t|\n");
		System.out.printf("|%02d\t|%03d\t|%03d\t|%03d\t|%03d\t|%03d\t|%03d\t|%05.2f%s\t|%03d\t|\n",
				procura , fAbs[procura-1] , fAbsPos[POSICAO1][procura-1] , fAbsPos[POSICAO2][procura-1] ,
				fAbsPos[POSICAO3][procura-1] , fAbsPos[POSICAO4][procura-1] , fAbsPos[POSICAO5][procura-1] ,
				fRel[procura-1] , '%' , nAus[procura-1]);
		System.out.println();
	}
	//---------------------------------------------------------------------------------------------------------------------
	//Metodo para imprimir a consulta da estrela desejada
	private static void consultarEstrelas(int fAbs [] , int nAus [] , int fAbsPos [][] , double fRel []) {
		Scanner sc=new Scanner(System.in);
		int procura = 0;
		procura = lerInt( sc ,"Qual o numero da estrela a pesquisar?" , 1 , 12 );

		System.out.printf("|Estrela|F.Abs\t|Pos. 1\t|Pos. 2\t|F.Rel.\t|Aus.\t|\n");
		System.out.printf("|%02d\t|%03d\t|%03d\t|%03d\t|%05.2f%s\t|%03d\t|\n",
				procura,fAbs[procura+49], fAbsPos[POSICAO6][procura+49] ,
				fAbsPos[POSICAO7][procura+49] , fRel[procura+49] , '%' , nAus[procura+49]);
		System.out.println();
	}
	//---------------------------------------------------------------------------------------------------------------------´
	//Metodo para imprimir a chave com o ano e semana desejados
	private static void consultarChaves(ListasLigadas listaDeChaves) {
		Scanner sc=new Scanner(System.in);

		int opcao=0;
		int opcao2=0;
		opcao=lerInt(sc,"Qual o ano da chave:",1980,2019);
		opcao2=lerInt(sc,"Qual a semana da chave:",1,52);
		for (int i = 0; i < listaDeChaves.getTamanho(); i++){
			if ( ((Chave) listaDeChaves.get(i)).getAno() == opcao && ((Chave) listaDeChaves.get(i)).getSemana() == opcao2) {
				System.out.printf("{%02d/%02d}\t|%02d|%02d|%02d|%02d|%02d|\t|%02d|%02d|\n", 
						(((Chave) listaDeChaves.get(i)).getAno()),
						((Chave) listaDeChaves.get(i)).getSemana(),
						((Chave) listaDeChaves.get(i)).getBola1(),
						((Chave) listaDeChaves.get(i)).getBola2(),
						((Chave) listaDeChaves.get(i)).getBola3(),
						((Chave) listaDeChaves.get(i)).getBola4(),
						((Chave) listaDeChaves.get(i)).getBola5(),
						((Chave) listaDeChaves.get(i)).getEstrela1(),
						((Chave) listaDeChaves.get(i)).getEstrela2());
			}
		}
		System.out.println();
	}	
	//----------------------------------------------------------------------------------------------------------------------
	//Metodo para o programa esperar cada vez que imprime 20 linhas
	private static void inserirEnterContinue()
	{ 
		System.out.println();
		System.out.printf("\t => Pressione a tecla ENTER para continuar... \t\n");
		System.out.println();

		try
		{
			System.in.read();
			System.in.read();
		}
		catch(Exception e)
		{

		}
	}
}
