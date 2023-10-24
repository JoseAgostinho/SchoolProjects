import java.util.Scanner;
public class TerceiroTrabalho {

	public static void main(String[] args) {

		int tamMax = 100;
		String [] linhas = new String[tamMax];
		int [] nPalavras = new int[tamMax];
		int [] nChars = new int[tamMax];
		boolean [] apagada = new boolean[tamMax];
		int nLinhas = 0;
		String auxiliar = "";
		Scanner input = new Scanner(System.in);
		char menus = 0;

		String [] copiarColar = new String[tamMax];
		int [] nPalavrasMenuCopiar = new int [tamMax];
		int [] nCharsMenuCopiar = new int [tamMax];
		boolean [] apagadaMenuCopiar = new boolean [tamMax];


		do {
			imprimirMenuPrincipal();
			auxiliar=input.nextLine();
			menus = protecaoEntrada(auxiliar);

			switch (menus) {
			case 'i': nLinhas = inserirTexto(linhas, nLinhas, nChars, nPalavras, apagada, input);
			break;
			case 'l':listar(linhas, nLinhas, apagada);
			break;
			case 'a':apagarUltima(nLinhas, apagada);
			break;
			case 'e':nLinhas = menuEditar (linhas, nChars, nPalavras, nLinhas, apagada, auxiliar, input, menus, tamMax);
			break;
			case 'f':menuFerramentas( linhas, nLinhas, nChars, nPalavras, apagada, auxiliar, input, menus);
			break;
			case 'c': nLinhas = menuCopiar( linhas, copiarColar, apagadaMenuCopiar, nPalavras, nPalavrasMenuCopiar , nCharsMenuCopiar, nChars, apagada , nLinhas, menus, auxiliar, input, tamMax);
			break;
			case 's':
				break;
			default: System.out.println("Erro opção invalida");
			}
		} while (menus != 's');

		input.close();
	}
	private static int menuCopiar(String []linhas, String []copiarColar,boolean []apagadaMenuCopiar,int []nPalavras, int []nPalavrasMenuCopiar , int []nCharsMenuCopiar, int []nChars,boolean []apagada , int nLinhas, char menus, String auxiliar, Scanner input, int tamMax) {
		int nLinhasColar= 0;
		do {
			imprimirMenuCopiar();
			auxiliar=input.nextLine();
			menus = protecaoEntrada(auxiliar);

			switch (menus) {
			case 'p':nLinhasColar = copiarDeXaY(linhas, copiarColar, apagadaMenuCopiar, apagada, nCharsMenuCopiar, nChars, nPalavras, nPalavrasMenuCopiar, nLinhas, nLinhasColar, input);
			break;
			case 'r':nLinhasColar = cortarDeXaY(linhas, copiarColar, apagadaMenuCopiar, nPalavras, nPalavrasMenuCopiar , nCharsMenuCopiar, nChars, apagada , nLinhas, nLinhasColar, input, tamMax);
			nLinhas = nLinhas - nLinhasColar;
			break;
			case 'i': nLinhas = inserirAPartirDe(linhas, copiarColar, apagadaMenuCopiar, nPalavras, nPalavrasMenuCopiar , nCharsMenuCopiar, nChars, apagada , nLinhas, nLinhasColar, input, tamMax);
			break;
			case 's': substituirAPartirDe(linhas, copiarColar,apagadaMenuCopiar,nPalavras, nPalavrasMenuCopiar , nCharsMenuCopiar, nChars,apagada ,nLinhas, nLinhasColar,  input  );
			break;
			case 'v':
				break;
			}
		}while(menus != 'v');
		return nLinhas;
	}

	private static void substituirAPartirDe(String []linhas, String []copiarColar,boolean []apagadaMenuCopiar,int []nPalavras, int []nPalavrasMenuCopiar , int []nCharsMenuCopiar, int []nChars,boolean []apagada ,int nLinhas,int nLinhasColar, Scanner input) {
		int linhaInicio = 0;
		System.out.println("digite a linha a partir de onde quer subestituir");
		linhaInicio = input.nextInt();
		for (int i = linhaInicio , j = 0; j  < nLinhasColar; i++, j++) {
			linhas[i] = copiarColar[j];
			apagada[i] = apagadaMenuCopiar[j];
			nChars[i] = nCharsMenuCopiar[j];
		}

	}

