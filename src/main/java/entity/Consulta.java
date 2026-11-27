
package entity;

import java.time.LocalDate;
import javafx.beans.property.*;

// CLASSE CONSULTA - REPRESENTA OS DADOS DE UMA CONSULTA
public class Consulta {

    // ATRIBUTOS - PROPRIEDADES JAVAFX DA CONSULTA
    private LongProperty id = new SimpleLongProperty(0);
    private ObjectProperty<LocalDate> data = new SimpleObjectProperty<>();
    private StringProperty motivo = new SimpleStringProperty("");
    private ObjectProperty<Paciente> paciente = new SimpleObjectProperty<>();
    private ObjectProperty<Profissional> profissional = new SimpleObjectProperty<>();

    // CONSTRUTOR VAZIO - CRIA UMA CONSULTA SEM INFORMAR OS DADOS
    public Consulta() {
    }

    // CONSTRUTOR COMPLETO - INICIALIZA TODOS OS ATRIBUTOS DA CONSULTA
    public Consulta(long id, LocalDate data, String motivo, Paciente paciente, Profissional profissional) {
        this.id.set(id);
        this.data.set(data);
        this.motivo.set(motivo);
        this.paciente.set(paciente);
        this.profissional.set(profissional);
    }

    // METODOS GET, SET E PROPERTY - ID
    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    // METODOS GET, SET E PROPERTY - DATA
    public LocalDate getData() { return data.get(); }
    public void setData(LocalDate data) { this.data.set(data); }
    public ObjectProperty<LocalDate> dataProperty() { return data; }

    // METODOS GET, SET E PROPERTY - MOTIVO
    public String getMotivo() { return motivo.get(); }
    public void setMotivo(String motivo) { this.motivo.set(motivo); }
    public StringProperty motivoProperty() { return motivo; }

    // METODOS GET, SET E PROPERTY - PACIENTE
    public Paciente getPaciente() { return paciente.get(); }
    public void setPaciente(Paciente paciente) { this.paciente.set(paciente); }
    public ObjectProperty<Paciente> pacienteProperty() { return paciente; }

    // METODOS GET, SET E PROPERTY - PROFISSIONAL
    public Profissional getProfissional() { return profissional.get(); }
    public void setProfissional(Profissional profissional) { this.profissional.set(profissional); }
    public ObjectProperty<Profissional> profissionalProperty() { return profissional; }

    // METODO TOSTRING - RETORNA OS DADOS PRINCIPAIS DA CONSULTA EM TEXTO
    @Override
    public String toString() {
        return id.get() + " - " + data.get() + " - " + motivo.get();
    }
}
