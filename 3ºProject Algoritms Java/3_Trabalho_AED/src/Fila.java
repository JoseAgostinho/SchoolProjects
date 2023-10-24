public class Fila{
/*
 * Classe que implementa uma Fila (queue) em que o primeiro a chegar 
 * é o primeiro a ser atendido, ou seja:
 * insere os novos elementos na cauda e retira da cabeça 
 * 
 * trabalha com Object e é necessário fazer um cast type quando se 
 * retiram elementos
 * 
 * */
//-------------------------------------------------------------------------------
	private int numeroElementos;
	private No primeiro;
	private No ultimo;	
//-------------------------------------------------------------------------------
	public Fila(){
		numeroElementos=0;
		primeiro=ultimo=null;
	}
//-------------------------------------------------------------------------------	
	private class No{		
		Object oElemento;
		No prox;			
		No (Object obj){
			oElemento=obj; 
			prox=null;
		}
	}
//-------------------------------------------------------------------------------
	public void adicionar(Object obj){
		No novo=new No(obj);
		if(primeiro==null)primeiro=ultimo=novo;
		else{
			ultimo.prox=novo;
			ultimo=ultimo.prox;
		}
		numeroElementos++;
	}
//-------------------------------------------------------------------------------
	public Object retirar(){
		if(primeiro==null)return null;
		No tmp=primeiro;
		primeiro=primeiro.prox;
		numeroElementos--;
		return tmp.oElemento;
	}
//-------------------------------------------------------------------------------
	public boolean temElementos(){
		return numeroElementos>0;
	}
//-------------------------------------------------------------------------------
	public void esvaziar(){
		numeroElementos=0;
		primeiro=ultimo=null;
	}
//-------------------------------------------------------------------------------
	public String toString(){
		String tmp="";
		No actual;
		if(numeroElementos==0)tmp="A Fila encontra-se vazia.";
		else
		{
			int i=1;
			actual=primeiro;
			do{
				tmp+=("["+i+"]="+actual.oElemento+"\n");
				i++;
				actual=actual.prox;
			}while(actual!=null);			
		}
		return tmp;
	}
//-------------------------------------------------------------------------------
}