	private static int inserirAPartirDe(String []linhas, String []copiarColar,boolean []apagadaMenuCopiar,int []nPalavras, int []nPalavrasMenuCopiar , int []nCharsMenuCopiar, int []nChars,boolean []apagada ,int nLinhas,int nLinhasColar, Scanner input, int tamMax) {
		int linhaInicio = 0;
		System.out.println("digite a linha a partir de onde quer inserir");
		linhaInicio = input.nextInt()-1;
		if(linhaInicio <= nLinhas && nLinhas <= tamMax && linhaInicio > 0) {


			for (int i = nLinhas; i >=linhaInicio; i--) {
				linhas[i+nLinhasColar] = linhas[i];
				apagada[i+nLinhasColar] = apagada[i];
				nChars[i+nLinhasColar] = nChars[i];
			}
			for (int i = linhaInicio , j = 0; j  < nLinhasColar; i++, j++) {
				linhas[i] = copiarColar[j];
				apagada[i] = apagadaMenuCopiar[j];
				nChars[i] = nCharsMenuCopiar[j];
			}

		}
		nLinhas = nLinhas + nLinhasColar;

		return nLinhas;
	}

	private static int cortarDeXaY(String []linhas, String []copiarColar,boolean []apagadaMenuCopiar,int []nPalavras, int []nPalavrasMenuCopiar , int []nCharsMenuCopiar, int []nChars,boolean []apagada ,int nLinhas,int nLinhasColar, Scanner input , int tamMax) {
		int linhaInicio = 0;
		int linhaFim = 0;
		nLinhasColar= 0;
		System.out.println("Cortar De: ");
		linhaInicio = input.nextInt() -1;
		System.out.println("Cortar Até: ");
		linhaFim = input.nextInt() -1;
		
		if(linhaInicio <= nLinhas && nLinhas <= tamMax && linhaInicio > 0) {
		for (int i = linhaInicio , j = 0; j  < linhaFim; i++, j++) {
			linhas[i] = copiarColar[j];
			apagada[i] = apagadaMenuCopiar[j];
			nChars[i] = nCharsMenuCopiar[j];
			nLinhasColar++;
		}

		for (int i = nLinhas; i >=linhaInicio; i--) {
			linhas[i] = linhas[i-nLinhasColar];
			apagada[i] = apagada[i-nLinhasColar];
			nChars[i] = nChars[i-nLinhasColar];
		}
	}return nLinhasColar;
	}

	private static int copiarDeXaY(String []linha, String []copiarColar, boolean []apagadaMenuCopiar, boolean[]apagada,int []nCharsMenuCopiar, int []nChars,int []nPalavras, int []nPalavrasMenuCopiar,int nLinhas, int nLinhasColar, Scanner input) {
		int linhaInicio = 0;
		int linhaFim = 0;
		System.out.println("digite o nr da linha desde onde quer copiar");
		linhaInicio = input.nextInt();

		System.out.println("digite o nr da linha até onde quer copiar");
		linhaFim= input.nextInt();

		for (int i = linhaInicio-1, j = 0; i < linhaFim; i++, j++) {
			copiarColar[j]=linha[i];
			apagadaMenuCopiar[j] = apagada[i];
			nCharsMenuCopiar[j] = nChars[i];
			nPalavrasMenuCopiar[j]= nPalavras[i];
			nLinhasColar++;
		}
		return nLinhasColar;
	}

	private static void menuFerramentas(String []linhas, int nLinhas, int []nChars, int []nPalavras, boolean []apagada, String auxiliar, Scanner input, char menus) {
		do {
			imprimirMenuFerramentas();
			auxiliar=input.nextLine();
			menus = protecaoEntrada(auxiliar);
			switch (menus){

			case 'm':
				mostrarLinha(linhas, apagada, nLinhas, input);
				break;
			case 's':
				substituirPalavra(linhas, input);
				break;
			case 'l':
				imprimirNrLinhas(apagada, nLinhas);
				break;
			case 'p':
				imprimirNrPalavras( nPalavras, apagada, nLinhas);
				break;
			case 'c':
				imprimirNrCaracteres( nChars, apagada, nLinhas);
				break;
			case 'v':
				break;
			default: System.out.println("Erro opção invalida");
			}
		}while(menus != 'v');
	}

