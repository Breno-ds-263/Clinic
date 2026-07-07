package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Consulta;
import com.Breno.Clinic.model.entities.Prontuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class ProntuarioRepository implements Repository<Prontuario, Integer> {

    @Override
    public void create(Prontuario prontuario) throws SQLException {

        String sql = "INSERT INTO prontuarios (descricao, observacao, codigo_consulta) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, prontuario.getDescricao());
            stmt.setString(2, prontuario.getObservacao());
            stmt.setInt(3, prontuario.getConsulta().getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public Prontuario read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM prontuarios WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Prontuario prontuario = new Prontuario();

                    prontuario.setCodigo(rs.getInt("codigo"));
                    prontuario.setDescricao(rs.getString("descricao"));
                    prontuario.setObservacao(rs.getString("observacao"));

                    ConsultaRepository consultaRepository = new ConsultaRepository();
                    Consulta consulta = consultaRepository.read(rs.getInt("codigo_consulta"));

                    prontuario.setConsulta(consulta);

                    return prontuario;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Prontuario prontuario) throws SQLException {

        String sql = """
                UPDATE prontuarios
                SET descricao = ?, observacao = ?, codigo_consulta = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, prontuario.getDescricao());
            stmt.setString(2, prontuario.getObservacao());
            stmt.setInt(3, prontuario.getConsulta().getCodigo());
            stmt.setInt(4, prontuario.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM prontuarios WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Prontuario> readAll() throws SQLException {

        List<Prontuario> prontuarios = new ArrayList<>();

        String sql = "SELECT * FROM prontuarios";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ConsultaRepository consultaRepository = new ConsultaRepository();

            while (rs.next()) {

                Prontuario prontuario = new Prontuario();

                prontuario.setCodigo(rs.getInt("codigo"));
                prontuario.setDescricao(rs.getString("descricao"));
                prontuario.setObservacao(rs.getString("observacao"));

                Consulta consulta = consultaRepository.read(rs.getInt("codigo_consulta"));

                prontuario.setConsulta(consulta);

                prontuarios.add(prontuario);
            }
        }

        return prontuarios;
    }

    public Prontuario findByConsulta(Integer codigoConsulta) throws SQLException {

        String sql = "SELECT * FROM prontuarios WHERE codigo_consulta = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigoConsulta);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Prontuario prontuario = new Prontuario();

                    prontuario.setCodigo(rs.getInt("codigo"));
                    prontuario.setDescricao(rs.getString("descricao"));
                    prontuario.setObservacao(rs.getString("observacao"));

                    ConsultaRepository consultaRepository = new ConsultaRepository();
                    prontuario.setConsulta(consultaRepository.read(codigoConsulta));

                    return prontuario;
                }
            }
        }

        return null;
    }
}