package library;

public class NoAVL {
	public long elemento;
    public NoAVL direita;
    public NoAVL esquerda;
    public int peso;
    public NoAVL pai;
    
    public boolean naoePai() {
        return (direita == null && esquerda == null);
      }
}
