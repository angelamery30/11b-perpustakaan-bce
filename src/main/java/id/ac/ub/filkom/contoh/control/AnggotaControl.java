package id.ac.ub.filkom.contoh.control;

import id.ac.ub.filkom.contoh.entity.Anggota;
import id.ac.ub.filkom.contoh.entity.AnggotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Control: logika aplikasi untuk fitur Anggota. */
@Service
public class AnggotaControl {

    private final AnggotaRepository anggotaRepository;

    public AnggotaControl(AnggotaRepository anggotaRepository) {
        this.anggotaRepository = anggotaRepository;
    }

    public List<Anggota> lihatSemuaAnggota() {
        return anggotaRepository.findAll();
    }

    public void tambahAnggota(String idAnggota, String nama, int batasPinjam) {
        if (idAnggota == null || idAnggota.isBlank() || nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("ID dan nama wajib diisi.");
        }
        if (batasPinjam < 1) {
            throw new IllegalArgumentException("Batas pinjam minimal 1.");
        }
        if (anggotaRepository.existsById(idAnggota)) {
            throw new IllegalArgumentException("ID anggota sudah dipakai: " + idAnggota);
        }
        anggotaRepository.save(new Anggota(idAnggota, nama, batasPinjam));
    }
}
