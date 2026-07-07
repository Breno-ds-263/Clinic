package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.IndicadorExame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class IndicadorExameRepository implements Repository<IndicadorExame, Integer> {

    @Override
    public void create(IndicadorExame indicadorExame) throws SQLException {

        String sql = "INSERT INTO indicadores_exame " +
                "(indicador, descricao, min_valor_referencia, max_valor_referencia) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, indicadorExame.getIndicador());
            stmt.setString(2, indicadorExame.getDescricao());
            stmt.setDouble(3, indicadorExame.getMinValorReferencia());
            stmt.setDouble(4, indicadorExame.getMaxValorReferencia());

            stmt.executeUpdate();
        }
    }

    @Override
    public IndicadorExame read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM indicadores_exame WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    IndicadorExame indicadorExame = new IndicadorExame();

                    indicadorExame.setCodigo(rs.getInt("codigo"));
                    indicadorExame.setIndicador(rs.getString("indicador"));
                    indicadorExame.setDescricao(rs.getString("descricao"));
                    indicadorExame.setMinValorReferencia(rs.getDouble("min_valor_referencia"));
                    indicadorExame.setMaxValorReferencia(rs.getDouble("max_valor_referencia"));

                    return indicadorExame;
                }
            }
        }

        return null;
    }

    @Override
    public void update(IndicadorExame indicadorExame) throws SQLException {

        String sql = """
                UPDATE indicadores_exame
                SET indicador = ?,
                    descricao = ?,
                    min_valor_referencia = ?,
                    max_valor_referencia = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, indicadorExame.getIndicador());
            stmt.setString(2, indicadorExame.getDescricao());
            stmt.setDouble(3, indicadorExame.getMinValorReferencia());
            stmt.setDouble(4, indicadorExame.getMaxValorReferencia());
            stmt.setInt(5, indicadorExame.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM indicadores_exame WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<IndicadorExame> readAll() throws SQLException {

        List<IndicadorExame> indicadores = new ArrayList<>();

        String sql = "SELECT * FROM indicadores_exame";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                IndicadorExame indicadorExame = new IndicadorExame();

                indicadorExame.setCodigo(rs.getInt("codigo"));
                indicadorExame.setIndicador(rs.getString("indicador"));
                indicadorExame.setDescricao(rs.getString("descricao"));
                indicadorExame.setMinValorReferencia(rs.getDouble("min_valor_referencia"));
                indicadorExame.setMaxValorReferencia(rs.getDouble("max_valor_referencia"));

                indicadores.add(indicadorExame);
            }
        }

        return indicadores;
    }
}