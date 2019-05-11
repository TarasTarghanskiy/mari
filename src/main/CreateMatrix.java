package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreateMatrix extends Thread {
    private double matrix[][];
    private boolean isReady = false;
    private String name;

    public CreateMatrix(int n, String name) {
        this.matrix = new double[n][n];
        this.name = name;
    }

    public synchronized double[][] getMatrix() {
        while (!isReady) {
            try {
                join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return matrix;
    }

    @Override
    public void run() {
        switch (Main.enterNumber("для матриці " + name
                + " вибрати: 1 - записати вручну, 2 - отримати з файла, 3 - заповнити випадковими числами")) {
            case 1:
                createMatrixManually();
                break;
            case 2:
                matrixReader();
                break;
            default:
                createRandomMatrix();
        }
        Main.show(matrix, "матриця "+ name);
    }

    private void createRandomMatrix() {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = (int) ((Math.random() * 10) + 1);
        isReady = true;
    }


    private void createMatrixManually() {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = Main.enterNumber("введіть елемент [" + i + "][" + j + "]");
        isReady = true;
    }


    private void matrixReader() {
        String string = Main.enterString("введіть назву файла");
        try (BufferedReader br = new BufferedReader(new FileReader(string))) {
            for (int i = 0; i < matrix.length; i++) {
                string = br.readLine();
                int j = 0;
                for (String s : string.split(" ")) {
                    matrix[i][j] = Integer.valueOf(s);
                    j++;
                    if (j == matrix.length) break;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Main.show(matrix, "матрицю зчитано");
        isReady = true;
    }

}
