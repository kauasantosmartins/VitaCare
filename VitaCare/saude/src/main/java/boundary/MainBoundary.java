
package boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// CLASSE MAINBOUNDARY - CLASSE PRINCIPAL DO SISTEMA
public class MainBoundary extends Application {

    // METODO START - INICIALIZA A INTERFACE GRAFICA
    @Override
    public void start(Stage stage) {

        // INSTANCIACAO DAS INTERFACES DOS CRUDS
        PacienteBoundary pacientes = new PacienteBoundary();
        ProfissionalBoundary profissionais = new ProfissionalBoundary();
        ConsultaBoundary consultas = new ConsultaBoundary();
        HabitoBoundary habitos = new HabitoBoundary();

        // CONFIGURACAO DAS ABAS DO SISTEMA
        TabPane tabs = new TabPane(
                criarTab("Pacientes", pacientes.getPane(), pacientes::atualizar),
                criarTab("Profissionais", profissionais.getPane(), profissionais::atualizar),
                criarTab("Consultas", consultas.getPane(), consultas::atualizar),
                criarTab("Hábitos", habitos.getPane(), habitos::atualizar));

        // CARREGAMENTO INICIAL DOS PACIENTES
        pacientes.atualizar();

        // CONFIGURACAO DA JANELA PRINCIPAL
        stage.setScene(new Scene(tabs, 640, 480));
        stage.setTitle("Saúde e Bem-Estar");
        stage.show();
    }

    // METODO CRIARTAB - CRIA AS ABAS E ATUALIZA OS DADOS AO SELECIONAR
    private Tab criarTab(String titulo, Pane pane, Runnable atualizar) {

        // CRIACAO E CONFIGURACAO DA ABA
        Tab tab = new Tab(titulo, pane);
        tab.setClosable(false);

        // EVENTO DE SELECAO - RECARREGA OS DADOS AO TROCAR DE ABA
        tab.setOnSelectionChanged(e -> {
            if (tab.isSelected()) atualizar.run();
        });

        return tab;
    }

    // METODO MAIN - INICIA A APLICACAO JAVAFX
    public static void main(String[] args) {
        Application.launch(MainBoundary.class, args);
    }
}
