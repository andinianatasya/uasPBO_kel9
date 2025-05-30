package kel9.uas.demo.repository;

import kel9.uas.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KelasRepository extends JpaRepository<Kelas, Long> {
    List<Kelas> findByDosenPengajar(Dosen dosen);
    List<Kelas> findByMataKuliah(MataKuliah mataKuliah);
    List<Kelas> findByDosenPengajarIsNull();

    @Query("SELECT k FROM Kelas k JOIN FETCH k.mataKuliah LEFT JOIN FETCH k.dosenPengajar")
    List<Kelas> findAllWithDetails();

    @Query("SELECT k FROM Kelas k JOIN FETCH k.mahasiswa WHERE k.id = :kelasId")
    Optional<Kelas> findByIdWithMahasiswa(@Param("kelasId") Long kelasId);
}