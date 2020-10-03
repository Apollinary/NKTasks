package com.netcracker.edu.tricks.matrix;

import com.netcracker.edu.tricks.matrix.exceptions.MatrixException;

public interface IMatrixWorker {
    public void print(Matrix m);
    public boolean haveSameDimension(Matrix m1, Matrix m2);
    public Matrix add(Matrix m1, Matrix m2) throws MatrixException;
    public Matrix subtract(Matrix m1, Matrix m2) throws MatrixException;
    public Matrix multiply(Matrix m1, Matrix m2) throws MatrixException;
    public Matrix multiplyByScalar(Matrix m, double scalar);
    public double getDeterminantByKramerMethod(Matrix m) throws MatrixException;
}
