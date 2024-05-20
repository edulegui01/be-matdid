package com.app.bematdid.service;

import com.app.bematdid.dto.ProductoDTO;
import com.app.bematdid.mapper.ProductoMapper;
import com.app.bematdid.model.Producto;
import com.app.bematdid.repository.ProductoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoService {

    private final static String UPLOADS_FOLDER = "uploads";

    private final static String URL_GET_IMAGE = "http://localhost:8090/producto/imagen?searchImagen=";

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    EntityManager entityManager;

    public Page<ProductoDTO> listar(Pageable pageable, String nombre){
        Page<Producto> productos = productoRepository.listarProducto(pageable, nombre);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public List<ProductoDTO> listarSelect(String search){

        return productoMapper.productosAProductosDTO(productoRepository.listarSelect(search));

    }

    public Page<Producto> getListProduct(Pageable pageable, String nombreProducto, String idCiclo){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Producto> query = criteriaBuilder.createQuery(Producto.class);
        Root<Producto> productoRoot = query.from(Producto.class);

        List<Predicate> searchCriteria = new ArrayList<>();

        //se agrega el where estado = true
        searchCriteria.add(criteriaBuilder.isTrue(productoRoot.get("estado")));

        if(nombreProducto != null && !nombreProducto.isEmpty()){
            searchCriteria.add(criteriaBuilder.like(criteriaBuilder.upper(productoRoot.get("nombre")),"%"+nombreProducto.toUpperCase()+"%"));
        }
        if(idCiclo != null && !idCiclo.isEmpty()){
            searchCriteria.add(criteriaBuilder.equal(productoRoot.get("idCiclo"),idCiclo));
        }

        Predicate[] restriccions = searchCriteria.toArray(new Predicate[0]);
        query.select(productoRoot).where(criteriaBuilder.and(restriccions)).orderBy(criteriaBuilder.
                desc(productoRoot.get("idProducto")));

        TypedQuery<Producto> queryResult = entityManager.createQuery(query);

        int total =queryResult.getResultList().size();

        List<Producto> pagedData = paginateQuery(entityManager.createQuery(query), pageable).getResultList();



        //calcular cantidad de item en la consulta
        /*CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Producto> productoCountRoot = countQuery.from(Producto.class);
        countQuery
                .select(criteriaBuilder.count(productoCountRoot))
                .where(criteriaBuilder.
                        and(restriccions));
        var totalCount = entityManager.createQuery(countQuery).getSingleResult();*/

        return new PageImpl<>(pagedData,pageable,total);




    }

    public static <T> TypedQuery<T> paginateQuery(TypedQuery<T> query, Pageable pageable) {
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        return query;
    }

    public Page<ProductoDTO> listarPorEditorial (Pageable pageable, int idEditorial){
        Page<Producto> productos = productoRepository.listarPorEditorial(pageable,idEditorial);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public Page<ProductoDTO> listarPorCategoria (Pageable pageable, int idCategoria){
        Page<Producto> productos = productoRepository.listarPorCategoria(pageable,idCategoria);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public Page<ProductoDTO> listarPorCiclo (Pageable pageable, int idCiclo){
        Page<Producto> productos = productoRepository.listarPorCiclo(pageable,idCiclo);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public Page<ProductoDTO> listarPorMateria (Pageable pageable, int idMateria){
        Page<Producto> productos = productoRepository.listarPorMateria(pageable,idMateria);
        return new PageImpl<>(productoMapper.productosAProductosDTO(productos.getContent()), pageable, productos.getTotalElements());
    }

    public ProductoDTO guardar(ProductoDTO productoDTO){

        Producto producto = productoMapper.productoDTOAProducto(productoDTO);
        return productoMapper.productoAProductoDTO(productoRepository.save(producto));
    }


    public  Producto actualizar(Producto request,Long id){

        return productoRepository.save(request);

    }




    public String subirImagen(MultipartFile image, Long idProducto) throws IOException{

        String uniqueFilename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        Optional<Producto> productoBuscado = productoRepository.findById(idProducto);

        Producto producto =  productoBuscado.get();

        producto.setImage(uniqueFilename);

        Producto productoSave = productoRepository.save(producto);

        if (!image.isEmpty()) {
            if (producto.getIdProducto() > 0 && producto.getImage() != null && producto.getImage().length() > 0) {
                deleteImage(producto.getImage());
            }
        }


        Path rootPath = getPath(uniqueFilename);
        Files.copy(image.getInputStream(),rootPath);

        return uniqueFilename;


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

    public void borrar(Long id){
        Optional<Producto> productos = productoRepository.findById(id);

        Producto producto = productos.get();

        producto.setEstado(false);

        productoRepository.save(producto);
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

}
