package com.Breno.Clinic.controllers;

import com.Breno.Clinic.model.entities.ItemReceituario;
import com.Breno.Clinic.model.entities.Medicamento;
import com.Breno.Clinic.model.entities.Receituario;
import com.Breno.Clinic.model.repositories.ItemReceituarioRepository;
import com.Breno.Clinic.model.repositories.MedicamentoRepository;
import com.Breno.Clinic.model.repositories.ReceituarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/itemReceituario")
public class ItemReceituarioController {

    @Autowired
    private ItemReceituarioRepository repository;

    @Autowired
    private ReceituarioRepository receituarioRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @PostMapping
    public String save(ItemReceituario item,
                       @RequestParam("codigoReceituario") Integer codigoReceituario,
                       @RequestParam("codigoMedicamento") Integer codigoMedicamento) {

        try {

            Receituario receituario = receituarioRepository.read(codigoReceituario);
            Medicamento medicamento = medicamentoRepository.read(codigoMedicamento);

            item.setReceituario(receituario);
            item.setMedicamento(medicamento);

            repository.create(item);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/itemReceituario";
    }

    @GetMapping
    public String listar(Model model) {

        try {
            model.addAttribute("itens", repository.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "itensReceituario";
    }

    @GetMapping("/{codigo}")
    public String buscar(@PathVariable Integer codigo, Model model) {

        try {
            ItemReceituario item = repository.read(codigo);
            model.addAttribute("item", item);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "itemReceituario";
    }

    @PostMapping("/update")
    public String update(ItemReceituario item,
                         @RequestParam("codigoReceituario") Integer codigoReceituario,
                         @RequestParam("codigoMedicamento") Integer codigoMedicamento) {

        try {

            item.setReceituario(receituarioRepository.read(codigoReceituario));
            item.setMedicamento(medicamentoRepository.read(codigoMedicamento));

            repository.update(item);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/itemReceituario";
    }

    @GetMapping("/delete/{codigo}")
    public String delete(@PathVariable Integer codigo) {

        try {
            repository.delete(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/itemReceituario";
    }

    @GetMapping("/novo")
    public String novoItem(Model model) {

        try {

            model.addAttribute("item", new ItemReceituario());
            model.addAttribute("receituarios", receituarioRepository.readAll());
            model.addAttribute("medicamentos", medicamentoRepository.readAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "itemReceituario";
    }
}