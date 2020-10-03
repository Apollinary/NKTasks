package com.netcracker.edu.tricks.matrix;

import com.netcracker.edu.tricks.matrix.exceptions.MatrixException;

public class MatrixWorker implements IMatrixWorker {
    @Override
    public void print(Matrix m) {
        System.out.println(m.toString());
    }

    @Override
    public boolean haveSameDimension(Matrix m1, Matrix m2) {
        return m1.getNumberOfColumns() == m2.getNumberOfColumns() && m1.getNumberOfRows() == m2.getNumberOfRows();
    }

    public static void fillMatrixRandomized(Matrix m, int start, int end) {
        for (int i = 0; i < m.getNumberOfRows(); i++) {
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                try {
                    m.setElement(i, j, (int) (Math.random() * (end - start) + start));
                } catch (MatrixException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public Matrix add(Matrix m1, Matrix m2) throws MatrixException {
        if (haveSameDimension(m1, m2)) {
            Matrix result = new Matrix(m1.getNumberOfRows(), m1.getNumberOfColumns());
            try {
                for (int i = 0; i < m1.getNumberOfRows(); i++) {
                    for (int j = 0; j < m1.getNumberOfColumns(); j++) {
                        result.setElement(i, j, (m1.getElement(i, j) + m2.getElement(i, j)));
                    }
                }
            } catch (MatrixException e) {
                System.err.println(e.getMessage());
            }

            return result;
        } else {
            throw new MatrixException("Matrices must have the same dimensions!");
        }

    }

    @Override
    public Matrix subtract(Matrix m1, Matrix m2) throws MatrixException {
        return add(m1, multiplyByScalar(m2, -1));
    }

    @Override
    public Matrix multiply(Matrix m1, Matrix m2) throws MatrixException {
        if (m1.getNumberOfColumns() != m2.getNumberOfRows()) {
            throw new MatrixException("");
        }

        Matrix result = new Matrix(m1.getNumberOfRows(), m2.getNumberOfColumns());
        try {
            for (int i = 0; i < m1.getNumberOfRows(); i++) {
                for (int j = 0; j < m2.getNumberOfColumns(); j++) {
                    int value = 0;
                    for (int k = 0; k < m1.getNumberOfColumns(); k++) {
                        value += m1.getElement(i, k) * m2.getElement(k, j);
                    }
                    result.setElement(i, j, value);
                }
            }
        } catch (MatrixException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Matrix multiplyByScalar(Matrix m, double scalar) {
        try {
            for (int i = 0; i < m.getNumberOfRows(); i++) {
                for (int j = 0; j < m.getNumberOfColumns(); j++) {
                    m.setElement(i, j, scalar * m.getElement(i, j));
                }
            }
        } catch (MatrixException e) {
            System.err.println(e.getMessage());
        }
        return m;
    }

    @Override
    public double getDeterminantByKramerMethod(Matrix m) throws MatrixException {
        if (m.getNumberOfRows() == m.getNumberOfColumns()) {
            double result = 0;
            if (m.getNumberOfColumns() == 2) {
                result = m.getElement(0, 0) * m.getElement(1, 1) - m.getElement(0, 1) * m.getElement(1, 0);
            } else {
                for (int i = 0; i < m.getNumberOfColumns(); i++) {
                    result += Math.pow(-1, i + 1 + 1) * m.getElement(0, i) * getDeterminantByKramerMethod(m.getMinor(0, i));
                }
            }
            return result;
        } else {
            throw new MatrixException("Matrix must be squared. Provided: number of rows =" + m.getNumberOfRows()
                    + ", number of columns= " + m.getNumberOfColumns() + "\n");
        }
    }
}
