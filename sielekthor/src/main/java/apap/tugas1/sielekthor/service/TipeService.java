package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.TipeModel;

import java.math.BigInteger;
import java.util.List;

public interface TipeService {
    //Method untuk menambah tipe
    void addTipe(TipeModel tipe);

    //Method untuk memperbarui tipe
    void updateTipe(TipeModel tipe);

    //Method untuk menghapus tipe
    void deleteTipe(TipeModel tipe);

    //Method untuk mendapatkan daftar tipe yang telah tersimpan
    List<TipeModel> getTipeList();

    //Method untuk mendapatkan data sebuah tipe berdasarkan id tipe
    TipeModel getTipeById(Long id);
}

