package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.Consulta;
import com.Breno.Clinic.model.entities.Prontuario;
import com.Breno.Clinic.model.repositories.ConsultaRepository;
import com.Breno.Clinic.model.repositories.ProntuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/prontuario")
public class ProntuarioController {

    @Autowired
    private ProntuarioRepository repository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    public String save(Prontuario prontuario,
                       @RequestParam("codigoConsulta") Integer codigoConsulta) {

        try {

            Consulta consulta = consultaRepository.read(codigoConsulta);
            prontuario.setConsulta(consulta);

            repository.create(prontuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/prontuario";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("prontuarios", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "prontuarios";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {

        try {
            Prontuario prontuario = repository.read(codigo);
            model.addAttribute("prontuario", prontuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "prontuario";
    }

    @PostMapping("/update")
    public String update(Prontuario prontuario,
                         @RequestParam("codigoConsulta") Integer codigoConsulta) {

        try {

            Consulta consulta = consultaRepository.read(codigoConsulta);
            prontuario.setConsulta(consulta);

            repository.update(prontuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/prontuario";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {

        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/prontuario";
    }

    @GetMapping("/novo")
    public String novoProntuario(Model model) {

        try {
            model.addAttribute("prontuario", new Prontuario());
            model.addAttribute("consultas", consultaRepository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "prontuario";
    }
}