package es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The type Tooltip example.
 */
public class TooltipExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crear un botón con el texto '?'
        Button helpButton = new Button("?");

        // Crear un tooltip con un mensaje informativo
        Tooltip tooltip = new Tooltip("Haz clic aquí para obtener más información.");
        Tooltip.install(helpButton, tooltip);


        // Definir la acción del botón cuando se hace clic
        helpButton.setOnAction(event -> {
            // Mostrar ventana de Alerta de advertencia
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Precaución");
            alert.setContentText("Ten en cuenta la información proporcionada.");
            alert.showAndWait();

            // Mostrar ventana de Información adicional
            Alert infoAlert = new Alert(AlertType.INFORMATION);
            infoAlert.setTitle("Información");
            infoAlert.setHeaderText("Detalles Adicionales");
            infoAlert.setContentText("Esta es una ventana de información adicional.");
            infoAlert.showAndWait();
        });

        // Crear un contenedor VBox y agregar el botón
        VBox root = new VBox(helpButton);

        // Crear la escena con dimensiones específicas
        Scene scene = new Scene(root, 200, 100);

        // Configurar la ventana principal
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ayuda Contextual");
        primaryStage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
// Método principal para lanzar la aplicación
    public static void main(String[] args) {
        launch(args);
    }
}
