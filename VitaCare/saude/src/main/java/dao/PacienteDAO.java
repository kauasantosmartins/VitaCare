
package dao;

import entity.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CLASSE PACIENTEDAO - GERENCIA AS OPERACOES SQL DOS PACIENTES
public class PacienteDAO implements DAO<Paciente> {

    // METODO SALVAR - INSERE OU ATUALIZA UM PACIENTE NO BANCO
    @Override
    public void salvar(Paciente p) throws SQLException {

        // VERIFICACAO - DEFINE SE A OPERACAO SERA INSERT OU UPDATE
        boolean novo = p.getId() == 0;

        // COMANDO SQL - INSERCAO OU ATUALIZACAO DO PACIENTE
        String sql = novo
                ? "INSERT INTO paciente (nome, nascimento, objetivo, telefone) VALUES (?, ?, ?, ?)"
                : "UPDATE paciente SET nome=?, nascimento=?, objetivo=?, telefone=? WHERE id=?";

        // PREPAREDSTATEMENT - EXECUTA O COMANDO SQL COM PARAMETROS
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setDate(2, Conexao.toSql(p.getNascimento()));
            ps.setString(3, p.getObjetivo());
            ps.setString(4, p.getTelefone());

            if (!novo) ps.setLong(5, p.getId());

            ps.executeUpdate();
        }
    }

    // METODO EXCLUIR - REMOVE UM PACIENTE PELO ID
    @Override
    public void excluir(Paciente p) throws SQLException {

        // COMANDO SQL DELETE
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM paciente WHERE id=?")) {

            ps.setLong(1, p.getId());
            ps.executeUpdate();
        }
    }

    // METODO PESQUISAR - BUSCA PACIENTES PELO NOME
    @Override
    public List<Paciente> pesquisar(String texto) throws SQLException {

        // LISTA DE RESULTADOS
        List<Paciente> lista = new ArrayList<>();

        // COMANDO SQL SELECT - FILTRA PACIENTES PELO NOME
        String sql = "SELECT * FROM paciente WHERE nome LIKE ? ORDER BY nome";

        // EXECUCAO DA PESQUISA
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + (texto == null ? "" : texto) + "%");

            // RESULTSET - PERCORRE OS REGISTROS ENCONTRADOS
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(montar(rs));
            }
        }

        return lista;
    }

    // METODO BUSCARPORID - LOCALIZA UM PACIENTE PELO IDENTIFICADOR
    public Paciente buscarPorId(long id) throws SQLException {

        // COMANDO SQL SELECT - PESQUISA PELO ID
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM paciente WHERE id=?")) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? montar(rs) : null;
            }
        }
    }

    // METODO MONTAR - CONVERTE O RESULTSET EM UM OBJETO PACIENTE
    private Paciente montar(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getLong("id"),
                rs.getString("nome"),
                Conexao.toLocal(rs.getDate("nascimento")),
                rs.getString("objetivo"),
                rs.getString("telefone")
        );
    }
}
