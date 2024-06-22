package com.app.models;

import java.util.Objects;

public class Kursi {
    private final char kodeKursi;
    private final int nomorKursi;

    public Kursi(char kodeKursi, int nomorKursi) {
        this.kodeKursi = kodeKursi;
        this.nomorKursi = nomorKursi;
    }

    public char getKodeKursi() {
        return kodeKursi;
    }

    public int getNomorKursi() {
        return nomorKursi;
    }

    @Override
    public String toString() {
        return kodeKursi + "" + nomorKursi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kursi kursi = (Kursi) o;
        return kodeKursi == kursi.kodeKursi &&
                nomorKursi == kursi.nomorKursi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kodeKursi, nomorKursi);
    }
}
