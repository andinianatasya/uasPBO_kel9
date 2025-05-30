package kel9.uas.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa extends User{
    @Column(unique = true)
    private String nim;
    private String nama;

    @ManyToMany
    @JoinTable(
            name = "mahasiswa_kelas",
            joinColumns = @JoinColumn(name = "mahasiswa_id"),
            inverseJoinColumns = @JoinColumn(name = "kelas_id")
    )
    private List<Kelas> kelasDiambil = new ArrayList<>();

    public Mahasiswa(String username, String password, String nim, String nama){
        super(username, password, "MAHASISWA");
        this.nim = nim;
        this.nama = nama;
    }

    public Mahasiswa(){}

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKelasDiambil(List<Kelas> kelasDiambil) {
        this.kelasDiambil = kelasDiambil;
    }

    public List<Kelas> getKelasDiambil() {
        return kelasDiambil;
    }
}
