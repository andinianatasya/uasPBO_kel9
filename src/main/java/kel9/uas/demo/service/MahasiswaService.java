package kel9.uas.demo.service;

import kel9.uas.demo.entity.Mahasiswa;
import kel9.uas.demo.entity.Kelas;
import kel9.uas.demo.repository.MahasiswaRepository;
import kel9.uas.demo.repository.KelasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MahasiswaService {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private KelasRepository kelasRepository;

    public Optional<Mahasiswa> findByUsername(String username) {
        return mahasiswaRepository.findByUsernameWithKelas(username);
    }

    public List<Mahasiswa> findAll() {
        return mahasiswaRepository.findAll();
    }

    @Transactional
    public void daftarKelas(String username, Long kelasId) {
        Mahasiswa mahasiswa = mahasiswaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Mahasiswa tidak ditemukan"));

        Kelas kelas = kelasRepository.findById(kelasId)
                .orElseThrow(() -> new RuntimeException("Kelas tidak ditemukan"));

        if (!mahasiswa.getKelasDiambil().contains(kelas)) {
            mahasiswa.getKelasDiambil().add(kelas);
            mahasiswaRepository.save(mahasiswa);
        }
    }
}
