package Main;

import utils.Convertor;



public class Main {
    public static void main(String[] args) {
        String key = Convertor.hexToBin("133457799BBCDFF1");
        String plainText =Convertor.hexToBin("0123456789ABCDEF133457799BBCDFF1");

        Problem problem = new Problem();
        problem.setPlainText(plainText);
        problem.setKey(key);

        problem.crypt();
        System.out.println(problem.getCriptoText());

        String secondPlainText = Convertor.hexToBin(problem.getCriptoText());
        String secondKey = Convertor.hexToBin("123459877BBCDFF1");
        Problem problem1 = new Problem();
        problem1.setPlainText(secondPlainText);
        problem1.setKey(secondKey);
        problem1.crypt();
        System.out.println(problem1.getCriptoText());






    }
}
