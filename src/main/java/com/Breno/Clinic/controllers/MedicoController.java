package com.Breno.Clinic.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Breno.Clinic.model.entities.Medico;
import com.Breno.Clinic.model.repositories.MedicoRepository;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    public String save(Medico medico) {

        try {
            repository.create(medico);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medico";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("medicos", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "medicos";
    }

    @GetMapping("/{crm}")
    public String buscar(@PathVariable String crm, Model model) {

        try {
            Medico medico = repository.read(crm);
            model.addAttribute("medico", medico);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "medico";
    }

    @PostMapping("/update")
    public String update(Medico medico) {

        try {
            repository.update(medico);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medico";
    }

    @GetMapping("/delete/{crm}")
    public String delete(@PathVariable String crm) {

        try {
            repository.delete(crm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medico";
    }

    @GetMapping("/novo")
    public String novoMedico() {
        return "medico";
    }
}