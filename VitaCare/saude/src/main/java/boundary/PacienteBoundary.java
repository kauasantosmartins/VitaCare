
package boundary;

import control.PacienteControl;
import entity.Paciente;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

// CLASSE PACIENTEBOUNDARY - INTERFACE DO CRUD DE PACIENTES
public class PacienteBoundary implements EventHandler<ActionEvent> {

    // ATRIBUTOS - OBJETO PACIENTE E CONTROLE
    private Paciente paciente = new Paciente();
    private PacienteControl control = new PacienteControl();

    // FORMATACAO DE DATAS
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // CAMPOS DE TEXTO
    private TextField tfId = new TextField();
    private TextField tfNome = new TextField();
    private TextField tfNascimento = new TextField();
    private TextField tfObjetivo = new TextField();
    private TextField tfTelefone = new TextField();

    // BOTOES DO CRUD
    private Button btnSalvar = new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnExcluir = new Button("Excluir");
    private Button btnLimpar = new Button("Limpar");

    // TABELA DE PACIENTES
    private TableView<Paciente> table = new TableView<>();

    // METODO GETPANE - MONTA A INTERFACE DE PACIENTES
    public BorderPane getPane() {
        BorderPane pane = new BorderPane();

        // CONFIGURACAO DOS CAMPOS
        tfId.setEditable(false);
        tfNascimento.setPromptText("dd/MM/aaaa");

        // CONFIGURACAO DO FORMULARIO
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.add(new Label("Id:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Nome:"), 0, 1);
        grid.add(tfNome, 1, 1);
        grid.add(new Label("Nascimento:"), 0, 2);
        grid.add(tfNascimento, 1, 2);
        grid.add(new Label("Objetivo:"), 0, 3);
        grid.add(tfObjetivo, 1, 3);
        grid.add(new Label("Telefone:"), 0, 4);
        grid.add(tfTelefone, 1, 4);

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

        // SELECAO DE PACIENTES NA TABELA
        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, sel) -> {
            if (sel != null) {
                paciente.setId(sel.getId());
                paciente.setNome(sel.getNome());
                paciente.setNascimento(sel.getNascimento());
                paciente.setObjetivo(sel.getObjetivo());
                paciente.setTelefone(sel.getTelefone());
            }
        });

        // VINCULACAO DOS CAMPOS
        vincular();

        // ORGANIZACAO FINAL DA INTERFACE
        pane.setTop(new VBox(grid, boxBotoes));
        pane.setCenter(table);
        return pane;
    }

    // METODO ATUALIZAR - RECARREGA A LISTA DE PACIENTES
    public void atualizar() {
        try {
            control.pesquisar("");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    // METODO VINCULAR - BINDINGS DOS CAMPOS
    private void vincular() {
        Bindings.bindBidirectional(tfId.textProperty(), paciente.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tfNome.textProperty(), paciente.nomeProperty());
        Bindings.bindBidirectional(tfNascimento.textProperty(), paciente.nascimentoProperty(),
                new LocalDateStringConverter(FMT, FMT));
        Bindings.bindBidirectional(tfObjetivo.textProperty(), paciente.objetivoProperty());
        Bindings.bindBidirectional(tfTelefone.textProperty(), paciente.telefoneProperty());
        tfId.clear();
    }

    // METODO GERARCOLUNAS - CONFIGURA AS COLUNAS DA TABELA
    private void gerarColunas() {

        // COLUNA ID
        TableColumn<Paciente, Number> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // COLUNA NOME
        TableColumn<Paciente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // COLUNA NASCIMENTO
        TableColumn<Paciente, LocalDate> colNascimento =
                new TableColumn<>("Nascimento");

        colNascimento.setCellValueFactory(
                new PropertyValueFactory<>("nascimento"));

        // FORMATACAO DA DATA NA TABELA
        colNascimento.setCellFactory(c -> new TableCell<Paciente, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(FMT));
            }
        });

        // COLUNA OBJETIVO
        TableColumn<Paciente, String> colObjetivo = new TableColumn<>("Objetivo");
        colObjetivo.setCellValueFactory(new PropertyValueFactory<>("objetivo"));

        // COLUNA TELEFONE
        TableColumn<Paciente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // ADICIONA AS COLUNAS A TABELA
        table.getColumns().addAll(colId, colNome, colNascimento, colObjetivo, colTelefone);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // METODO COPIAR - CRIA UMA COPIA DOS DADOS DO PACIENTE
    private Paciente copiar() {
        return new Paciente(paciente.getId(), paciente.getNome(),
                paciente.getNascimento(), paciente.getObjetivo(),
                paciente.getTelefone());
    }

    // METODO LIMPAR - LIMPA OS CAMPOS DO FORMULARIO
    private void limpar() {
        paciente.setId(0);
        paciente.setNome("");
        paciente.setNascimento(null);
        paciente.setObjetivo("");
        paciente.setTelefone("");
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
                control.pesquisar(paciente.getNome());

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
