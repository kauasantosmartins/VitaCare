
package control;

import dao.ProfissionalDAO;
import entity.Profissional;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// CLASSE PROFISSIONALCONTROL - CONTROLA AS OPERACOES DO CRUD DE PROFISSIONAIS
public class ProfissionalControl {

    // ATRIBUTOS - OBJETO DAO PARA ACESSO AO BANCO DE DADOS
    private ProfissionalDAO dao = new ProfissionalDAO();

    // LISTA OBSERVAVEL - DADOS EXIBIDOS NA TABELA
    private ObservableList<Profissional> tableData = FXCollections.observableArrayList();

    // METODO GETTABLEDATA - RETORNA A LISTA DE PROFISSIONAIS
    public ObservableList<Profissional> getTableData() {
        return tableData;
    }

    // METODO SALVAR - SALVA O PROFISSIONAL E ATUALIZA A TABELA
    public void salvar(Profissional p) throws SQLException {
        dao.salvar(p);
        pesquisar("");
    }

    // METODO EXCLUIR - EXCLUI O PROFISSIONAL E ATUALIZA A TABELA
    public void excluir(Profissional p) throws SQLException {
        dao.excluir(p);
        pesquisar("");
    }

    // METODO PESQUISAR - BUSCA PROFISSIONAIS E ATUALIZA A LISTA
    public void pesquisar(String texto) throws SQLException {
        tableData.setAll(dao.pesquisar(texto));
    }
}
