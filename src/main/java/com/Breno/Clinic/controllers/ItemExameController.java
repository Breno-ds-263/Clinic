package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.Exame;
import com.Breno.Clinic.model.entities.IndicadorExame;
import com.Breno.Clinic.model.entities.ItemExame;
import com.Breno.Clinic.model.repositories.ExameRepository;
import com.Breno.Clinic.model.repositories.IndicadorExameRepository;
import com.Breno.Clinic.model.repositories.ItemExameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/itemExame")
public class ItemExameController {

    @Autowired
    private ItemExameRepository repository;

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private IndicadorExameRepository indicadorExameRepository;

    @PostMapping
    public String save(ItemExame itemExame,
                       @RequestParam("codigoExame") Integer codigoExame,
                       @RequestParam("codigoIndicador") Integer codigoIndicador) {

        try {
            Exame exame = exameRepository.read(codigoExame);
            IndicadorExame indicador = indicadorExameRepository.read(codigoIndicador);

            itemExame.setExame(exame);
            itemExame.setIndicadorExame(indicador);

            repository.create(itemExame);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/itemExame";
    }

    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("itensExame", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "itensExame";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {
        try {
            ItemExame itemExame = repository.read(codigo);
            model.addAttribute("itemExame", itemExame);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "itemExame";
    }

    @PostMapping("/update")
    public String update(ItemExame itemExame,
                         @RequestParam("codigoExame") Integer codigoExame,
                         @RequestParam("codigoIndicador") Integer codigoIndicador) {

        try {
            itemExame.setExame(exameRepository.read(codigoExame));
            itemExame.setIndicadorExame(indicadorExameRepository.read(codigoIndicador));

            repository.update(itemExame);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/itemExame";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {
        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/itemExame";
    }

    @GetMapping("/novo")
    public String novoItemExame(Model model) {
        try {
            model.addAttribute("itemExame", new ItemExame());
            model.addAttribute("exames", exameRepository.readAll());
            model.addAttribute("indicadores", indicadorExameRepository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "itemExame";
    }
}