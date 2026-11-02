package id.ac.ub.filkom.contoh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

/** Entity: satu transaksi peminjaman buku oleh seorang anggota. */
@Entity
@Table(name = "peminjaman")
public class Peminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_peminjaman")
    private Long idPeminjaman;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_anggota")
    private Anggota anggota;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_buku")
    private Buku buku;

    @Column(name = "tanggal_pinjam", nullable = false)
    private LocalDate tanggalPinjam;

    @Column(name = "tanggal_kembali")
    private LocalDate tanggalKembali;

    protected Peminjaman() { }

    public Peminjaman(Anggota anggota, Buku buku) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = LocalDate.now();
    }

    public Long getIdPeminjaman()        { return idPeminjaman; }
    public Anggota getAnggota()          { return anggota; }
    public Buku getBuku()                { return buku; }
    public LocalDate getTanggalPinjam()  { return tanggalPinjam; }
    public LocalDate getTanggalKembali() { return tanggalKembali; }

    public boolean sedangDipinjam() {
        return tanggalKembali == null;
    }

    /** Aturan domain: tandai dikembalikan dan kembalikan stok buku. */
    public void kembalikan() {
        if (!sedangDipinjam()) {
            throw new IllegalStateException("Buku ini sudah dikembalikan.");
        }
        this.tanggalKembali = LocalDate.now();
        this.buku.tambahStok();
    }
}
