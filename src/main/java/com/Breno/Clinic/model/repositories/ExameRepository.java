package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Exame;
import com.Breno.Clinic.model.entities.Prontuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class ExameRepository implements Repository<Exame, Integer> {

    @Override
    public void create(Exame exame) throws SQLException {

        String sql = "INSERT INTO exames (observacao, codigo_prontuario) VALUES (?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, exame.getObservacao());
            stmt.setInt(2, exame.getProntuario().getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public Exame read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM exames WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Exame exame = new Exame();

                    exame.setCodigo(rs.getInt("codigo"));
                    exame.setObservacao(rs.getString("observacao"));

                    ProntuarioRepository prontuarioRepository = new ProntuarioRepository();
                    Prontuario prontuario = prontuarioRepository.read(rs.getInt("codigo_prontuario"));
                    exame.setProntuario(prontuario);

                    return exame;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Exame exame) throws SQLException {

        String sql = """
                UPDATE exames
                SET observacao = ?,
                    codigo_prontuario = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, exame.getObservacao());
            stmt.setInt(2, exame.getProntuario().getCodigo());
            stmt.setInt(3, exame.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM exames WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Exame> readAll() throws SQLException {

        List<Exame> exames = new ArrayList<>();

        String sql = "SELECT * FROM exames";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

            while (rs.next()) {

                Exame exame = new Exame();

                exame.setCodigo(rs.getInt("codigo"));
                exame.setObservacao(rs.getString("observacao"));

                exame.setProntuario(
                        prontuarioRepository.read(rs.getInt("codigo_prontuario"))
                );

                exames.add(exame);
            }
        }

        return exames;
    }
}