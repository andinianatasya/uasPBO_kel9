package kel9.uas.demo.controller;

import kel9.uas.demo.entity.*;
import kel9.uas.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private KelasService kelasService;

    @Autowired
    private MataKuliahService mataKuliahService;

    @Autowired
    private DosenService dosenService;

    @Autowired
    private MahasiswaService mahasiswaService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("semuaKelas", kelasService.findAll());
        model.addAttribute("semuaMataKuliah", mataKuliahService.findAll());
        model.addAttribute("semuaDosen", dosenService.findAll());
        model.addAttribute("semuaMahasiswa", mahasiswaService.findAll());

        return "admin/dashboard";
    }

    @PostMapping("/tambah-mata-kuliah")
    public String tambahMataKuliah(@RequestParam String kode,
                                   @RequestParam String nama,
                                   RedirectAttributes redirectAttributes) {
        try {
            mataKuliahService.createMataKuliah(kode, nama);
            redirectAttributes.addFlashAttribute("successMessage", "Mata kuliah berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/hapus-mata-kuliah/{id}")
    public String hapusMataKuliah(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            mataKuliahService.deleteMataKuliah(id);
            redirectAttributes.addFlashAttribute("successMessage", "Mata kuliah beserta kelas dan mahasiswa yang terkait berhasil dihapus!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus mata kuliah");
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/tambah-kelas")
    public String tambahKelas(@RequestParam String namaKelas,
                              @RequestParam Long mataKuliahId,
                              RedirectAttributes redirectAttributes) {
        try {
            kelasService.createKelas(namaKelas, mataKuliahId);
            redirectAttributes.addFlashAttribute("successMessage", "Kelas berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/tambah-mahasiswa-ke-kelas/{kelasId}")
    public String tambahMahasiswaKeKelas(@PathVariable Long kelasId,
                                         @RequestParam Long mahasiswaId,
                                         RedirectAttributes redirectAttributes) {
        try {
            kelasService.addMahasiswaToKelas(kelasId, mahasiswaId);
            redirectAttributes.addFlashAttribute("successMessage", "Mahasiswa berhasil ditambahkan ke kelas!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}
