package Model;

public class Constraint {
    private int firstVariable;
    private int secondVariable;
    private String type;
    private String operator;
    private int deviation;
    private int indexOfWeighting;

    public int getFirstVariable() {
        return firstVariable;
    }

    public void setFirstVariable(int firstVariable) {
        this.firstVariable = firstVariable;
    }

    public int getSecondVariable() {
        return secondVariable;
    }

    public void setSecondVariable(int secondVariable) {
        this.secondVariable = secondVariable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getDeviation() {
        return deviation;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }

    public int getIndexOfWeighting() {
        return indexOfWeighting;
    }

    public void setIndexOfWeighting(int indexOfWeighting) {
        this.indexOfWeighting = indexOfWeighting;
    }
}
