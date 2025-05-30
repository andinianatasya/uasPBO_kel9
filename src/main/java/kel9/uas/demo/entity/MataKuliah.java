package kel9.uas.demo.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mata_kuliah")
public class MataKuliah {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kode;
    private String nama;

    @OneToMany(mappedBy = "mataKuliah")
    private Set<Kelas> kelas;

    public MataKuliah(String kode, String nama){
        this.kode = kode;
        this.nama = nama;
    }

    public MataKuliah(){}

    public Long getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKelas(Set<Kelas> kelas) {
        this.kelas = kelas;
    }
}
