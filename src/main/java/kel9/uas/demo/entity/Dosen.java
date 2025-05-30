package kel9.uas.demo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dosen")
public class Dosen extends User{
    @Column(unique = true)
    private String nip;

    private String nama;

    @OneToMany(mappedBy = "dosenPengajar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Kelas> kelasYangDiajarkan = new HashSet<>();

    public Dosen(String username, String password, String nip, String nama){
        super(username, password, "DOSEN");
        this.nip = nip;
        this.nama = nama;
    }

    public Dosen(){}

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Set<Kelas> getKelasYangDiajarkan() {
        return kelasYangDiajarkan;
    }

    public void setKelasYangDiajarkan(Set<Kelas> kelasYangDiajarkan) {
        this.kelasYangDiajarkan = kelasYangDiajarkan;
    }
}
