package com.main.service;

import com.main.model.Nota;
import com.main.model.Usuario;
import com.main.repository.NotaRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NotaService {
    @Autowired
    NotaRepository notaRep;
    @Autowired
    UsuarioService uServ;
    @Autowired
    PagoService pagServ;

    public void leerCSV(MultipartFile csvFile) {
        if (csvFile != null) {
            List<String> ruts = new ArrayList<>();
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(csvFile.getInputStream()))) {
                String[] nextRecord;
                while ((nextRecord = csvReader.readNext()) != null) {
                    Usuario usuario = uServ.findByRut(nextRecord[0]);
                    if (!ruts.contains(nextRecord[0])) {
                        ruts.add(nextRecord[0]);
                    }
                    int nota = Integer.parseInt(nextRecord[1]);
                    notaRep.save(new Nota(nota, usuario));
                }

                pagServ.dctoNotas(ruts);

            } catch (IOException e) {
                // Maneja excepciones de lectura de archivo
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Nota> getByUser(Long uId) {
        return notaRep.findAllByUsuario(uServ.show(uId));
    }

    public double promedioGeneralNotas(Long uId) {
        List<Nota> notas = getByUser(uId);
        double sumaNotas = 0;
        int cantidadNotas = 0;

        for (Nota nota : notas) {
            sumaNotas += nota.getNota();
            cantidadNotas++;
        }

        if (cantidadNotas == 0) {
            return 0; // Evitar división por cero
        }

        return sumaNotas / cantidadNotas;
    }
}
