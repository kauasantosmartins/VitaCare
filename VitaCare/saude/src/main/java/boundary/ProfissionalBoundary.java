
package boundary;

import control.ProfissionalControl;
import entity.Profissional;
import java.sql.SQLException;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.converter.NumberStringConverter;

// CLASSE PROFISSIONALBOUNDARY - INTERFACE DO CRUD DE PROFISSIONAIS
public class ProfissionalBoundary implements EventHandler<ActionEvent> {

    // ATRIBUTOS - OBJETO PROFISSIONAL E CONTROLE
    private Profissional profissional = new Profissional();
    private ProfissionalControl control = new ProfissionalControl();

    // CAMPOS DE TEXTO
    private TextField tfId = new TextField();
    private TextField tfNome = new TextField();
    private TextField tfEspecialidade = new TextField();
    private TextField tfRegistro = new TextField();
    private TextField tfEmail = new TextField();

    // BOTOES DO CRUD
    private Button btnSalvar = new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnExcluir = new Button("Excluir");
    private Button btnLimpar = new Button("Limpar");

    // TABELA DE PROFISSIONAIS
    private TableView<Profissional> table = new TableView<>();

    // METODO GETPANE - MONTA A INTERFACE DE PROFISSIONAIS
    public BorderPane getPane() {
        BorderPane pane = new BorderPane();

        // CONFIGURACAO DOS CAMPOS
        tfId.setEditable(false);

        // CONFIGURACAO DO FORMULARIO
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.add(new Label("Id:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Nome:"), 0, 1);
        grid.add(tfNome, 1, 1);
        grid.add(new Label("Especialidade:"), 0, 2);
        grid.add(tfEspecialidade, 1, 2);
        grid.add(new Label("Registro:"), 0, 3);
        grid.add(tfRegistro, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(tfEmail, 1, 4);

        // CONFIGURACAO DOS BOTOES
        HBox boxBotoes = new HBox(5, btnSalvar, btnPesquisar, btnExcluir, btnLimpar);
        boxBotoes.setPadding(new Insets(5));

        btnSalvar.setOnAction(this);
        btnPesquisar.setOnAction(this);
        btnExcluir.setOnAction(this);
        btnLimpar.setOnAction(this);

        // CONFIGURACAO DA TABELA
        gerarColunas();
        table.setItems(control.getTableData());

        // SELECAO DE PROFISSIONAIS NA TABELA
        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, sel) -> {
            if (sel != null) {
                profissional.setId(sel.getId());
                profissional.setNome(sel.getNome());
                profissional.setEspecialidade(sel.getEspecialidade());
                profissional.setRegistro(sel.getRegistro());
                profissional.setEmail(sel.getEmail());
            }
        });

        // VINCULACAO DOS CAMPOS
        vincular();

        // ORGANIZACAO FINAL DA INTERFACE
        pane.setTop(new VBox(grid, boxBotoes));
        pane.setCenter(table);
        return pane;
    }

    // METODO ATUALIZAR - RECARREGA A LISTA DE PROFISSIONAIS
    public void atualizar() {
        try {
            control.pesquisar("");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    // METODO VINCULAR - BINDINGS DOS CAMPOS
    private void vincular() {
        Bindings.bindBidirectional(tfId.textProperty(), profissional.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tfNome.textProperty(), profissional.nomeProperty());
        Bindings.bindBidirectional(tfEspecialidade.textProperty(), profissional.especialidadeProperty());
        Bindings.bindBidirectional(tfRegistro.textProperty(), profissional.registroProperty());
        Bindings.bindBidirectional(tfEmail.textProperty(), profissional.emailProperty());
        tfId.clear();
    }

    // METODO GERARCOLUNAS - CONFIGURA AS COLUNAS DA TABELA
    private void gerarColunas() {

        // COLUNA ID
        TableColumn<Profissional, Number> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // COLUNA NOME
        TableColumn<Profissional, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // COLUNA ESPECIALIDADE
        TableColumn<Profissional, String> colEsp = new TableColumn<>("Especialidade");
        colEsp.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        // COLUNA REGISTRO
        TableColumn<Profissional, String> colReg = new TableColumn<>("Registro");
        colReg.setCellValueFactory(new PropertyValueFactory<>("registro"));

        // COLUNA EMAIL
        TableColumn<Profissional, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // ADICIONA AS COLUNAS A TABELA
        table.getColumns().addAll(colId, colNome, colEsp, colReg, colEmail);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // METODO COPIAR - CRIA UMA COPIA DOS DADOS DO PROFISSIONAL
    private Profissional copiar() {
        return new Profissional(profissional.getId(), profissional.getNome(),
                profissional.getEspecialidade(), profissional.getRegistro(),
                profissional.getEmail());
    }

    // METODO LIMPAR - LIMPA OS CAMPOS DO FORMULARIO
    private void limpar() {
        profissional.setId(0);
        profissional.setNome("");
        profissional.setEspecialidade("");
        profissional.setRegistro("");
        profissional.setEmail("");
        tfId.clear();
    }

    // METODO HANDLE - CONTROLA AS ACOES DOS BOTOES
    @Override
    public void handle(ActionEvent e) {
        try {

            // BOTAO SALVAR
            if (e.getSource() == btnSalvar) {
                control.salvar(copiar());
                limpar();

            // BOTAO PESQUISAR
            } else if (e.getSource() == btnPesquisar) {
                control.pesquisar(profissional.getNome());

            // BOTAO EXCLUIR
            } else if (e.getSource() == btnExcluir) {
                control.excluir(copiar());
                limpar();

            // BOTAO LIMPAR
            } else if (e.getSource() == btnLimpar) {
                limpar();
            }

        } catch (SQLException ex) {

            // TRATAMENTO DE ERRO
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }
}
