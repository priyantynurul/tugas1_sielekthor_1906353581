package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.MemberModel;
import apap.tugas1.sielekthor.repository.MemberDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDB memberDB;

    @Override
    public void addMember(MemberModel member) { memberDB.save(member); }

    @Override
    public void updateMember(MemberModel member) { memberDB.save(member); }

    @Override
    public List<MemberModel> getMemberList() { return memberDB.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    @Override
    public void deleteMember(MemberModel member) {
        memberDB.delete(member);
    }

    @Override
    public MemberModel getMemberById(Long id) {
        Optional<MemberModel> member = memberDB.findById(id);
        if (member.isPresent()) {
            return member.get();
        }
        return null;
    }
}
