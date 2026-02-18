package es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.persistence;

import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.model.Person;
import es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.util.DateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CsvPersonRepository implements PersonRepository {

    private static final String DELIMITER = ";";
    private static final String HEADER = "FirstName;LastName;Street;PostalCode;City;Birthday";

    @Override
    public List<Person> load(File file) throws IOException {
        List<Person> persons = new ArrayList<>();
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (i == 0 && line.startsWith("FirstName")) continue;
            if (line.trim().isEmpty()) continue;

            String[] tokens = line.split(DELIMITER);
            if (tokens.length >= 6) {
                Person person = new Person(tokens[0], tokens[1]);
                person.setStreet(tokens[2]);
                try {
                    person.setPostalCode(Integer.parseInt(tokens[3]));
                } catch (NumberFormatException e) {
                    person.setPostalCode(0);
                }
                person.setCity(tokens[4]);
                person.setBirthday(DateUtil.parse(tokens[5]));
                persons.add(person);
            }
        }
        return persons;
    }

    @Override
    public void save(File file, List<Person> persons) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();

            for (Person p : persons) {
                String line = String.join(DELIMITER,
                        p.getFirstName(), p.getLastName(), p.getStreet(),
                        String.valueOf(p.getPostalCode()), p.getCity(),
                        DateUtil.format(p.getBirthday()));
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
