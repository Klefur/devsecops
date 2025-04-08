package com.main.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.model.Usuario;
import com.main.model.Nota;
import com.main.model.Pago;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
public class ExcelService {

    @Autowired
    UsuarioService uServ;

    public ByteArrayInputStream exportarUsuariosToExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Usuarios");
            
            System.err.println("aquii");

            // Estilo para el encabezado
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            
            // Definir columnas basadas en tu modelo Usuario
            String[] columns = {
                "ID", "Nombres", "Apellidos", "RUT", "Fecha Nacimiento", 
                "Año Graduación", "Nombre Colegio", "Tipo Colegio"
            };
            
            // Crear celdas de encabezado
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }
            
            // Formato para fechas
            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy"));
            
            // Crear filas de datos
            int rowNum = 1;
            // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
            List<Usuario> usuarios = uServ.getALl();

            for (Usuario usuario : usuarios) {
                Row row = sheet.createRow(rowNum++);
                
                
                // Datos básicos
                row.createCell(0).setCellValue(usuario.getId());
                row.createCell(1).setCellValue(usuario.getNombres());
                row.createCell(2).setCellValue(usuario.getApellidos());
                row.createCell(3).setCellValue(usuario.getRut());
                
                // Fecha de nacimiento con formato
                Cell fechaNacCell = row.createCell(4);
                if (usuario.getFecha_nacimiento() != null) {
                    fechaNacCell.setCellValue(usuario.getFecha_nacimiento());
                    fechaNacCell.setCellStyle(dateCellStyle);
                }
                
                // Resto de datos básicos
                if (usuario.getGrad_year() != null) {
                    row.createCell(5).setCellValue(usuario.getGrad_year());
                }
                row.createCell(6).setCellValue(usuario.getNombre_colegio());
                
                // Tipo de colegio
                if (usuario.getTipo_colegio() != null) {
                    row.createCell(7).setCellValue(usuario.getTipo_colegio().getNombre());
                }
                
               
            }
            
            // Ajustar ancho de columnas
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

}

