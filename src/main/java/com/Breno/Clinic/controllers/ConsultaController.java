package com.Breno.Clinic.controllers;

import java.sql.SQLException;

import com.Breno.Clinic.model.repositories.MedicoRepository;
import com.Breno.Clinic.model.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Breno.Clinic.model.entities.Consulta;
import com.Breno.Clinic.model.repositories.ConsultaRepository;

@Controller
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    public String save(
            Consulta consulta,
            @RequestParam("cpfPaciente") String cpfPaciente,
            @RequestParam("crmMedico") String crmMedico) {

        try {

            consulta.setPaciente(pacienteRepository.read(cpfPaciente));
            consulta.setMedico(medicoRepository.read(crmMedico));

            repository.create(consulta);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/consulta";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("consultas", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "consultas";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {

        try {
            Consulta consulta = repository.read(codigo);
            model.addAttribute("consulta", consulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "consulta";
    }

    @PostMapping("/update")
    public String update(Consulta consulta) {

        try {
            repository.update(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/consulta";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {

        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/consulta";
    }

    @GetMapping("/novo")
    public String novaConsulta(Model model) {

        try {
            model.addAttribute("consulta", new Consulta());

            model.addAttribute("pacientes",
                    pacienteRepository.readAll());

            model.addAttribute("medicos",
                    medicoRepository.readAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "consulta";
    }
}