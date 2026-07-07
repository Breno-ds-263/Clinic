package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Medico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class MedicoRepository implements Repository<Medico, String> {

    @Override
    public void create(Medico medico) throws SQLException {

        String sql = "INSERT INTO medicos (crm, nome, especialidade, contato) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, medico.getCrm());
            stmt.setString(2, medico.getNome());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getContato());

            stmt.executeUpdate();
        }
    }

    @Override
    public Medico read(String crm) throws SQLException {

        String sql = "SELECT * FROM medicos WHERE crm = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, crm);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Medico medico = new Medico();

                    medico.setCrm(rs.getString("crm"));
                    medico.setNome(rs.getString("nome"));
                    medico.setEspecialidade(rs.getString("especialidade"));
                    medico.setContato(rs.getString("contato"));

                    return medico;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Medico medico) throws SQLException {

        String sql = "UPDATE medicos SET nome = ?, especialidade = ?, contato = ? WHERE crm = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setString(3, medico.getContato());
            stmt.setString(4, medico.getCrm());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String crm) throws SQLException {

        String sql = "DELETE FROM medicos WHERE crm = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, crm);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Medico> readAll() throws SQLException {

        List<Medico> medicos = new ArrayList<>();

        String sql = "SELECT * FROM medicos";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Medico medico = new Medico();

                medico.setCrm(rs.getString("crm"));
                medico.setNome(rs.getString("nome"));
                medico.setEspecialidade(rs.getString("especialidade"));
                medico.setContato(rs.getString("contato"));

                medicos.add(medico);
            }
        }

        return medicos;
    }
}