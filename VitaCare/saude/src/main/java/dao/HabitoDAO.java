
package dao;

import entity.Habito;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CLASSE HABITODAO - GERENCIA AS OPERACOES SQL DOS HABITOS
public class HabitoDAO implements DAO<Habito> {

    // ATRIBUTOS - DAO PARA BUSCAR OS PACIENTES
    private PacienteDAO pacienteDAO = new PacienteDAO();

    // METODO SALVAR - INSERE OU ATUALIZA UM HABITO NO BANCO
    @Override
    public void salvar(Habito h) throws SQLException {

        // VERIFICACAO - DEFINE SE A OPERACAO SERA INSERT OU UPDATE
        boolean novo = h.getId() == 0;

        // COMANDO SQL - INSERCAO OU ATUALIZACAO DO HABITO
        String sql = novo
                ? "INSERT INTO habito (tipo, minutos, data, paciente_id) VALUES (?, ?, ?, ?)"
                : "UPDATE habito SET tipo=?, minutos=?, data=?, paciente_id=? WHERE id=?";

        // PREPAREDSTATEMENT - EXECUTA O COMANDO SQL COM PARAMETROS
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, h.getTipo());
            ps.setInt(2, h.getMinutos());
            ps.setDate(3, Conexao.toSql(h.getData()));
            ps.setLong(4, h.getPaciente().getId());

            if (!novo) ps.setLong(5, h.getId());

            ps.executeUpdate();
        }
    }

    // METODO EXCLUIR - REMOVE UM HABITO PELO ID
    @Override
    public void excluir(Habito h) throws SQLException {

        // COMANDO SQL DELETE
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM habito WHERE id=?")) {

            ps.setLong(1, h.getId());
            ps.executeUpdate();
        }
    }

    // METODO PESQUISAR - BUSCA HABITOS POR TIPO OU NOME DO PACIENTE
    @Override
    public List<Habito> pesquisar(String texto) throws SQLException {

        // LISTA DE RESULTADOS
        List<Habito> lista = new ArrayList<>();

        // COMANDO SQL SELECT - CONSULTA COM JOIN ENTRE HABITO E PACIENTE
        String sql = "SELECT h.* FROM habito h JOIN paciente p ON p.id = h.paciente_id "
                + "WHERE h.tipo LIKE ? OR p.nome LIKE ? ORDER BY h.data DESC";

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

    // METODO MONTAR - CONVERTE O RESULTSET EM UM OBJETO HABITO
    private Habito montar(ResultSet rs) throws SQLException {
        return new Habito(
                rs.getLong("id"),
                rs.getString("tipo"),
                rs.getInt("minutos"),
                Conexao.toLocal(rs.getDate("data")),
                pacienteDAO.buscarPorId(rs.getLong("paciente_id"))
        );
    }
}
