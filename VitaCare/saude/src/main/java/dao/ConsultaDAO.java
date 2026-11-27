
package dao;

import entity.Consulta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CLASSE CONSULTADAO - GERENCIA AS OPERACOES SQL DAS CONSULTAS
public class ConsultaDAO implements DAO<Consulta> {

    // ATRIBUTOS - DAOS PARA BUSCAR PACIENTES E PROFISSIONAIS
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private ProfissionalDAO profissionalDAO = new ProfissionalDAO();

    // METODO SALVAR - INSERE OU ATUALIZA UMA CONSULTA NO BANCO
    @Override
    public void salvar(Consulta k) throws SQLException {

        // VERIFICACAO - DEFINE SE A OPERACAO SERA INSERT OU UPDATE
        boolean novo = k.getId() == 0;

        // COMANDO SQL - INSERCAO OU ATUALIZACAO DA CONSULTA
        String sql = novo
                ? "INSERT INTO consulta (data, motivo, paciente_id, profissional_id) VALUES (?, ?, ?, ?)"
                : "UPDATE consulta SET data=?, motivo=?, paciente_id=?, profissional_id=? WHERE id=?";

        // PREPAREDSTATEMENT - EXECUTA O COMANDO SQL COM PARAMETROS
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Conexao.toSql(k.getData()));
            ps.setString(2, k.getMotivo());
            ps.setLong(3, k.getPaciente().getId());
            ps.setLong(4, k.getProfissional().getId());

            if (!novo) ps.setLong(5, k.getId());

            ps.executeUpdate();
        }
    }

    // METODO EXCLUIR - REMOVE UMA CONSULTA PELO ID
    @Override
    public void excluir(Consulta k) throws SQLException {

        // COMANDO SQL DELETE
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM consulta WHERE id=?")) {

            ps.setLong(1, k.getId());
            ps.executeUpdate();
        }
    }

    // METODO PESQUISAR - BUSCA CONSULTAS POR MOTIVO OU NOME DO PACIENTE
    @Override
    public List<Consulta> pesquisar(String texto) throws SQLException {

        // LISTA DE RESULTADOS
        List<Consulta> lista = new ArrayList<>();

        // COMANDO SQL SELECT - CONSULTA COM JOIN ENTRE CONSULTA E PACIENTE
        String sql = "SELECT k.* FROM consulta k JOIN paciente p ON p.id = k.paciente_id "
                + "WHERE k.motivo LIKE ? OR p.nome LIKE ? ORDER BY k.data DESC";

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

    // METODO MONTAR - CONVERTE O RESULTSET EM UM OBJETO CONSULTA
    private Consulta montar(ResultSet rs) throws SQLException {
        return new Consulta(
                rs.getLong("id"),
                Conexao.toLocal(rs.getDate("data")),
                rs.getString("motivo"),
                pacienteDAO.buscarPorId(rs.getLong("paciente_id")),
                profissionalDAO.buscarPorId(rs.getLong("profissional_id"))
        );
    }
}
