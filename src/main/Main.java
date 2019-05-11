package main;

import java.io.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        int n = enterNumber("введіть N");
        CreateMatrix matrixA0 = new CreateMatrix(n, "A0");
        matrixA0.start();
        VectorB vectorB0 = new VectorB(n);
        vectorB0.start();
        CreateMatrix matrixA1 = new CreateMatrix(n, "A1");
        matrixA1.start();
        CreateVector vectorB1 = new CreateVector(n, "b1");
        vectorB1.start();
        CreateVector vectorC1 = new CreateVector(n, "c1");
        vectorC1.start();
        CreateMatrix matrixA2 = new CreateMatrix(n, "A2");
        matrixA2.start();
        CreateMatrix matrixB2 = new CreateMatrix(n, "B2");
        matrixB2.start();
        MatrixC matrixC2 = new MatrixC(n);
        matrixC2.start();

        ArithmeticThread thread1 = new ArithmeticThread(n);
        thread1.start();
        ArithmeticThread thread2 = new ArithmeticThread(n);
        thread2.start();
        ArithmeticThread thread3 = new ArithmeticThread(n);
        thread3.start();

        thread1.matrixMultiplyVector(matrixA0.getMatrix(), vectorB0.getVectorB(), "y1 = A0*b0");
        thread2.matrixMultiplyNumber(matrixA1.getMatrix(), 15, "15*A1");
        thread3.matrixMultiplyMatrix(matrixA2.getMatrix(), matrixC2.getMatrixC(), "A2*C2");

        thread2.matrixMultiplyVector(thread2.getResultMatrix(), vectorB1.getVector(), "(15*A1)*b1");
        thread3.matrixPlusMatrix(thread3.getResultMatrix(), matrixB2.getMatrix(), "(A2*C2)+B2");

        thread2.vectorPlusVector(thread2.getResultVector(), vectorC1.getVector(),"((15*A1)*b1) + c1");

        double[] vectorY1 = thread1.getResultVector();
        double[] vectorY2 = thread2.getResultVector();
        double[][] matrixY3 = thread3.getResultMatrix();

        thread1.matrixMultiplyMatrix(matrixY3, matrixY3, "Y3*Y3");
        thread2.vectorMultiplyVectorInNumber(vectorY2, vectorY2, "y2`*y2");

        thread1.matrixMultiplyVector(thread1.getResultMatrix(), vectorY1, "(Y3*Y3)*y1");
        thread2.vectorMultiplyNumber(vectorY1,thread2.getResultNumber(), "(y2`*y2)*y1`");


        thread1.vectorPlusVector(thread1.getResultVector(), vectorY1,"((Y3*Y3)*y1)` + y1`" );
        thread2.matrixMultiplyVector(matrixY3, thread2.getResultVector(), "((y2`*y2)*y1`)*Y3");

        thread1.vectorPlusVector(vectorY2, thread1.getResultVector(), "(((Y3*Y3)*y1)` + y1`)+y2`");
        thread1.vectorPlusVector(thread1.getResultVector(), thread2.getResultVector(),
                "((((Y3*Y3)*y1)` + y1`)+y2`)+(((y2`*y2)*y1`)*Y3)");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Main.show(thread1.getResultVector(), "фінальний вектор");
        thread1.exit();
        thread2.exit();
        thread3.exit();
    }

    public static synchronized int enterNumber(String string) {
        try {
            System.out.print(string + ":  ");
            int num = Integer.parseInt(br.readLine());
            System.out.println();
            return num;
        } catch (NullPointerException | IOException | NumberFormatException e) {
            return enterNumber(string);
        }
    }


    public static synchronized String enterString(String string) {
        try {
            System.out.print(string + ":  ");
            String str = br.readLine();
            System.out.println();
            return str;
        } catch (IOException e) {
            return enterString(string);
        }
    }

    public static synchronized void vectorWriter(double[] vector) {
        String string = enterString("введіть назву файлу");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(string))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < vector.length; i++)
                stringBuilder.append(vector[i]).append(" \r\n");
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static synchronized void show(double[] vector, String string) {

        int m = enterNumber(string + ": 1 - записати в файл, 2 - вивести в консоль, 3 - ігнорувати");
        if (m == 1) {
            vectorWriter(vector);
        } else if (m == 2) {
            System.out.println(string + ": ");
            for (int i = 0; i < vector.length; i++) {
                System.out.print(vector[i] + "  ");
            }
            System.out.println();
        }

    }
    public static synchronized void show(double[][] matrix, String string) {
        int m = enterNumber(string + ": 1 - записати в файл, 2 - вивести в консоль, 3 - ігнорувати");
        if (m == 1) {
            matrixWriter(matrix);
        } else if (m == 2) {
            System.out.println(string + ": ");
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.print(matrix[i][j] + "  ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    public static synchronized void matrixWriter(double[][] matrix) {
        String string = enterString("введіть назву файлу");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(string))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    String s = matrix[i][j] + " ";
                    stringBuilder.append(s);
                }
                stringBuilder.append("\r\n");
            }
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

