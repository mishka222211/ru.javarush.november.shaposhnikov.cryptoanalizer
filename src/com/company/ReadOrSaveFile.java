package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadOrSaveFile {
    private static final Scanner scanner = new Scanner(System.in);

    private static File file;

    public static ArrayList<Character> readFile() {
        ArrayList<Character> textArrayList = new ArrayList<>();
        while (true) {
            System.out.println("Напишите путь к файлу");
            file = new File(scanner.nextLine());
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                if (file.length() == 0) {
                    System.out.println("Файл пустой\nВыберете другой");
                    continue;
                }
                int count;
                while ((count = bufferedReader.read()) != -1) {
                    char symbol = (char) count;
                    textArrayList.add(symbol);
                }
                return textArrayList;
            } catch (IOException e) {
                System.out.println("Некорректный ввод");

            }
        }
    }

    public static void saveFileSameFolder(ArrayList<Character> textArrayList, String additionNameFile) {
        File finalPath = new File(file.getParentFile().getPath(), additionNameFile + file.getName());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(finalPath))) {
            for (int i = 0; i < textArrayList.size(); i++) {
                bufferedWriter.write(textArrayList.get(i));
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nФайл сохранен  по пути : " + finalPath + "\n");
    }

    public static void saveFileByPath(ArrayList<Character> textArrayList) {
        String nameFile;
        File namePath;
        File finalPath;
        while (true) {
            System.out.println("Введите путь к директории");
            namePath = new File(scanner.nextLine()).getAbsoluteFile();
            System.out.println("Как будет называться файл?");
            nameFile = scanner.nextLine() + ".txt";
            finalPath = new File(namePath, nameFile);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(finalPath))) {
                for (int i = 0; i < textArrayList.size(); i++) {
                    bufferedWriter.write(textArrayList.get(i));
                    bufferedWriter.flush();
                }
                System.out.println("\nФайл сохранен  по пути : " + finalPath);
                return;
            } catch (IOException e) {
                System.out.println("\nТакого пути не существует\nПовторите попытку");
            }
        }

    }
}
