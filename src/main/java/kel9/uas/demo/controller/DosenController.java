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
@RequestMapping("/dosen")
public class DosenController {
    @Autowired
    private DosenService dosenService;

    @Autowired
    private KelasService kelasService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        Dosen dosen = dosenService.findByUsername(username).orElse(null);

        model.addAttribute("dosen", dosen);
        model.addAttribute("semuaKelas", kelasService.findAll());
        model.addAttribute("kelasTanpaDosen", kelasService.findKelasTanpaDosen());

        return "dosen/dashboard";
    }

    @PostMapping("/daftar-mengajar/{kelasId}")
    public String daftarMengajar(@PathVariable Long kelasId,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        try {
            dosenService.daftarMengajar(authentication.getName(), kelasId);
            redirectAttributes.addFlashAttribute("successMessage", "Berhasil mendaftar mengajar!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/dosen/dashboard";
    }
}
