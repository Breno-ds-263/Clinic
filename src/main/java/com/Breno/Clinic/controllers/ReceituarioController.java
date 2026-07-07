package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.Prontuario;
import com.Breno.Clinic.model.entities.Receituario;
import com.Breno.Clinic.model.repositories.ProntuarioRepository;
import com.Breno.Clinic.model.repositories.ReceituarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/receituario")
public class ReceituarioController {

    @Autowired
    private ReceituarioRepository repository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @PostMapping
    public String save(Receituario receituario,
                       @RequestParam("codigoProntuario") Integer codigoProntuario) {

        try {

            Prontuario prontuario = prontuarioRepository.read(codigoProntuario);
            receituario.setProntuario(prontuario);

            repository.create(receituario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/receituario";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("receituarios", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "receituarios";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {

        try {
            Receituario receituario = repository.read(codigo);
            model.addAttribute("receituario", receituario);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "receituario";
    }

    @PostMapping("/update")
    public String update(Receituario receituario,
                         @RequestParam("codigoProntuario") Integer codigoProntuario) {

        try {

            Prontuario prontuario = prontuarioRepository.read(codigoProntuario);
            receituario.setProntuario(prontuario);

            repository.update(receituario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/receituario";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {

        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/receituario";
    }

    @GetMapping("/novo")
    public String novoReceituario(Model model) {

        try {

            model.addAttribute("receituario", new Receituario());
            model.addAttribute("prontuarios", prontuarioRepository.readAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "receituario";
    }
}