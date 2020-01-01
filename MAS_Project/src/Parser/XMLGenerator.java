package Parser;

import Model.Constraint;
import Model.Domain;
import Model.Variable;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

public class XMLGenerator {
    public void generateXCSP(String name, String path, List<Variable> variables, List<Domain> domains, List<Constraint> constraints) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            /* Root element (instance) */
            Element instance = document.createElement("instance");
            document.appendChild(instance);

            /* Presentation */
            Element presentation = document.createElement("presentation");

            Attr presentationName = document.createAttribute("name");
            presentationName.setValue(name);
            presentation.setAttributeNode(presentationName);

            Attr presentationMaxConstraintArity = document.createAttribute("maxConstraintArity");
            presentationMaxConstraintArity.setValue("2");
            presentation.setAttributeNode(presentationMaxConstraintArity);

            Attr presentationMaximize = document.createAttribute("maximize");
            presentationMaximize.setValue("false");
            presentation.setAttributeNode(presentationMaximize);

            Attr presentationFormat = document.createAttribute("format");
            presentationFormat.setValue("XCSP 2.1_FRODO");
            presentation.setAttributeNode(presentationFormat);

            instance.appendChild(presentation);

            /* Agents */
            Element agents = document.createElement("agents");

            Attr nbAgents = document.createAttribute("nbAgents");
            nbAgents.setValue(String.valueOf(variables.size()));
            agents.setAttributeNode(nbAgents);

            for (int i = 0; i < variables.size(); i++) {
                Element agent = document.createElement("agent");
                /* Agent name */
                Attr agentName = document.createAttribute("name");
                agentName.setValue("agent" + i);
                agent.setAttributeNode(agentName);
                agents.appendChild(agent);
            }
            instance.appendChild(agents);

            /* Domains */
            Element domainsElement = document.createElement("domains");
            instance.appendChild(domainsElement);

            Attr nbDomains = document.createAttribute("nbDomains");
            nbDomains.setValue(String.valueOf(domains.size()));
            domainsElement.setAttributeNode(nbDomains);

            for (int i = 0; i < domains.size(); i++) {
                Element domain = document.createElement("domain");
                /* Domain name */
                Attr domainName = document.createAttribute("name");
                domainName.setValue(String.valueOf(domains.get(i).getNumber()));
                domain.setAttributeNode(domainName);
                /* Domain number of values */
                Attr nbValues = document.createAttribute("nbValues");
                nbValues.setValue(String.valueOf(domains.get(i).getCardinality()));
                domain.setAttributeNode(nbValues);
                /* Domain values */
                for (int j = 0; j < domains.get(i).getValues().size() - 1; j++) {
                    domain.appendChild(document.createTextNode(String.valueOf(domains.get(i).getValues().get(j)) + " "));
                }
                domain.appendChild(document.createTextNode(String.valueOf(domains.get(i).getValues().get(domains.get(i).getValues().size() - 1))));

                domainsElement.appendChild(domain);
            }
            instance.appendChild(domainsElement);

            /* Variables */
            Element variablesElement = document.createElement("variables");
            instance.appendChild(variablesElement);

            Attr nbVariables = document.createAttribute("nbVariables");
            nbVariables.setValue(String.valueOf(variables.size()));
            variablesElement.setAttributeNode(nbVariables);

            for (int i = 0; i < variables.size(); i++) {
                Element variable = document.createElement("variable");
                /* Variable name */
                Attr variableName = document.createAttribute("name");
                variableName.setValue(String.valueOf(variables.get(i).getNumber()));
                variable.setAttributeNode(variableName);
                /* Variable domain */
                Attr variableDomain = document.createAttribute("domain");
                variableDomain.setValue(String.valueOf(variables.get(i).getDomain()));
                variable.setAttributeNode(variableDomain);
                /* Variable agent */
                Attr variableAgent = document.createAttribute("agent");
                variableAgent.setValue("agent" + i);
                variable.setAttributeNode(variableAgent);

                variablesElement.appendChild(variable);
            }
            instance.appendChild(variablesElement);

            /* Predicates */
            Element predicatesElement = document.createElement("predicates");
            instance.appendChild(predicatesElement);

