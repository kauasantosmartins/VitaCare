
package control;

import dao.PacienteDAO;
import entity.Paciente;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// CLASSE PACIENTECONTROL - CONTROLA AS OPERACOES DO CRUD DE PACIENTES
public class PacienteControl {

    // ATRIBUTOS - OBJETO DAO PARA ACESSO AO BANCO DE DADOS
    private PacienteDAO dao = new PacienteDAO();

    // LISTA OBSERVAVEL - DADOS EXIBIDOS NA TABELA
    private ObservableList<Paciente> tableData = FXCollections.observableArrayList();

    // METODO GETTABLEDATA - RETORNA A LISTA DE PACIENTES
    public ObservableList<Paciente> getTableData() {
        return tableData;
    }

    // METODO SALVAR - SALVA O PACIENTE E ATUALIZA A TABELA
    public void salvar(Paciente p) throws SQLException {
        dao.salvar(p);
        pesquisar("");
    }

    // METODO EXCLUIR - EXCLUI O PACIENTE E ATUALIZA A TABELA
    public void excluir(Paciente p) throws SQLException {
        dao.excluir(p);
        pesquisar("");
    }

    // METODO PESQUISAR - BUSCA PACIENTES E ATUALIZA A LISTA
    public void pesquisar(String texto) throws SQLException {
        tableData.setAll(dao.pesquisar(texto));
    }
}
