package com.company;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Crypto {
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<Character> alphabetMain;

    public Crypto() {
        alphabetMain = new ArrayList<>();
        addAlphabet(Languages.RUSSIAN);
    }

    public void addAlphabet(String alphabet) {
        alphabetMain.clear();
        for (int i = 0; i < alphabet.length(); i++) {
            alphabetMain.add(alphabet.charAt(i));

        }
    }

    /*
    we read the original text,
    read the key,
    find the index of each character in the main alphabet,
    then move forward through the indexes by the number of the key, adjusting to the conditions,
    return the resulting character and add it to the textEncryptArrayList
     */
    public ArrayList<Character> encryptFile() {
        ArrayList<Character> textOriginalArrayList = ReadOrSaveFile.readFile();
        ArrayList<Character> textEncryptArrayList = new ArrayList<>();
        int key = getKey();
        int remnant = key % alphabetMain.size();//remainder for the shift, after full alphabetical traversals
        char symbol;
        for (int i = 0; i < textOriginalArrayList.size(); i++) {
            int index = alphabetMain.indexOf(textOriginalArrayList.get(i));
            if (index == -1) {
                textEncryptArrayList.add(textOriginalArrayList.get(i));
                continue;
            }

            if (key >= alphabetMain.size()) {
                if (remnant + index >= alphabetMain.size()) {
                    symbol = alphabetMain.get(index + remnant - alphabetMain.size());
                    textEncryptArrayList.add(symbol);
                } else {
                    symbol = alphabetMain.get(index + remnant);
                    textEncryptArrayList.add(symbol);
                }
            } else if (index + key >= alphabetMain.size()) {
                symbol = alphabetMain.get(index + key - alphabetMain.size());
                textEncryptArrayList.add(symbol);
            } else {
                symbol = alphabetMain.get(index + key);
                textEncryptArrayList.add(symbol);
            }
        }
        for (Character character : textEncryptArrayList) {
            System.out.print(character);
        }
        return textEncryptArrayList;
    }

    public ArrayList<Character> decryptFile() {
        ArrayList<Character> textOriginalArrayList = ReadOrSaveFile.readFile();
        ArrayList<Character> textDecryptArrayList = new ArrayList<>();
        int key = getKey();
        decoding(textOriginalArrayList, textDecryptArrayList, key);
        for (Character character : textDecryptArrayList) {
            System.out.print(character);
        }
        return textDecryptArrayList;
    }

    /*
    We accept the arguments
    get the index of each character in our alphabet
    then move back along the key value, adjusting to the conditions,
    pull out this character and add it to the list
     */
    private void decoding(ArrayList<Character> textOriginalArrayList, ArrayList<Character> textDecryptArrayList, int key) {
        int remnant = key % alphabetMain.size();//remainder for the shift, after full alphabetical traversals
        char symbol;
        for (int i = 0; i < textOriginalArrayList.size(); i++) {
            int index = alphabetMain.indexOf(textOriginalArrayList.get(i));
            if (index == -1) {
                textDecryptArrayList.add(textOriginalArrayList.get(i));
                continue;
            }

            if (key >= alphabetMain.size()) {
                if (index - remnant < 0) {
                    symbol = alphabetMain.get(alphabetMain.size() + index - remnant);
                    textDecryptArrayList.add(symbol);
                } else {
                    symbol = alphabetMain.get(index - remnant);
                    textDecryptArrayList.add(symbol);
                }
            } else if (index - key < 0) {
                symbol = alphabetMain.get(alphabetMain.size() + index - key);
                textDecryptArrayList.add(symbol);
            } else {
                symbol = alphabetMain.get(index - key);
                textDecryptArrayList.add(symbol);
            }
        }
    }

    private int getKey() {
        System.out.println("Напишите ключ");
        int key;
        while (true) {
            try {
                key = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Не корректно введено число\nПовторите попытку");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return key;
    }

    /*
    We read the source text
    read the key
    in the loop, we iterate over the decryption key
    if there are matches in the text according to our condition's template
    we will find out from the user whether the decrypted text is similar to the original
    if not, we continue to select the key until the next match
    if yes, then call the method (UserInterface.openUserSaveFileInterface)
     */
    public ArrayList<Character> bruteForceFile() {
        ArrayList<Character> textArrayList = ReadOrSaveFile.readFile();
        ArrayList<Character> bruteForceText = new ArrayList<>();
        int key = 1;

        while (true) {
            decoding(textArrayList, bruteForceText, key);
            for (int i = 0; i < bruteForceText.size() - 1; i++) {
                if ((bruteForceText.get(i).equals('.') || bruteForceText.get(i).equals(',')) && bruteForceText.get(i + 1).equals(' ')) {
                    System.out.println("Это похоже на расшифрованный текст ?");
                    for (Character character : bruteForceText) {

                        System.out.print(character);
                    }
                    System.out.println();
                    System.out.println("Да (1)  Нет(2)");
                    switch (scanner.next()) {
                        case "1":
                            return bruteForceText;
                        case "2":
                            i = bruteForceText.size() - 2;//exiting the inner loop
                            break;
                        default:
                            System.out.println("\nНекорректный ввод\nПовторите попытку\n");
                    }
                }
            }
            bruteForceText.clear();
            key++;
        }
    }
}
