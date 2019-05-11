package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreateVector extends Thread {
    private double[] vector;
    private boolean isReady = false;
    private String name;

    public CreateVector(int n, String name) {
        this.vector = new double[n];
        this.name = name;
    }

    public synchronized double[] getVector() {
        while (!isReady) {
            try {
                join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return vector;
    }

    @Override
    public void run() {
        switch (4) { //Main.enterNumber("для вектора " + name + " вибрати: 1 - вручну, 2 - з файла, решта випадкові")
            case 1:
                createVectorManually();
                break;
            case 2:
                vectorReader();
                break;
            default:
                createRandomVector();
        }
        Main.show(vector, "вектор "+ name);
    }

    private void createRandomVector() {
        for (int i = 0; i < vector.length; i++)
            vector[i] = (int) ((Math.random() * 10) + 1);
        isReady = true;
    }


    private void createVectorManually() {
        for (int i = 0; i < vector.length; i++)
            vector[i] = Main.enterNumber("введіть елемент [" + i + "]");
        isReady = true;
    }


    private void vectorReader() {
        String string = Main.enterString("введіть назву файла");
        try (BufferedReader br = new BufferedReader(new FileReader(string))) {
            string = br.readLine();
            int i = 0;
            for (String s : string.split(" ")) {
                vector[i] = Integer.valueOf(s);
                i++;
                if (i == vector.length) break;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Main.show(vector, "вектор зчитано");
        isReady = true;
    }

}
