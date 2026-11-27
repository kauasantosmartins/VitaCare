
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

// CLASSE CONEXAO - GERENCIA A CONEXAO COM O BANCO SQL SERVER
public class Conexao {

    // CONFIGURACAO JDBC - ENDERECO, PORTA E BANCO DE DADOS
    // A PORTA DO SQLEXPRESS E DINAMICA E PODE MUDAR AO REINICIAR O SERVICO
    private static final String URL =
            "jdbc:sqlserver://localhost:55298;databaseName=saude;"
            + "encrypt=true;trustServerCertificate=true";

    // METODO GETCONNECTION - ESTABELECE A CONEXAO COM O BANCO
    public static Connection getConnection() throws SQLException {

        // AUTENTICACAO SQL SERVER - VERIFICA AS VARIAVEIS DE AMBIENTE
        String usuario = System.getenv("SAUDE_DB_USER");
        String senha = System.getenv("SAUDE_DB_PASSWORD");

        if (usuario != null && !usuario.isBlank()) {
            return DriverManager.getConnection(URL, usuario, senha == null ? "" : senha);
        }

        // AUTENTICACAO WINDOWS - UTILIZA A DLL JDBC PARA CONEXAO INTEGRADA
        return DriverManager.getConnection(URL + ";integratedSecurity=true");
    }

    // METODO TOSQL - CONVERTE LOCALDATE PARA DATE DO SQL
    public static Date toSql(LocalDate d) {
        return d == null ? null : Date.valueOf(d);
    }

    // METODO TOLOCAL - CONVERTE DATE DO SQL PARA LOCALDATE
    public static LocalDate toLocal(Date d) {
        return d == null ? null : d.toLocalDate();
    }
}
