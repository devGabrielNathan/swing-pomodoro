package br.com.dao;

import br.com.exceptions.CustomFileNotFoundException;
import br.com.exceptions.IntegrationException;
import br.com.model.Category;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.*;

public class CategoryDAO {
    private final Category category;
    // Adicionando configurações de 'log' par facilitar o desenvolvimento.
    private static final Logger logger = Logger.getLogger(CategoryDAO.class.getName());
    // caminho onde o arquivo das categorias está salvo.
    private static final String FILE = "src/main/resources/categoria.txt";
    private static final short FIELDS = 3;

    public CategoryDAO () {
        this.category = new Category();
    }
    // Obtém a categoria pelo ‘id’ acessando o arquivo categoria.txt
    public Category findById(Long id) {
        try {
            // Criando uma instância de BuffereReader para ler o arquivo
            // e criando uma instância de FileReader para dizer qual arquivo será lido
            BufferedReader reader = new BufferedReader(new FileReader(FILE));
            String line;

            // Lendo o arquivo linha por linha até o final do arquivo enquanto a linha for diferente de nulo
            while ((line = reader.readLine()) != null) {
                // Ignorando linhas vazias
                if (line.trim().isEmpty()) continue;

                // Dividindo a linha em partes usando vírgula como separador
                String[] parts = line.split(",");
                // Verificando se há pelo menos 3 partes ('id', nome, descrição)
                if (parts.length < FIELDS) continue;

                // Convertendo a primeira parte para Long ('id' da categoria)
                Long categoryId = Long.parseLong(parts[0]);

                // Verificando se o 'id' da categoria corresponde ao 'id' fornecido
                if (categoryId.equals(id)) {
                    // Salvando nome e descrição obtidos do arquivo
                    String name = parts[1];
                    String description = parts[2];

                    // Retornando uma nova instância de Category com os dados obtidos
                    // Criando uma instância com nome e descrição do arquivo e o 'id' fornecido na requisição
                    return new Category(categoryId, name, description);
                }
            }
        } catch (FileNotFoundException e) {
            // verificando se o arquivo foi passado corretamente no BuffereReader/FileReader
            logger.log(Level.INFO, "File not found: {0}.", FILE);
            logger.warning(e.getMessage());
            // modificando a exception para uma personalizada para evitar o uso do throws na assinatura do método
            // e facilitar a compreensão do erro com uma mensagem mais amigável
            throw new CustomFileNotFoundException();
        } catch (Exception e) {
            // capturando qualquer outra exception que pode ser lançada
            logger.log(Level.INFO, "Error finding category by ID: {0}.", id);
            logger.warning(e.getMessage());
            // lançando uma exception personalizada para facilitar a
            // compreensão do erro com uma mensagem mais amigável
            throw new IntegrationException();
        }
        return null;
    }

    // Obtém todas as categorias
    public List<Category> findAll() {
        try {
            // Criando uma instância de BuffereReader para ler o arquivo
            // e criando uma instância de FileReader para dizer qual arquivo será lido
            BufferedReader reader = new BufferedReader(new FileReader(FILE));
            List<Category> categories = new ArrayList<>();
            String line;

            // Lendo o arquivo linha por linha até o final do arquivo enquanto a linha for diferente de nulo
            while ((line = reader.readLine()) != null) {
                // Ignorando linhas vazias
                if (line.trim().isEmpty()) continue;

                // Dividindo a linha em partes usando vírgula como separador
                String[] parts = line.split(",");
                // Verificando se há pelo menos 3 partes ('id', nome, descrição)
                if (parts.length < FIELDS) continue;
                // Convertendo a primeira parte para Long ('id' da categoria)
                Long categoryId = Long.parseLong(parts[0]);
                categories.add(new Category(categoryId, parts[1], parts[2]));
            }
            if (categories.isEmpty()) {
                logger.log(Level.INFO, "No categories found.");
                return List.of();
            }
            return categories;
        } catch (FileNotFoundException e) {
            // verificando se o arquivo foi passado corretamente no BuffereReader/FileReader
            logger.log(Level.INFO, "File not found: {}.", FILE);
            logger.warning(e.getMessage());
            // modificando a exception para uma personalizada para evitar o uso do throws na assinatura do método
            // e facilitar a compreensão do erro com uma mensagem mais amigável
            throw new CustomFileNotFoundException();
        } catch (Exception e) {
            // capturando qualquer outra exception que pode ser lançada
            logger.log(Level.INFO, "Error searching for all categories.");
            logger.warning(e.getMessage());
            // lançando uma exception personalizada para facilitar a
            // compreensão do erro com uma mensagem mais amigável
            throw new IntegrationException();
        }
    }

