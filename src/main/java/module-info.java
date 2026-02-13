module es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.prefs;

    //opens es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.persistence;
    opens es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.model;
    opens es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.view;
    opens es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose to javafx.fxml;
    opens es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.util;
    exports es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose;
}