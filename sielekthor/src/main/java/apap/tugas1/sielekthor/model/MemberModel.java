package apap.tugas1.sielekthor.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="member")
public class MemberModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jenis_kelamin", nullable = false)
    private Integer jenisKelamin;

    @NotNull
    @Size(max=255)
    @Column(name = "nama_member", nullable = false)
    private String namaMember;

    @NotNull
    @Column(name = "tanggal_pendaftaran", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tanggalPendaftaran;

    @NotNull
    @Column(name = "tanggal_lahir",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tanggalLahir;

    //Relasi dengan PenjagaModel
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PembelianModel> listPembelian;
}
