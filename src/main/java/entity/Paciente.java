
package entity;

import java.time.LocalDate;
import javafx.beans.property.*;

// CLASSE PACIENTE - REPRESENTA OS DADOS DE UM PACIENTE
public class Paciente {

    // ATRIBUTOS - PROPRIEDADES JAVAFX DO PACIENTE
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> nascimento = new SimpleObjectProperty<>();
    private StringProperty objetivo = new SimpleStringProperty("");
    private StringProperty telefone = new SimpleStringProperty("");

    // CONSTRUTOR VAZIO - CRIA UM PACIENTE SEM INFORMAR OS DADOS
    public Paciente() {
    }

    // CONSTRUTOR COMPLETO - INICIALIZA TODOS OS ATRIBUTOS DO PACIENTE
    public Paciente(long id, String nome, LocalDate nascimento, String objetivo, String telefone) {
        this.id.set(id);
        this.nome.set(nome);
        this.nascimento.set(nascimento);
        this.objetivo.set(objetivo);
        this.telefone.set(telefone);
    }

    // METODOS GET, SET E PROPERTY - ID
    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    // METODOS GET, SET E PROPERTY - NOME
    public String getNome() { return nome.get(); }
    public void setNome(String nome) { this.nome.set(nome); }
    public StringProperty nomeProperty() { return nome; }

    // METODOS GET, SET E PROPERTY - NASCIMENTO
    public LocalDate getNascimento() { return nascimento.get(); }
    public void setNascimento(LocalDate nascimento) { this.nascimento.set(nascimento); }
    public ObjectProperty<LocalDate> nascimentoProperty() { return nascimento; }

    // METODOS GET, SET E PROPERTY - OBJETIVO
    public String getObjetivo() { return objetivo.get(); }
    public void setObjetivo(String objetivo) { this.objetivo.set(objetivo); }
    public StringProperty objetivoProperty() { return objetivo; }

    // METODOS GET, SET E PROPERTY - TELEFONE
    public String getTelefone() { return telefone.get(); }
    public void setTelefone(String telefone) { this.telefone.set(telefone); }
    public StringProperty telefoneProperty() { return telefone; }

    // METODO TOSTRING - RETORNA O ID E O NOME DO PACIENTE EM TEXTO
    @Override
    public String toString() {
        return id.get() + " - " + nome.get();
    }
}
