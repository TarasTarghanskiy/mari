package main;

import java.io.Serializable;

public class ArithmeticThread extends Thread implements Serializable {
    private int n;
    private int number;
    private String string;
    private double[][] resultMatrix;
    private double[] resultVector;
    private double resultNumber;
    private double[][] matrix2;
    private double[][] matrix1;
    private double[][] matrix0;
    private double[] vector1;
    private double[] vector0;

    public ArithmeticThread(int n) {
        this.n = n;
    }

    public synchronized void exit(){
        number = 69;
    }

    public synchronized void matrixMultiplyVector(double[][] matrix0, double[] vector0, String s) {
        resultVector = null;
        string = s;
        this.matrix0 = matrix0;
        this.vector0 = vector0;
        number = 2;
    }


    public synchronized void vectorPlusVector(double[] vector0, double[] vector1, String s) {
        resultVector = null;
        string = s;
        this.vector0 = vector0;
        this.vector1 = vector1;
        number = 4;
    }

    public synchronized void vectorMultiplyVector(double[] vector0, double[] vector1, String s) {
        resultVector = null;
        string = s;
        this.vector0 = vector0;
        this.vector1 = vector1;
        number = 6;
    }

    public synchronized void vectorMultiplyVectorInNumber(double[] vector0, double[] vector1, String s) {
        resultNumber = 0;
        string = s;
        this.vector0 = vector0;
        this.vector1 = vector1;
        number = 7;
    }

    public synchronized void matrixMultiplyMatrix(double[][] matrix0, double[][] matrix1, String s) {
        resultMatrix = null;
        string = s;
        this.matrix0 = matrix0;
        this.matrix1 = matrix1;
        number = 1;
    }

    public synchronized void matrixMultiplyNumber(double[][] matrix0, double num, String s) {
        resultMatrix = null;
        string = s;
        this.matrix0 = matrix0;
        this.resultNumber= num;
        number = 8;
    }

    public synchronized void vectorMultiplyNumber(double[] vector0, double num, String s) {
        resultVector = null;
        string = s;
        this.vector0 = vector0;
        this.resultNumber= num;
        number = 9;
    }

    public synchronized void matrixPlusMatrix(double[][] matrix0, double[][] matrix1, String s) {
        resultMatrix = null;
        string = s;
        this.matrix0 = matrix0;
        this.matrix1 = matrix1;
        number = 5;
    }
    public synchronized void matrixMinusMatrix(double[][] matrix0, double[][] matrix1, String s) {
        resultMatrix = null;
        string = s;
        this.matrix0 = matrix0;
        this.matrix1 = matrix1;
        number = 3;
    }

    public synchronized double[][] getResultMatrix() {
        while (resultMatrix == null) {
            try {
                join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultMatrix;
    }

    public synchronized double[] getResultVector() {
        while (resultVector == null) {
            try {
                join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultVector;
    }

    public synchronized double getResultNumber() {
        while (resultNumber == 0) {
            try {
                join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultNumber;
    }

    @Override
    public void run() {
        while (true) {
            switch (number) {
                case 1: // A*B
                    matrix2 = new double[n][n];
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            for (int k = 0; k < n; k++)
                                matrix2[i][j] += matrix0[i][k] * matrix1[k][j];
                    Main.show(matrix2, string );
                    resultMatrix = matrix2;
                    number = 0;
                    break;
                case 2: // A*b
                    vector1 = new double[n];
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            vector1[i] += matrix0[i][j] * vector0[j];
                        }
                    }
                    Main.show(vector1, string);
                    resultVector = vector1;
                    number = 0;
                    break;
                case 3: // A-B
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            matrix0[i][j] = matrix0[i][j] - matrix1[i][j];
                    number = 0;
                    Main.show(matrix0, string);
                    resultMatrix = matrix0;
                    break;
                case 4: // a+b
                    for (int i = 0; i < n; i++)
                        vector0[i] = vector0[i] + vector1[i];
                    resultVector = vector0;
                    number = 0;
                    Main.show(vector0, string);
                    break;
                case 5: // A+B
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            matrix0[i][j] = matrix0[i][j] + matrix1[i][j];
                    number = 0;
                    Main.show(matrix0, string);
                    resultMatrix = matrix0;
                    break;
                case 6: // a*b
                    matrix0 = new double[n][n];
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            matrix0[i][j] = vector0[i] * vector1[j];
                    number = 0;
                    Main.show(matrix0, string);
                    resultMatrix = matrix0;
                    break;
                case 7: // a*b
                    int num = 0;
                    for (int i = 0; i < n; i++)
                        num += vector0[i] + vector1[i];
                    number = 0;
                    resultNumber = num;
                    break;
                case 8: // A*1
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            matrix0[i][j] = matrix0[i][j] * resultNumber;
                        }
                    }
                    Main.show(matrix0, string);
                    resultMatrix = matrix0;
                    number = 0;
                    break;
                case 9: //a*1
                    for (int i = 0; i < n; i++) {
                        vector0[i] = vector0[i] * resultNumber;
                    }
                    Main.show(vector0, string);
                    resultVector = vector0;
                    number = 0;
                    break;
                case 69:
                    return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


