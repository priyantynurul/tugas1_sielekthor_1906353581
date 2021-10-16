package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.BarangModel;

import java.math.BigInteger;
import java.util.List;

public interface BarangService {
    //Method untuk menambah barang
    int addBarang(BarangModel barang);

    //Method untuk memperbarui barang
    void updateBarang(BarangModel barang);

    //Method untuk menghapus barang
    void deleteBarang(BarangModel barang);

    //Method untuk mendapatkan daftar barang yang telah tersimpan
    List<BarangModel> getBarangList();

    //Method untuk mendapatkan data sebuah barang berdasarkan id barang
    BarangModel getBarangById(Long id);

    //Method untuk mendapatkan data sebuah barang berdasarkan kode barang
    public BarangModel getBarangByKodeBarang(String kodeBarang);
}