	private static void imprimirNrCaracteres(int []nChars, boolean []apagada, int nLinhas) {
		int somaDeChars = 0;
		for (int j = 0; j < nLinhas; j++) {
			if (!apagada[j])
				somaDeChars += nChars[j];
		}

		System.out.println("O total de carateres é: " + somaDeChars);

	}

	private static void imprimirNrPalavras(int [] nPalavras, boolean []apagada, int nLinhas) {
		int somaDePalavras = 0;
		for (int j = 0; j < nLinhas; j++) {
			if (!apagada[j])
				somaDePalavras += nPalavras[j];
		}

		System.out.println("O total de palavras é: " + somaDePalavras);
	}

	private static void imprimirNrLinhas(boolean []apagada, int nLinhas) {
		int somador = 0;
		for (int j = 0; j < nLinhas; j++) {
			if (apagada[j]) {
				somador++;
			}
		}
		System.out.println(" O nr de linhas totais é: " + nLinhas + " das quais " + somador + " estão apagadas.");

	}

	private static void substituirPalavra(String []linhas, Scanner input) {

		String palavraAntiga = "";
		int linhaUtilizador = 0;
		int arrayDeInicio = 0;
		String novaLinha = "";
		String palavraNova = "";
		String linhaParte1;
		String linhaParte2;


		System.out.println("Insira o número da linha onde quer fazer a mudança");
		linhaUtilizador = input.nextInt();
		input.nextLine();

		System.out.println("Insira a palavra que quer mudar");
		palavraAntiga = input.nextLine();

		System.out.println("Insira a palavra nova que quer inserir");
		palavraNova = input.nextLine();


		arrayDeInicio = linhas[linhaUtilizador-1].indexOf(palavraAntiga);

		if (arrayDeInicio <0) {
			System.out.println(" palavra nao econtrada ");
		}
		else {

			linhaParte1 = linhas[linhaUtilizador-1].substring (0,arrayDeInicio);
			linhaParte2 = linhas[linhaUtilizador-1].substring(palavraAntiga.length() + arrayDeInicio,linhas[linhaUtilizador-1].length());
			novaLinha = linhaParte1 + palavraNova + linhaParte2;
			linhas[linhaUtilizador-1] = novaLinha;
			System.out.println("A linha alterada ficou: ");
			System.out.println(novaLinha);
		}

	}

	private static void mostrarLinha(String []linhas, boolean []apagada, int nLinhas, Scanner input) {
		int arrayDeInicio = 0;
		String palavraProcurada;
		System.out.println("Qual a palavra que pretende procurar? ");
		palavraProcurada = input.nextLine();
		for (int j = 0; j < nLinhas; j++) {
			if(!apagada[j]) {
				arrayDeInicio = linhas[j].indexOf(palavraProcurada);
				if(arrayDeInicio != -1) {
					System.out.println( j+1 + "ª: "+ linhas[j]);
				}
			}
		}
	}

	private static int menuEditar (String []linhas, int []nChars, int []nPalavras, int nLinhas, boolean []apagada, String auxiliar, Scanner input, char menus, int tamMax) {
		do {
			imprimirMenuEditar();
			auxiliar=input.nextLine();
			menus = protecaoEntrada(auxiliar);

			switch (menus){
			case 'i':nLinhas = inserirLinhasEmN(linhas, nChars, apagada, nPalavras, nLinhas, input, tamMax);
			break;
			case 'a':apagarLinhaEmN(apagada, nLinhas, input);
			break;
			case 'l':apagarDeXaY(apagada ,  nLinhas, input);
			break;
			case 'r':recuperarLinhasApagadas(linhas, apagada,   nLinhas, input);
			break;
			case 'e':
				nLinhas = eleminarapagadas(linhas, nChars, apagada, nLinhas);
				break;
			case 'v':
				break;
			default: System.out.println("Erro opção invalida");
			}
		}while(menus != 'v');
		return nLinhas;
	}

