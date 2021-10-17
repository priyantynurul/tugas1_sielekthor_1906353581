package apap.tugas1.sielekthor.controller;

import apap.tugas1.sielekthor.model.*;
import apap.tugas1.sielekthor.model.MemberModel;
import apap.tugas1.sielekthor.service.PembelianService;
import apap.tugas1.sielekthor.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

    @GetMapping("/member")
    public String listMember(Model model) {
        List<MemberModel> listMember = memberService.getMemberList();
        model.addAttribute("listMember", listMember);
        return "daftar-member";
    }

    //Routing URL yang diinginkan
    @GetMapping("/member/tambah")
    public String addMemberForm( Model model ) {
        model.addAttribute("member", new MemberModel());

        //Return view template yang digunakan
        return "form-tambah-member";
    }

    @PostMapping("/member/tambah")
    public String addMemberSubmit(
            @ModelAttribute MemberModel member,
            Model model
    ) {
        memberService.addMember(member);
        model.addAttribute("noMember", member.getId());

        //Return view template yang diinginkan
        return "tambah-member-success";
    }

    @GetMapping("/member/ubah/{noMember}")
    public String updateMemberForm(@PathVariable Long noMember, Model model){
        //Mendapatkan member sesuai dengan noMember
        MemberModel member = memberService.getMemberById(noMember);

        //Add variable MemberModel ke 'member' untuk dirender dalam thymeleaf
        model.addAttribute("member", member);

        return "form-ubah-member";
    }

    @PostMapping("/member/ubah/{noMember}")
    public String updateMemberSubmit(@ModelAttribute MemberModel member, Model model){
        //Mendapatkan member sesuai dengan noMember

        MemberModel memberPast = memberService.getMemberById(member.getId());
        String namaLama = memberPast.getNamaMember();

        if(member.getNamaMember()!=namaLama) {
            String firstletter = Integer.toString((((int) (member.getNamaMember().toUpperCase().charAt(0))) - 64) % 10);
            List<String> listNoInvoice = new ArrayList<String>();

            for (PembelianModel p : memberPast.getListPembelian()) {
                String noInvoice = p.getNoInvoice();
                String remLetStr = noInvoice.substring(1);
                noInvoice = firstletter + remLetStr;
                p.setNoInvoice(noInvoice);
                listNoInvoice.add(noInvoice);
            }
        }


        memberService.updateMember(member);

        //Add variable MemberModel ke 'member' untuk dirender dalam thymeleaf
        model.addAttribute("namaMember", member.getNamaMember());

        return "ubah-member-success";
    }

}
