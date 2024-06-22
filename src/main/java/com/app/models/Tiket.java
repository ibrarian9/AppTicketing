package com.app.models;

public class Tiket {
    private Film film;
    private Kursi kursi;
    private int jumlahTiket;
    private int totalBayar;
    private int kembali;

    public Tiket(Film film, Kursi kursi, int jumlahTiket, int totalBayar, int kembali) {
        this.film = film;
        this.kursi = kursi;
        this.jumlahTiket = jumlahTiket;
        this.totalBayar = totalBayar;
        this.kembali = kembali;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Kursi getKursi() {
        return kursi;
    }

    public void setKursi(Kursi kursi) {
        this.kursi = kursi;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public int getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }

    public int getKembali() {
        return kembali;
    }

    public void setKembali(int kembali) {
        this.kembali = kembali;
    }
}
