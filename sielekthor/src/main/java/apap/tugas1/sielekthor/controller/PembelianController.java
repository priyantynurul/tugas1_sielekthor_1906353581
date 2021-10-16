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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("metode", pembelian.getIsCash());

        model.addAttribute("pembelianBarang", pembelian.getListPembelianBarang());

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

        pembelianService.addPembelian(pembelian);

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

}