	private static void recuperarLinhasApagadas(String[] linhas, boolean[] apagada, int nLinhas, Scanner input) {
		int linhaRecuperada = 0;
		boolean haLinhasPorRecuperar = false;
		for (int j = 0; j < nLinhas; j++) {
			if(apagada[j]) {
				System.out.println((j+1) + " : " + linhas[j]);
				haLinhasPorRecuperar = true;
			}

		}

		if(haLinhasPorRecuperar) {
			System.out.println("Qual a linha que quer recuperar?");
			linhaRecuperada= input.nextInt();
			input.nextLine();

			if (apagada[linhaRecuperada-1]) {
				apagada[linhaRecuperada-1] = false;
				System.out.println("linha " + linhaRecuperada + " recuperada");
			}else
				System.out.println("Erro de utilizador");
		}
		else
			System.out.println("Não há linhas por recuperar");


	}

	private static void apagarDeXaY(boolean[] apagada, int nLinhas, Scanner input) {
		int apagarN;
		int apagarM;
		System.out.println("inserir o nr da linha A PARTIR de onde quer apagar");
		apagarN= input.nextInt();
		input.nextLine();
		if (apagarN > 0 && apagarN-1 < nLinhas) {

			System.out.println("inserir o nr da linha ATÉ onde quer apagar");
			apagarM= input.nextInt();
			input.nextLine();

			if(apagarM >0 && apagarM-1 <nLinhas) {
				for (int j = apagarN-1; j < apagarM; j++) {
					apagada[j] = true;

				}System.out.println("Linha(s) Apagada(s)");

			}else 
				System.out.println("Erro do utilizador");



		}else System.out.println("Erro do utilizador");

	}

	private static void apagarLinhaEmN(boolean []apagada, int nLinhas, Scanner input) {
		System.out.println("inserir o nr da linha que quer apagar");
		int linhaapagar;
		linhaapagar= input.nextInt();
		input.nextLine();
		if (linhaapagar <nLinhas && linhaapagar >0) {
			apagada [linhaapagar-1]= true;
			System.out.println("A linha " + linhaapagar + " foi apagada.");
		}
		else 
			System.out.println("Erro do utilizador, volta a tentar");

	}

	private static int inserirLinhasEmN(String []linhas, int []nChars, boolean []apagada, int []nPalavras, int nLinhas, Scanner input, int tamMax) {

		int i = 0;
		String linhaT = new String();
		int contadorLinhas = 0;
		int contadorChars = 0;
		int inserirLinha = 0;

		System.out.println(" insira o numero da linha onde pretende inserir uma nova");
		inserirLinha = input.nextInt();
		input.nextLine();
		if(inserirLinha <= nLinhas && nLinhas <= tamMax && inserirLinha > 0) {
			System.out.println("insira o texto: ");
			linhaT = input.nextLine();
			for (i = nLinhas; i >= inserirLinha-1; i--) {
				linhas[i+1] = linhas[i];
				apagada[i+1] = apagada[i];
				nChars[i+1] = nChars[i];
			}
			for (i = 0; i < linhaT.length(); i++) {

				if (linhaT.charAt(i) == ' ') {
					contadorLinhas ++;
					i = i+1;
				}
				if( i == linhaT.length()-1) {	
					contadorLinhas++;
				}
			}
			for (i = 0; i < linhaT.length(); i++) {
				if (linhaT.charAt(i) != ' ')
					contadorChars ++;
			}
			linhas[inserirLinha-1]=linhaT;
			apagada[inserirLinha-1] = false;
			nPalavras[nLinhas] = contadorLinhas;
			nChars[nLinhas] = contadorChars;
			nLinhas ++;
			System.out.println("Operação concluida");
		}else
			System.out.println("Operação invalida, reveja os limites de linhas e o numero inserido");
		return nLinhas;
	}

	private static int eleminarapagadas(String []linhas, int []nChars, boolean []apagada, int nLinhas) {
		int i = 0;
		do  {
			if (apagada[i]) {
				for (int j = i; j < nLinhas-1; j++) {
					linhas[j] = linhas[j+1];
					apagada[j] = apagada[j+1];
					nChars[j] = nChars[j+1];
				}
				nLinhas--;
			}
			else 
				i++;
		}while (i < nLinhas);
		System.out.println("Linha(s) apagada(s)");
		return nLinhas;
	}

