package kel9.uas.demo.service;

import kel9.uas.demo.entity.Dosen;
import kel9.uas.demo.entity.Kelas;
import kel9.uas.demo.repository.DosenRepository;
import kel9.uas.demo.repository.KelasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DosenService {
    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private KelasRepository kelasRepository;

    public Optional<Dosen> findByUsername(String username) {
        return dosenRepository.findByUsernameWithKelas(username);
    }

    public List<Dosen> findAll() {
        return dosenRepository.findAll();
    }

    @Transactional
    public void daftarMengajar(String username, Long kelasId) {
        Dosen dosen = dosenRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Dosen tidak ditemukan"));

        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        if (kelas.getDosenPengajar() != null) {
            throw new RuntimeException("Kelas sudah memiliki dosen pengajar");
        }

        kelas.setDosenPengajar(dosen);
        kelasRepository.save(kelas);
    }

    @Transactional
    public void batalMengajar(String username, Long kelasId) {
        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        kelas.setDosenPengajar(null);
        kelasRepository.save(kelas);
    }
}
