package com.czavala.productmanagementsystem.services.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final static List<String> ALLOWED_EXTENSIONS_FILE = Arrays.asList("png", "jpg", "jpeg");

    public String uploadImage(MultipartFile image) throws IOException {

        try {

            // Obtiene nombre original del archivo subido por el cliente (nombre de la imagen en pc del cliente, incluye la extension)
            String originalFileName = image.getOriginalFilename();
            System.out.println();
            System.out.println("variable 'originalFileName': " + originalFileName);

            // Si el archivo no tiene nombre o si la extension no una de las permitidas, se lanza una exception
            if (originalFileName == null || !isExtensionAllowed(originalFileName)) {
                throw new MultipartException("Invalid file extension. Extensions allowed: PNG, JPG, JPEG.");
            }


            // Extrae el nombre original del archivo pero sin la extension (el metodo lastIndexOf
            int lastDotIndex = originalFileName.lastIndexOf('.'); // retorna el indice del ultimo punto en el nombre del archivo
            String baseName = originalFileName.substring(0, lastDotIndex);
            System.out.println();
            System.out.println("variable 'baseName': " + baseName);

            // Identificador unico
            String uniqueId = UUID.randomUUID().toString();
            System.out.println();
            System.out.println("variable 'uniqueId': " + uniqueId);

            // Concate el nombre base del archivo (sin la extension) con la id unico generado previamente
            // Agrega el prefijo "ecommerce" para indicar que la imagen será guardada en la carpeta "ecommerce" en Cloudinary
            /*
            Ejemplo: baseName = 'imagen1', uniqueId = 12345 -> publicId = ecommerce/imagen1_12345
            */
            String publicId = "ecommerce/" + baseName + "_" + uniqueId;
            System.out.println();
            System.out.println("variable 'publicId': " + publicId);

            // Configuracion parametros de subida de la imagen a Cloudinary
            Map uploadParams = ObjectUtils.asMap(
                    "public_id", publicId,
                    // indica que se debe utilizar el nombre del archivo original
                    "use_filename", true,
                    // indica si Cloudinary debe generar automaticamente un nombre unico de archivo
                    // esta seteando en 'false' porque la unicidad del nombre del archivo la manejamos mediante 'publicId'
                    "unique_filename", false
            );
            System.out.println();
            System.out.println("variable 'uploadParams': " + uploadParams);

            // Metodo que efectivamente carga la image a Cloudinary
            // Convierte el archivo en un array de bytes, ademas de utilizar los parametros de subida definidos previamente
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), uploadParams);
            System.out.println();
            System.out.println("variable 'uploadResult': " + uploadResult);

            String imageUrl = uploadResult.get("url").toString();
            System.out.println();
            System.out.println("Variable 'imageUrl': " + imageUrl);
            System.out.println();

            return imageUrl;

        } catch (IOException exception) {
            throw new MultipartException("Error uploading image file", exception);
        }

    }

    // Extrae extension del archivo subido por el usuario
    // Verifica si existe en la lista de extensiones permitidas: retorna 'true' si existe, de lo contrario 'false'
    private boolean isExtensionAllowed(String originalFileName) {
        int lastDotIndex = originalFileName.lastIndexOf('.'); // retorna el indice del ultimo punto en el nombre del archivo
        // Si nombre del archivo no contiene puntos(-1) significa que al extension NO esta permitida (return false)
        if (lastDotIndex == -1) {
            return false;
        }

        // Obtiene la extension del archivo (lo que esta despues del punto -> lastDotIndex + 1)
        String fileExtension = originalFileName.substring(lastDotIndex + 1).toLowerCase();
        return ALLOWED_EXTENSIONS_FILE.contains(fileExtension);
    }

}
