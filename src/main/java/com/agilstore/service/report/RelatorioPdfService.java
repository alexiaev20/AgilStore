package com.agilstore.service.report;

import com.agilstore.entity.Produto;
import com.agilstore.repository.ProdutoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RelatorioPdfService {

    private final ProdutoRepository produtoRepository;

    public RelatorioPdfService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public byte[] gerarRelatorioInventario() throws DocumentException {
        List<Produto> produtos = produtoRepository.findAll();
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph titulo = new Paragraph("Relatório de Inventário - AgilStore", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell(new PdfPCell(new Phrase("Nome")));
        table.addCell(new PdfPCell(new Phrase("Categoria")));
        table.addCell(new PdfPCell(new Phrase("Quantidade")));
        table.addCell(new PdfPCell(new Phrase("Preço")));

        double valorTotalEstoque = 0;

        for (Produto p : produtos) {
            table.addCell(p.getNome());
            table.addCell(p.getCategoria());
            table.addCell(String.valueOf(p.getQuantidade()));
            table.addCell("R$ " + String.format("%.2f", p.getPreco()));
            valorTotalEstoque += (p.getQuantidade() * p.getPreco());
        }

        document.add(table);
        document.add(Chunk.NEWLINE);

        Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.RED);
        Paragraph total = new Paragraph("Valor Total do Estoque: R$ " + String.format("%.2f", valorTotalEstoque), fontTotal);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();
        return out.toByteArray();
    }
}
