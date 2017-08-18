package math;

import core.Options;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

public final class Matrix {

    private int rows;
    private int cols;
    
    private double[][] data;

    public static Matrix createRow(int size) {
        return new Matrix(1, size);
    }

    public static Matrix createCol(int size) {
        return new Matrix(size, 1);
    }

    public static Matrix createSq(int size) {
        return new Matrix(size, size);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSize() {
        return rows * cols;
    }

    public double get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, double value) {
        data[row][col] = value;
    }

    public void setAdd(int row, int col, double value) {
        data[row][col] += value;
    }

    public double[] getRow(int row) {
        double[] z = new double[cols];
        for (int i = 0; i < cols; i++) {
            z[i] = data[row][i];
        }
        return z;
    }

    public void setRow(int row, double value) {
        for (int i = 0; i < cols; i++) {
            data[row][i] = value;
        }
    }

    public void setAddRow(int row, double value) {
        for (int i = 0; i < cols; i++) {
            data[row][i] += value;
        }
    }

    public void setRow(int row, double[] value) {
        for (int i = 0; i < cols; i++) {
            data[row][i] = value[i];
        }
    }

    public void setAddRow(int row, double[] value) {
        for (int i = 0; i < cols; i++) {
            data[row][i] += value[i];
        }
    }

    public double[] getCol(int col) {
        double[] z = new double[rows];
        for (int i = 0; i < rows; i++) {
            z[i] = data[i][col];
        }
        return z;
    }

    public void setCol(int col, double value) {
        for (int i = 0; i < rows; i++) {
            data[i][col] = value;
        }
    }

    public void setAddCol(int col, double value) {
        for (int i = 0; i < rows; i++) {
            data[i][col] += value;
        }
    }

    public void setCol(int col, double[] value) {
        for (int i = 0; i < rows; i++) {
            data[i][col] = value[i];
        }
    }

    public void setAddCol(int col, double[] value) {
        for (int i = 0; i < rows; i++) {
            data[i][col] += value[i];
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = data;
    }
    //</editor-fold>

    public void iadd(double value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] += value;
            }
        }
    }

    public void iadd(double[][] array) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] += array[i][j];
            }
        }
    }

    public void isub(double value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] -= value;
            }
        }
    }

    public void isub(double[][] array) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] -= array[i][j];
            }
        }
    }

    public void imul(double value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= value;
            }
        }
    }

    public void imul(double[][] array) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= array[i][j];
            }
        }
    }

    public void idiv(double value) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] /= value;
            }
        }
    }

    public void idiv(double[][] array) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] /= array[i][j];
            }
        }
    }

    public void print() {
        StringBuilder z = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double value = (Math.abs(data[i][j]) > Options.num_zero) ? data[i][j] : 0.0;
                z.append(value).append(" ");
            }
            z.append('\n');
        }
        System.out.println(z.toString());
    }

    public void print(String name) {
        StringBuilder z = new StringBuilder(name);
        z.append('\n');
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double value = (Math.abs(data[i][j]) > Options.num_zero) ? data[i][j] : 0.0;
                z.append(value).append(" ");
            }
            z.append('\n');
        }
        System.out.println(z.toString());
    }

    public Matrix solve(Matrix b) {
        RealMatrix A = new Array2DRowRealMatrix(data);
        RealMatrix B = new Array2DRowRealMatrix(b.data);

        DecompositionSolver solver = new LUDecomposition(A).getSolver();
        return new Matrix(solver.solve(B).getData());
    }

    public double max() {
        double z = data[0][0];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                z = (z > data[i][j]) ? z : data[i][j];
            }
        }
        return z;
    }

    public double min() {
        double z = data[0][0];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                z = (z < data[i][j]) ? z : data[i][j];
            }
        }
        return z;
    }
    
    public double absSum() {
        double z = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                z += Math.abs(data[i][j]);
            }
        }
        return z;
    }
}
