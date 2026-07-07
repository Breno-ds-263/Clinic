package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.Exame;
import com.Breno.Clinic.model.entities.Prontuario;
import com.Breno.Clinic.model.repositories.ExameRepository;
import com.Breno.Clinic.model.repositories.ProntuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/exame")
public class ExameController {

    @Autowired
    private ExameRepository repository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @PostMapping
    public String save(Exame exame,
                       @RequestParam("codigoProntuario") Integer codigoProntuario) {

        try {
            Prontuario prontuario = prontuarioRepository.read(codigoProntuario);
            exame.setProntuario(prontuario);

            repository.create(exame);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/exame";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("exames", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "exames";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {

        try {
            Exame exame = repository.read(codigo);
            model.addAttribute("exame", exame);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "exame";
    }

    @PostMapping("/update")
    public String update(Exame exame,
                         @RequestParam("codigoProntuario") Integer codigoProntuario) {

        try {
            Prontuario prontuario = prontuarioRepository.read(codigoProntuario);
            exame.setProntuario(prontuario);

            repository.update(exame);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/exame";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {

        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/exame";
    }

    @GetMapping("/novo")
    public String novoExame(Model model) {

        try {
            model.addAttribute("exame", new Exame());
            model.addAttribute("prontuarios", prontuarioRepository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "exame";
    }
}