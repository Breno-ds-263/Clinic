package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Paciente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class PacienteRepository implements Repository<Paciente, String> {

    @Override
    public void create(Paciente paciente) throws SQLException {

        String sql = "INSERT INTO pacientes (cpf, nome, endereco, contato, plano_saude) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, paciente.getCpf());
            stmt.setString(2, paciente.getNome());
            stmt.setString(3, paciente.getEndereco());
            stmt.setString(4, paciente.getContato());
            stmt.setString(5, paciente.getPlanoSaude());

            stmt.executeUpdate();
        }
    }

    @Override
    public Paciente read(String cpf) throws SQLException {

        String sql = "SELECT * FROM pacientes WHERE cpf = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Paciente paciente = new Paciente();

                    paciente.setCpf(rs.getString("cpf"));
                    paciente.setNome(rs.getString("nome"));
                    paciente.setEndereco(rs.getString("endereco"));
                    paciente.setContato(rs.getString("contato"));
                    paciente.setPlanoSaude(rs.getString("plano_saude"));

                    return paciente;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Paciente paciente) throws SQLException {

        String sql = "UPDATE pacientes SET nome = ?, endereco = ?, contato = ?, plano_saude = ? WHERE cpf = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getEndereco());
            stmt.setString(3, paciente.getContato());
            stmt.setString(4, paciente.getPlanoSaude());
            stmt.setString(5, paciente.getCpf());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String cpf) throws SQLException {

        String sql = "DELETE FROM pacientes WHERE cpf = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, cpf);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Paciente> readAll() throws SQLException {

        List<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT * FROM pacientes";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Paciente paciente = new Paciente();

                paciente.setCpf(rs.getString("cpf"));
                paciente.setNome(rs.getString("nome"));
                paciente.setEndereco(rs.getString("endereco"));
                paciente.setContato(rs.getString("contato"));
                paciente.setPlanoSaude(rs.getString("plano_saude"));

                pacientes.add(paciente);
            }
        }

        return pacientes;
    }
}