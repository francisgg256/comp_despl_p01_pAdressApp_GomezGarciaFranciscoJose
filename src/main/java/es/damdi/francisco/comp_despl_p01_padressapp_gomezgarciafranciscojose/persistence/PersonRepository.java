package es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.persistence;

import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PersonRepository {
    List<Person> load(File file) throws IOException;
    void save(File file, List<Person> persons) throws IOException;
}
