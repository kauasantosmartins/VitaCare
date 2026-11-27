
package boundary;

import control.HabitoControl;
import entity.Habito;
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

// CLASSE HABITOBOUNDARY - INTERFACE DO CRUD DE HABITOS
public class HabitoBoundary implements EventHandler<ActionEvent> {

    // ATRIBUTOS - OBJETO HABITO E CONTROLE
    private Habito habito = new Habito();
    private HabitoControl control = new HabitoControl();

    // FORMATACAO DE DATAS
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // CAMPOS DE TEXTO E COMBOBOX
    private TextField tfId = new TextField();
    private TextField tfTipo = new TextField();
    private TextField tfMinutos = new TextField();
    private TextField tfData = new TextField();
    private ComboBox<Paciente> cbPaciente = new ComboBox<>();

    // BOTOES DO CRUD
    private Button btnSalvar = new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnExcluir = new Button("Excluir");
    private Button btnLimpar = new Button("Limpar");

    // TABELA DE HABITOS
    private TableView<Habito> table = new TableView<>();

    // METODO GETPANE - MONTA A INTERFACE DE HABITOS
    public BorderPane getPane() {
        BorderPane pane = new BorderPane();

        // CONFIGURACAO DOS CAMPOS E COMBOBOX
        tfId.setEditable(false);
        tfTipo.setPromptText("caminhada, sono, água...");
        tfData.setPromptText("dd/MM/aaaa");
        cbPaciente.setItems(control.getPacientes());

        // CONFIGURACAO DO FORMULARIO
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.add(new Label("Id:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Tipo:"), 0, 1);
        grid.add(tfTipo, 1, 1);
        grid.add(new Label("Minutos:"), 0, 2);
        grid.add(tfMinutos, 1, 2);
        grid.add(new Label("Data:"), 0, 3);
        grid.add(tfData, 1, 3);
        grid.add(new Label("Paciente:"), 0, 4);
        grid.add(cbPaciente, 1, 4);

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

        // SELECAO DE HABITOS NA TABELA
        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, sel) -> {
            if (sel != null) {
                habito.setId(sel.getId());
                habito.setTipo(sel.getTipo());
                habito.setMinutos(sel.getMinutos());
                habito.setData(sel.getData());
                habito.setPaciente(sel.getPaciente());
            }
        });

        // VINCULACAO DOS CAMPOS
        vincular();

        // ORGANIZACAO FINAL DA INTERFACE
        pane.setTop(new VBox(grid, boxBotoes));
        pane.setCenter(table);
        return pane;
    }

    // METODO ATUALIZAR - RECARREGA OS HABITOS E OS PACIENTES
    public void atualizar() {
        try {
            control.carregarCombos();
            control.pesquisar("");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    // METODO VINCULAR - BINDINGS DOS CAMPOS
    private void vincular() {
        Bindings.bindBidirectional(tfId.textProperty(), habito.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tfTipo.textProperty(), habito.tipoProperty());
        Bindings.bindBidirectional(tfMinutos.textProperty(), habito.minutosProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tfData.textProperty(), habito.dataProperty(),
                new LocalDateStringConverter(FMT, FMT));
        Bindings.bindBidirectional(cbPaciente.valueProperty(), habito.pacienteProperty());
        tfId.clear();
    }

    // METODO GERARCOLUNAS - CONFIGURA AS COLUNAS DA TABELA
    private void gerarColunas() {

        // COLUNA ID
        TableColumn<Habito, Number> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // COLUNA TIPO
        TableColumn<Habito, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // COLUNA MINUTOS
        TableColumn<Habito, Number> colMinutos = new TableColumn<>("Minutos");
        colMinutos.setCellValueFactory(new PropertyValueFactory<>("minutos"));

        // COLUNA DATA
        TableColumn<Habito, LocalDate> colData =
                new TableColumn<>("Data");

        colData.setCellValueFactory(
                new PropertyValueFactory<>("data"));

        // FORMATACAO DA DATA NA TABELA
        colData.setCellFactory(c -> new TableCell<Habito, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(FMT));
            }
        });

        // COLUNA PACIENTE
        TableColumn<Habito, Paciente> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));

        // ADICIONA AS COLUNAS A TABELA
        table.getColumns().addAll(colId, colTipo, colMinutos, colData, colPaciente);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // METODO COPIAR - CRIA UMA COPIA DOS DADOS DO HABITO
    private Habito copiar() {
        return new Habito(habito.getId(), habito.getTipo(), habito.getMinutos(),
                habito.getData(), habito.getPaciente());
    }

    // METODO LIMPAR - LIMPA OS CAMPOS DO FORMULARIO
    private void limpar() {
        habito.setId(0);
        habito.setTipo("");
        habito.setMinutos(0);
        habito.setData(null);
        habito.setPaciente(null);
        tfId.clear();
        tfMinutos.clear();
    }

    // METODO HANDLE - CONTROLA AS ACOES DOS BOTOES
    @Override
    public void handle(ActionEvent e) {
        try {

            // BOTAO SALVAR
            if (e.getSource() == btnSalvar) {

                // VALIDACAO - EXIGE UM PACIENTE SELECIONADO
                if (habito.getPaciente() == null) {
                    new Alert(Alert.AlertType.WARNING, "Selecione um paciente.").show();
                    return;
                }

                control.salvar(copiar());
                limpar();

            // BOTAO PESQUISAR
            } else if (e.getSource() == btnPesquisar) {
                control.pesquisar(habito.getTipo());

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
