package apap.tugas1.sielekthor.service;

import apap.tugas1.sielekthor.model.TipeModel;
import apap.tugas1.sielekthor.repository.TipeDB;
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
public class TipeServiceImpl implements TipeService{

    @Autowired
    TipeDB tipeDB;

    @Override
    public void addTipe(TipeModel tipe) { tipeDB.save(tipe); }

    @Override
    public void updateTipe(TipeModel tipe) { tipeDB.save(tipe); }

    @Override
    public List<TipeModel> getTipeList() { return tipeDB.findAll(Sort.by(Sort.Direction.ASC, "id")); }

    @Override
    public void deleteTipe(TipeModel tipe) {
        tipeDB.delete(tipe);
    }

    @Override
    public TipeModel getTipeById(Long id) {
        Optional<TipeModel> tipe = tipeDB.findById(id);
        if (tipe.isPresent()) {
            return tipe.get();
        }
        return null;
    }
}
