package com.main.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.main.service.ExcelService;


@Controller
@CrossOrigin
@RequestMapping()
public class ExcelController {
    
    @Autowired
    ExcelService excelServ;

    @GetMapping("/excel/exportar-usuarios/")
    public ResponseEntity<InputStreamResource> exportarUsuarios() {
        try {
            ByteArrayInputStream stream = excelServ.exportarUsuariosToExcel();
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=usuarios.xlsx");
            
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(stream));
        } catch (IOException e) {
            // e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reporte")
    public String reporte() {
        return "reporte";
    }
    
    

}
