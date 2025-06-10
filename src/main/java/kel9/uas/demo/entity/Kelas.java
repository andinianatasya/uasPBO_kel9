package kel9.uas.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "kelas")
public class Kelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaKelas;

    private Integer semester;
    private String ruangan;
    private String jadwal;

    @ManyToOne
    @JoinColumn(name = "mata_kuliah_id")
    private MataKuliah mataKuliah;

    @ManyToOne
    @JoinColumn(name = "dosen_id")
    private Dosen dosenPengajar;

    @ManyToMany(mappedBy = "kelasDiambil")
    private Set<Mahasiswa> mahasiswa;

    public Kelas(String namaKelas, MataKuliah mataKuliah, Dosen dosenPengajar,
                 Integer semester, String ruangan, String jadwal){
        this.namaKelas = namaKelas;
        this.mataKuliah = mataKuliah;
        this.dosenPengajar = dosenPengajar;
        this.semester = semester;
        this.ruangan = ruangan;
        this.jadwal = jadwal;
    }

    public Kelas(String namaKelas, MataKuliah mataKuliah, Dosen dosenPengajar){
        this.namaKelas = namaKelas;
        this.mataKuliah = mataKuliah;
        this.dosenPengajar = dosenPengajar;
    }

    public Kelas(){}

    public Long getId() {
        return id;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public MataKuliah getMataKuliah() {
        return mataKuliah;
    }

    public Dosen getDosenPengajar() {
        return dosenPengajar;
    }

    public Set<Mahasiswa> getMahasiswa() {
        return mahasiswa;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public void setDosenPengajar(Dosen dosenPengajar) {
        this.dosenPengajar = dosenPengajar;
    }

    public void setMahasiswa(Set<Mahasiswa> mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public void setMataKuliah(MataKuliah mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }
}