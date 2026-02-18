package es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose;

import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.model.Person;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.persistence.JacksonPersonRepository;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.persistence.PersonRepository;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.settings.AppPreferences;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.view.BirthdayStatisticsController;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.view.PersonEditDialogController;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.view.PersonOverviewController;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();


    private final PersonRepository repository = new JacksonPersonRepository();

    private File personFilePath;
    private boolean dirty;
    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
        personData.add(new Person("Francisco", "Gómez"));
    }

    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp - Francisco José Gómez García");

        initRootLayout();

        showPersonOverview();
        personData.addListener((javafx.collections.ListChangeListener<Person>) c -> setDirty(true));
        loadOnStartup();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void setPersonFilePath(File file) {
        this.personFilePath = file;
        AppPreferences.setPersonFile(file == null ? null : file.getAbsolutePath());
        // opcional: reflejar en el título
        if (primaryStage != null) {
            String name = (file == null) ? "AddressApp FGG" : "AddressApp FGG - " + file.getName();
            primaryStage.setTitle(name);
        }
    }

    public void loadPersonDataFromJson(File file) throws IOException {
        // 1) Cargar desde repositorio
        List<Person> loaded = repository.load(file);
        // 2) IMPORTANTE: NO reasignar personData. Usar setAll.
        // Así la TableView sigue enlazada a la misma lista.
        personData.setAll(loaded);
        // 3) Guardar el fichero actual (y en preferencias)
        setPersonFilePath(file);
        // 4) Acabamos de cargar: no hay cambios sin guardar
        setDirty(false);
    }

    public void savePersonDataToJson(File file) throws IOException {
        // 1) Guardar con el repositorio
        repository.save(file, new ArrayList<>(personData));
        // 2) Marcar fichero actual (y en preferencias)
        setPersonFilePath(file);
        // 3) Tras guardar, ya no hay cambios pendientes
        setDirty(false);
    }

    private void loadOnStartup() {
        // 1) si hay ruta en Preferences -> carga
        AppPreferences.getPersonFile().ifPresentOrElse(
                path -> {
                    File f = new File(path);
                    if (f.exists()) {
                        try {
                            loadPersonDataFromJson(f);
                            setPersonFilePath(f);
                        } catch (IOException e) {
                            // si falla, cae al default
                            loadDefaultIfExists();
                        }
                    } else {
                        loadDefaultIfExists();
                    }
                },
                this::loadDefaultIfExists
        );
    }

    private void loadDefaultIfExists() {
        File f = defaultJsonPath.toFile();
        if (f.exists()) {
            try {
                loadPersonDataFromJson(f);
                setPersonFilePath(f);
            } catch (IOException ignored) {
                // si falla, te quedas con los datos en memoria (ej. sample data)
            }
        } else {
            // No existe aún: te quedas con los sample data (o lista vacía, como prefieras)
            setPersonFilePath(f); // así autosave crea el fichero al salir
        }
    }

    /**
     * Opens a dialog to show birthday statistics.
     */
    public void showBirthdayStatistics(int tabIndex) {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");

            // CAMBIO: Modality.NONE permite interactuar con la ventana principal
            dialogStage.initModality(Modality.NONE);
            // Eliminado dialogStage.initOwner(primaryStage) para hacerla independiente

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            // CAMBIO: Selecciona la pestaña que le hemos pasado
            controller.selectTab(tabIndex);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Path defaultJsonPath =
            Paths.get(System.getProperty("user.home"), ".addressappv2", "persons.json");

    public PersonRepository getRepository() {
        return repository;
    }


    public File getPersonFilePath() {
        return personFilePath;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setPersonData(ObservableList<Person> personData) {
        this.personData = personData;
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
