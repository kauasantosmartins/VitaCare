
package control;

import dao.ConsultaDAO;
import dao.PacienteDAO;
import dao.ProfissionalDAO;
import entity.Consulta;
import entity.Paciente;
import entity.Profissional;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// CLASSE CONSULTACONTROL - CONTROLA AS OPERACOES DO CRUD DE CONSULTAS
public class ConsultaControl {

    // ATRIBUTOS - OBJETOS DAO PARA ACESSO AO BANCO DE DADOS
    private ConsultaDAO dao = new ConsultaDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private ProfissionalDAO profissionalDAO = new ProfissionalDAO();

    // LISTAS OBSERVAVEIS - DADOS DA TABELA E DOS COMBOBOX
    private ObservableList<Consulta> tableData = FXCollections.observableArrayList();
    private ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
    private ObservableList<Profissional> profissionais = FXCollections.observableArrayList();

    // METODOS GET - RETORNAM AS LISTAS PARA A INTERFACE
    public ObservableList<Consulta> getTableData() {
        return tableData;
    }

    public ObservableList<Paciente> getPacientes() {
        return pacientes;
    }

    public ObservableList<Profissional> getProfissionais() {
        return profissionais;
    }

    // METODO CARREGARCOMBOS - ATUALIZA AS LISTAS DE PACIENTES E PROFISSIONAIS
    public void carregarCombos() throws SQLException {
        pacientes.setAll(pacienteDAO.pesquisar(""));
        profissionais.setAll(profissionalDAO.pesquisar(""));
    }

    // METODO SALVAR - SALVA A CONSULTA E ATUALIZA A TABELA
    public void salvar(Consulta c) throws SQLException {
        dao.salvar(c);
        pesquisar("");
    }

    // METODO EXCLUIR - EXCLUI A CONSULTA E ATUALIZA A TABELA
    public void excluir(Consulta c) throws SQLException {
        dao.excluir(c);
        pesquisar("");
    }

    // METODO PESQUISAR - BUSCA CONSULTAS E ATUALIZA A LISTA
    public void pesquisar(String texto) throws SQLException {
        tableData.setAll(dao.pesquisar(texto));
    }
}
