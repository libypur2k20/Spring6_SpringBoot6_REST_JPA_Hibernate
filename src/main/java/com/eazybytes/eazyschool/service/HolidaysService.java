package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.model.Holiday;
import com.eazybytes.eazyschool.repository.HolidaysRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@ApplicationScope
public class HolidaysService {

    private final HolidaysRepository holidaysRepository;

    public HolidaysService(HolidaysRepository holidaysRepository) {

        this.holidaysRepository = holidaysRepository;
    }

    public List<Holiday> findAllHolidays() {
        Iterable<Holiday> holidays = holidaysRepository.findAll();
       return StreamSupport.stream(holidays.spliterator(), false).collect(Collectors.toList());
    }

}
