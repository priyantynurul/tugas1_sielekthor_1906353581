package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.MemberModel;

import java.math.BigInteger;
import java.util.List;

public interface MemberService {
    //Method untuk menambah member
    void addMember(MemberModel member);

    //Method untuk memperbarui member
    void updateMember(MemberModel member);

    //Method untuk menghapus member
    void deleteMember(MemberModel member);

    //Method untuk mendapatkan daftar member yang telah tersimpan
    List<MemberModel> getMemberList();

    //Method untuk mendapatkan data sebuah member berdasarkan id member
    MemberModel getMemberById(Long id);
}


