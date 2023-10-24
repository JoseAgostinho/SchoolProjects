import java.io.*;
import java.util.Scanner;

public class ArvoreBinaria{
	//-------------------------------------------------------------------------------
	private No raiz;
	private int nElems;
	//-------------------------------------------------------------------------------	
	private class No{		
		int oElemento;
		No esq;
		No dir;
		No pai;

		No (int elem){
			esq=dir=pai=null;
			oElemento=elem; 
		}
	}
	//-------------------------------------------------------------------------------
	ArvoreBinaria(){
		nElems=0;
		raiz=null;
	}
	//-------------------------------------------------------------------------------
	public boolean inserir(int elem){
		No novoNo=new No(elem);
		No anterior=null;
		No actual=raiz;
		if(raiz==null){
			raiz=novoNo;
			nElems=1;
			return true;
		}
		while(actual!=null){
			anterior=actual;
			if(actual.oElemento==elem)return false;
			if(elem>actual.oElemento)actual=actual.dir;
			else actual=actual.esq;
		}
		if(elem>anterior.oElemento)anterior.dir = novoNo;
		else anterior.esq=novoNo;
		novoNo.pai=anterior;
		nElems++;
		return true;
	}
	//-------------------------------------------------------------------------------
	public boolean lerFicheiro(String nomeDoFicheiro){
		File file = new File(nomeDoFicheiro);
		nElems=0;
		raiz=null;
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String tmp=sc.nextLine();
				if(!tmp.isEmpty())inserir(Integer.parseInt(tmp));
			}
			sc.close();
			return true;
		} 
		catch (Exception e) {
			return false;
		}			
	}
	//-------------------------------------------------------------------------------
	public boolean gravarFicheiro(String nomeDoFicheiro){		
		try {
			PrintWriter writer=new PrintWriter(nomeDoFicheiro);
			inserirFicheiro(writer);
			writer.close();
			return true;
		} 
		catch (Exception e) {
			return false;
		}	
	}
	//-------------------------------------------------------------------------------
	private void inserirFicheiro(PrintWriter writer){
		if(nElems==0){
			System.out.println ("Árvore vazia.");
			return;
		}
		writer.println("Esta árvore tem "+nElems+ " elementos:");
		Fila nos=new Fila();
		Fila nivel=new Fila();
		nos.adicionar(raiz);
		nivel.adicionar(new Integer(1));
		while(nos.temElementos()){
			No noTmp=(No)(nos.retirar());
			Integer nivelTmp=(Integer)(nivel.retirar());
			writer.println(noTmp.oElemento);
			if(noTmp.esq!=null){
				nos.adicionar(noTmp.esq);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));
			}
			if(noTmp.dir!=null){
				nos.adicionar(noTmp.dir);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));
			}
		}
		/*
		 * o ficheiro já se encontra aberto 
		 * e recebe um PrintWriter já criado e devidamente inicializado
		 *
		 * para escrever nele basta fazer:
		     writer.println("escreve qualquer coisa no ficheiro");
		 * é preciso percorrer a arvore pela ordem certa 
		 * (por nivel da raiz para as folhas) e 
		 * escrever cada valor para o ficheiro
		 * 
		 */

	}
	//-------------------------------------------------------------------------------
	public Fila imprimirNivel(int niv) {
		/*
		 * é necessário percorrer a arvore toda e adicionar à fila
		 * os elementos que estão no nivel niv
		 * 
		 */
		Fila valores=new Fila();
		//---------------------------
		if(nElems==0){
			System.out.println ("Árvore vazia.");
			return null;
		}				
		Fila nos = new Fila();
		Fila nivel = new Fila();
		nos.adicionar(raiz);		
		nivel.adicionar(new Integer(1));				

		while(nos.temElementos()){

			No noTmp = (No)(nos.retirar());
			Integer nivelTmp=(Integer)(nivel.retirar());

			if(noTmp.esq != null){
				nos.adicionar(noTmp.esq);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));
			}
			if(noTmp.dir != null){
				nos.adicionar(noTmp.dir);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));
			}
			if ( niv == nivelTmp.intValue()) {
				valores.adicionar(noTmp.oElemento);
			}

		}
		return valores;
	}
	//-------------------------------------------------------------------------------	
	public Fila procurarIntervalo(int ini,int fim){
		/*
		 * é necessário percorrer a arvore toda e adicionar à fila
		 * os elementos que estão no intervalo [ini,fim]
		 * 
		 */

		Fila valores=new Fila();
		//---------------------------
		if(nElems==0){
			System.out.println ("Árvore vazia.");
			return null;
		}				
		Fila nos = new Fila();
		Fila nivel = new Fila();
		nos.adicionar(raiz);		
		nivel.adicionar(new Integer(1));				

		while(nos.temElementos()){



			No noTmp = (No)(nos.retirar());
			Integer nivelTmp=(Integer)(nivel.retirar());
			

			if(noTmp.esq != null){
				nos.adicionar(noTmp.esq);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));
				
			}
			if(noTmp.dir != null){
				nos.adicionar(noTmp.dir);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));	
			}
			
			if ( noTmp.oElemento >= ini &&  noTmp.oElemento <= fim) {
				valores.adicionar(noTmp.oElemento);
			}
		}

		return valores;
	}

	//-------------------------------------------------------------------------------
	public Fila procurarFolhas(){
		Fila valores=new Fila();
		/*
		 * é necessário percorrer a arvore toda e adicionar à fila
		 * os elementos que são folhas
		 * 
		 */					

		if(nElems==0){
			System.out.println ("Árvore vazia.");
			return null;
		}				
		Fila nos = new Fila();
		Fila nivel = new Fila();
		nos.adicionar(raiz);		
		nivel.adicionar(new Integer(1));				

		while(nos.temElementos()){

			No noTmp = (No)(nos.retirar());
			Integer nivelTmp=(Integer)(nivel.retirar());

			if(noTmp.esq==null && noTmp.dir==null) {
				valores.adicionar(noTmp.oElemento);
			}

			if(noTmp.esq != null){
				nos.adicionar(noTmp.esq);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));
			}
			if(noTmp.dir != null){
				nos.adicionar(noTmp.dir);
				nivel.adicionar(new Integer(nivelTmp.intValue()+1));
			}			
		}
		return valores;
	}
	//-------------------------------------------------------------------------------
	public int getNElems(){
		return nElems; 
	}
	//-------------------------------------------------------------------------------	
	private No procuraNo(int n){
		No actual=raiz;
		while(actual!=null && n!=actual.oElemento){
			if(n>actual.oElemento)actual=actual.dir;
			else actual=actual.esq;
		}	
		return actual; 
	}
	//-------------------------------------------------------------------------------	
	public boolean procura(int n){
		No tmp=procuraNo(n);
		return (tmp!=null); 
	}
	//-------------------------------------------------------------------------------
	private No menorNo(No inicio){
		No actual=inicio;
		if(inicio==null )return null;
		while(actual.esq!=null)actual=actual.esq;
		return actual;
	}
	//-------------------------------------------------------------------------------
	private int menor(No inicio){
		No tmp=menorNo(inicio);
		return tmp.oElemento;
	}
	//-------------------------------------------------------------------------------
	public int menor(){
		return menor(raiz);
	}
	//-------------------------------------------------------------------------------
	private No maiorNo(No inicio){
		No actual=inicio;
		if(inicio==null )return null;
		while(actual.dir!= null)actual = actual.dir;
		return actual;
	}
	//-------------------------------------------------------------------------------
	private int maior(No inicio){
		No tmp=maiorNo(inicio);
		return tmp.oElemento;
	}
	//-------------------------------------------------------------------------------
	public int maior(){
		return maior(raiz);
	}
	//-------------------------------------------------------------------------------
	public boolean eliminar(int n){
		No aEliminar=procuraNo(n);
		if(aEliminar==null)return false;		
		//o nó a eliminar não tem filhos
		if(aEliminar.esq==null&&aEliminar.dir==null){
			if(raiz==aEliminar){
				raiz=null;
				nElems=0;
				return true;
			}
			if(aEliminar.pai.dir==aEliminar)aEliminar.pai.dir=null;
			else aEliminar.pai.esq=null;
			nElems--;
			return true;
		}		
		//o nó a eliminar tem 2 filhos
		if(aEliminar.esq!=null&&aEliminar.dir!=null){
			No menorDoLadoDir=menorNo(aEliminar.dir);
			eliminar(menorDoLadoDir.oElemento);		 //chamada (semi)recursiva	
			aEliminar.oElemento=menorDoLadoDir.oElemento;	
			return true;
		}		
		//o nó a eliminar só tem 1 filho (é o que sobra, nem é preciso teste)
		if(raiz==aEliminar){
			if(aEliminar.dir!=null)raiz=aEliminar.dir;
			else raiz=aEliminar.esq;
			nElems--;
			return true;
		}
		No filho;
		if(aEliminar.dir!=null)filho=aEliminar.dir;
		else filho=aEliminar.esq;
		if(aEliminar.pai.dir==aEliminar)aEliminar.pai.dir=filho;
		else aEliminar.pai.esq=filho;
		filho.pai=aEliminar.pai;
		nElems--;
		return true;
	}
	//-------------------------------------------------------------------------------
	public int getMaxDepth(){
		return (maxHeight(raiz)-1);
	}
	//-------------------------------------------------------------------------------
	private int maxHeight(No node){
		if (node==null)return 0;
		int left_height=maxHeight(node.esq);
		int right_height=maxHeight(node.dir);
		return (left_height>right_height)?left_height+1:right_height+1;
	}
	//-------------------------------------------------------------------------------
	public double grauBalanceamento(){
		if(nElems<=2)return 100.0;
		double nivelOptimo=1.0+log2(nElems);
		return 100.0*
				(1.0-(((double)maxHeight(raiz)-nivelOptimo)/
						((double)nElems-nivelOptimo)));
	}
	//-------------------------------------------------------------------------------
	private double log2(int bits){
		if(bits==0)return 0;
		return 31.0-((double)Integer.numberOfLeadingZeros(bits));
	}
	//-------------------------------------------------------------------------------
	public void imprimeConteudoOrdenado(){
		System.out.println ("Esta árvore tem "+nElems+ " elementos:");
		listarOrdem (raiz);
	}
	//-------------------------------------------------------------------------------
	private void listarOrdem(No inicio){
		if(inicio==null)return;
		listarOrdem(inicio.esq);
		System.out.println(inicio.oElemento);
		listarOrdem(inicio.dir);
	}
	//-------------------------------------------------------------------------------
	public void imprimeConteudoNivel(){
		if(nElems==0){
			System.out.println ("Árvore vazia.");
			return;
		}
		System.out.println ("Esta árvore tem "+nElems+ " elementos:");
		Fila nos=new Fila();
		Fila nivel=new Fila();
		nos.adicionar(raiz);
		nivel.adicionar(Integer.valueOf(1));
		while(nos.temElementos()){
			No noTmp=(No)(nos.retirar());
			Integer nivelTmp=(Integer)(nivel.retirar());
			System.out.println("nivel:"+nivelTmp+" valor:"+noTmp.oElemento);
			if(noTmp.esq!=null){
				nos.adicionar(noTmp.esq);
				nivel.adicionar(Integer.valueOf(nivelTmp.intValue()+1));
			}
			if(noTmp.dir!=null){
				nos.adicionar(noTmp.dir);
				nivel.adicionar(Integer.valueOf(nivelTmp.intValue()+1));
			}
		}		
	}
	//-------------------------------------------------------------------------------
	public void DSW(){
		if(null!=raiz){
			createBackbone();
			createPerfectBST();
		}
	}
	//-------------------------------------------------------------------------------
	private void createBackbone(){
		No grandParent = null;
		No parent = raiz;
		No leftChild;
		while(null != parent){
			leftChild = parent.esq;
			if (null!=leftChild){
				grandParent=rotateRight(grandParent,parent, leftChild);
				parent=leftChild;
			}
			else{
				grandParent=parent;
				parent=parent.dir;
			}
		}
	}
	//-------------------------------------------------------------------------------
	private No rotateRight(No grandParent,No parent,No leftChild){
		if(null!=grandParent)grandParent.dir=leftChild;
		else raiz=leftChild;
		parent.esq=leftChild.dir;
		leftChild.dir=parent;
		return grandParent;
	}
	//-------------------------------------------------------------------------------
	private void createPerfectBST(){
		int n=0;
		for(No tmp=raiz;null!=tmp;tmp=tmp.dir)n++;
		int m=greatestPowerOf2LessThanN(n + 1) - 1;
		makeRotations(n-m);
		while(m>1)makeRotations(m/=2);
	}

	//-------------------------------------------------------------------------------
	private int greatestPowerOf2LessThanN(int n){
		int x=MSB(n);
		return(1 << x);
	}
	//-------------------------------------------------------------------------------
	public int MSB(int n){
		int ndx=0;
		while(1 < n){
			n=(n >> 1);
			ndx++;
		}
		return ndx;
	}
	//-------------------------------------------------------------------------------
	private void makeRotations(int bound){
		No grandParent=null;
		No parent=raiz;
		No child=raiz.dir;
		for(;bound>0;bound--){
			try{
				if(null!=child){
					rotateLeft(grandParent,parent,child);
					grandParent=child;
					parent=grandParent.dir;
					child=parent.dir;
				}
				else break;
			}catch(NullPointerException convenient){
				break;
			}
		}
	}
	//-------------------------------------------------------------------------------
	private void rotateLeft(No grandParent,No parent,No rightChild){
		if(null!=grandParent)grandParent.dir=rightChild;
		else raiz=rightChild;
		parent.dir=rightChild.esq;
		rightChild.esq=parent;
	}
	//-------------------------------------------------------------------------------
}

