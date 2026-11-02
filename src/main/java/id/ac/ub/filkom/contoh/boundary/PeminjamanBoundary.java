package id.ac.ub.filkom.contoh.boundary;

import id.ac.ub.filkom.contoh.control.AnggotaControl;
import id.ac.ub.filkom.contoh.control.BukuControl;
import id.ac.ub.filkom.contoh.control.PeminjamanControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Boundary: antarmuka web untuk fitur Peminjaman (pinjam, kembalikan, riwayat). */
@Controller
public class PeminjamanBoundary {

    private final PeminjamanControl peminjamanControl;
    private final AnggotaControl anggotaControl;
    private final BukuControl bukuControl;

    public PeminjamanBoundary(PeminjamanControl peminjamanControl,
                              AnggotaControl anggotaControl,
                              BukuControl bukuControl) {
        this.peminjamanControl = peminjamanControl;
        this.anggotaControl = anggotaControl;
        this.bukuControl = bukuControl;
    }

    // GET http://localhost:8080/peminjaman
    @GetMapping("/peminjaman")
    public String halamanPeminjaman(Model model) {
        isiHalaman(model);
        return "peminjaman";
    }

    @PostMapping("/peminjaman")
    public String pinjam(@RequestParam String idAnggota,
                         @RequestParam String idBuku,
                         Model model,
                         RedirectAttributes redirect) {
        try {
            peminjamanControl.pinjamBuku(idAnggota, idBuku);
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            isiHalaman(model);
            return "peminjaman";
        }
        redirect.addFlashAttribute("pesan", "Peminjaman berhasil dicatat.");
        return "redirect:/peminjaman";
    }

    @PostMapping("/peminjaman/{id}/kembali")
    public String kembalikan(@PathVariable("id") Long id,
                             Model model,
                             RedirectAttributes redirect) {
        try {
            peminjamanControl.kembalikanBuku(id);
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            isiHalaman(model);
            return "peminjaman";
        }
        redirect.addFlashAttribute("pesan", "Buku berhasil dikembalikan.");
        return "redirect:/peminjaman";
    }

    private void isiHalaman(Model model) {
        model.addAttribute("daftarPeminjaman", peminjamanControl.riwayatPeminjaman());
        model.addAttribute("daftarAnggota", anggotaControl.lihatSemuaAnggota());
        model.addAttribute("daftarBuku", bukuControl.semuaBuku());
    }
}
