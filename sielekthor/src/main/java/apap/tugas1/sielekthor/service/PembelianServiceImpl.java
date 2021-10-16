package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.MemberModel;
import apap.tugas1.sielekthor.model.PembelianBarangModel;
import apap.tugas1.sielekthor.model.PembelianModel;
import apap.tugas1.sielekthor.repository.MemberDB;
import apap.tugas1.sielekthor.repository.PembelianDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class PembelianServiceImpl implements PembelianService{

    @Autowired
    PembelianDB pembelianDB;

    @Override
    public void addPembelian(PembelianModel pembelian) { pembelianDB.save(pembelian); }

    @Override
    public void updatePembelian(PembelianModel pembelian) { pembelianDB.save(pembelian); }

    @Override
    public List<PembelianModel> getPembelianList() { return pembelianDB.findAll(); }

    @Override
    public void deletePembelian(PembelianModel pembelian) {
        pembelianDB.delete(pembelian);
    }

    @Override
    public int getTotalQuantity(PembelianModel pembelian) {
        int totalQuantity = 0;
        for (PembelianBarangModel pembelianBarang:pembelian.getListPembelianBarang()){
            totalQuantity += pembelianBarang.getQuantity();
        }
        return totalQuantity;
    }

    @Override
    public PembelianModel getPembelianById(Long id) {
        Optional<PembelianModel> pembelian = pembelianDB.findById(id);
        if (pembelian.isPresent()) {
            return pembelian.get();
        }
        return null;
    }

    @Override
    public String generateNoInvoice(PembelianModel pembelian){
        String noInvoice = "";

        String firstletter = Integer.toString(((int)(pembelian.getMember().getNamaMember().toUpperCase().charAt(0))-100)%10);
        String endletter = Character.toString(pembelian.getMember().getNamaMember().toUpperCase().charAt(pembelian.getMember().getNamaMember().length()-1));
        String month = Integer.toString(pembelian.getTanggalPembelian().getMonthValue());
        String date = pembelian.getTanggalPembelian().toString().substring(0,2);
        int isCash = (pembelian.getIsCash())? 1 : 2;
        String payment = '0' + Integer.toString(isCash);
        String unik = Integer.toString((Integer.parseInt(date) + Integer.parseInt(month))*5);
        if(unik.length()==2){ unik = '0' + unik;}
        Random rd = new Random();
        String random = Character.toString((char) (rd.nextInt(26) + 'A') + (char) (rd.nextInt(26) + 'A'));

        while(pembelianDB.findByNoInvoice(noInvoice) != null){
            random = Character.toString((char) (rd.nextInt(26) + 'A') + (char) (rd.nextInt(26) + 'A'));
            noInvoice = firstletter + endletter + month + date + isCash + payment + unik + random;
        }

        return noInvoice;
    }
}
