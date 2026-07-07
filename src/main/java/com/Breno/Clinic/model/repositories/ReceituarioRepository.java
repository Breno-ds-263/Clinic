package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Prontuario;
import com.Breno.Clinic.model.entities.Receituario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class ReceituarioRepository implements Repository<Receituario, Integer> {

    @Override
    public void create(Receituario receituario) throws SQLException {

        String sql = "INSERT INTO receituarios (observacao, codigo_prontuario) VALUES (?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, receituario.getObservacao());
            stmt.setInt(2, receituario.getProntuario().getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public Receituario read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM receituarios WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Receituario receituario = new Receituario();

                    receituario.setCodigo(rs.getInt("codigo"));
                    receituario.setObservacao(rs.getString("observacao"));

                    ProntuarioRepository prontuarioRepository = new ProntuarioRepository();
                    Prontuario prontuario = prontuarioRepository.read(rs.getInt("codigo_prontuario"));

                    receituario.setProntuario(prontuario);

                    return receituario;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Receituario receituario) throws SQLException {

        String sql = """
                UPDATE receituarios
                SET observacao = ?, codigo_prontuario = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, receituario.getObservacao());
            stmt.setInt(2, receituario.getProntuario().getCodigo());
            stmt.setInt(3, receituario.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM receituarios WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Receituario> readAll() throws SQLException {

        List<Receituario> receituarios = new ArrayList<>();

        String sql = "SELECT * FROM receituarios";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

            while (rs.next()) {

                Receituario receituario = new Receituario();

                receituario.setCodigo(rs.getInt("codigo"));
                receituario.setObservacao(rs.getString("observacao"));

                Prontuario prontuario = prontuarioRepository.read(rs.getInt("codigo_prontuario"));

                receituario.setProntuario(prontuario);

                receituarios.add(receituario);
            }
        }

        return receituarios;
    }
}