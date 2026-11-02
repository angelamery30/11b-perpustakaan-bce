package id.ac.ub.filkom.contoh.boundary;

import id.ac.ub.filkom.contoh.control.BukuControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Boundary: antarmuka web untuk fitur Buku. */
@Controller
public class BukuBoundary {

    private final BukuControl bukuControl;

    public BukuBoundary(BukuControl bukuControl) {
        this.bukuControl = bukuControl;
    }

    @GetMapping("/")
    public String beranda() {
        return "redirect:/buku";
    }

    // GET http://localhost:8080/buku?kata=java
    @GetMapping("/buku")
    public String daftarBuku(@RequestParam(required = false) String kata, Model model) {
        model.addAttribute("daftarBuku", bukuControl.cariBuku(kata));
        model.addAttribute("kata", kata);
        return "buku";
    }

    @PostMapping("/buku")
    public String tambahBuku(@RequestParam String idBuku,
                             @RequestParam String judul,
                             @RequestParam int stok,
                             Model model) {
        try {
            bukuControl.tambahBuku(idBuku.trim(), judul.trim(), stok);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("daftarBuku", bukuControl.semuaBuku());
            return "buku";
        }
        return "redirect:/buku";
    }
}
