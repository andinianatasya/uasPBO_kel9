package kel9.uas.demo.controller;

import kel9.uas.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private KelasService kelasService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        // Handle logout success message
        String logout = request.getParameter("logout");
        if (logout != null) {
            model.addAttribute("logoutSuccess", true);
        }

        // Handle login error
        String error = request.getParameter("error");
        if (error != null) {
            model.addAttribute("loginError", true);
        }

        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String userType,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) String nim,
                           @RequestParam(required = false) String nip,
                           @RequestParam String nama,
                           RedirectAttributes redirectAttributes) {
        try {
            switch (userType) {
                case "MAHASISWA":
                    userService.registerMahasiswa(username, password, nim, nama);
                    break;
                case "DOSEN":
                    userService.registerDosen(username, password, nip, nama);
                    break;
                case "ADMIN":
                    userService.registerAdmin(username, password);
                    break;
                default:
                    throw new RuntimeException("Tipe user tidak valid");
            }
            redirectAttributes.addFlashAttribute("successMessage", "Registrasi berhasil! Silakan login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Registrasi gagal: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @PostMapping("/assign-dosen/{kelasId}")
    public String assignDosen(@PathVariable Long kelasId,
                              @RequestParam Long dosenId,
                              RedirectAttributes redirectAttributes) {
        try {
            kelasService.assignDosen(kelasId, dosenId);
            redirectAttributes.addFlashAttribute("successMessage", "Dosen berhasil ditugaskan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/remove-mahasiswa/{kelasId}/{mahasiswaId}")
    public String removeMahasiswa(@PathVariable Long kelasId,
                                  @PathVariable Long mahasiswaId,
                                  RedirectAttributes redirectAttributes) {
        try {
            kelasService.removeMahasiswaFromKelas(kelasId, mahasiswaId);
            redirectAttributes.addFlashAttribute("successMessage", "Mahasiswa berhasil dihapus dari kelas!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MAHASISWA"))) {
            return "redirect:/mahasiswa/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOSEN"))) {
            return "redirect:/dosen/dashboard";
        }
        return "redirect:/login";
    }
}