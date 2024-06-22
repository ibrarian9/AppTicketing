package com.app;

import com.app.models.Film;
import com.app.models.Kursi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main extends JFrame {

    private final JComboBox<Film> filmComboBox;
    private final JComboBox<String> timeComboBox;
    private final ArrayList<JComboBox<Kursi>> seatComboBoxes;
    private final JTextField ticketQuantityField;
    private final JTextField paymentField;
    private final JTextArea outputArea;
    private final JPanel seatPanel;
    private final Set<Kursi> selectedSeats;
    private final JTextField searchField;
    private final JTextArea filmListTextArea;

    public Main() {
        setTitle("Pemesanan Tiket Bioskop");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        Film[] listFilm = {
                new Film("MPJ", "Malam Para Jahanam", 35000),
                new Film("AMDS", "Air Mata Diujung Sajadah", 25000),
                new Film("TM", "The Marvels", 35000),
                new Film("AATLK", "Aquaman And The Lost Kingdom", 40000),
                new Film("AARI", "Alena Anak Ratu Iblis", 30000)
        };

        selectionSort(listFilm);

        filmListTextArea = new JTextArea(5, 20);
        filmListTextArea.setEditable(false);
        JScrollPane filmListScrollPane = new JScrollPane(filmListTextArea);
        updateFilmListTextArea(listFilm);

        filmComboBox = new JComboBox<>(listFilm);
        String[] waktufilm = {
                "10.00-12.00",
                "12.00-14.00",
                "14.00-16.00",
                "16.00-18.00",
                "18.00-20.00"
        };
        timeComboBox = new JComboBox<>(waktufilm);
        ticketQuantityField = new JTextField();
        paymentField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        searchField = new JTextField();
        JButton searchButton = new JButton("Cari Film");
        searchButton.addActionListener(e -> searchFilm(listFilm));

        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 2, 10, 10));
        seatComboBoxes = new ArrayList<>();
        selectedSeats = new HashSet<>();

        JButton seatButton = new JButton("Seat");
        seatButton.addActionListener(e -> generateSeatSelection());

        inputPanel.add(new JLabel("Cari Film:"));
        inputPanel.add(searchField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(searchButton);
        inputPanel.add(new JLabel("Pilih Waktu:"));
        inputPanel.add(timeComboBox);
        inputPanel.add(new JLabel("Jumlah Tiket:"));
        inputPanel.add(ticketQuantityField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(seatButton);
        inputPanel.add(new JLabel("Pembayaran:"));
        inputPanel.add(paymentField);

        JButton orderButton = new JButton("Pesan");
        orderButton.addActionListener(e -> processOrder());

        add(filmListScrollPane, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(seatPanel, BorderLayout.WEST);
        add(orderButton, BorderLayout.SOUTH);
        add(new JScrollPane(outputArea), BorderLayout.EAST);
    }

    private void generateSeatSelection() {
        seatPanel.removeAll();
        seatComboBoxes.clear();
        selectedSeats.clear();

        int quantity = Integer.parseInt(ticketQuantityField.getText());
        Kursi[] listKursi = {
                new Kursi('A', 1), new Kursi('A', 2), new Kursi('A', 3), new Kursi('A', 4),
                new Kursi('B', 1), new Kursi('B', 2), new Kursi('B', 3), new Kursi('B', 4),
                new Kursi('C', 1), new Kursi('C', 2), new Kursi('C', 3), new Kursi('C', 4),
                new Kursi('D', 1), new Kursi('D', 2), new Kursi('D', 3), new Kursi('D', 4)
        };

        for (int i = 0; i < quantity; i++) {
            JComboBox<Kursi> seatComboBox = new JComboBox<>(listKursi);
            seatComboBoxes.add(seatComboBox);
            seatPanel.add(new JLabel("Pilih Kursi " + (i + 1) + ":"));
            seatPanel.add(seatComboBox);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private void searchFilm(Film[] films) {
        String searchText = searchField.getText().trim();
        int index = binarySearchFilm(films, searchText);
        if (index >= 0) {
            JOptionPane.showMessageDialog(this, "Film ditemukan", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            filmComboBox.setSelectedIndex(index);
        } else {
            JOptionPane.showMessageDialog(this, "Film tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int binarySearchFilm(Film[] films, String searchText) {
        int left = 0;
        int right = films.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int comparison = films[mid].getNamaFilm().compareToIgnoreCase(searchText);
            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Tidak Ada
    }

    private void processOrder() {
        Film selectedFilm = (Film) filmComboBox.getSelectedItem();
        String selectedTime = (String) timeComboBox.getSelectedItem();
        int quantity = Integer.parseInt(ticketQuantityField.getText());
        int payment = Integer.parseInt(paymentField.getText());

        selectedSeats.clear();
        StringBuilder seats = new StringBuilder();
        boolean isDuplicate = false;

        for (JComboBox<Kursi> seatComboBox : seatComboBoxes) {
            Kursi selectedSeat = (Kursi) seatComboBox.getSelectedItem();
            if (!selectedSeats.add(selectedSeat)) {
                JOptionPane.showMessageDialog(this, "Tidak dapat memilih kursi yang sama untuk lebih dari satu tiket.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                isDuplicate = true;
                break;
            }
            seats.append(selectedSeat).append(" ");
        }

        if (!isDuplicate) {
            int totalPrice = selectedFilm.getHarga() * quantity;
            int change = payment - totalPrice;

            outputArea.setText("");
            outputArea.append("Film: " + selectedFilm.getNamaFilm() + "\n");
            outputArea.append("Waktu: " + selectedTime + "\n");
            outputArea.append("Kursi: " + seats + "\n");
            outputArea.append("Jumlah Tiket: " + quantity + "\n");
            outputArea.append("Total Harga: " + totalPrice + "\n");
            outputArea.append("Bayar: " + payment + "\n");
            outputArea.append("Kembalian: " + change + "\n");
            outputArea.append("=========================================================\n");
            outputArea.append("                   CIPUTRA SERAYA XXI                    \n");
            outputArea.append("             Terimakasih telah memesan tiket             \n");
            outputArea.append("                     Selamat Menonton                    \n");
            outputArea.append("=========================================================\n");
        }

        selectedSeats.clear();
    }

    private void selectionSort(Film[] films) {
        int n = films.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (films[j].getNamaFilm().compareTo(films[minIndex].getNamaFilm()) < 0) {
                    minIndex = j;
                }
            }
            Film temp = films[minIndex];
            films[minIndex] = films[i];
            films[i] = temp;
        }
    }

    private void updateFilmListTextArea(Film[] films) {
        StringBuilder sb = new StringBuilder();
        sb.append("List Film dan Harga:\n");
        for (Film film : films) {
            sb.append(film.getNamaFilm()).append(" - Rp").append(film.getHarga()).append("\n");
        }
        filmListTextArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
