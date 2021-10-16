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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="tipe")
public class TipeModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String namaTipe;

    @NotNull
    @Size(max=255)
    @Column(nullable = false)
    private String deskripsiTipe;

    //Relasi dengan PenjagaModel
    @OneToMany(mappedBy = "tipe", fetch = FetchType.LAZY)
    private List<BarangModel> listBarang;
}
