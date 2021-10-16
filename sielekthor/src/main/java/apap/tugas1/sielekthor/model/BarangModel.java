package apap.tugas1.sielekthor.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="barang")
public class BarangModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(name = "nama_barang", nullable = false)
    private String namaBarang;

    @NotNull
    @Column(nullable = false)
    private Integer stok;

    @NotNull
    @Column(name = "jumlah_garansi", nullable = false)
    private Integer jumlahGaransi;

    @NotNull
    @Size(max=255)
    @Column(name = "deskripsi_barang", nullable = false)
    private String deskripsiBarang;

    @NotNull
    @Size(max=255)
    @Column(name = "kode_barang", nullable = false, unique=true)
    private String kodeBarang;

    @NotNull
    @Size(max=255)
    @Column(name = "merk_barang", nullable = false)
    private String merkBarang;

    @NotNull
    @Column(name = "harga_barang", nullable = false)
    private Integer hargaBarang;

    //Relasi dengan TipeModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idTipe", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TipeModel tipe;

    //Relasi dengan PenjagaModel
    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY)
    private List<PembelianBarangModel> listPembelianBarang;
}
