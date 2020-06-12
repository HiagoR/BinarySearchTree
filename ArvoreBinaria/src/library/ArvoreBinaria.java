package library;

public class ArvoreBinaria {
    private No raiz;
    private int totalValor = 0;
  
    public ArvoreBinaria() 
    { 
      this.raiz = null; 
    } 

    public No menorValor() {
        No atual = raiz;
        No anterior = null;
        while (atual != null) {
          anterior = atual;
          atual = atual.esquerda;
        }
  
        return anterior;
      }
    
      public No maiorValor() {
        No atual = raiz;
        No anterior = null;
        while (atual != null) {
          anterior = atual;
          atual = atual.direita;
        }
  
        return anterior;
      }

      public float mediaValor() {
        if (this.estaVazia()) return 0;

        return this.totalValor / totalDeNos(this.raiz);
      }
  
    public No buscar(long chave) {
      if (this.estaVazia()) return null;

      No atual = raiz;

      while (atual.elemento != chave) {
        if (chave < atual.elemento) {
            atual = atual.esquerda;
        }
        else {
            atual = atual.direita;
        }         
      }

      return atual == null ? null : atual; 
    }

    public void getStatus() throws InterruptedException {
        System.out.println();
        System.out.println("\n Aguarde um momento estamos montando o status....");
        Thread.sleep(1000);
  
        System.out.println("\n Exibindo em ordem: ");
        emOrdem(raiz);
        System.out.println("\n Exibindo em pós-ordem: ");
        posOrdem(raiz);
        System.out.println("\n Exibindo em pré-ordem: ");
        preOrdem(raiz);
        System.out.print("\n Altura da árvore: " + altura(raiz));
        System.out.print("\n Quantidade de folhas: " + totalDeFolhas(raiz));
        System.out.print("\n Quantidade de Nós: " + totalDeNos(raiz));
  
        if (!this.estaVazia()) {
           System.out.print("\n Valor máximo: " + maiorValor().elemento);
           System.out.print("\n Valor minimo: " + menorValor().elemento);
           System.out.print("\n Valor médio: " + mediaValor());
        }
    }
  
    public boolean remover(long elemento) {
      if (this.estaVazia()) return false;
  
      No atual = raiz;
      No pai = raiz;
      boolean filhoEsquerdo = true;
  
      while (atual.elemento != elemento) {
        pai = atual;
        if(elemento < atual.elemento ) { 
          atual = atual.esquerda;
          filhoEsquerdo = true;
        }
        else {
          atual = atual.direita; 
          filhoEsquerdo = false;
        }
        if (atual == null) return false;
      }

      this.totalValor -= elemento;
  
      if (atual.esquerda == null && atual.direita == null) {
        if (atual == raiz ) raiz = null;
        else if (filhoEsquerdo) pai.esquerda = null;
             else pai.direita = null;
      }
  
      else if (atual.direita == null) {
         if (atual == raiz) raiz = atual.esquerda;
         else if (filhoEsquerdo) pai.esquerda = atual.esquerda;
              else pai.direita = atual.esquerda;
      }
      
      else if (atual.esquerda == null) {
         if (atual == raiz) raiz = atual.direita;
         else if (filhoEsquerdo) pai.esquerda = atual.direita;
              else pai.direita = atual.direita;
      }
  
      else {
        No sucessor = getSucessor(atual);

        if (atual == raiz) raiz = sucessor;
        else if(filhoEsquerdo) pai.esquerda = sucessor;
             else pai.direita = sucessor;
        sucessor.esquerda = atual.esquerda;
      }
  
      return true;
    }
    
    public No getSucessor(No item) {
       No paidosucessor = item;
       No sucessor = item;
       No atual = item.direita;
  
       while (atual != null) {
         paidosucessor = sucessor;
         sucessor = atual;
         atual = atual.esquerda;
       } 
     
       if (sucessor != item.direita) { 
         paidosucessor.esquerda = sucessor.direita;
         sucessor.direita = item.direita;
       }

       return sucessor;
    }
    
    public void emOrdem(No atual) {
      if (atual != null) {
        emOrdem(atual.esquerda);
        System.out.print(" "+ atual.elemento + " | ");
        emOrdem(atual.direita);
      }
    }
    
    public void preOrdem(No atual) {
      if (atual != null) {
        System.out.print(" "+ atual.elemento + " | ");
        preOrdem(atual.esquerda);
        preOrdem(atual.direita);
      }
    }
    
    public void posOrdem(No atual) {
      if (atual != null) {
        posOrdem(atual.esquerda);
        posOrdem(atual.direita);
        System.out.print(" "+ atual.elemento + " | ");
      }
    }  
    
    public int altura(No atual) {
       if(atual == null || atual.naoePai())
         return 0;
       else {
         if (altura(atual.esquerda) > altura(atual.direita))
            return ( 1 + altura(atual.esquerda) );
         else
         return ( 1 + altura(atual.direita) );
       }
    }
    
    public int totalDeFolhas(No atual) {
      if(atual == null) return 0;
      if(atual.naoePai()) return 1;
      
      return totalDeFolhas(atual.esquerda) + totalDeFolhas(atual.direita);
    }
    
    public int totalDeNos(No atual) {
     if (atual == null) {
        return 0;
     }

      return (1 + totalDeNos(atual.esquerda) + totalDeNos(atual.direita));
    }

    private boolean estaVazia() {
        return this.raiz == null;
    }
    
    public void adicionar(long elemento) {
        No novo = new No();
        novo.elemento = elemento;
        novo.direita = null;
        novo.esquerda = null;
        this.totalValor += elemento;
    
        if (raiz == null) {
          raiz = novo; 
          return;
        } 
        
          No atual = raiz;
          No anterior;
  
          while(true) {
              anterior = atual;
              if (elemento <= atual.elemento) {
                  atual = atual.esquerda;
                  if (atual == null) {
                      anterior.esquerda = novo;
                      return;
                  } 
              }
              else {
                  atual = atual.direita;
                  if (atual == null) {
                  anterior.direita = novo;
                  return;
                  }
              }
          }
      }
  }