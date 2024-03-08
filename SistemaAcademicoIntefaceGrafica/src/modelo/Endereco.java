package modelo;

/**
 *
 * @author Andre Leiria
 */

public class Endereco {
    protected int id;
    protected String cidade;
    protected String rua;
    protected String numero;

    public Endereco() {
    }

    public Endereco(String cidade, String rua, String numero) {
        this.cidade = cidade;
        this.rua = rua;
        this.numero = numero;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cidade='" + cidade + '\'' +
                ", rua='" + rua + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
