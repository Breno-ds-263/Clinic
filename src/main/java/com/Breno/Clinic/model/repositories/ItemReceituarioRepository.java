package com.Breno.Clinic.model.repositories;

import com.Breno.Clinic.model.entities.ItemReceituario;
import com.Breno.Clinic.model.entities.Medicamento;
import com.Breno.Clinic.model.entities.Receituario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class ItemReceituarioRepository implements Repository<ItemReceituario, Integer> {

    @Override
    public void create(ItemReceituario item) throws SQLException {

        String sql = "INSERT INTO itens_receituario (dosagem, intervalo_entre_doses, observacao, codigo_receituario, codigo_medicamento) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, item.getDosagem());
            stmt.setInt(2, item.getIntervaloEntreDoses());
            stmt.setString(3, item.getObservacao());
            stmt.setInt(4, item.getReceituario().getCodigo());
            stmt.setInt(5, item.getMedicamento().getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public ItemReceituario read(Integer codigo) throws SQLException {

        String sql = "SELECT * FROM itens_receituario WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    ItemReceituario item = new ItemReceituario();

                    item.setCodigo(rs.getInt("codigo"));
                    item.setDosagem(rs.getInt("dosagem"));
                    item.setIntervaloEntreDoses(rs.getInt("intervalo_entre_doses"));
                    item.setObservacao(rs.getString("observacao"));

                    ReceituarioRepository receituarioRepository = new ReceituarioRepository();
                    Receituario receituario = receituarioRepository.read(rs.getInt("codigo_receituario"));
                    item.setReceituario(receituario);

                    MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
                    Medicamento medicamento = medicamentoRepository.read(rs.getInt("codigo_medicamento"));
                    item.setMedicamento(medicamento);

                    return item;
                }
            }
        }

        return null;
    }

    @Override
    public void update(ItemReceituario item) throws SQLException {

        String sql = """
                UPDATE itens_receituario
                SET dosagem = ?,
                    intervalo_entre_doses = ?,
                    observacao = ?,
                    codigo_receituario = ?,
                    codigo_medicamento = ?
                WHERE codigo = ?
                """;

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, item.getDosagem());
            stmt.setInt(2, item.getIntervaloEntreDoses());
            stmt.setString(3, item.getObservacao());
            stmt.setInt(4, item.getReceituario().getCodigo());
            stmt.setInt(5, item.getMedicamento().getCodigo());
            stmt.setInt(6, item.getCodigo());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codigo) throws SQLException {

        String sql = "DELETE FROM itens_receituario WHERE codigo = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<ItemReceituario> readAll() throws SQLException {

        List<ItemReceituario> itens = new ArrayList<>();

        String sql = "SELECT * FROM itens_receituario";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ReceituarioRepository receituarioRepository = new ReceituarioRepository();
            MedicamentoRepository medicamentoRepository = new MedicamentoRepository();

            while (rs.next()) {

                ItemReceituario item = new ItemReceituario();

                item.setCodigo(rs.getInt("codigo"));
                item.setDosagem(rs.getInt("dosagem"));
                item.setIntervaloEntreDoses(rs.getInt("intervalo_entre_doses"));
                item.setObservacao(rs.getString("observacao"));

                item.setReceituario(
                        receituarioRepository.read(rs.getInt("codigo_receituario"))
                );

                item.setMedicamento(
                        medicamentoRepository.read(rs.getInt("codigo_medicamento"))
                );

                itens.add(item);
            }
        }

        return itens;
    }
}