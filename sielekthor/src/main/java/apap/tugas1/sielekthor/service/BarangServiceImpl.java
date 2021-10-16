package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.repository.BarangDB;
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
public class BarangServiceImpl implements BarangService{

    @Autowired
    BarangDB barangDB;

    @Override
    public int addBarang(BarangModel barang) {
        Optional<BarangModel> barangCheck = barangDB.findByKodeBarang(barang.getKodeBarang());
        if (barangCheck.isPresent()) {
            return 0;
        }
        barangDB.save(barang);
        return 1;
    }

    @Override
    public void updateBarang(BarangModel barang) { barangDB.save(barang); }

    @Override
    public List<BarangModel> getBarangList() { return barangDB.findAll(Sort.by(Sort.Direction.ASC, "namaBarang")); }

    @Override
    public void deleteBarang(BarangModel barang) {
        barangDB.delete(barang);
    }

    @Override
    public BarangModel getBarangById(Long id) {
        Optional<BarangModel> barang = barangDB.findById(id);
        if (barang.isPresent()) {
            return barang.get();
        }
        return null;
    }

    @Override
    public BarangModel getBarangByKodeBarang(String kodeBarang) {
        Optional<BarangModel> barang = barangDB.findByKodeBarang(kodeBarang);
        if (barang.isPresent()) {
            return barang.get();
        }
        return null;
    }
}
