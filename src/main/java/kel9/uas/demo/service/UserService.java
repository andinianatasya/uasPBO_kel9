package kel9.uas.demo.service;

import kel9.uas.demo.entity.*;
import kel9.uas.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private DosenRepository dosenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerMahasiswa(String username, String password, String nim, String nama) {
        if (userRepository.existsByUsername(username) || mahasiswaRepository.existsByNim(nim)) {
            throw new RuntimeException("Username atau NIM sudah digunakan");
        }

        Mahasiswa mahasiswa = new Mahasiswa(username, passwordEncoder.encode(password), nim, nama);
        return mahasiswaRepository.save(mahasiswa);
    }

    public User registerDosen(String username, String password, String nip, String nama) {
        if (userRepository.existsByUsername(username) || dosenRepository.existsByNip(nip)) {
            throw new RuntimeException("Username atau NIP sudah digunakan");
        }

        Dosen dosen = new Dosen(username, passwordEncoder.encode(password), nip, nama);
        return dosenRepository.save(dosen);
    }

    public User registerAdmin(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username sudah digunakan");
        }

        User admin = new User(username, passwordEncoder.encode(password), "ADMIN");
        return userRepository.save(admin);
    }
}
