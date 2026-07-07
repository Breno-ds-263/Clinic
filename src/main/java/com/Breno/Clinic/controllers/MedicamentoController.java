package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.Medicamento;
import com.Breno.Clinic.model.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/medicamento")
public class MedicamentoController {

    @Autowired
    private MedicamentoRepository repository;

    @PostMapping
    public String save(Medicamento medicamento) {

        try {
            repository.create(medicamento);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medicamento";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("medicamentos", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "medicamentos";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {

        try {
            Medicamento medicamento = repository.read(codigo);
            model.addAttribute("medicamento", medicamento);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "medicamento";
    }

    @PostMapping("/update")
    public String update(Medicamento medicamento) {

        try {
            repository.update(medicamento);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medicamento";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {

        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/medicamento";
    }

    @GetMapping("/novo")
    public String novoMedicamento(Model model) {

        model.addAttribute("medicamento", new Medicamento());

        return "medicamento";
    }
}