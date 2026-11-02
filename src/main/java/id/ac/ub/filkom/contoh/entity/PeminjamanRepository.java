package id.ac.ub.filkom.contoh.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Akses data Peminjaman. */
public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {

    long countByAnggotaAndTanggalKembaliIsNull(Anggota anggota);

    boolean existsByAnggotaAndBukuAndTanggalKembaliIsNull(Anggota anggota, Buku buku);

    List<Peminjaman> findAllByOrderByIdPeminjamanDesc();
}
