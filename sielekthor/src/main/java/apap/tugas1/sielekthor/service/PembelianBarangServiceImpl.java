package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.PembelianBarangModel;
import apap.tugas1.sielekthor.model.MemberModel;
import apap.tugas1.sielekthor.repository.PembelianBarangDB;
import apap.tugas1.sielekthor.repository.MemberDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PembelianBarangServiceImpl implements PembelianBarangService{

    @Autowired
    PembelianBarangDB pembelianBarangDB;

    @Override
    public void addPembelianBarang(PembelianBarangModel pembelianBarang) { pembelianBarangDB.save(pembelianBarang); }

    @Override
    public void updatePembelianBarang(PembelianBarangModel pembelianBarang) { pembelianBarangDB.save(pembelianBarang); }

    @Override
    public List<PembelianBarangModel> getPembelianBarangList() { return pembelianBarangDB.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    @Override
    public void deletePembelianBarang(PembelianBarangModel pembelianBarang) {
        pembelianBarangDB.delete(pembelianBarang);
    }

    @Override
    public PembelianBarangModel getPembelianBarangById(Long id) {
        Optional<PembelianBarangModel> pembelianBarang = pembelianBarangDB.findById(id);
        if (pembelianBarang.isPresent()) {
            return pembelianBarang.get();
        }
        return null;
    }
}
