package main;

public class VectorB extends Thread {
    private double [] vectorB;
    private int n;

    public VectorB(int n) {
        this.n = n;
    }

    public synchronized double[] getVectorB() {
        while (vectorB == null){
            try {
                join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return vectorB;
    }

    @Override
    public void run() {
        double [] vector = new double[n];
        for (int i = 0; i < vector.length; i++) {
            if ((i+1)%2 == 0) vector[i] = i+1;
            vector[i] = 15.0/(i+1);
        }
        Main.show(vector, "вектор b0 готовий");
        this.vectorB = vector;
    }
}
