package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.PembelianModel;
import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.model.PembelianBarangModel;
import apap.tugas1.sielekthor.service.BarangService;
import apap.tugas1.sielekthor.service.PembelianBarangService;
import apap.tugas1.sielekthor.service.MemberService;
import apap.tugas1.sielekthor.service.PembelianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PembelianController {

    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Qualifier("pembelianBarangServiceImpl")
    @Autowired
    private PembelianBarangService pembelianBarangService;

    @GetMapping("/pembelian")
    public String listPembelian(Model model) {
        List<PembelianModel> listPembelian = pembelianService.getPembelianList();
        model.addAttribute("listPembelian", listPembelian);
        return "daftar-pembelian";
    }

    @GetMapping("/pembelian/")
    public String viewDetailPembelian(
            @RequestParam(value = "noPembelian") Long id,
            Model model
    ) {
        PembelianModel pembelian = pembelianService.getPembelianById(id);
        pembelian.setTanggalPembelian(LocalDateTime.now());
        model.addAttribute("pembelian", pembelian);
        model.addAttribute("metode", pembelian.getIsCash());

        model.addAttribute("listPembelianBarang", pembelian.getListPembelianBarang());

        int qty = 0;
        for(PembelianBarangModel pb : pembelian.getListPembelianBarang()){
            qty += pb.getQuantity();
        }

        model.addAttribute("qty", qty);

        String metode = "";
        if(pembelian.getIsCash()==true){
            metode = "Cash";
        } else {
            metode = "Cicilan";
        }
        model.addAttribute("metode", metode);

        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-LL-uuuu");
        String tanggalPembelian = date.format(pembelian.getTanggalPembelian());
        model.addAttribute("tanggalPembelian", tanggalPembelian);

        return "detail-pembelian";
    }

    //Routing URL yang diinginkan
    @GetMapping("/pembelian/tambah")
    public String addPembelianForm( Model model ) {
        PembelianModel pembelian = new PembelianModel();
        List<BarangModel> listBarang = barangService.getBarangList();
        List<PembelianBarangModel> listPembelianBarang = new ArrayList<PembelianBarangModel>();

        pembelian.setListPembelianBarang(listPembelianBarang);
        pembelian.getListPembelianBarang().add(new PembelianBarangModel());

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listMember", memberService.getMemberList());

        //Return view template yang digunakan
        return "form-tambah-pembelian";
    }

    @PostMapping(value = "/pembelian/tambah", params = {"save"})
    public String addPembelianSubmit(
            @ModelAttribute PembelianModel pembelian,
            Model model
    ) {
        LocalDateTime date = LocalDateTime.now();
        pembelian.setTanggalPembelian(date);
        String noInvoice = pembelianService.generateNoInvoice(pembelian);
        pembelian.setNoInvoice(noInvoice);

        model.addAttribute("NoInvoice", noInvoice);
//        if(pembelianService.cekKetersediaanBarang(pembelian.getListPembelianBarang()) == false){
//            return "tambah-pembelian-failed";
//        }
        int sumHarga = 0;
        for(PembelianBarangModel pb:pembelian.getListPembelianBarang()){
            BarangModel targetBarang = barangService.getBarangById(pb.getBarang().getId());
            int harga = targetBarang.getHargaBarang() * pb.getQuantity();
            sumHarga += harga;
        }
        pembelian.setTotal(sumHarga);
        pembelianService.addPembelian(pembelian);

        for(PembelianBarangModel pb : pembelian.getListPembelianBarang()){
            BarangModel targetBarang = barangService.getBarangById(pb.getBarang().getId());
            int updateStok = targetBarang.getStok() - pb.getQuantity();
            targetBarang.setStok(updateStok);
        }

        for(PembelianBarangModel pb : pembelian.getListPembelianBarang()){
            pb.setPembelian(pembelian);
            LocalDate tanggal = LocalDate.now();
            BarangModel targetBarang = barangService.getBarangById(pb.getBarang().getId());
            tanggal = tanggal.plusDays(targetBarang.getJumlahGaransi());
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date targetDate = Date.from(tanggal.atStartOfDay(defaultZoneId).toInstant());
            pb.setTanggalGaransi(targetDate);
            pembelianBarangService.addPembelianBarang(pb);
        }
        model.addAttribute("noInvoice", pembelian.getNoInvoice());

        //Return view template yang diinginkan
        return "tambah-pembelian-success";
    }

    @PostMapping(value = "/pembelian/tambah", params = {"addRow"})
    private String addRowBarangMultiple(@ModelAttribute PembelianModel pembelian, Model model) {
        if (pembelian.getListPembelianBarang() == null || pembelian.getListPembelianBarang().size() == 0) {
            pembelian.setListPembelianBarang(new ArrayList<>());
        }
        pembelian.getListPembelianBarang().add(new PembelianBarangModel());
        List<BarangModel> listBarang = barangService.getBarangList();

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listMember", memberService.getMemberList());

        return "form-tambah-pembelian";
    }

    @PostMapping(value = "/pembelian/tambah", params = {"deleteRow"})
    private String deleteRowBarangMultiple(@ModelAttribute PembelianModel pembelian, @RequestParam("deleteRow") Integer row,
                                         Model model) {
        final Integer rowId = Integer.valueOf(row);
        pembelian.getListPembelianBarang().remove(rowId.intValue());

        List<BarangModel> listBarang = barangService.getBarangList();

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listMember", memberService.getMemberList());

        return "form-tambah-pembelian";
    }

    @GetMapping("/pembelian/hapus/{idPembelian}")
    public String deletePembelian(@PathVariable Long idPembelian, Model model){
        //Mendapatkan penjaga sesuai dengan noPenjaga
        PembelianModel pembelian = pembelianService.getPembelianById(idPembelian);

        List<BarangModel> listBarang = new ArrayList<BarangModel>();
        for(PembelianBarangModel pb : pembelian.getListPembelianBarang()){
            BarangModel targetBarang = barangService.getBarangById(pb.getBarang().getId());
            int updateStok = targetBarang.getStok() + pb.getQuantity();
            targetBarang.setStok(updateStok);
            listBarang.add(targetBarang);
        }
        System.out.println(listBarang.size());
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("noInvoice", pembelian.getNoInvoice());
        pembelianService.deletePembelian(pembelian);

        return "hapus-pembelian-success";

    }
}
