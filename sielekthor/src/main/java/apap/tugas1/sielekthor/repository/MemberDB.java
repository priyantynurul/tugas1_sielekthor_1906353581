package apap.tugas1.sielekthor.repository;

import apap.tugas1.sielekthor.model.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;

import java.util.Optional;

@Repository
public interface MemberDB extends JpaRepository<MemberModel, Long>{
    Optional<MemberModel> findById(BigInteger id);
}
