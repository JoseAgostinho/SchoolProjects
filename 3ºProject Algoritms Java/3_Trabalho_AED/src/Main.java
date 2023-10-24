import java.util.Scanner;
//-------------------------------------------------------------------------------
public class Main {
	private static Scanner in;
	private static ArvoreBinaria arv;
//-------------------------------------------------------------------------------
	public static void main(String[] args) {
		in=new Scanner(System.in);
		arv=new ArvoreBinaria();
		int opcao;
		do{
			menu();
			opcao=lerInteiro();
			switch(opcao){
			case 1:lerFicheiro(); break;
			case 2:imprimirOrdem(); break;
			case 3:imprimirPorNivel(); break;
			case 4:imprimirNivel(); break;
			case 5:procurarIntervalo(); break;
			case 6:imprimirFolhas(); break;
			case 7:verificarBalanceamento(); break;
			case 8:balancear(); break;
			case 9:gravarFicheiro(); break;
			case 0:
				break;
			default:
				System.out.println("OPCAO INVALIDA");
			}
		}while(opcao!=0);
		in.close();
	}
//-------------------------------------------------------------------------------	
	public static void menu(){
		System.out.println("MENU\n");
		System.out.println("1 - ler ficheiro");
		System.out.println("2 - imprimir a arvore ordenada");
		System.out.println("3 - imprimir a arvore por niveis");
		System.out.println("4 - imprimir os valores de um determinado nivel");
		System.out.println("5 - procurar valores num intervalo");
		System.out.println("6 - imprimir as folhas");
		System.out.println("7 - verificar balanceamento");
		System.out.println("8 - balancear a arvore");
		System.out.println("9 - gravar ficheiro");
		System.out.println("0 - terminar");
		System.out.print("\nOpcao: ");
	}
//-------------------------------------------------------------------------------
	public static int lerInteiro(){
		int num=0;
		boolean ok;
		do{
			ok=true;
			try{
				num=in.nextInt();
			}
			catch(Exception e){
				in.nextLine();
				System.out.println("INPUT INVALIDO\n");
				ok=false;
			}			
		}while(!ok);
		in.nextLine();
		return num;
	}
//-------------------------------------------------------------------------------
	private static void lerFicheiro() {
		System.out.println("Insira o nome do ficheiro:");
		String nomeFicheiro=in.nextLine();
		if(arv.lerFicheiro(nomeFicheiro))
			System.out.println("Ficheiro lido e inserido na arvore com sucesso.");
		else
			System.out.println("ERRO: Ficheiro não lido.");		
	}
//-------------------------------------------------------------------------------
	private static void imprimirOrdem() {
		arv.imprimeConteudoOrdenado();
	}
//-------------------------------------------------------------------------------
	private static void imprimirPorNivel() {
		arv.imprimeConteudoNivel();
	}
//-------------------------------------------------------------------------------
private static void imprimirNivel() {
	int niv=0;
	System.out.println("introduza o nivel a imprimir:");
	niv=lerInteiro();

	Fila resultado=arv.imprimirNivel(niv);
	if(!resultado.temElementos())
		System.out.println("Não foram encontrados valores nesse nivel");
	else{
		System.out.println("os valores do nivel "+niv+" da arvore são:");
		while(resultado.temElementos()){
			Integer tmp=(Integer)resultado.retirar();
			System.out.println(tmp);
		}
	}
}
//-------------------------------------------------------------------------------
	private static void procurarIntervalo() {
		int ini=0,fim=0;
		System.out.println("introduza o limite inferior:");
		ini=lerInteiro();
		System.out.println("introduza o limite superior:");
		fim=lerInteiro();
		Fila resultado=arv.procurarIntervalo(ini,fim);
		if(!resultado.temElementos())
			System.out.println("Não foram encontrados valores");
		else{
			System.out.println("os valores no intervalo ["+ini+","+fim+"] são:");
			while(resultado.temElementos()){
				Integer tmp=(Integer)resultado.retirar();
				System.out.println(tmp);
			}
		}
	}
	//-------------------------------------------------------------------------------
		private static void imprimirFolhas() {
			Fila resultado=arv.procurarFolhas();
			if(!resultado.temElementos())
				System.out.println("Não foram encontrados folhas");
			else{
				System.out.println("As folhas da arvore são:");
				while(resultado.temElementos()){
					Integer tmp=(Integer)resultado.retirar();
					System.out.println(tmp);
				}
			}
		}
//-------------------------------------------------------------------------------
	private static void verificarBalanceamento() {
		System.out.println("A arvore está "+arv.grauBalanceamento()+
				"% balanceada");
	}
//-------------------------------------------------------------------------------
	private static void balancear() {
		arv.DSW();
		System.out.println("Arvore Balanceada!");
	}
//-------------------------------------------------------------------------------
	private static void gravarFicheiro() {
		System.out.println("Insira o nome do ficheiro:");
		String nomeFicheiro=in.nextLine();
		if(arv.gravarFicheiro(nomeFicheiro))
			System.out.println("Ficheiro escrito com sucesso.");
		else
			System.out.println("ERRO: Ficheiro não escrito.");
	}
//-------------------------------------------------------------------------------
}
