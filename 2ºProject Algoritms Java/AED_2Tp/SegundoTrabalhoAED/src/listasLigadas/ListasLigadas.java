package listasLigadas;

public class ListasLigadas   {

	private int nItems;
	private No  cabeca;

	private class No {
		/** cria um novo nó com um dado elemento */
		No( Object e ) {
	          item = e;     // o item passa a ser o Object
	          prox = null;  // o nó é inicializado com
                            // prox a null (não há próximo)
		}
		No prox;
		Object item;
	}
	
	/** Inicializa a lista. */
	public ListasLigadas( ){
	    cabeca = null;      // não há nós
	    nItems = 0;     // não há Objects 
	}
	
	public int getTamanho() {
		return nItems;
	}	
	
	/**
	 * insere um Object na lista, na respectiva ordem.<br>
	 * Nem todas as listas podem implementar este método! 
	 * @param e Object a adicionar 
	 */ 
// Só funciona com objectos que tenham o método compareTo implementado 
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
	 * Indica se o índice idx é um índice válido nesta lista.<br>
	 * @param idx índice a avaliar
	 */
	public boolean eIndiceValido( int idx ){
		return idx >= 0 && idx < nItems;
	}
	
	/**
	 * Acede a um Object da lista 
	 * @param idx posição do Object a aceder
	 * @throws ArrayIndexOutOfBoundsException quando idx é inválida  
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
