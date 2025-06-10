package kel9.uas.demo.service;

import kel9.uas.demo.entity.*;
import kel9.uas.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class KelasService {
    @Autowired
    private KelasRepository kelasRepository;

    @Autowired
    private MataKuliahRepository mataKuliahRepository;

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public List<Kelas> findAll() {
        return kelasRepository.findAllWithDetails();
    }

    public Optional<Kelas> findById(Long id) {
        return kelasRepository.findById(id);
    }

    public List<Kelas> findByDosenPengajar(Dosen dosen) {
        return kelasRepository.findByDosenPengajar(dosen);
    }

    public List<Kelas> findKelasTanpaDosen() {
        return kelasRepository.findByDosenPengajarIsNull();
    }

    @Transactional
    public Kelas createKelas(String namaKelas, Long mataKuliahId) {
        MataKuliah mataKuliah = mataKuliahRepository.findById(mataKuliahId)
                .orElseThrow(() -> new RuntimeException("Mata kuliah tidak ditemukan"));

        Kelas kelas = new Kelas(namaKelas, mataKuliah, null);
        return kelasRepository.save(kelas);
    }

    // Overloaded method untuk create kelas dengan informasi lengkap
    @Transactional
    public Kelas createKelas(String namaKelas, Long mataKuliahId, Integer semester,
                             String ruangan, String jadwal) {
        MataKuliah mataKuliah = mataKuliahRepository.findById(mataKuliahId)
                .orElseThrow(() -> new RuntimeException("Mata kuliah tidak ditemukan"));

        Kelas kelas = new Kelas(namaKelas, mataKuliah, null, semester, ruangan, jadwal);
        return kelasRepository.save(kelas);
    }

    @Transactional
    public void assignDosen(Long kelasId, Long dosenId) {
        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        Dosen dosen = dosenRepository.findById(dosenId)
                .orElseThrow(() -> new RuntimeException("Dosen tidak ditemukan"));

        kelas.setDosenPengajar(dosen);
        kelasRepository.save(kelas);
    }

    @Transactional
    public void addMahasiswaToKelas(Long kelasId, Long mahasiswaId) {
        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        Mahasiswa mahasiswa = mahasiswaRepository.findById(mahasiswaId)
                .orElseThrow(() -> new RuntimeException("Mahasiswa tidak ditemukan"));

        if (kelas.getMahasiswa().contains(mahasiswa)) {
            throw new RuntimeException("Mahasiswa sudah terdaftar di kelas ini");
        }

        kelas.getMahasiswa().add(mahasiswa);
        mahasiswa.getKelasDiambil().add(kelas);

        kelasRepository.save(kelas);
        mahasiswaRepository.save(mahasiswa);
    }

    @Transactional
    public void removeMahasiswaFromKelas(Long kelasId, Long mahasiswaId) {
        Mahasiswa mahasiswa = mahasiswaRepository.findById(mahasiswaId)
                .orElseThrow(() -> new RuntimeException("Mahasiswa tidak ditemukan"));

        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        mahasiswa.getKelasDiambil().remove(kelas);
        mahasiswaRepository.save(mahasiswa);
    }

    @Transactional
    public void updateKelasDetails(Long kelasId, String namaKelas, Integer semester,
                                   String ruangan, String jadwal) {
        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        kelas.setNamaKelas(namaKelas);
        kelas.setSemester(semester);
        kelas.setRuangan(ruangan);
        kelas.setJadwal(jadwal);

        kelasRepository.save(kelas);
    }
}