    public Category create(Category request) {
        try {
            // verificando se a requisição é nula
            if (request == null) {
                logger.warning("Request is null.");
                return null;
            }
            // salvando os dados da nova categoria
            Long nextId = getNextId();
            this.category.setId(nextId);
            this.category.setName(request.getName());
            this.category.setDescription(request.getDescription());
            // salvando a nova categoria no arquivo
            saveFormat(this.category);
            return this.findById(nextId);

        } catch (Exception e) {
            // capturando qualquer outra exception que pode ser lançada
            logger.log(Level.INFO, "Error creating category.");
            logger.warning(e.getMessage());
            // lançando uma exception personalizada para facilitar a
            // compreensão do erro com uma mensagem mais amigável
            throw new IntegrationException();
        }
    }

    public void delete(Long id) {
        Category category = findById(id);

        if (Objects.isNull(category)) {
            logger.log(Level.INFO, "Category with ID: {0} not found for deletion.", id);
            return;
        }

        File inputFile = new File(FILE);
        File tempFile = new File("src/main/resources/categoria_temp.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < FIELDS) continue;
                Long categoryId = Long.parseLong(parts[0]);
                if (!categoryId.equals(id)) {
                    writer.write(line);
                    writer.newLine();
                }

            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Error deleting category with ID: {0}.", id);
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }
        if (!inputFile.delete()) {
            logger.log(Level.INFO, "Could not delete original file: {0}.", FILE);
            throw new IntegrationException();
        }
        if (!tempFile.renameTo(inputFile)) {
            logger.log(Level.INFO, "Could not rename temp file to original file name: {0}.", FILE);
            throw new IntegrationException();
        }
        logger.log(Level.INFO, "Category with ID: {0} successfully deleted.", id);
    }

    public Boolean existsCategory(Long id) {
        Category category = findById(id);
        return Objects.isNull(category);
    }

    private Long getNextId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            long max = 0L;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < FIELDS) continue;

                long id = Long.parseLong(parts[0]);
                if (id > max) max = id;
            }

            return max + 1;

        } catch (FileNotFoundException e) {
            // arquivo ainda não existe? Primeiro ID = 1
            return 1L;
        } catch (Exception e) {
            throw new IntegrationException();
        }
    }

    private void saveFormat(Category category) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            String line = category.getId() + "," +
                    category.getName() + "," +
                    category.getDescription();

            writer.write(line);
            writer.newLine();

        } catch (Exception e) {
            throw new IntegrationException();
        }
    }

    // Atualiza uma categoria existente substituindo a linha correspondente no arquivo
    public Category update(Long id, Category request) {
        Category existing = findById(id);
        if (existing == null) {
            return null;
        }

        File inputFile = new File(FILE);
        File tempFile = new File("src/main/resources/categoria_temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < FIELDS) continue;
                Long categoryId = Long.parseLong(parts[0]);
                if (categoryId.equals(id)) {
                    String newLine = id + "," + request.getName() + "," + request.getDescription();
                    writer.write(newLine);
                    writer.newLine();
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Error updating category with ID: {0}.", id);
            logger.warning(e.getMessage());
            throw new IntegrationException();
        }

        if (!inputFile.delete()) {
            logger.log(Level.INFO, "Could not delete original file: {0}.", FILE);
            throw new IntegrationException();
        }
        if (!tempFile.renameTo(inputFile)) {
            logger.log(Level.INFO, "Could not rename temp file to original file name: {0}.", FILE);
            throw new IntegrationException();
        }

        return findById(id);
    }

}
