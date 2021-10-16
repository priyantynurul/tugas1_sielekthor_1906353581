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
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="pembelian_barang")
public class PembelianBarangModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relasi dengan BarangModel++++>                                                                                                                 ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''/                                        'kmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm,kmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm,kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk/3wwwwwwwwwwwwwwe45 Size---------------------------------------------------------------------------------------------------------------------------------gt6. b
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_barang", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BarangModel barang;

    //Relasi dengan PembelianModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pembelian", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PembelianModel pembelian;

    @NotNull
    @Column(name = "tanggal_garansi", nullable = false)
    private Date tanggalGaransi;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;
}
