package kel9.uas.demo.repository;

import kel9.uas.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {
    Optional<Mahasiswa> findByUsername(String username);
    Optional<Mahasiswa> findByNim(String nim);
    boolean existsByNim(String nim);

    @Query("SELECT m FROM Mahasiswa m JOIN FETCH m.kelasDiambil WHERE m.username = :username")
    Optional<Mahasiswa> findByUsernameWithKelas(@Param("username") String username);
}
