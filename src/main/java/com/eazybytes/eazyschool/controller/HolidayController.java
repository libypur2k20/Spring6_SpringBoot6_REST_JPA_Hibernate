package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Holiday;
import com.eazybytes.eazyschool.service.HolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidayController {

    private final HolidaysService holidaysService;

    public HolidayController(HolidaysService holidaysService) {
        this.holidaysService = holidaysService;
    }

    @GetMapping("/holidays")
    public String displayHolidays(@RequestParam(required = false, defaultValue = "true") boolean festival, @RequestParam(required = false, defaultValue = "true") boolean federal, Model model){

        model.addAttribute("festival",festival);
        model.addAttribute("federal",federal);

        List<Holiday> holidays = holidaysService.findAllHolidays();

        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(), holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList()));
        }

        return "holidays.html";
    }

    @GetMapping("/holidays/{display}")
    public ModelAndView displayHoliday(@PathVariable(required = true) String display) {

        if (display == null || display.isEmpty()) { display = "all"; }

        boolean festival = (display.equals("all") || display.equals("festival"));
        boolean federal = (display.equals("all") || display.equals("federal"));

        return new ModelAndView("redirect:/holidays?festival=" + festival + "&federal=" + federal);

    }
}
