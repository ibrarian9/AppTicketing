package com.app.models;

public class Film {
    private String kodeFilm;
    private String namaFilm;
    private int harga;

    public Film(String kodeFilm, String namaFilm, int harga) {
        this.kodeFilm = kodeFilm;
        this.namaFilm = namaFilm;
        this.harga = harga;
    }

    public String getKodeFilm() {
        return kodeFilm;
    }

    public void setKodeFilm(String kodeFilm) {
        this.kodeFilm = kodeFilm;
    }

    public String getNamaFilm() {
        return namaFilm;
    }

    public void setNamaFilm(String namaFilm) {
        this.namaFilm = namaFilm;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return namaFilm;
    }
}
