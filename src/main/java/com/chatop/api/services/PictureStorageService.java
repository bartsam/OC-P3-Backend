package com.chatop.api.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Gère le stockage physique des images uploadées sur le serveur local
 */
@Service
public class PictureStorageService {

  // Path du répertoire d'upload défini dans application.properties
  @Value("${app.upload.dir}")
  private String uploadDir;

  // Préfixe d'URL public utilisé pour construire le lien accessible via WebConfig
  @Value("${app.upload.base-url}")
  private String baseUrl;

  /**
   * Sauvegarde l'image reçue et retourne l'URL publique à enregistrer en base.
   * Lève une exception si le fichier est absent, invalide, ou en cas d'erreur.
   */
  public String storeFile(MultipartFile file) {
    // Rejette les fichiers absents ou vides
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Picture file is required");
    }

    String originalName = file.getOriginalFilename();

    // Autorise uniquement les extensions image connues
    if (originalName == null || !originalName.toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$")) {
      throw new RuntimeException("Only pictures are allowed");
    }

    try {
      // Convertit en chemin absolu et nettoie le chemin du répertoire d'upload
      Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

      // Extrait l'extension de l'image
      String extension = originalName.substring(originalName.lastIndexOf("."));

      // Nom unique avec UUID pour éviter les collisions
      String storedName = UUID.randomUUID() + extension;

      // Construit et nettoie le chemin du fichier uploadé
      Path targetFile = uploadPath.resolve(storedName).normalize();

      // Vérifie que le fichier final reste dans le dossier autorisé
      // (anti-path-traversal)
      if (!targetFile.startsWith(uploadPath)) {
        throw new RuntimeException("File path invalid");
      }

      // Crée le répertoire d'upload s'il n'existe pas encore
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      // Copie le contenu du fichier uploadé vers le chemin validé
      Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

      // Retourne l'URL publique à enregistrer en base
      return baseUrl + storedName;

    } catch (IOException e) {
      throw new RuntimeException("Fail to save the image", e);
    }
  }
}