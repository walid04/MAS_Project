package Main;

import Parser.TxtToObject;

public class Main {
    public static void main(String[] args) {
        TxtToObject txtToObject = new TxtToObject();
        txtToObject.constraintFileParser("FullRLFAP/CELAR/scen04/ctr.txt");
    }
}
