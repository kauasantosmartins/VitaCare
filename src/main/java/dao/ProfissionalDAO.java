
package dao;

import entity.Profissional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CLASSE PROFISSIONALDAO - GERENCIA AS OPERACOES SQL DOS PROFISSIONAIS
public class ProfissionalDAO implements DAO<Profissional> {

    // METODO SALVAR - INSERE OU ATUALIZA UM PROFISSIONAL NO BANCO
    @Override
    public void salvar(Profissional p) throws SQLException {

        // VERIFICACAO - DEFINE SE A OPERACAO SERA INSERT OU UPDATE
        boolean novo = p.getId() == 0;

        // COMANDO SQL - INSERCAO OU ATUALIZACAO DO PROFISSIONAL
        String sql = novo
                ? "INSERT INTO profissional (nome, especialidade, registro, email) VALUES (?, ?, ?, ?)"
                : "UPDATE profissional SET nome=?, especialidade=?, registro=?, email=? WHERE id=?";

        // PREPAREDSTATEMENT - EXECUTA O COMANDO SQL COM PARAMETROS
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getEspecialidade());
            ps.setString(3, p.getRegistro());
            ps.setString(4, p.getEmail());

            if (!novo) ps.setLong(5, p.getId());

            ps.executeUpdate();
        }
    }

    // METODO EXCLUIR - REMOVE UM PROFISSIONAL PELO ID
    @Override
    public void excluir(Profissional p) throws SQLException {

        // COMANDO SQL DELETE
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM profissional WHERE id=?")) {

            ps.setLong(1, p.getId());
            ps.executeUpdate();
        }
    }

    // METODO PESQUISAR - BUSCA PROFISSIONAIS POR NOME OU ESPECIALIDADE
    @Override
    public List<Profissional> pesquisar(String texto) throws SQLException {

        // LISTA DE RESULTADOS
        List<Profissional> lista = new ArrayList<>();

        // COMANDO SQL SELECT - FILTRA POR NOME OU ESPECIALIDADE
        String sql = "SELECT * FROM profissional WHERE nome LIKE ? OR especialidade LIKE ? ORDER BY nome";

        // EXECUCAO DA PESQUISA
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String filtro = "%" + (texto == null ? "" : texto) + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);

            // RESULTSET - PERCORRE OS REGISTROS ENCONTRADOS
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(montar(rs));
            }
        }

        return lista;
    }

    // METODO BUSCARPORID - LOCALIZA UM PROFISSIONAL PELO IDENTIFICADOR
    public Profissional buscarPorId(long id) throws SQLException {

        // COMANDO SQL SELECT - PESQUISA PELO ID
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM profissional WHERE id=?")) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? montar(rs) : null;
            }
        }
    }

    // METODO MONTAR - CONVERTE O RESULTSET EM UM OBJETO PROFISSIONAL
    private Profissional montar(ResultSet rs) throws SQLException {
        return new Profissional(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("especialidade"),
                rs.getString("registro"),
                rs.getString("email")
        );
    }
;;;}
