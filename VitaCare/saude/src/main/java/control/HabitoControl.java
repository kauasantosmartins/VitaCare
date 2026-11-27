
package control;

import dao.HabitoDAO;
import dao.PacienteDAO;
import entity.Habito;
import entity.Paciente;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// CLASSE HABITOCONTROL - CONTROLA AS OPERACOES DO CRUD DE HABITOS
public class HabitoControl {

    // ATRIBUTOS - OBJETOS DAO PARA ACESSO AO BANCO DE DADOS
    private HabitoDAO dao = new HabitoDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();

    // LISTAS OBSERVAVEIS - DADOS DA TABELA E DO COMBOBOX
    private ObservableList<Habito> tableData = FXCollections.observableArrayList();
    private ObservableList<Paciente> pacientes = FXCollections.observableArrayList();

    // METODOS GET - RETORNAM AS LISTAS PARA A INTERFACE
    public ObservableList<Habito> getTableData() {
        return tableData;
    }

    public ObservableList<Paciente> getPacientes() {
        return pacientes;
    }

    // METODO CARREGARCOMBOS - ATUALIZA A LISTA DE PACIENTES
    public void carregarCombos() throws SQLException {
        pacientes.setAll(pacienteDAO.pesquisar(""));
    }

    // METODO SALVAR - SALVA O HABITO E ATUALIZA A TABELA
    public void salvar(Habito h) throws SQLException {
        dao.salvar(h);
        pesquisar("");
    }

    // METODO EXCLUIR - EXCLUI O HABITO E ATUALIZA A TABELA
    public void excluir(Habito h) throws SQLException {
        dao.excluir(h);
        pesquisar("");
    }

    // METODO PESQUISAR - BUSCA HABITOS E ATUALIZA A LISTA
    public void pesquisar(String texto) throws SQLException {
        tableData.setAll(dao.pesquisar(texto));
    }
}
