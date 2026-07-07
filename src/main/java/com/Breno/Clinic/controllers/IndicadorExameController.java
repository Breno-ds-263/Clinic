package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.IndicadorExame;
import com.Breno.Clinic.model.repositories.IndicadorExameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/indicadorExame")
public class IndicadorExameController {

    @Autowired
    private IndicadorExameRepository repository;

    @PostMapping
    public String save(IndicadorExame indicadorExame) {

        try {
            repository.create(indicadorExame);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/indicadorExame";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("indicadores", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "indicadoresExame";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {

        try {
            IndicadorExame indicadorExame = repository.read(codigo);
            model.addAttribute("indicadorExame", indicadorExame);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "indicadorExame";
    }

    @PostMapping("/update")
    public String update(IndicadorExame indicadorExame) {

        try {
            repository.update(indicadorExame);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/indicadorExame";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {

        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/indicadorExame";
    }

    @GetMapping("/novo")
    public String novoIndicador(Model model) {

        model.addAttribute("indicadorExame", new IndicadorExame());

        return "indicadorExame";
    }
}