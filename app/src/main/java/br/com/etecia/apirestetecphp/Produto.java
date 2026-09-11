package br.com.etecia.apirestetecphp;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String urlImagem;

    public Produto(int id, String nome, double preco, String urlImagem) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.urlImagem = urlImagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
