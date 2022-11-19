package com.maersk.bookingsystem.services;

import com.maersk.bookingsystem.model.IDHolder;
import com.maersk.bookingsystem.repositories.IDHolderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.maersk.bookingsystem.constants.Constants.ID_CONTEXT_NAME;

@Slf4j
@Service
public class IDGeneratorService {


    @Value("${app.database.sequence.id.start-value}")
    private Integer idValue;

    private final IDHolderRepository idHolderRepository;

    @Autowired
    public IDGeneratorService(IDHolderRepository idHolderRepository) {
        this.idHolderRepository = idHolderRepository;
    }

    @Transactional
    public IDHolder getCurrentIdHolder() {
        Optional<IDHolder> idHolderOptional = idHolderRepository.findById(ID_CONTEXT_NAME);

        IDHolder idHolder;
        if (idHolderOptional.isPresent()) {
            idHolder = idHolderOptional.get();
        } else {
            idHolder = new IDHolder(ID_CONTEXT_NAME, idValue);
            idHolderRepository.save(idHolder);
        }

        log.info("Fetched current ID - [{}]", idHolder);
        return idHolder;
    }

    public void incrementIDValue(IDHolder idHolder) {
        Integer nextId = idHolder.getId() + 1;
        idHolder.setId(nextId);
        idHolderRepository.save(idHolder);
        log.info("Next id available in DB to use is [{}]", nextId);
    }


}