	private static void apagarUltima(int nLinhas, boolean []apagada) {
		int i= nLinhas-1;
		while(i >= 0 && apagada[i]) {
			i--;
		}
		if(i >= 0) {
			apagada [i] = true;
			System.out.println("Ultima linha apagada");
		}
		else System.out.println("Não há nada para apagar");

	}

	private static void listar(String []linhas, int nLinhas, boolean []apagada) {
		if(nLinhas > 0) {
			for (int i = 0; i < nLinhas; i++) {

				if (!apagada [i] ) {	//quando as linhas estao todas apagadas ele não faz nada
					System.out.println(i+1+ "ª: " + linhas[i]);//vai imprimir e numerar cada linha
				}
			}
		}else System.out.println("Não há nada para listar");

	}

	private static int inserirTexto(String []linhas, int nLinhas, int []nChars, int []nPalavras, boolean []apagada, Scanner input) {
		String linhaT = new String();
		do {
			if( nLinhas< linhas.length) {//insere um linha do utilizador
				linhaT = input.nextLine();
				if (linhaT.length() != 0) {//se nao ouver nada gravado na linha 
					linhas[nLinhas] =linhaT;//o que for inserido vai gravar nessa posiçao
					nChars[nLinhas] = 0;
					for (int i = 0; i < linhaT.length(); i++) {

						if (linhaT.charAt(i) == ' ') {
							nPalavras[nLinhas] ++;
							i = i+1;
						}
						if( i == linhaT.length()-1) {	
							nPalavras[nLinhas]++;
						}
					}
					for ( int i = 0; i < linhaT.length(); i++) {
						if (linhaT.charAt(i) != ' ')
							nChars[nLinhas] ++;
					}
					//gravar os caracteres da linha
					apagada[nLinhas] = false;//para assegurar que a linha não inicializa apagada
					nLinhas++;//adiciona uma linha ao nr de linhas

				}
			}


		}while(!linhaT.equals(""));
		return nLinhas;
	}

	private static char protecaoEntrada(String auxiliar) {
		if(auxiliar.length()!=0) {
			char letraT=auxiliar.charAt(0);
			if ( letraT >= 'A' && letraT <= 'Z' )//transforma todas as letras em minusculas
				letraT = (char)(letraT +32);
			return letraT;
		}
		return 'z';
	}

	private static void imprimirMenuCopiar() {
		System.out.println("		[Menu Copiar/Colar]		");
		System.out.println("	Co(p)iar para memória da linha m à n.");
		System.out.println("	Co(r)tar para memória da linha m à n");
		System.out.println("	(I)nserir da memória a partir da linha m");
		System.out.println("	(S)ubstituir da memória a partir da linha m");
		System.out.println("	(V)oltar ");
		System.out.println();
	}

	private static void imprimirMenuFerramentas() {
		System.out.println("		[Menu Ferramentas]		");
		System.out.println("	(M)ostrar linhas onde ocorre a palavra p.");
		System.out.println("	(S)ubstituir a palavra p na linha n.");
		System.out.println("	Mostrar número (L)inhas.");
		System.out.println("	Mostrar número (P)alavras.");
		System.out.println("	Mostrar número (C)aracteres.");
		System.out.println("	(V)oltar ");
		System.out.println();	
	}

	private static void imprimirMenuEditar() {
		System.out.println("		[Menu Editar]		");
		System.out.println("	(I)nserir linhas na posição n");
		System.out.println("	(A)pagar linha posição n ");
		System.out.println("	Apagar (L)inhas da posição n à posição m ");
		System.out.println("	(R)ecuperar linha");
		System.out.println("	(E)liminar linhas apagadas");
		System.out.println("	(V)oltar ");
		System.out.println();
	}

	private static void imprimirMenuPrincipal() {
		System.out.println("		[Menu Principal]		");
		System.out.println("	(I)nserir linhas no fim (termine com uma linha vazia)");
		System.out.println("	(L)istar linhas");
		System.out.println("	(A)pagar última linha");
		System.out.println("	(E)ditar");
		System.out.println("	(F)erramentas");
		System.out.println("	(C)opiar/colar");
		System.out.println("	(S)air");
		System.out.println();
	}

}



