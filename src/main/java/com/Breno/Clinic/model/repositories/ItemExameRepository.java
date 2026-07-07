package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.Exame;
import com.Breno.Clinic.model.entities.IndicadorExame;
import com.Breno.Clinic.model.entities.ItemExame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class ItemExameRepository implements Repository<ItemExame, Integer> {

    @Override
    public void create(ItemExame itemExame) throws SQLException {

        String sql = """
                INSERT INTO itens_exame
                (valor_indicador, observacao, codigo_exame, codigo_indicador)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, itemExame.getValorIndicador());
            stmt.setString(2, itemExame.getObservacao());
            stmt.setInt(3, itemExame.getExame().getCodigo());
            stmt.setInt(4, itemExame.getIndicadorExame().getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public ItemExame read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM itens_exame WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    ItemExame itemExame = new ItemExame();

                    itemExame.setCodigo(rs.getInt("codigo"));
                    itemExame.setValorIndicador(rs.getString("valor_indicador"));
                    itemExame.setObservacao(rs.getString("observacao"));

                    ExameRepository exameRepository = new ExameRepository();
                    IndicadorExameRepository indicadorRepository = new IndicadorExameRepository();

                    Exame exame = exameRepository.read(rs.getInt("codigo_exame"));
                    IndicadorExame indicador =
                            indicadorRepository.read(rs.getInt("codigo_indicador"));

                    itemExame.setExame(exame);
                    itemExame.setIndicadorExame(indicador);

                    return itemExame;
                }
            }
        }

        return null;
    }

    @Override
    public void update(ItemExame itemExame) throws SQLException {

        String sql = """
                UPDATE itens_exame
                SET valor_indicador = ?,
                    observacao = ?,
                    codigo_exame = ?,
                    codigo_indicador = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setString(1, itemExame.getValorIndicador());
            stmt.setString(2, itemExame.getObservacao());
            stmt.setInt(3, itemExame.getExame().getCodigo());
            stmt.setInt(4, itemExame.getIndicadorExame().getCodigo());
            stmt.setInt(5, itemExame.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM itens_exame WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<ItemExame> readAll() throws SQLException {

        List<ItemExame> itens = new ArrayList<>();

        String sql = "SELECT * FROM itens_exame";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ExameRepository exameRepository = new ExameRepository();
            IndicadorExameRepository indicadorRepository = new IndicadorExameRepository();

            while (rs.next()) {

                ItemExame itemExame = new ItemExame();

                itemExame.setCodigo(rs.getInt("codigo"));
                itemExame.setValorIndicador(rs.getString("valor_indicador"));
                itemExame.setObservacao(rs.getString("observacao"));

                itemExame.setExame(
                        exameRepository.read(rs.getInt("codigo_exame"))
                );

                itemExame.setIndicadorExame(
                        indicadorRepository.read(rs.getInt("codigo_indicador"))
                );

                itens.add(itemExame);
            }
        }

        return itens;
    }
}