            Attr nbPredicates = document.createAttribute("nbPredicates");
            nbPredicates.setValue("2");
            predicatesElement.setAttributeNode(nbPredicates);

            /* Eq predicate (=) */
            Element eqPredicate = document.createElement("predicate");
            /* Predicate name */
            Attr eqPredicateName = document.createAttribute("name");
            eqPredicateName.setValue("eq");
            eqPredicate.setAttributeNode(eqPredicateName);
            /* Predicate Parameters (Child element) */
            Element eqParameters = document.createElement("parameters");
            eqParameters.appendChild(document.createTextNode("int V1 int V2 int K12"));
            /* Predicate Expression (Child element) */
            Element eqExpression = document.createElement("expression");
            /* Expression Functional (Child element) */
            Element eqFunctional = document.createElement("functional");
            eqFunctional.appendChild(document.createTextNode("eq(abs(sub(V1,V2)),K12)"));
            eqExpression.appendChild(eqFunctional);

            eqPredicate.appendChild(eqParameters);
            eqPredicate.appendChild(eqExpression);
            predicatesElement.appendChild(eqPredicate);

            /* Gt predicate (>) */
            Element gtPredicate = document.createElement("predicate");
            /* Predicate name */
            Attr gtPredicateName = document.createAttribute("name");
            gtPredicateName.setValue("gt");
            gtPredicate.setAttributeNode(gtPredicateName);
            /* Predicate Parameters (Child element) */
            Element gtParameters = document.createElement("parameters");
            gtParameters.appendChild(document.createTextNode("int V1 int V2 int K12"));
            /* Predicate Expression (Child element) */
            Element gtExpression = document.createElement("expression");
            /* Expression Functional (Child element) */
            Element gtFunctional = document.createElement("functional");
            gtFunctional.appendChild(document.createTextNode("gt(abs(sub(V1,V2)),K12)"));
            gtExpression.appendChild(gtFunctional);

            gtPredicate.appendChild(gtParameters);
            gtPredicate.appendChild(gtExpression);
            predicatesElement.appendChild(gtPredicate);
            instance.appendChild(predicatesElement);

            /* Constraints */
            Element constraintsElement = document.createElement("constraints");

            Attr nbConstraints = document.createAttribute("nbConstraints");
            nbConstraints.setValue(String.valueOf(constraints.size()));
            constraintsElement.setAttributeNode(nbConstraints);

            for (int i = 0; i < constraints.size(); i++) {
                Element constraint = document.createElement("constraint");
                /* Constraint name */
                Attr constraintName = document.createAttribute("name");
                if (constraints.get(i).getOperator().equals("=")) {
                    constraintName.setValue(constraints.get(i).getFirstVariable() + "_eq_" + constraints.get(i).getSecondVariable() + "_with_K12=" + constraints.get(i).getDeviation());
                }
                else {
                    constraintName.setValue(constraints.get(i).getFirstVariable() + "_gt_" + constraints.get(i).getSecondVariable() + "_with_K12=" + constraints.get(i).getDeviation());
                }
                constraint.setAttributeNode(constraintName);
                /* Constraint arity */
                Attr constraintArity = document.createAttribute("arity");
                constraintArity.setValue("2");
                constraint.setAttributeNode(constraintArity);
                /* Constraint scope */
                Attr constraintScope = document.createAttribute("scope");
                constraintScope.setValue(constraints.get(i).getFirstVariable() + " " + constraints.get(i).getSecondVariable());
                constraint.setAttributeNode(constraintScope);
                /* Constraint reference */
                Attr constraintReference = document.createAttribute("reference");
                if (constraints.get(i).getOperator().equals("=")) {
                    constraintReference.setValue("eq");
                }
                else {
                    constraintReference.setValue("gt");
                }
                constraint.setAttributeNode(constraintReference);

                constraintsElement.appendChild(constraint);
            }
            instance.appendChild(constraintsElement);

            /* Create the XML file */
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource domSource = new DOMSource(document);
            transformer.transform(domSource, result);
            StreamResult streamResult = new StreamResult(new File(path));

            /* Print result */
//            StreamResult result = new StreamResult(System.out);

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
