package apap.tugas1.sielekthor.repository;

import apap.tugas1.sielekthor.model.PembelianBarangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;

import java.util.Optional;

@Repository
public interface PembelianBarangDB extends JpaRepository<PembelianBarangModel, Long>{
    Optional<PembelianBarangModel> findById(BigInteger id);
}
