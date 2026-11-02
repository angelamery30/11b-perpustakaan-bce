package id.ac.ub.filkom.contoh.control;

import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.entity.BukuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Control: logika aplikasi untuk fitur Buku. */
@Service
public class BukuControl {

    private final BukuRepository bukuRepository;

    public BukuControl(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    public List<Buku> semuaBuku() {
        return bukuRepository.findAll();
    }

    public List<Buku> cariBuku(String kata) {
        if (kata == null || kata.isBlank()) {
            return bukuRepository.findAll();
        }
        return bukuRepository.findByJudulContainingIgnoreCase(kata.trim());
    }

    public Buku tambahBuku(String idBuku, String judul, int stok) {
        if (idBuku == null || idBuku.isBlank() || judul == null || judul.isBlank()) {
            throw new IllegalArgumentException("ID dan judul wajib diisi.");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        if (bukuRepository.existsById(idBuku)) {
            throw new IllegalArgumentException("ID buku sudah dipakai: " + idBuku);
        }
        return bukuRepository.save(new Buku(idBuku, judul, stok));
    }
}
