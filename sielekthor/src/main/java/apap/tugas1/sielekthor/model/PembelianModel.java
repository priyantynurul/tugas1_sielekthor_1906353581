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
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="pembelian")
public class PembelianModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer total;

    @NotNull
    @Column(name = "tanggal_pembelian", nullable = false)
    @DateTimeFormat(pattern = "DD/MM/YYYY")
    private LocalDateTime tanggalPembelian;

    @NotNull
    @Size(max=255)
    @Column(name = "nama_admin", nullable = false)
    private String namaAdmin;

    @NotNull
    @Size(max=255)
    @Column(name = "no_invoice", nullable = false, unique=true)
    private String noInvoice;

    @NotNull
    @Column(name = "is_cash", nullable = false)
    private Boolean isCash;

    //Relasi dengan MemberModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_member", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberModel member;

    //Relasi dengan PembelianBarangModel
    @OneToMany(mappedBy = "pembelian", fetch = FetchType.LAZY)
    private List<PembelianBarangModel> listPembelianBarang;
}
