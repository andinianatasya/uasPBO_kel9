package kel9.uas.demo.controller;

import kel9.uas.demo.entity.*;
import kel9.uas.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {
    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private KelasService kelasService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        Mahasiswa mahasiswa = mahasiswaService.findByUsername(username).orElse(null);

        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("semuaKelas", kelasService.findAll());

        return "mahasiswa/dashboard";
    }

    @PostMapping("/daftar-kelas/{kelasId}")
    public String daftarKelas(@PathVariable Long kelasId,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        try {
            mahasiswaService.daftarKelas(authentication.getName(), kelasId);
            redirectAttributes.addFlashAttribute("successMessage", "Berhasil mendaftar kelas!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/mahasiswa/dashboard";
    }
}
