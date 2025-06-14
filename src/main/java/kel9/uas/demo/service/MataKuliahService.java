package kel9.uas.demo.service;

import kel9.uas.demo.entity.*;
import kel9.uas.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
public class MataKuliahService {
    @Autowired
    private MataKuliahRepository mataKuliahRepository;

    @Autowired
    private KelasRepository kelasRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public List<MataKuliah> findAll() {
        return mataKuliahRepository.findAll();
    }

    public Optional<MataKuliah> findById(Long id) {
        return mataKuliahRepository.findById(id);
    }

    @Transactional
    public MataKuliah createMataKuliah(String kode, String nama) {
        if (mataKuliahRepository.existsByKode(kode)) {
            throw new RuntimeException("Kode mata kuliah sudah digunakan");
        }

        MataKuliah mataKuliah = new MataKuliah(kode, nama);
        return mataKuliahRepository.save(mataKuliah);
    }

    @Transactional
    public void deleteMataKuliah(Long id) {
        MataKuliah mataKuliah = mataKuliahRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mata kuliah tidak ditemukan"));

        List<Kelas> kelasList = kelasRepository.findByMataKuliah(mataKuliah);

        for (Kelas kelas : kelasList) {
            if (kelas.getMahasiswa() != null && !kelas.getMahasiswa().isEmpty()) {

                List<Mahasiswa> mahasiswaList = List.copyOf(kelas.getMahasiswa());

                for (Mahasiswa mahasiswa : mahasiswaList) {

                    mahasiswa.getKelasDiambil().remove(kelas);
                    mahasiswaRepository.save(mahasiswa);
                }

                kelas.getMahasiswa().clear();
                kelasRepository.save(kelas);
            }
        }

        kelasRepository.deleteAll(kelasList);

        mataKuliahRepository.deleteById(id);
    }
}