package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Consulta;
import com.Breno.Clinic.model.entities.Medico;
import com.Breno.Clinic.model.entities.Paciente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class ConsultaRepository implements Repository<Consulta, Integer> {

    @Override
    public void create(Consulta consulta) throws SQLException {

        String sql = """
                INSERT INTO consultas
                (data_hora_volta, observacao, cpf_paciente, crm_medico)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt =
                     ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            if (consulta.getDataHoraVolta() != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHoraVolta()));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }

            stmt.setString(2, consulta.getObservacao());
            stmt.setString(3, consulta.getPaciente().getCpf());
            stmt.setString(4, consulta.getMedico().getCrm());

            stmt.executeUpdate();
        }
    }

    @Override
    public Consulta read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM consultas WHERE codigo = ?";

        try (PreparedStatement stmt =
                     ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Consulta consulta = new Consulta();

                    consulta.setCodigo(rs.getInt("codigo"));

                    Timestamp volta = rs.getTimestamp("data_hora_volta");
                    if (volta != null) {
                        consulta.setDataHoraVolta(volta.toLocalDateTime());
                    }

                    consulta.setObservacao(rs.getString("observacao"));

                    PacienteRepository pacienteRepository = new PacienteRepository();
                    MedicoRepository medicoRepository = new MedicoRepository();

                    Paciente paciente = pacienteRepository.read(rs.getString("cpf_paciente"));
                    Medico medico = medicoRepository.read(rs.getString("crm_medico"));

                    consulta.setPaciente(paciente);
                    consulta.setMedico(medico);

                    return consulta;
                }
            }
        }

        return null;
    }

    @Override
    public void update(Consulta consulta) throws SQLException {

        String sql = """
                UPDATE consultas
                SET data_hora_volta = ?,
                    observacao = ?,
                    cpf_paciente = ?,
                    crm_medico = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt =
                     ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            if (consulta.getDataHoraVolta() != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHoraVolta()));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }

            stmt.setString(2, consulta.getObservacao());
            stmt.setString(3, consulta.getPaciente().getCpf());
            stmt.setString(4, consulta.getMedico().getCrm());
            stmt.setInt(5, consulta.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM consultas WHERE codigo = ?";

        try (PreparedStatement stmt =
                     ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Consulta> readAll() throws SQLException {

        List<Consulta> consultas = new ArrayList<>();

        String sql = "SELECT * FROM consultas";

        try (PreparedStatement stmt =
                     ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            PacienteRepository pacienteRepository = new PacienteRepository();
            MedicoRepository medicoRepository = new MedicoRepository();

            while (rs.next()) {

                Consulta consulta = new Consulta();

                consulta.setCodigo(rs.getInt("codigo"));

                Timestamp volta = rs.getTimestamp("data_hora_volta");
                if (volta != null) {
                    consulta.setDataHoraVolta(volta.toLocalDateTime());
                }

                consulta.setObservacao(rs.getString("observacao"));

                consulta.setPaciente(
                        pacienteRepository.read(rs.getString("cpf_paciente")));

                consulta.setMedico(
                        medicoRepository.read(rs.getString("crm_medico")));

                consultas.add(consulta);
            }
        }

        return consultas;
    }

    public List<Consulta> findConsultasPendentesPorMedico(String crm) throws SQLException {

        List<Consulta> consultas = new ArrayList<>();

        String sql = """
                SELECT c.codigo
                FROM consultas c
                LEFT JOIN prontuarios p
                    ON p.codigo_consulta = c.codigo
                WHERE c.crm_medico = ?
                AND p.codigo IS NULL
                ORDER BY c.data_hora_volta
                """;

        try (PreparedStatement stmt =
                     ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, crm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(read(rs.getInt("codigo")));
                }
            }
        }

        return consultas;
    }

    public List<Consulta> findConsultasRealizadasPorMedico(String crm) throws SQLException {

        List<Consulta> consultas = new ArrayList<>();

        String sql = """
                SELECT c.codigo
                FROM consultas c
                INNER JOIN prontuarios p
                    ON p.codigo_consulta = c.codigo
                WHERE c.crm_medico = ?
                ORDER BY c.data_hora_volta DESC
                """;

        try (PreparedStatement stmt =
                     ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, crm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(read(rs.getInt("codigo")));
                }
            }
        }

        return consultas;
    }
}