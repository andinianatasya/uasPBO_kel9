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
    public void removeMahasiswaFromKelas(Long kelasId, Long mahasiswaId) {
        Mahasiswa mahasiswa = mahasiswaRepository.findById(mahasiswaId)
                .orElseThrow(() -> new RuntimeException("Mahasiswa tidak ditemukan"));

        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        mahasiswa.getKelasDiambil().remove(kelas);
        mahasiswaRepository.save(mahasiswa);
    }
}
