package library;

public class No {
    public long elemento;
    public No direita;
    public No esquerda;

    public boolean naoePai() {
      return (direita == null && esquerda == null);
    }
}