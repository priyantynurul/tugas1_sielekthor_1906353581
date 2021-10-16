package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.PembelianModel;

import java.math.BigInteger;
import java.util.List;

public interface PembelianService {
    //Method untuk menambah pembelian
    void addPembelian(PembelianModel pembelian);

    //Method untuk memperbarui pembelian
    void updatePembelian(PembelianModel pembelian);

    //Method untuk menghapus pembelian
    void deletePembelian(PembelianModel pembelian);

    //Method untuk mendapatkan daftar pembelian yang telah tersimpan
    List<PembelianModel> getPembelianList();

    //Method untuk mendapatkan data sebuah pembelian berdasarkan id pembelian
    PembelianModel getPembelianById(Long id);

    //Method untuk mendapatkan total barang yang dibeli pada suatu invoice
    int getTotalQuantity(PembelianModel pembelian);

    //Method untuk men-generate NoInvoice pembelian
    String generateNoInvoice(PembelianModel pembelian);
}


