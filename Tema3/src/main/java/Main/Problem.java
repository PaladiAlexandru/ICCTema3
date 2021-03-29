package Main;

import utils.Convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Problem {
    String plainText;
    String criptoText;
    String key;
    int cursor1 = -64;
    int cursor2 = -64;
    List<String> keysRight;
    List<String> keysLeft;
    List<String> completeKeys;
    String[] L;
    String[] R;
    List<String> B;
    boolean val = true;

    public Problem() {
        keysRight = new ArrayList<>();
        keysLeft = new ArrayList<>();
        completeKeys = new ArrayList<>();
        criptoText = "";
        B = new ArrayList<>();
        L = new String[1000];
        R = new String[1000];
    }

    private void splitKeyInTwo(String string) {
        StringBuilder keyLeft = new StringBuilder();
        for (int i = 0; i < string.length() / 2; i++) {
            keyLeft.append(string.charAt(i));
        }
        keysLeft.add(keyLeft.toString());
        StringBuilder keyRight = new StringBuilder();
        for (int i = string.length() / 2; i < string.length(); i++) {
            keyRight.append(string.charAt(i));
        }
        keysRight.add(keyRight.toString());

    }

    private void splitTextInTwo(String string) {

        StringBuilder keyLeft = new StringBuilder();
        for (int i = 0; i < string.length() / 2; i++) {
            keyLeft.append(string.charAt(i));
        }
        L[0] = keyLeft.toString();

        StringBuilder keyRight = new StringBuilder();
        for (int i = string.length() / 2; i < string.length(); i++) {
            keyRight.append(string.charAt(i));
        }
        R[0] = keyRight.toString();

    }

    private String leftShift(String text, int nr) {
        text = text.substring(nr) + text.substring(0, nr);
        return text;
    }

    private void solveKeys() {
        int[] leftShifts = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
        pc1();
        splitKeyInTwo(key);

        for (int i = 1; i <= 16; i++) {
            keysLeft.add(leftShift(keysLeft.get(i - 1), leftShifts[i - 1]));
            keysRight.add(leftShift(keysRight.get(i - 1), leftShifts[i - 1]));
            pc2(keysLeft.get(i).concat(keysRight.get(i)));
        }

    }

    private String take64Bits() {
        cursor1 += 64;
        return plainText.substring(cursor1, cursor1 + 64);

    }

    private String ip(String text) {
        int[][] arr =
                {
                        {58, 50, 42, 34, 26, 18, 10, 2},
                        {60, 52, 44, 36, 28, 20, 12, 4},
                        {62, 54, 46, 38, 30, 22, 14, 6},
                        {64, 56, 48, 40, 32, 24, 16, 8},
                        {57, 49, 41, 33, 25, 17, 9, 1},
                        {59, 51, 43, 35, 27, 19, 11, 3},
                        {61, 53, 45, 37, 29, 21, 13, 5},
                        {63, 55, 47, 39, 31, 23, 15, 7}
                };
        StringBuilder permutedKey = new StringBuilder();
        for (int[] ints : arr) {
            for (int anInt : ints) permutedKey.append(text.charAt(anInt - 1));

        }

        return permutedKey.toString();
    }

    public String intToBin(int i) {
        String bin = "";
        while (i != 0) {
            if (i % 2 == 0) {
                bin = bin.concat("0");
            } else {
                bin = bin.concat("1");
            }
            i /= 2;
        }
        while (bin.length() < 4) {
            bin = bin.concat("0");
        }
        String reverse = "";
        for (int j = bin.length() - 1; j >= 0; j--) {
            reverse = reverse.concat(Character.toString(bin.charAt(j)));
        }
        return reverse;
    }

    private String xor(String text1, String text2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text1.length(); i++) {


            int i1 = Integer.parseInt(String.valueOf(text1.charAt(i)));
            int i2 = Integer.parseInt(String.valueOf(text2.charAt(i)));

            stringBuilder.append((i1 + i2) % 2);
        }

        return stringBuilder.toString();
    }

    private String E(String text) {
        int[][] arr =
                {
                        {32, 1, 2, 3, 4, 5},
                        {4, 5, 6, 7, 8, 9},
                        {8, 9, 10, 11, 12, 13},
                        {12, 13, 14, 15, 16, 17},
                        {16, 17, 18, 19, 20, 21},
                        {20, 21, 22, 23, 24, 25},
                        {24, 25, 26, 27, 28, 29},
                        {28, 29, 30, 31, 32, 1}
                };
        StringBuilder permutedText = new StringBuilder();
        for (int[] ints : arr) {
            for (int anInt : ints) permutedText.append(text.charAt(anInt - 1));
        }
        return permutedText.toString();
    }

    private String f(String text, String key) {

        String string = xor(E(text), key);


        splitIntoB(string);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= 8; i++) {
            stringBuilder.append(S(i, B.get(i - 1)));



        }
        B.clear();


        return P(stringBuilder.toString());

    }

    private String S(int i, String b) {
        int[][] S1 =
                {
                        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
                };
        int[][] S2 =
                {
                        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
                };
        int[][] S3 =
                {
                        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
                };
        int[][] S4 =
                {
                        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
                };
        int[][] S5 =
                {
                        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
                };
        int[][] S6 =
                {
                        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
                };
        int[][] S7 =
                {
                        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
                };
        int[][] S8 =
                {
                        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
                };
        int linie = Integer.parseInt(Character.toString(b.charAt(0)).concat(Character.toString((b.charAt(b.length() - 1)))), 2);
        int coloana = Integer.parseInt(b.substring(1, b.length() - 1), 2);
        if (i == 1) {
            return intToBin(S1[linie][coloana]);
        }
        if (i == 2) {
            return intToBin(S2[linie][coloana]);
        }
        if (i == 3) {
            return intToBin(S3[linie][coloana]);
        }
        if (i == 4) {
            return intToBin(S4[linie][coloana]);
        }
        if (i == 5) {
            return intToBin(S5[linie][coloana]);
        }
        if (i == 6) {
            return intToBin(S6[linie][coloana]);
        }
        if (i == 7) {
            return intToBin(S7[linie][coloana]);
        }
        return intToBin(S8[linie][coloana]);


    }

    private void splitIntoB(String string) {
        for (int i = 0; i <= string.length() - 6; i += 6) {
            int j = i + 6;
            B.add(string.substring(i, j));
        }
    }

    private String P(String text) {
        int[][] arr =
                {
                        {16, 7, 20, 21},
                        {29, 12, 28, 17},
                        {1, 15, 23, 26},
                        {5, 18, 31, 10},
                        {2, 8, 24, 14},
                        {32, 27, 3, 9},
                        {19, 13, 30, 6},
                        {22, 11, 4, 25}
                };
        StringBuilder permutedKey = new StringBuilder();
        for (int[] ints : arr) {
            for (int anInt : ints) permutedKey.append(text.charAt(anInt - 1));

        }
        return permutedKey.toString();

    }

    private String IP2(String text) {
        int[][] arr =
                {
                        {40, 8, 48, 16, 56, 24, 64, 32},
                        {39, 7, 47, 15, 55, 23, 63, 31},
                        {38, 6, 46, 14, 54, 22, 62, 30},
                        {37, 5, 45, 13, 53, 21, 61, 29},
                        {36, 4, 44, 12, 52, 20, 60, 28},
                        {35, 3, 43, 11, 51, 19, 59, 27},
                        {34, 2, 42, 10, 50, 18, 58, 26},
                        {33, 1, 41, 9, 49, 17, 57, 25}
                };
        StringBuilder permutedKey = new StringBuilder();
        for (int[] ints : arr) {
            for (int anInt : ints) permutedKey.append(text.charAt(anInt - 1));

        }
        return permutedKey.toString();
    }

    private void solvePlaintext() {
        while (cursor1 + 64 < plainText.length()) {
            String text = take64Bits();
            String IP = ip(text);
            splitTextInTwo(IP);

            for (int i = 1; i <= 16; i++) {
                L[i] = R[i - 1];
                R[i] = xor(L[i - 1], f(R[i - 1], completeKeys.get(i - 1)));

            }
            criptoText += Convertor.binToHex(IP2(R[16].concat(L[16])));
        }


    }

    public void crypt() {
        solveKeys();
        solvePlaintext();



    }
    private String take64BitsCT() {
        cursor2 += 64;
        return plainText.substring(cursor2, cursor2 + 64);

    }
    private void solveCryptoText(){

        while (cursor2 + 64 < criptoText.length()) {
            String text = take64BitsCT();
            String IP = ip(text);
            splitTextInTwo(IP);

            for (int i = 1; i <= 16; i++) {
                L[i] = R[i - 1];
                R[i] = xor(L[i - 1], f(R[i - 1], completeKeys.get(16-i)));

            }

        }
    }
    public void  decrypt(){


        solveCryptoText();
        System.out.println(Convertor.binToHex(IP2(R[16].concat(L[16]))));
    }
    private void pc2(String text) {
        int[][] arr =
                {
                        {14, 17, 11, 24, 1, 5},
                        {3, 28, 15, 6, 21, 10},
                        {23, 19, 12, 4, 26, 8},
                        {16, 7, 27, 20, 13, 2},
                        {41, 52, 31, 37, 47, 55},
                        {30, 40, 51, 45, 33, 48},
                        {44, 49, 39, 56, 34, 53},
                        {46, 42, 50, 36, 29, 32}
                };
        StringBuilder permutedKey = new StringBuilder();
        for (int[] ints : arr) {
            for (int anInt : ints) permutedKey.append(text.charAt(anInt - 1));

        }

        completeKeys.add(permutedKey.toString());

    }

    public void pc1() {
        int[][] arr =
                {
                        {57, 49, 41, 33, 25, 17, 9},
                        {1, 58, 50, 42, 34, 26, 18},
                        {10, 2, 59, 51, 43, 35, 27},
                        {19, 11, 3, 60, 52, 44, 36},
                        {63, 55, 47, 39, 31, 23, 15},
                        {7, 62, 54, 46, 38, 30, 22},
                        {14, 6, 61, 53, 45, 37, 29},
                        {21, 13, 5, 28, 20, 12, 4}
                };
        StringBuilder permutedKey = new StringBuilder();
        for (int[] ints : arr) {
            for (int anInt : ints) permutedKey.append(key.charAt(anInt - 1));

        }
        key = permutedKey.toString();


    }


    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getCriptoText() {
        return criptoText;
    }

    public void setCriptoText(String criptoText) {
        this.criptoText = criptoText;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
