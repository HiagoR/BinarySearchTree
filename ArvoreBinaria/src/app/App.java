package app;

import library.ArvoreBinaria;
import library.AVL;

public class App {
    public static void main(String[] args) throws Exception {

        //new EADTest().montarPainel("DISCIPLINA: Estrutura de Dados Avançadas \n Aluno: Hiago Brandão Reis", new ArvoreBinaria());
        new EADTest2().montarPainel("DISCIPLINA: Estrutura de Dados Avançadas \n Aluno: Hiago Brandão Reis", new AVL());
    }
}