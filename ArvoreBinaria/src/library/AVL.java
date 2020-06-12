package library;

public class AVL {
	private NoAVL raiz;

	public AVL() {
		this.raiz = null;
	}
	
	public void limpar() {
		this.raiz = null;
	}

	public NoAVL buscar(long chave) {
		if (this.estaVazia())
			return null;

		NoAVL atual = raiz;

		while (atual != null && atual.elemento != chave) {
			if (chave < atual.elemento) {
				atual = atual.esquerda;
			} else {
				atual = atual.direita;
			}
		}

		return atual == null ? null : atual;
	}
	
	public void listar() throws InterruptedException {
        System.out.println();
        System.out.println("\n Aguarde um momento estamos montando o status....");
        Thread.sleep(1000);
  
        System.out.println("\n Exibindo em pré-ordem: ");
        preOrdem(raiz);
        
        System.out.println("\n Exibindo em ordem: ");
        emOrdem(raiz);
        
        System.out.println("\n Exibindo em pós-ordem: ");
        posOrdem(raiz);
	}

	public boolean remover(long elemento) {
		if (this.estaVazia())
			return false;

		NoAVL atual = raiz;
		NoAVL pai = raiz;
		boolean filhoEsquerdo = true;

		while (atual.elemento != elemento) {
			pai = atual;
			if (elemento < atual.elemento) {
				atual = atual.esquerda;
				filhoEsquerdo = true;
			} else {
				atual = atual.direita;
				filhoEsquerdo = false;
			}
			if (atual == null)
				return false;
		}

		if (atual.esquerda == null && atual.direita == null) {
			if (atual == raiz)
				raiz = null;
			else if (filhoEsquerdo)
				pai.esquerda = null;
			else
				pai.direita = null;
		}

		else if (atual.direita == null) {
			if (atual == raiz)
				raiz = atual.esquerda;
			else if (filhoEsquerdo)
				pai.esquerda = atual.esquerda;
			else
				pai.direita = atual.esquerda;
		}

		else if (atual.esquerda == null) {
			if (atual == raiz)
				raiz = atual.direita;
			else if (filhoEsquerdo)
				pai.esquerda = atual.direita;
			else
				pai.direita = atual.direita;
		}

		else {
			NoAVL sucessor = getSucessor(atual);

			if (atual == raiz)
				raiz = sucessor;
			else if (filhoEsquerdo)
				pai.esquerda = sucessor;
			else
				pai.direita = sucessor;
			sucessor.esquerda = atual.esquerda;
		}

		return true;
	}

