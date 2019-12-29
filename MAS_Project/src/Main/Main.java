package Main;

import Model.Constraint;
import Model.Domain;
import Model.Variable;
import Parser.TxtToObject;
import Parser.XMLGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /* Extract files info */
        TxtToObject txtToObject = new TxtToObject();
        List fileInfo = txtToObject.fileParser("scen02");
        List<Variable> variables = (List<Variable>) fileInfo.get(0);
        List<Domain> domains = (List<Domain>) fileInfo.get(1);
        List<Constraint> constraints = (List<Constraint>) fileInfo.get(2);

        /* Generate XML file */
        XMLGenerator xmlGenerator = new XMLGenerator();
        xmlGenerator.generateXCSP("Problem name", System.getProperty("user.dir") + "/output.xml", variables, domains, constraints);
    }
}
