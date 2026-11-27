
package dao;

import java.sql.SQLException;
import java.util.List;

// INTERFACE DAO - DEFINE OS METODOS PADRAO DE ACESSO AO BANCO
public interface DAO<T> {

    // METODO SALVAR - INSERE SE O ID FOR ZERO OU ATUALIZA SE JA EXISTIR
    void salvar(T t) throws SQLException;

    // METODO EXCLUIR - REMOVE UM REGISTRO DO BANCO
    void excluir(T t) throws SQLException;

    // METODO PESQUISAR - RETORNA UMA LISTA DE REGISTROS FILTRADOS
    List<T> pesquisar(String texto) throws SQLException;
}