	private NoAVL getSucessor(NoAVL item) {
		NoAVL paidosucessor = item;
		NoAVL sucessor = item;
		NoAVL atual = item.direita;

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

    private void emOrdem(NoAVL atual) {
      if (atual != null) {
        emOrdem(atual.esquerda);
        System.out.print(" "+ atual.elemento + " | ");
        emOrdem(atual.direita);
      }
    }
    
    private void preOrdem(NoAVL atual) {
        if (atual != null) {
          System.out.print(" "+ atual.elemento + " | ");
          preOrdem(atual.esquerda);
          preOrdem(atual.direita);
        }
      }
      
      private void posOrdem(NoAVL atual) {
        if (atual != null) {
          posOrdem(atual.esquerda);
          posOrdem(atual.direita);
          System.out.print(" "+ atual.elemento + " | ");
        }
      }  
 

	private boolean estaVazia() {
		return this.raiz == null;
	}

	public void adicionar(long elemento) {
		NoAVL novo = new NoAVL();
		novo.elemento = elemento;
		novo.direita = null;
		novo.esquerda = null;
		//this.totalValor += elemento;

		if (raiz == null) {
			raiz = novo;
			return;
		}

		NoAVL atual = raiz;
		NoAVL anterior;

		while (true) {
			anterior = atual;
			if (elemento <= atual.elemento) {
				atual = atual.esquerda;
				if (atual == null) {
					novo.pai = anterior;
					anterior.esquerda = novo;
					rebalance(novo);
					return;
				}
			} else {
				atual = atual.direita;
				if (atual == null) {
					novo.pai = anterior;
					anterior.direita = novo;
					rebalance(novo);
					return;
				}
			}
		}	
	}
	
	private void rebalance(NoAVL node) {
        while (node != null) {
            
            NoAVL pai = node.pai;
            
            int esquerdaHeight = (node.esquerda == null) ? -1 : (node.esquerda).peso;
            int direitaHeight = (node.direita == null) ? -1 : (node.direita).peso;
            int nodeBalance =  esquerdaHeight - direitaHeight;
            // rebalance (-2 means esquerda subtree outgrow, 2 means direita subtree)
            if (nodeBalance == -2) {
                if (node.direita.direita != null) {
                    node = avlRotateLeft(node);
                    break;
                } else {
                    node = doubleRotateRightLeft(node);
                    break;
                }
            } else if (nodeBalance == 2) {
                if (node.esquerda.esquerda != null) {
                    node = avlRotateRight(node);
                    break;
                } else {
                    node = doubleRotateLeftRight(node);
                    break;
                }
            } else {
                updateHeight(node);
            }
            
            node = pai;
        }
    }
	
	private NoAVL avlRotateRight(NoAVL node) {
		NoAVL temp = rotateRight(node);
		
		updateHeight(temp.direita);
        updateHeight(temp);
        return temp;
	}
	
	private NoAVL avlRotateLeft(NoAVL node) {
        NoAVL temp = rotateLeft(node);
        
        updateHeight(temp.esquerda);
        updateHeight(temp);
        return temp;
    }
	
	protected NoAVL doubleRotateRightLeft(NoAVL node) {
        node.direita = avlRotateRight(node.direita);
        return avlRotateLeft(node);
    }
	
	protected NoAVL doubleRotateLeftRight(NoAVL node) {
        node.esquerda = avlRotateLeft(node.esquerda);
        return avlRotateRight(node);
    }
	
	private NoAVL rotateRight(NoAVL node) {
		NoAVL temp = node.esquerda;
        temp.pai = node.pai;

        node.esquerda = temp.direita;
        if (node.esquerda != null) {
            node.esquerda.pai = node;
        }

        temp.direita = node;
        node.pai = temp;

        // temp took over node's place so now its pai should point to temp
        if (temp.pai != null) {
            if (node == temp.pai.esquerda) {
                temp.pai.esquerda = temp;
            } else {
                temp.pai.direita = temp;
            }
        } else {
            raiz = temp;
        }
        
        return temp;
	}
	
	private NoAVL rotateLeft(NoAVL node) {
		NoAVL temp = node.direita;
        temp.pai = node.pai;

        node.direita = temp.esquerda;
        if (node.direita != null) {
            node.direita.pai = node;
        }

        temp.esquerda = node;
        node.pai = temp;

        // temp took over node's place so now its pai should point to temp
        if (temp.pai != null) {
            if (node == temp.pai.esquerda) {
                temp.pai.esquerda = temp;
            } else {
                temp.pai.direita = temp;
            }
        } else {
            raiz = temp;
        }
        
        return temp;
    }
	
	private static final void updateHeight(NoAVL node) {
        int leftHeight = (node.esquerda == null) ? -1 : (node.esquerda).peso;
        int rightHeight = (node.direita == null) ? -1 : (node.direita).peso;
        node.peso = 1 + getMax(leftHeight, rightHeight);
    }
	
	private void recomputeHeight(NoAVL node) {
	       while (node != null) {
	          node.peso = maxHeight((NoAVL)node.esquerda, (NoAVL)node.direita) + 1;
	          node = (NoAVL)node.pai;
	       }
	}
	
	private int maxHeight(NoAVL node1, NoAVL node2) {
        if (node1 != null && node2 != null) {
            return node1.peso > node2.peso ? node1.peso : node2.peso;
        } else if (node1 == null) {
            return node2 != null ? node2.peso : -1;
        } else if (node2 == null) {
            return node1 != null ? node1.peso : -1;
        }
        return -1;
    }
	
	
	
	private static int getMax(int first, int second) {
        return first > second ? first : second;
    }
}
