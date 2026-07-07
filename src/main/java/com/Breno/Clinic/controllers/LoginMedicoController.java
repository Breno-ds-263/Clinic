package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.Medico;
import com.Breno.Clinic.model.repositories.MedicoRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/login-medico")
public class LoginMedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public String telaLogin() {
        return "loginMedico";
    }

    @PostMapping
    public String login(@RequestParam("crm") String crm,
                        HttpSession session,
                        Model model) {

        try {
            Medico medico = medicoRepository.read(crm);

            if (medico != null) {
                session.setAttribute("medicoLogado", medico);
                return "redirect:/area-medico";
            }

            model.addAttribute("erro", "CRM não encontrado.");
            return "loginMedico";

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao realizar login.");
            return "loginMedico";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-medico";
    }
}