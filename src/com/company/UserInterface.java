package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private String currentAlphabet = "(Русский)";
    private Crypto crypto;


    //Language Selection
    public void changeLanguage() {
        while (true) {
            System.out.println(currentAlphabet + "\nВыберете алфавит\n(1) - Русский\n(2) - Английский\n(3) - Украинский\n(4) - Завершить программу");
            switch (scanner.next().toLowerCase()) {
                case "1":
                    crypto.addAlphabet(Languages.RUSSIAN);
                    currentAlphabet = "(Русский)";
                    return;
                case "2":
                    crypto.addAlphabet(Languages.ENGLISH);
                    currentAlphabet = "(Английский)";
                    return;
                case "3":
                    crypto.addAlphabet(Languages.UKRAINIAN);
                    currentAlphabet = "(Украинский)";
                    return;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("\nНекорректный ввод\nПовторите попытку\n");
            }

        }
    }

    //Operation selection
    public void openUserMainInterface() {
        crypto = new Crypto();
        while (true) {
            System.out.println(currentAlphabet + "\nВыберите операцию\n(1) - Зашифровать\n(2) - Расшифровать\n(3) - Расшифровать Brute Force\n(4) - Поменять Язык\n(5) - Завершить программу");
            switch (scanner.next().toLowerCase()) {
                case "1":
                    openUserSaveFileInterface(crypto.encryptFile(), "Encrypt_");
                    break;
                case "2":
                    openUserSaveFileInterface(crypto.decryptFile(), "Decrypt_");
                    break;
                case "3":
                    openUserSaveFileInterface(crypto.bruteForceFile(), "BruteForce_");
                    break;
                case "4":
                    changeLanguage();
                    break;
                case "5":
                    System.exit(0);
                default:
                    System.out.println("\nНекорректный ввод\nПовторите попытку\n");
            }
        }
    }

    //Choosing to save a file
    public void openUserSaveFileInterface(ArrayList<Character> saveText, String additionNameFile) {

        while (true) {
            System.out.println("\nСохранить файл?\n(1) - Да\n(2) - Нет");
            switch (scanner.next()) {
                case "1":
                    openUserChoiceSaveFileInterface(saveText, additionNameFile);
                    return;
                case "2":
                    return;
                default:
                    System.out.println("\nНекорректный ввод\nПовторите попытку\n");
            }
        }
    }

    //Choosing a saving method
    public void openUserChoiceSaveFileInterface(ArrayList<Character> saveText, String additionNameFile) {

        while (true) {
            System.out.println("Как сохранить?\n(1) - Ввести путь\n(2) - Сохранить в эту же папку");
            switch (scanner.next()) {
                case "1":
                    ReadOrSaveFile.saveFileByPath(saveText);
                    return;
                case "2":
                    ReadOrSaveFile.saveFileSameFolder(saveText, additionNameFile);
                    return;
            }
        }
    }
}
