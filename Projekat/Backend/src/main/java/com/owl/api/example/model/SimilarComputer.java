package com.owl.api.example.model;

public class SimilarComputer {
    private double eval;
    private Computer computer;

    public SimilarComputer() {
    }

    public SimilarComputer(Computer computer, double eval) {
        this.eval = eval;
        this.computer = computer;
    }

    public double getEval() {
        return eval;
    }

    public void setEval(double eval) {
        this.eval = eval;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}
