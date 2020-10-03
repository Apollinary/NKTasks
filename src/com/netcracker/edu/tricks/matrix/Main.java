package com.netcracker.edu.tricks.matrix;

import com.netcracker.edu.tricks.matrix.exceptions.MatrixException;

public class Main {
    public static void main(String[] args) {
        try {
            Matrix firstMatrix = new Matrix(2, 3);
            MatrixWorker.fillMatrixRandomized(firstMatrix, 1, 10);
            System.out.println("First matrix is: " + firstMatrix);

            Matrix secondMatrix = new Matrix(3, 4);
            MatrixWorker.fillMatrixRandomized(secondMatrix, 1, 15);
            System.out.println("Second matrix is: " + secondMatrix);

            MatrixWorker matrixWorker = new MatrixWorker();
            System.out.println("Result of multiplication: " + matrixWorker.multiply(firstMatrix, secondMatrix));

            Matrix thirdMatrix = new Matrix(3, 4);
            MatrixWorker.fillMatrixRandomized(thirdMatrix, 2, 9);
            System.out.println("Third matrix is: " + thirdMatrix);

            System.out.println("Result of addition second matrix + third matrix:");
            matrixWorker.print(matrixWorker.add(secondMatrix, thirdMatrix));

            System.out.println("Result of subtraction second matrix - third matrix:");
            matrixWorker.print(matrixWorker.subtract(secondMatrix, thirdMatrix));

            Matrix squaredMatrix = new Matrix(4, 4);
            MatrixWorker.fillMatrixRandomized(squaredMatrix, 1, 10);
            System.out.println("Squared matrix: " + squaredMatrix);
            System.out.println("Determinant of squared matrix: " + matrixWorker.getDeterminantByKramerMethod(squaredMatrix));

        } catch (MatrixException e) {
            System.err.println("Error of creating matrix " + e);
        }
    }
}