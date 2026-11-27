
package entity;

import java.time.LocalDate;
import javafx.beans.property.*;

// CLASSE HABITO - REPRESENTA OS DADOS DE UM HABITO DO PACIENTE
public class Habito {

    // ATRIBUTOS - PROPRIEDADES JAVAFX DO HABITO
    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty tipo = new SimpleStringProperty("");
    private IntegerProperty minutos = new SimpleIntegerProperty(0);
    private ObjectProperty<LocalDate> data = new SimpleObjectProperty<>();
    private ObjectProperty<Paciente> paciente = new SimpleObjectProperty<>();

    // CONSTRUTOR VAZIO - CRIA UM HABITO SEM INFORMAR OS DADOS
    public Habito() {
    }

    // CONSTRUTOR COMPLETO - INICIALIZA TODOS OS ATRIBUTOS DO HABITO
    public Habito(long id, String tipo, int minutos, LocalDate data, Paciente paciente) {
        this.id.set(id);
        this.tipo.set(tipo);
        this.minutos.set(minutos);
        this.data.set(data);
        this.paciente.set(paciente);
    }

    // METODOS GET, SET E PROPERTY - ID
    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    // METODOS GET, SET E PROPERTY - TIPO
    public String getTipo() { return tipo.get(); }
    public void setTipo(String tipo) { this.tipo.set(tipo); }
    public StringProperty tipoProperty() { return tipo; }

    // METODOS GET, SET E PROPERTY - MINUTOS
    public int getMinutos() { return minutos.get(); }
    public void setMinutos(int minutos) { this.minutos.set(minutos); }
    public IntegerProperty minutosProperty() { return minutos; }

    // METODOS GET, SET E PROPERTY - DATA
    public LocalDate getData() { return data.get(); }
    public void setData(LocalDate data) { this.data.set(data); }
    public ObjectProperty<LocalDate> dataProperty() { return data; }

    // METODOS GET, SET E PROPERTY - PACIENTE
    public Paciente getPaciente() { return paciente.get(); }
    public void setPaciente(Paciente paciente) { this.paciente.set(paciente); }
    public ObjectProperty<Paciente> pacienteProperty() { return paciente; }

    // METODO TOSTRING - RETORNA OS DADOS PRINCIPAIS DO HABITO EM TEXTO
    @Override
    public String toString() {
        return id.get() + " - " + tipo.get() + " - " + minutos.get() + " min";
    }
}
