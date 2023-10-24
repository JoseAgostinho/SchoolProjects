package listasLigadas;

public class ListasLigadas   {

	private int nItems;
	private No  cabeca;

	private class No {
		/** cria um novo n� com um dado elemento */
		No( Object e ) {
	          item = e;     // o item passa a ser o Object
	          prox = null;  // o n� � inicializado com
                            // prox a null (n�o h� pr�ximo)
		}
		No prox;
		Object item;
	}
	
	/** Inicializa a lista. */
	public ListasLigadas( ){
	    cabeca = null;      // n�o h� n�s
	    nItems = 0;     // n�o h� Objects 
	}
	
	public int getTamanho() {
		return nItems;
	}	
	
	/**
	 * insere um Object na lista, na respectiva ordem.<br>
	 * Nem todas as listas podem implementar este m�todo! 
	 * @param e Object a adicionar 
	 */ 
// S� funciona com objectos que tenham o m�todo compareTo implementado 
	public void inserirOrdem( Chave c )	{
	    No novoNo = new No( c );  
	    No actual = cabeca;
	    No anterior = null;

	    // procurar o local onde inserir
	    while( actual != null && ((Chave) actual.item).compareTo( c ) < 0 ) {
	        anterior = actual;
	        actual = actual.prox;
	    }

	    novoNo.prox = actual;
	    if( anterior == null )
	        cabeca = novoNo;
	    else
	        anterior.prox = novoNo;
	    nItems++;
	}
	
	/**
	 * Indica se o �ndice idx � um �ndice v�lido nesta lista.<br>
	 * @param idx �ndice a avaliar
	 */
	public boolean eIndiceValido( int idx ){
		return idx >= 0 && idx < nItems;
	}
	
	/**
	 * Acede a um Object da lista 
	 * @param idx posi��o do Object a aceder
	 * @throws ArrayIndexOutOfBoundsException quando idx � inv�lida  
	 */ 
	public Object get( int idx )	{
	    if( !eIndiceValido( idx ) )
	    	throw new ArrayIndexOutOfBoundsException( idx );
	    
	    No actual = cabeca;
	    int pos = 0;
	    while( actual != null && pos < idx ) {
	        pos++;
	        actual = actual.prox;
	    }
	    return actual.item; 
	}

}
