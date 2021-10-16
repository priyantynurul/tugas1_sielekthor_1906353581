package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.BarangModel;
import apap.tugas1.sielekthor.service.BarangService;
import apap.tugas1.sielekthor.service.TipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BarangController {

    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @GetMapping("/barang")
    public String listBarang(Model model) {
        List<BarangModel> listBarang = barangService.getBarangList();
        model.addAttribute("listBarang", listBarang);
        return "daftar-barang";
    }

    @GetMapping("/barang/")
    public String viewDetailBarang(
            @RequestParam(value = "noBarang") Long id,
            Model model
    ) {
        BarangModel barang = barangService.getBarangById(id);

        model.addAttribute("barang", barang);
        model.addAttribute("tipe", barang.getTipe().getNamaTipe());

        return "detail-barang";
    }

    //Routing URL yang diinginkan
    @GetMapping("/barang/tambah")
    public String addBarangForm( Model model ) {
        model.addAttribute("barang", new BarangModel());
        model.addAttribute("listTipe", tipeService.getTipeList());

        //Return view template yang digunakan
        return "form-tambah-barang";
    }

    @PostMapping("/barang/tambah")
    public String addBarangSubmit(
            @ModelAttribute BarangModel barang,
            Model model
    ) {
        model.addAttribute("kodeBarang", barang.getKodeBarang());

        if(barangService.addBarang(barang) == 0){
            return "tambah-barang-failed";
        }

        //Return view template yang diinginkan
        return "tambah-barang-success";
    }

    @GetMapping("/barang/ubah/{noBarang}")
    public String updateBarangForm(@PathVariable Long noBarang, Model model){
        //Mendapatkan barang sesuai dengan noBarang
        BarangModel barang = barangService.getBarangById(noBarang);

        //Add variable BarangModel ke 'barang' untuk dirender dalam thymeleaf
        model.addAttribute("barang", barang);
        model.addAttribute("listTipe", tipeService.getTipeList());

        return "form-ubah-barang";
    }

    @PostMapping("/barang/ubah/{noBarang}")
    public String updateBarangSubmit(@ModelAttribute BarangModel barang, Model model){
        //Mendapatkan barang sesuai dengan noBarang
        barangService.updateBarang(barang);

        //Add variable BarangModel ke 'barang' untuk dirender dalam thymeleaf
        model.addAttribute("noBarang", barang.getId());

        return "ubah-barang-success";
    }

    @GetMapping("/barang/hapus/{noBarang}")
    public String deleteBarang(@PathVariable Long noBarang, Model model){
        //Mendapatkan penjaga sesuai dengan noPenjaga
        BarangModel barang = barangService.getBarangById(noBarang);

        model.addAttribute("kodeBarang", barang.getKodeBarang());

        barangService.deleteBarang(barang);
        return "hapus-barang-success";

    }
}
