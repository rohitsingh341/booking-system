package com.maersk.bookingsystem.services;

import com.maersk.bookingsystem.model.IDHolder;
import com.maersk.bookingsystem.repositories.IDGeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.maersk.bookingsystem.constants.Constants.ID_CONTEXT_NAME;

@Service
public class IDGeneratorService {


    @Value("${app.database.sequence.id.start-value}")
    private Integer idValue;

    @Autowired
    private IDGeneratorRepository idGeneratorRepository;

    @Transactional
    public IDHolder getCurrentIdHolder() {
        Optional<IDHolder> idHolderOptional = idGeneratorRepository.findById(ID_CONTEXT_NAME);

        if (idHolderOptional.isPresent()) {
            return idHolderOptional.get();
        }
        else {
            IDHolder idHolder = new IDHolder(ID_CONTEXT_NAME, idValue);
            idGeneratorRepository.save(idHolder);
            return idHolder;
        }
    }

    public void incrementIDValue(IDHolder idHolder) {
        Integer nextId = idHolder.getId() + 1;
        idHolder.setId(nextId);
        idGeneratorRepository.save(idHolder);
    }


}
