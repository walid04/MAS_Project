package Parser;

import Model.Constraint;
import Model.Domain;
import Model.Variable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtToObject {
    public List fileParser (String directoryName) {
        List fileInfo = new ArrayList();
        fileInfo.add(variableFileParser("FullRLFAP/CELAR/" + directoryName + "/var.txt"));
        fileInfo.add(domainFileParser("FullRLFAP/CELAR/" + directoryName + "/dom.txt"));
        fileInfo.add(constraintFileParser("FullRLFAP/CELAR/" + directoryName + "/ctr.txt"));
        return fileInfo;
    }

    public List<Variable> variableFileParser (String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            List<Variable> variables = new ArrayList<Variable>();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                if (line != null) {
                    String[] columnDetail = line.split("   ");

                    Variable variable = new Variable();
                    if (columnDetail.length == 2) {
                        variable.setNumber(Integer.parseInt(columnDetail[0].replace(" ", "")));
                        variable.setDomain(Integer.parseInt(columnDetail[1].replace(" ", "")));
                    }
                    else {
                        variable.setNumber(Integer.parseInt(columnDetail[0].replace(" ", "")));
                        variable.setDomain(Integer.parseInt(columnDetail[1].replace(" ", "")));
                        variable.setInitialValue(Integer.parseInt(columnDetail[2].replace(" ", "")));
                        variable.setMobility(Integer.parseInt(columnDetail[3].replace(" ", "")));
                    }

                    variables.add(variable);
                    line = br.readLine();
                }
            }

            /* Print the variables */
//            for (int i = 0; i < variables.size(); i++) {
//                System.out.println("Variable number: " + variables.get(i).getNumber());
//                System.out.println("Variable domain: " + variables.get(i).getDomain());
//                System.out.println();
//            }
            return variables;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Domain> domainFileParser (String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            List<Domain> domains = new ArrayList<Domain>();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                String[] columnDetail = line.split("  ");

                /* Create a list in which we store all the values in the file
                 * the file has different separators */
                List<String> domainsInfo = new ArrayList<String>();

                for (int i = 0; i < columnDetail.length; i++) {
                    /* Separator switches from 2 spaces to 1 */
                    /* Separator: "  " */
                    if (columnDetail[i].length() < 4 && columnDetail[i].length() != 0) {
                        domainsInfo.add(columnDetail[i]);
                    }

                    /* Separator: " " */
                    if (columnDetail[i].length() > 3) {
                        String[] split = columnDetail[i].split(" ");
                        for (int j = 0; j < split.length; j++) {
                            if (split[j].length() > 0) {
                                domainsInfo.add(split[j]);
                            }
                        }
                    }
                }

                /* Create domains from created list */
                Domain domain = new Domain();
                domain.setNumber(Integer.parseInt(domainsInfo.get(0).replace(" ", "")));
                domain.setCardinality(Integer.parseInt(domainsInfo.get(1).replace(" ", "")));
                domain.setValues(new ArrayList<Integer>());

                for (int i = 2; i < domainsInfo.size(); i++) {
                    domain.getValues().add(Integer.parseInt(domainsInfo.get(i).replace(" ", "")));
                }

                domains.add(domain);
                line = br.readLine();
            }

            /* Print the domains */
//            for (int i = 0; i < domains.size(); i++) {
//                System.out.println("Domain number: " + domains.get(i).getNumber());
//                System.out.println("Domain cardinality: " + domains.get(i).getCardinality());
//                System.out.print("Domain Values: ");
//                for (int j = 0; j < domains.get(i).getValues().size(); j++) {
//                    System.out.print(domains.get(i).getValues().get(j) + " ");
//                }
//                System.out.println();
//            }
            return domains;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Constraint> constraintFileParser (String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            List<Constraint> constraints = new ArrayList<Constraint>();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                if (line != null) {
                    String[] columnDetail = line.split("   ");

                    /* Create a list in which we store all the values in the file
                     * the file has different separators */
                    List<String> constraintsInfo = new ArrayList<String>();

                    for (int i = 0; i < columnDetail.length; i++) {
                        /* Separator switches from 2 spaces to 1 */
                        /* Separator: "  " */
                        if (columnDetail[i].length() < 4 && columnDetail[i].length() != 0) {
                            constraintsInfo.add(columnDetail[i]);
                        }

                        /* Separator: " " */
                        if (columnDetail[i].length() > 3) {
                            String[] split = columnDetail[i].split(" ");
                            for (int j = 0; j < split.length; j++) {
                                if (split[j].length() > 0) {
                                    constraintsInfo.add(split[j]);
                                }
                            }
                        }
                    }

                    /* Create domains from created list */
                    Constraint constraint = new Constraint();
                    constraint.setFirstVariable(Integer.parseInt(constraintsInfo.get(0).replace(" ", "")));
                    constraint.setSecondVariable(Integer.parseInt(constraintsInfo.get(1).replace(" ", "")));
                    constraint.setType(constraintsInfo.get(2).replace(" ", ""));
                    constraint.setOperator(constraintsInfo.get(3).replace(" ", ""));
                    constraint.setDeviation(Integer.parseInt(constraintsInfo.get(4).replace(" ", "")));

                    constraints.add(constraint);
                    line = br.readLine();
                }
            }

            /* Print the constraints */
//            for (int i = 0; i < constraints.size(); i++) {
//                System.out.println("Constaint first variable: " + constraints.get(i).getFirstVariable());
//                System.out.println("Constaint second variable: " + constraints.get(i).getSecondVariable());
//                System.out.println("Constaint type: " + constraints.get(i).getType());
//                System.out.println("Constaint operator: " + constraints.get(i).getOperator());
//                System.out.println("Constaint deviation: " + constraints.get(i).getDeviation());
//                System.out.println();
//            }
            return constraints;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
