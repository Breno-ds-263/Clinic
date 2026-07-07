package com.Breno.Clinic.controllers;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Breno.Clinic.model.entities.Paciente;
import com.Breno.Clinic.model.repositories.PacienteRepository;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public String save(Paciente paciente) {

        try {
            repository.create(paciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/paciente";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("pacientes", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "pacientes";
    }

    @GetMapping("/{cpf}")
    public String buscar(@PathVariable String cpf, Model model) {

        try {
            Paciente paciente = repository.read(cpf);
            model.addAttribute("paciente", paciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "paciente";
    }

    @PostMapping("/update")
    public String update(Paciente paciente) {

        try {
            repository.update(paciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/paciente";
    }

    @GetMapping("/delete/{cpf}")
    public String delete(@PathVariable String cpf) {

        try {
            repository.delete(cpf);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/paciente";
    }

    @GetMapping("/novo")
    public String novoPaciente() {
        return "paciente";
    }

}
