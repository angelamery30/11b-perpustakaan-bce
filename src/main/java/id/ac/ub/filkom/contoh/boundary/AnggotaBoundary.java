package id.ac.ub.filkom.contoh.boundary;

import id.ac.ub.filkom.contoh.control.AnggotaControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Boundary: antarmuka web untuk fitur Anggota. */
@Controller
public class AnggotaBoundary {

    private final AnggotaControl anggotaControl;

    public AnggotaBoundary(AnggotaControl anggotaControl) {
        this.anggotaControl = anggotaControl;
    }

    // GET http://localhost:8080/anggota
    @GetMapping("/anggota")
    public String lihatAnggota(Model model) {
        model.addAttribute("daftarAnggota", anggotaControl.lihatSemuaAnggota());
        return "anggota";
    }

    @PostMapping("/anggota")
    public String tambahAnggota(@RequestParam String idAnggota,
                                @RequestParam String nama,
                                @RequestParam int batasPinjam,
                                Model model) {
        try {
            anggotaControl.tambahAnggota(idAnggota.trim(), nama.trim(), batasPinjam);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("daftarAnggota", anggotaControl.lihatSemuaAnggota());
            return "anggota";
        }
        return "redirect:/anggota";
    }
}
