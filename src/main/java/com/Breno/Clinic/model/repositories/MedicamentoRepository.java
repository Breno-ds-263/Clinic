package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Medicamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class MedicamentoRepository implements Repository<Medicamento, Integer> {

    @Override
    public void create(Medicamento medicamento) throws SQLException {

        String sql = "INSERT INTO medicamentos (nome, dosagem, tipo_dosagem, descricao, observacao) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNome());
            stmt.setInt(2, medicamento.getDosagem());
            stmt.setString(3, medicamento.getTipoDosagem());
            stmt.setString(4, medicamento.getDescricao());
            stmt.setString(5, medicamento.getObservacao());

            stmt.executeUpdate();
        }
    }

    @Override
    public Medicamento read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM medicamentos WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Medicamento medicamento = new Medicamento();

                    medicamento.setCodigo(rs.getInt("codigo"));
                    medicamento.setNome(rs.getString("nome"));
                    medicamento.setDosagem(rs.getInt("dosagem"));
                    medicamento.setTipoDosagem(rs.getString("tipo_dosagem"));
                    medicamento.setDescricao(rs.getString("descricao"));
                    medicamento.setObservacao(rs.getString("observacao"));

                    return medicamento;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Medicamento medicamento) throws SQLException {

        String sql = """
                UPDATE medicamentos
                SET nome = ?,
                    dosagem = ?,
                    tipo_dosagem = ?,
                    descricao = ?,
                    observacao = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, medicamento.getNome());
            stmt.setInt(2, medicamento.getDosagem());
            stmt.setString(3, medicamento.getTipoDosagem());
            stmt.setString(4, medicamento.getDescricao());
            stmt.setString(5, medicamento.getObservacao());
            stmt.setInt(6, medicamento.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM medicamentos WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Medicamento> readAll() throws SQLException {

        List<Medicamento> medicamentos = new ArrayList<>();

        String sql = "SELECT * FROM medicamentos";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Medicamento medicamento = new Medicamento();

                medicamento.setCodigo(rs.getInt("codigo"));
                medicamento.setNome(rs.getString("nome"));
                medicamento.setDosagem(rs.getInt("dosagem"));
                medicamento.setTipoDosagem(rs.getString("tipo_dosagem"));
                medicamento.setDescricao(rs.getString("descricao"));
                medicamento.setObservacao(rs.getString("observacao"));

                medicamentos.add(medicamento);
            }
        }

        return medicamentos;
    }
}