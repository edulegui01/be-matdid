package com.app.bematdid.service;


import com.app.bematdid.dto.AutorDTO;
import com.app.bematdid.mapper.AutorMapper;
import com.app.bematdid.model.Autor;
import com.app.bematdid.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final static String UPLOADS_FOLDER = "uploads";

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorMapper mapper;

    public Page<AutorDTO> listar (Pageable pageable, String nombre) {
        Optional<List<AutorDTO>> lista = autorRepository.listarAutores(nombre).map(autores -> mapper.autoresAAutoresDTO(autores));

        return new PageImpl<>(lista.get(),  pageable, lista.get().size());
    }

    public List<AutorDTO> listarTodos(){

        Optional<List<AutorDTO>> lista = autorRepository.listarAutores("").map(autores -> mapper.autoresAAutoresDTO(autores));
        return lista.get();
    }

    public List<AutorDTO> listarByCiclo(Long idCiclo){
        List<Autor> autores = autorRepository.listarAutoresByCiclo(idCiclo);
        return mapper.autoresAAutoresDTO(autores);
    }

    public Optional<AutorDTO> obtenerPorId (Integer id){
        return autorRepository.findById(id).map(autor -> mapper.autorAAutorDTO(autor));
    }

    public AutorDTO guardar(AutorDTO autorDTO) {
        Autor autor = mapper.autorDTOAAutor(autorDTO);
        return mapper.autorAAutorDTO(autorRepository.save(autor));
    }

    public void borrar(Integer id){
        Optional<Autor> autor = autorRepository.findById(id);

        Autor autor1 = autor.get();

        autor1.setEstado(false);

        autorRepository.save(autor1);
    }

    public String subirImagen(MultipartFile image, Integer idAutor) throws IOException {

        String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        Optional<Autor> autorBuscado = autorRepository.findById(idAutor);

        Autor autor =  autorBuscado.get();

        autor.setImage(uniqueFilename);

        Autor autorSave = autorRepository.save(autor);

        if (!image.isEmpty()) {
            if (autor.getIdAutor() > 0 && autor.getImage() != null && autor.getImage().length() > 0) {
                deleteImage(autor.getImage());
            }
        }


        Path rootPath = getPath(uniqueFilename);
        Files.copy(image.getInputStream(),rootPath);

        return uniqueFilename;
    }

    public boolean deleteImage(String filename) {
        Path rootPath = getPath(filename);
        File file = rootPath.toFile();
        if(file.exists() && file.canRead()) {
            if(file.delete()) {
                return true;
            }
        }
        return false;
    }

    public Path getPath(String filename) {
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    public Resource getImage(String nombreImagen) throws IOException {
        Path path = getPath(nombreImagen);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            //throw new RuntimeException("Error in path: " + path.toString());

            resource = null;
        }
        return resource;

    }
}
