package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.Consulta;
import com.Breno.Clinic.model.entities.Medico;
import com.Breno.Clinic.model.entities.Prontuario;
import com.Breno.Clinic.model.repositories.ConsultaRepository;
import com.Breno.Clinic.model.repositories.ProntuarioRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/area-medico")
public class AreaMedicoController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @GetMapping
    public String home(HttpSession session, Model model) {

        Medico medico = (Medico) session.getAttribute("medicoLogado");

        if (medico == null) {
            return "redirect:/login-medico";
        }

        try {
            List<Consulta> consultas =
                    consultaRepository.findConsultasPendentesPorMedico(medico.getCrm());

            model.addAttribute("medico", medico);
            model.addAttribute("consultas", consultas);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "areaMedico";
    }

    @GetMapping("/consulta/{codigo}")
    public String verConsulta(@PathVariable Integer codigo,
                              HttpSession session,
                              Model model) {

        Medico medico = (Medico) session.getAttribute("medicoLogado");

        if (medico == null) {
            return "redirect:/login-medico";
        }

        try {
            Consulta consulta = consultaRepository.read(codigo);

            model.addAttribute("medico", medico);
            model.addAttribute("consulta", consulta);
            model.addAttribute("prontuario", new Prontuario());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "consultaMedico";
    }

    @PostMapping("/consulta/{codigo}/prontuario")
    public String salvarProntuario(@PathVariable Integer codigo,
                                   Prontuario prontuario,
                                   HttpSession session) {

        Medico medico = (Medico) session.getAttribute("medicoLogado");

        if (medico == null) {
            return "redirect:/login-medico";
        }

        try {
            Consulta consulta = consultaRepository.read(codigo);
            prontuario.setConsulta(consulta);

            prontuarioRepository.create(prontuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/area-medico";
    }

    @GetMapping("/realizadas")
    public String consultasRealizadas(HttpSession session, Model model) {

        Medico medico = (Medico) session.getAttribute("medicoLogado");

        if (medico == null) {
            return "redirect:/login-medico";
        }

        try {
            List<Consulta> consultas =
                    consultaRepository.findConsultasRealizadasPorMedico(medico.getCrm());

            model.addAttribute("medico", medico);
            model.addAttribute("consultas", consultas);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "consultasRealizadas";
    }

    @GetMapping("/realizadas/{codigo}")
    public String verConsultaRealizada(@PathVariable Integer codigo,
                                       HttpSession session,
                                       Model model) {

        Medico medico = (Medico) session.getAttribute("medicoLogado");

        if (medico == null) {
            return "redirect:/login-medico";
        }

        try {
            Consulta consulta = consultaRepository.read(codigo);
            Prontuario prontuario = prontuarioRepository.findByConsulta(codigo);

            model.addAttribute("medico", medico);
            model.addAttribute("consulta", consulta);
            model.addAttribute("prontuario", prontuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "detalheConsultaRealizada";
    }
}