package id.ac.ub.filkom.contoh.control;

import id.ac.ub.filkom.contoh.entity.Anggota;
import id.ac.ub.filkom.contoh.entity.AnggotaRepository;
import id.ac.ub.filkom.contoh.entity.Buku;
import id.ac.ub.filkom.contoh.entity.BukuRepository;
import id.ac.ub.filkom.contoh.entity.Peminjaman;
import id.ac.ub.filkom.contoh.entity.PeminjamanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Control: mengatur alur peminjaman dan pengembalian buku. */
@Service
public class PeminjamanControl {

    private final PeminjamanRepository peminjamanRepository;
    private final AnggotaRepository anggotaRepository;
    private final BukuRepository bukuRepository;

    public PeminjamanControl(PeminjamanRepository peminjamanRepository,
                             AnggotaRepository anggotaRepository,
                             BukuRepository bukuRepository) {
        this.peminjamanRepository = peminjamanRepository;
        this.anggotaRepository = anggotaRepository;
        this.bukuRepository = bukuRepository;
    }

    @Transactional(readOnly = true)
    public List<Peminjaman> riwayatPeminjaman() {
        return peminjamanRepository.findAllByOrderByIdPeminjamanDesc();
    }

    @Transactional
    public Peminjaman pinjamBuku(String idAnggota, String idBuku) {
        Anggota anggota = anggotaRepository.findById(idAnggota)
                .orElseThrow(() -> new IllegalArgumentException("Anggota tidak ditemukan."));
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan."));

        if (peminjamanRepository.existsByAnggotaAndBukuAndTanggalKembaliIsNull(anggota, buku)) {
            throw new IllegalArgumentException("Anggota ini masih meminjam buku tersebut.");
        }
        long aktif = peminjamanRepository.countByAnggotaAndTanggalKembaliIsNull(anggota);
        if (!anggota.masihBisaPinjam(aktif)) {
            throw new IllegalArgumentException("Batas pinjam tercapai (" + anggota.getBatasPinjam()
                    + " buku) untuk " + anggota.getNama() + ".");
        }

        buku.kurangiStok();                       // melempar IllegalStateException jika stok habis
        return peminjamanRepository.save(new Peminjaman(anggota, buku));
    }

    @Transactional
    public void kembalikanBuku(Long idPeminjaman) {
        Peminjaman peminjaman = peminjamanRepository.findById(idPeminjaman)
                .orElseThrow(() -> new IllegalArgumentException("Data peminjaman tidak ditemukan."));
        peminjaman.kembalikan();                  // tandai kembali + stok bertambah
    }
}
