package com.example.cars.dataImport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class EntityRegistry {
    private final Map<String, RepositoryInfo> repositoryMap = new HashMap<>();

    @Autowired
    public EntityRegistry(ApplicationContext context) {
        context.getBeansOfType(JpaRepository.class).values().forEach(repo -> {
            Class<?> entityClass = getEntityClass(repo);
            repositoryMap.put(
                    entityClass.getSimpleName().toLowerCase(),
                    new RepositoryInfo(repo, entityClass)
            );
        });
    }

    private Class<?> getEntityClass(JpaRepository repository) {
        // Use Spring Data's RepositoryMetadata to reliably get entity type
        Class<?> repositoryInterface = repository.getClass().getInterfaces()[0]; // Proxy implements the repo interface
        RepositoryMetadata metadata = new DefaultRepositoryMetadata(repositoryInterface);
        return metadata.getDomainType();
    }

    public RepositoryInfo getRepositoryInfo(String entityName) {
        return repositoryMap.get(entityName.toLowerCase());
    }

    public static class RepositoryInfo {
        private final JpaRepository repository;
        private final Class<?> entityClass;

        public RepositoryInfo(JpaRepository repository, Class<?> entityClass) {
            this.repository = repository;
            this.entityClass = entityClass;
        }

        public JpaRepository getRepository() { return repository; }
        public Class<?> getEntityClass() { return entityClass; }
    }
}