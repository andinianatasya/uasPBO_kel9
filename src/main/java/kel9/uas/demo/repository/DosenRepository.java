package kel9.uas.demo.repository;

import kel9.uas.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DosenRepository extends JpaRepository<Dosen, Long> {
    Optional<Dosen> findByUsername(String username);
    Optional<Dosen> findByNip(String nip);
    boolean existsByNip(String nip);

    @Query("SELECT d FROM Dosen d JOIN FETCH d.kelasYangDiajarkan WHERE d.username = :username")
    Optional<Dosen> findByUsernameWithKelas(@Param("username") String username);
}