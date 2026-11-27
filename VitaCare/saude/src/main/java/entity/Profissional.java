
package entity;

import javafx.beans.property.*;

// CLASSE PROFISSIONAL - REPRESENTA OS DADOS DE UM PROFISSIONAL
public class Profissional {

    // ATRIBUTOS - PROPRIEDADES JAVAFX DO PROFISSIONAL
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty especialidade = new SimpleStringProperty("");
    private StringProperty registro = new SimpleStringProperty("");
    private StringProperty email = new SimpleStringProperty("");

    // CONSTRUTOR VAZIO - CRIA UM PROFISSIONAL SEM INFORMAR OS DADOS
    public Profissional() {
    }

    // CONSTRUTOR COMPLETO - INICIALIZA TODOS OS ATRIBUTOS DO PROFISSIONAL
    public Profissional(long id, String nome, String especialidade, String registro, String email) {
        this.id.set(id);
        this.nome.set(nome);
        this.especialidade.set(especialidade);
        this.registro.set(registro);
        this.email.set(email);
    }

    // METODOS GET, SET E PROPERTY - ID
    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    // METODOS GET, SET E PROPERTY - NOME
    public String getNome() { return nome.get(); }
    public void setNome(String nome) { this.nome.set(nome); }
    public StringProperty nomeProperty() { return nome; }

    // METODOS GET, SET E PROPERTY - ESPECIALIDADE
    public String getEspecialidade() { return especialidade.get(); }
    public void setEspecialidade(String especialidade) { this.especialidade.set(especialidade); }
    public StringProperty especialidadeProperty() { return especialidade; }

    // METODOS GET, SET E PROPERTY - REGISTRO
    public String getRegistro() { return registro.get(); }
    public void setRegistro(String registro) { this.registro.set(registro); }
    public StringProperty registroProperty() { return registro; }

    // METODOS GET, SET E PROPERTY - EMAIL
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() { return email; }

    // METODO TOSTRING - RETORNA OS DADOS PRINCIPAIS DO PROFISSIONAL EM TEXTO
    @Override
    public String toString() {
        return id.get() + " - " + nome.get() + " (" + especialidade.get() + ")";
    }
}
