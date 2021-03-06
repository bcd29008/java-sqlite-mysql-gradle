package exemplo04.entities;

/**
 * Plain Old Java Object (POJO) para Pessoa
 */
public class Pessoa {
    private int idPessoa;
    private String nome;
    private double peso;
    private int altura;
    private String email;

    public Pessoa() {

    }

    public Pessoa(String nome, double peso, int altura, String email) {
        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.email = email;
    }


    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return String.format("|%-5d|%-25s|%-10.2f|%-10d|%-25s|",
                idPessoa, nome, peso, altura, email);
    }
}
