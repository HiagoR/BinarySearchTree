package app;

import java.util.Scanner;

import library.ArvoreBinaria;

public class EADTest {
    private Scanner leitor;
    private int opcao;
    private ArvoreBinaria corrente;
    private int opcaoMaxima = 99;
    private boolean estaRodando;
    
    public EADTest() {
        this.leitor = new Scanner(System.in); 
    }

    public void montarPainel(String titulo, ArvoreBinaria arvore) throws InterruptedException {
        this.corrente = arvore;
        System.out.print(String.format("\n %s", titulo));

        do {
        this.montarOpcoes();
        opcao = leitor.nextInt();
        this.processarOpcao(opcao);
        } while(opcao != opcaoMaxima);

      }

      private void montarOpcoes() throws InterruptedException {
        if (!estaRodando) {
            System.out.print("\n**************** Iniciando.....  *******************");
            Thread.sleep(1000);
            System.out.print("\n**************** Vamos lá....  *******************");
            this.estaRodando = true;
        } else {
            System.out.print("\n**************** Informação Processada....  *******************");
        }

        System.out.print("\n Opção 1: Adicionar um nó na árvore");
        System.out.print("\n Opção 2: Permitir a remoção de um nó");
        System.out.print("\n Opção 3: Verificar se dado um valor X está presente na árvore");
        System.out.print("\n Opção 4: Trazer status da árvore binária.");
        System.out.print("\n Opção 99: Fechar");
        System.out.print("\n***********************************");
        System.out.println();
      }

      private void processarOpcao(int opcao) throws InterruptedException {

        switch(opcao) {
            case 1: {
                  System.out.print("\n Informe o valor:");
                  long valor = leitor.nextLong();
                  this.corrente.adicionar(valor);
                  break;
           }
           case 2: {
                System.out.print("\n Informe o valor:");
                long valor = leitor.nextLong();
                if (!this.corrente.remover(valor))
                    System.out.print("\n Valor não encontrado!");;
                break;
           }
           case 3: {
                System.out.print("\n Informe o valor:");
                long valor = leitor.nextLong();
                if( this.corrente.buscar(valor) != null )
                    System.out.print("\n Valor encontrado: " + valor);
                else 
                    System.out.print("\n Não foi encontrado o valor: " + valor);
                break;
           }	 
           case 4: {
               this.corrente.getStatus();
               break; 
            }
           }
      }
}