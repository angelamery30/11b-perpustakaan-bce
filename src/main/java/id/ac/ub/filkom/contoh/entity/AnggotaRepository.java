package id.ac.ub.filkom.contoh.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/** Akses data Anggota. */
public interface AnggotaRepository extends JpaRepository<Anggota, String> {
}
