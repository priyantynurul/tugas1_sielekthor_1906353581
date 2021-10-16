package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.PembelianBarangModel;
import apap.tugas1.sielekthor.model.MemberModel;

import java.math.BigInteger;
import java.util.List;

public interface PembelianBarangService {
    //Method untuk menambah pembelianBarang
    void addPembelianBarang(PembelianBarangModel pembelianBarang);

    //Method untuk memperbarui pembelianBarang
    void updatePembelianBarang(PembelianBarangModel pembelianBarang);

    //Method untuk menghapus pembelianBarang
    void deletePembelianBarang(PembelianBarangModel pembelianBarang);

    //Method untuk mendapatkan daftar pembelianBarang yang telah tersimpan
    List<PembelianBarangModel> getPembelianBarangList();

    //Method untuk mendapatkan data sebuah pembelianBarang berdasarkan id pembelianBarang
    PembelianBarangModel getPembelianBarangById(Long id);

}


