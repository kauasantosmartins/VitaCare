
package boundary;

import control.ConsultaControl;
import entity.Consulta;
import entity.Paciente;
import entity.Profissional;
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

// CLASSE CONSULTABOUNDARY - INTERFACE DO CRUD DE CONSULTAS
public class ConsultaBoundary implements EventHandler<ActionEvent> {

    // ATRIBUTOS - OBJETO CONSULTA E CONTROLE
    private Consulta consulta = new Consulta();
    private ConsultaControl control = new ConsultaControl();

    // FORMATACAO DE DATAS
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // CAMPOS DE TEXTO E COMBOBOX
    private TextField tfId = new TextField();
    private TextField tfData = new TextField();
    private TextField tfMotivo = new TextField();
    private ComboBox<Paciente> cbPaciente = new ComboBox<>();
    private ComboBox<Profissional> cbProfissional = new ComboBox<>();

    // BOTOES DO CRUD
    private Button btnSalvar = new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");
    private Button btnExcluir = new Button("Excluir");
    private Button btnLimpar = new Button("Limpar");

    // TABELA DE CONSULTAS
    private TableView<Consulta> table = new TableView<>();

    // METODO GETPANE - MONTA A INTERFACE DE CONSULTAS
    public BorderPane getPane() {
        BorderPane pane = new BorderPane();

        // CONFIGURACAO DOS CAMPOS E COMBOBOX
        tfId.setEditable(false);
        tfData.setPromptText("dd/MM/aaaa");
        cbPaciente.setItems(control.getPacientes());
        cbProfissional.setItems(control.getProfissionais());

        // CONFIGURACAO DO FORMULARIO
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.add(new Label("Id:"), 0, 0);
        grid.add(tfId, 1, 0);
        grid.add(new Label("Data:"), 0, 1);
        grid.add(tfData, 1, 1);
        grid.add(new Label("Motivo:"), 0, 2);
        grid.add(tfMotivo, 1, 2);
        grid.add(new Label("Paciente:"), 0, 3);
        grid.add(cbPaciente, 1, 3);
        grid.add(new Label("Profissional:"), 0, 4);
        grid.add(cbProfissional, 1, 4);

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

        // SELECAO DE CONSULTAS NA TABELA
        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, sel) -> {
            if (sel != null) {
                consulta.setId(sel.getId());
                consulta.setData(sel.getData());
                consulta.setMotivo(sel.getMotivo());
                consulta.setPaciente(sel.getPaciente());
                consulta.setProfissional(sel.getProfissional());
            }
        });

        // VINCULACAO DOS CAMPOS
        vincular();

        // ORGANIZACAO FINAL DA INTERFACE
        pane.setTop(new VBox(grid, boxBotoes));
        pane.setCenter(table);
        return pane;
    }

    // METODO ATUALIZAR - RECARREGA AS CONSULTAS E OS COMBOBOX
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
        Bindings.bindBidirectional(tfId.textProperty(), consulta.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tfData.textProperty(), consulta.dataProperty(),
                new LocalDateStringConverter(FMT, FMT));
        Bindings.bindBidirectional(tfMotivo.textProperty(), consulta.motivoProperty());
        Bindings.bindBidirectional(cbPaciente.valueProperty(), consulta.pacienteProperty());
        Bindings.bindBidirectional(cbProfissional.valueProperty(), consulta.profissionalProperty());
        tfId.clear();
    }

    // METODO GERARCOLUNAS - CONFIGURA AS COLUNAS DA TABELA
    private void gerarColunas() {

        // COLUNA ID
        TableColumn<Consulta, Number> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // COLUNA DATA
        TableColumn<Consulta, LocalDate> colData =
                new TableColumn<>("Data");

        colData.setCellValueFactory(
                new PropertyValueFactory<>("data"));

        // FORMATACAO DA DATA NA TABELA
        colData.setCellFactory(c -> new TableCell<Consulta, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.format(FMT));
            }
        });

        // COLUNA MOTIVO
        TableColumn<Consulta, String> colMotivo = new TableColumn<>("Motivo");
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));

        // COLUNA PACIENTE
        TableColumn<Consulta, Paciente> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));

        // COLUNA PROFISSIONAL
        TableColumn<Consulta, Profissional> colProfissional = new TableColumn<>("Profissional");
        colProfissional.setCellValueFactory(new PropertyValueFactory<>("profissional"));

        // ADICIONA AS COLUNAS A TABELA
        table.getColumns().addAll(colId, colData, colMotivo, colPaciente, colProfissional);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // METODO COPIAR - CRIA UMA COPIA DOS DADOS DA CONSULTA
    private Consulta copiar() {
        return new Consulta(consulta.getId(), consulta.getData(), consulta.getMotivo(),
                consulta.getPaciente(), consulta.getProfissional());
    }

    // METODO LIMPAR - LIMPA OS CAMPOS DO FORMULARIO
    private void limpar() {
        consulta.setId(0);
        consulta.setData(null);
        consulta.setMotivo("");
        consulta.setPaciente(null);
        consulta.setProfissional(null);
        tfId.clear();
    }

    // METODO HANDLE - CONTROLA AS ACOES DOS BOTOES
    @Override
    public void handle(ActionEvent e) {
        try {

            // BOTAO SALVAR
            if (e.getSource() == btnSalvar) {

                // VALIDACAO - EXIGE PACIENTE E PROFISSIONAL SELECIONADOS
                if (consulta.getPaciente() == null || consulta.getProfissional() == null) {
                    new Alert(Alert.AlertType.WARNING, "Selecione paciente e profissional.").show();
                    return;
                }

                control.salvar(copiar());
                limpar();

            // BOTAO PESQUISAR
            } else if (e.getSource() == btnPesquisar) {
                control.pesquisar(consulta.getMotivo());

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
